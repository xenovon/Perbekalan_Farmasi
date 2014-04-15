package com.binar.core.report.reportInterface.reportContent.reportRequirement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.SeriesToggles;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.directions.AnimationDirections;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LegendRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.Series;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.legend.EnhancedLegendRenderer;
import org.dussan.vaadin.dcharts.renderers.series.BarRenderer;
import org.dussan.vaadin.dcharts.renderers.series.animations.BarAnimation;
import org.joda.time.DateTime;

import com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend.FarmationGoodsWithIncreasingTrendModel;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportContentListener;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.core.report.reportInterface.reportContent.ReportData.ReportContent;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Supplier;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class ReportRequirementResultView extends VerticalLayout implements Button.ClickListener{

	private Label title;
	private  Label subTitle;
	private Component chart;
	private Table table;
	//khusus untuk report Consumption
	private ReportRequirementModel model;
	private IndexedContainer tableContainer;
	private Button buttonBack;
	private ReportContentListener listener;
	private DateManipulator date;
	private TextManipulator text;
	
	private GeneralFunction function;
	public ReportRequirementResultView(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
		this.text=function.getTextManipulator();
	}
	
	public void init(ReportData data, ReportContentListener listener) {
		this.listener=listener;
		this.setMargin(true);
		this.setSpacing(true);
		model=new ReportRequirementModel(function);

		title=new Label();
		title.setContentMode(ContentMode.HTML);
		subTitle=new Label();
		subTitle.setContentMode(ContentMode.HTML);
		
		
		table=new Table();
		table.setSizeFull();
		table.setPageLength(10);
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Nama", String.class,null);
				addContainerProperty("Satuan",String.class,null);
				addContainerProperty("HNA+PPN", String.class,null);
				addContainerProperty("Kebutuhan", String.class,null);
				addContainerProperty("Perkiraan Jumlah Harga", String.class,null);
				addContainerProperty("Produsen", String.class,null);
				addContainerProperty("Distributor", String.class,null);
				addContainerProperty("Keterangan", String.class,null);
			}
		};				
		table.setContainerDataSource(tableContainer);
		buttonBack=new Button("Kembali");
		buttonBack.addClickListener(this);
		
		processData(data);
	}
	String goodsType; //ok
	String periode; //ok
	String timecycle; //ok
	 

	private void processData(ReportData data){

		List<ReqPlanning> dataContent = new ArrayList<ReqPlanning>();
		DateTime periodeDate=new DateTime(data.getDateMonth());
		periode="Periode "+date.dateToText(periodeDate.toDate());
		
		
		if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_OBAT.toString())){
			goodsType="Obat";
			dataContent=model.getRequirement(periodeDate, true);
			
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			dataContent=model.getRequirement(periodeDate, false);
			goodsType="Alkes & BMHP";
		}
		
		construct(data.getReportContent(), dataContent);
	}
	
	
	private void construct(ReportContent reportContent, List<ReqPlanning> data){
		this.removeAllComponents();
		this.addComponent(buttonBack);
		title.setValue("<h2 style='text-align:center'>Daftar Kebutuhan "+goodsType+"</h3>");
		subTitle.setValue("<h5 style='text-align:center'>"+periode+"</h5>");
		
		this.addComponents(title, subTitle);
		if(reportContent==ReportContent.CHART){
			chart=generateChartData(data);
			this.addComponent(chart);
		}else if(reportContent==ReportContent.TABLE){
			generateTabelData(data);
			this.addComponent(table);
		}else if(reportContent==ReportContent.TABLE_CHART){
			chart=generateChartData(data);
			generateTabelData(data);
			this.addComponent(chart);
			this.addComponent(table);			
		}
		
	}
	
	public void generateTabelData(List<ReqPlanning> data){
		table.setVisible(true);

		tableContainer.removeAllItems();
		System.out.println(data.size());
		

		for(ReqPlanning datum:data){
			Item item=tableContainer.addItem(datum.getIdReqPlanning());
			Goods goods=datum.getSupplierGoods().getGoods();
			Supplier supplier=datum.getSupplierGoods().getSupplier();
			item.getItemProperty("Nama").setValue(goods.getName());
			item.getItemProperty("Satuan").setValue(goods.getUnit());
			item.getItemProperty("HNA+PPN").setValue(text.doubleToRupiah(datum.getPriceEstimation()));
			item.getItemProperty("Kebutuhan").setValue(text.intToAngka(datum.getQuantity()));
			item.getItemProperty("Perkiraan Jumlah Harga").setValue(text.doubleToRupiah(datum.getPriceEstimationPPN()*datum.getQuantity()));
			item.getItemProperty("Produsen").setValue(datum.getSupplierGoods().getManufacturer().getManufacturerName());
			item.getItemProperty("Distributor").setValue(datum.getSupplierGoods().getSupplier().getSupplierName());
			item.getItemProperty("Keterangan").setValue(datum.getInformation());
		}
	}
	public Map<String, Integer> filterData(List<ReqPlanning> data){
		Map<String, Integer> returnValue=new HashMap<String, Integer>();
		
		//Urutkan dari besar ke kecil
		Collections.sort(data,new Comparator<ReqPlanning>() {

			@Override
			public int compare(ReqPlanning o1, ReqPlanning o2) {

				return o2.getQuantity()-o1.getQuantity();
			}
		});
		int i=0;
		for(ReqPlanning entry:data){
			if(i==10){
				break;
			}
			returnValue.put(entry.getSupplierGoods().getGoods().getName(), entry.getQuantity());
			
			i++;			
		}
		return returnValue;
	}
	public Component generateChartData(List<ReqPlanning> data){
		if(data.size()==0){
			return new Label("Data Kosong");
		}
		
		Map<String, Integer> filtered=filterData(data);

		DataSeries dataSeries = new DataSeries();
		Series series = new Series();
		for(Map.Entry<String, Integer> entry:filtered.entrySet()){
			dataSeries.add(entry.getValue());
			series.addSeries(new XYseries().setLabel(entry.getKey()));
		}
		SeriesDefaults seriesDefaults = new SeriesDefaults()
			.setRenderer(SeriesRenderers.BAR).setRendererOptions(new BarRenderer().setAnimation(new BarAnimation(true, AnimationDirections.DOWN, 200)));
	
		Ticks ticks=new Ticks();
		ticks.add("Nama Barang");
		Axes axes = new Axes()
			.addAxis(
				new XYaxis()
					.setRenderer(AxisRenderers.CATEGORY)
					.setTicks(ticks)
		);
	
		Highlighter highlighter = new Highlighter()
		.setShow(true)
		.setShowTooltip(true)
		.setTooltipAlwaysVisible(true)
		.setKeepTooltipInsideChart(true)
		.setTooltipLocation(TooltipLocations.NORTH)
		.setTooltipAxes(TooltipAxes.Y_BAR)
		.setTooltipFormatString("%d obat");
	
		Legend legend = new Legend()
		.setShow(true)
		.setRenderer(LegendRenderers.ENHANCED)
		.setRendererOptions(
			new EnhancedLegendRenderer()
				.setSeriesToggle(SeriesToggles.SLOW)
				.setSeriesToggleReplot(true))
		.setPlacement(LegendPlacements.OUTSIDE_GRID);
	
		Options options = new Options()
			.setSeriesDefaults(seriesDefaults)
			.setAxes(axes)
			.setSeries(series)
			.setLegend(legend)
			.setHighlighter(highlighter);
	
		DCharts chart = new DCharts()
			.setDataSeries(dataSeries)
			.setOptions(options)
			.show();
		chart.setCaption("Grafik Jumlah Kebutuhan");
		return chart;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonBack){
			listener.backClick(ReportType.REQUIREMENT);
		}
	}
	
	public static void main(String[] args) {
		HashMap<String, Integer> data=new HashMap<String, Integer>();
		
		data.put("a", 1);
		data.put("5", 22);
		data.put("7", 66);
		data.put("1", 66);
		data.put("hb", 350);
		data.put("as", 210);
		data.put("asd", 34);
		data.put("da", 176);
		
		System.out.println(data);
		data=(HashMap<String, Integer>) FarmationGoodsWithIncreasingTrendModel.MapUtil.sortByValue(data);
		System.out.println(data);
		
		List<Integer> integer=new ArrayList<Integer>();
		
		integer.add(5);
		integer.add(51);
		integer.add(56);
		integer.add(50);
		integer.add(25);
		System.out.println(integer.toString());
		Collections.sort(integer,new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {

				return o2-o1;
			}
		});
		System.out.println(integer.toString());
	
	}
	
	
	
	
}
