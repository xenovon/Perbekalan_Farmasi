package com.binar.core.report.reportInterface.reportContent.reportReceipt;

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

import com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend.FarmationGoodsWithIncreasingTrendModel;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportContentListener;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.core.report.reportInterface.reportContent.ReportData.ReportContent;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.binar.entity.Supplier;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
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

public class ReportReceiptResultView extends VerticalLayout implements Button.ClickListener{

	private Label title;
	private  Label subTitle;
	private Component chart;
	private Table table;
	//khusus untuk report Consumption
	private ReportReceiptModel model;
	private IndexedContainer tableContainer;
	private Button buttonBack;
	private ReportContentListener listener;
	private DateManipulator date;
	
	private GeneralFunction function;
	public ReportReceiptResultView(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
	}
	
	public void init(ReportData data, ReportContentListener listener) {
		this.listener=listener;
		this.setMargin(true);
		this.setSpacing(true);
		model=new ReportReceiptModel(function);

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
				addContainerProperty("PBF", String.class,null);
				addContainerProperty("Jumlah", String.class,null);
				addContainerProperty("Faktur", String.class,null);
				addContainerProperty("Harga", String.class,null);
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
		boolean isObat=true;
		if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_OBAT.toString())){
			goodsType="Obat";
			isObat=true;
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			goodsType="Alkes & BMHP";
			isObat=false;
		}

		List<GoodsReception> dataContent;
		if(data.getPeriodeType()==PeriodeType.BY_MONTH){

			periode="Periode "+date.dateToText(data.getDateMonth());
			timecycle="Bulanan";
			dataContent=model.getReception(data.getDateMonth(), null, PeriodeType.BY_MONTH, isObat);
		}else if(data.getPeriodeType()==PeriodeType.BY_DAY){
			timecycle="Harian";
			periode="Per "+date.dateToText(data.getDate(), true);
			dataContent=model.getReception(data.getDate(), null, PeriodeType.BY_DAY, isObat);
		}else{
			timecycle="Mingguan";
			periode="Periode "+date.dateToText(data.getDateWeek()[0], true)+" - "+date.dateToText(data.getDateWeek()[1], true);
			dataContent=model.getReception(null, data.getDateWeek(), PeriodeType.BY_WEEK, isObat);				
		}
		
		
		construct(data.getReportContent(), dataContent);
	}
	
	
	private void construct(ReportContent reportContent, List<GoodsReception> data){
		this.removeAllComponents();
		this.addComponent(buttonBack);
		title.setValue("<h2 style='text-align:center'>Laporan Penerimaan "+goodsType+"</h3>");
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
	
	public void generateTabelData(List<GoodsReception> data){
		table.setVisible(true);

		tableContainer.removeAllItems();
		System.out.println(data.size());
		

		for(GoodsReception datum:data){
			Goods goods=datum.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods();
			Supplier supplier=datum.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getSupplier();

			Item item=tableContainer.addItem(datum.getIdGoodsReceipt());

			item.getItemProperty("Nama").setValue(goods.getName());
			item.getItemProperty("Satuan").setValue(goods.getUnit());
			item.getItemProperty("PBF").setValue(supplier.getSupplierName());
			item.getItemProperty("Jumlah").setValue(String.valueOf(datum.getQuantityReceived()));
			item.getItemProperty("Faktur").setValue(datum.getInvoiceItem().getInvoice().getInvoiceNumber());
			item.getItemProperty("Harga").setValue(function.getTextManipulator().doubleToRupiah(datum.getInvoiceItem().getPricePPN()));
			item.getItemProperty("Keterangan").setValue(datum.getInformation());
		}
	}
	public Map<String, Integer> filterData(List<GoodsReception> data){
		Map<String, Integer> returnValue=new HashMap<String, Integer>();
		
		//Urutkan dari besar ke kecil
		Collections.sort(data,new Comparator<GoodsReception>() {

			@Override
			public int compare(GoodsReception o1, GoodsReception o2) {

				return o2.getQuantityReceived()-o1.getQuantityReceived();
			}
		});
		int i=0;
		for(GoodsReception entry:data){
			if(i==10){
				break;
			}
			returnValue.put(entry.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods().getName(), entry.getQuantityReceived());
			
			i++;			
		}
		return returnValue;
	}
	public Component generateChartData(List<GoodsReception> data){
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
		chart.setCaption("Grafik Penerimaan Barang");
		return chart;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonBack){
			listener.backClick(ReportType.RECEIPT);
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
