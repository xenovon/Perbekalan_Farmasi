package com.binar.core.report.reportInterface.reportContent.reportConsumption;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class ReportConsumptionResultView extends VerticalLayout implements Button.ClickListener{

	private Label title;
	private  Label subTitle;
	private DCharts chart;
	private Table table;
	//khusus untuk report Consumption
	private ReportConsumptionModel model;
	private IndexedContainer tableContainer;
	private Button buttonBack;
	private ReportContentListener listener;
	private DateManipulator date;
	
	private GeneralFunction function;
	public ReportConsumptionResultView(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
	}
	
	public void init(ReportData data, ReportContentListener listener) {
		this.listener=listener;
		this.setMargin(true);
		this.setSpacing(true);
		model=new ReportConsumptionModel(function);

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
				addContainerProperty("Jumlah Pengeluaran", Integer.class,null);
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
		if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_OBAT.toString())){
			goodsType="Obat";
			
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			goodsType="Alkes & BMHP";
		}

		Map<Goods, Integer> dataContent;
		if(data.getPeriodeType()==PeriodeType.BY_MONTH){

			periode="Periode "+date.dateToText(data.getDateMonth());
			timecycle="Bulanan";
			dataContent=model.getConsumptionDataMonthly(data.getDateMonth(), null, true);
		}else if(data.getPeriodeType()==PeriodeType.BY_DAY){
			timecycle="Harian";
			periode="Per "+date.dateToText(data.getDate(), true);
			dataContent=model.getConsumptionDataDaily(data.getDate(), true);
		}else{
			timecycle="Mingguan";
			periode="Periode "+date.dateToText(data.getDateWeek()[0], true)+" - "+date.dateToText(data.getDateWeek()[1], true);
			dataContent=model.getConsumptionDataMonthly(null, data.getDateWeek(), true);
		}
		
		
		construct(data.getReportContent(), dataContent);
	}
	
	
	private void construct(ReportContent reportContent, Map<Goods, Integer> data){
		this.removeAllComponents();
		this.addComponent(buttonBack);
		title.setValue("<h2 style='text-align:center'>Laporan Pengeluaran "+goodsType+"</h3>");
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
	
	public void generateTabelData(Map<Goods, Integer> data){
		table.setVisible(true);

		tableContainer.removeAllItems();
		System.out.println(data.size());

		for(Map.Entry<Goods, Integer> datum:data.entrySet()){
			Item item=tableContainer.addItem(datum.getKey().getIdGoods());
			item.getItemProperty("Nama").setValue(datum.getKey().getName());
			item.getItemProperty("Satuan").setValue(datum.getKey().getUnit());
			item.getItemProperty("Jumlah Pengeluaran").setValue(datum.getValue());
		}
	}
	public Map<String, Integer> filterData(Map<Goods, Integer> data){
		Map<String, Integer> returnValue=new HashMap<String, Integer>();
		
		//Urutkan dari besar ke kecil
		data=FarmationGoodsWithIncreasingTrendModel.MapUtil.sortByValue(data);
		int i=0;
		for(Map.Entry<Goods, Integer> entry:data.entrySet()){
			if(i==10){
				break;
			}
			returnValue.put(entry.getKey().getName(), entry.getValue());
			i++;			
		}
		return returnValue;
	}
	public DCharts generateChartData(Map<Goods, Integer> data){
		Map<String, Integer> filtered=filterData(data);
		Object[] arrayValue=new Object[data.size()];
		Object[] arrayText=new Object[data.size()];
		//inisialisasi data
		int i=0;
		for(Map.Entry<String,Integer> entry:filtered.entrySet()){
			arrayValue[i]=entry.getValue();
			arrayText[i]=entry.getKey();
		}		
		DataSeries dataSeries = new DataSeries();
		Series series = new Series();
		for(Map.Entry<Goods, Integer> entry:data.entrySet()){
			dataSeries.add(entry.getValue());
			series.addSeries(new XYseries().setLabel(entry.getKey().getName()));
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
		return chart;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonBack){
			listener.backClick(ReportType.CONSUMPTION);
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
	}
	
	
}
