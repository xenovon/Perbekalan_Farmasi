package com.binar.core.report.reportInterface.reportContent.reportProcurement;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.SeriesToggles;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.directions.AnimationDirections;
import org.dussan.vaadin.dcharts.metadata.locations.LegendLocations;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LabelRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LegendRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.AxesDefaults;
import org.dussan.vaadin.dcharts.options.Cursor;
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
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
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

public class ReportProcurementResultView extends VerticalLayout implements Button.ClickListener{

	private Label title;
	private  Label subTitle;
	private Component chart;
	private Table table;
	//khusus untuk report Consumption
	private ReportProcurementModel model;
	private IndexedContainer tableContainer;
	private Button buttonBack;
	private ReportContentListener listener;
	private DateManipulator date;
	
	private GeneralFunction function;
	public ReportProcurementResultView(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
	}
	
	public void init(ReportData data, ReportContentListener listener) {
		this.listener=listener;
		this.setMargin(true);
		this.setSpacing(true);
		model=new ReportProcurementModel(function);

		title=new Label();
		title.setContentMode(ContentMode.HTML);
		subTitle=new Label();
		subTitle.setContentMode(ContentMode.HTML);
		
		
		table=new Table();
		table.setSizeFull();
		table.setPageLength(10);
		table.setSortEnabled(true);
		table.setImmediate(true);
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("No", String.class, null);
				addContainerProperty("Nomor SP", String.class,null);
				addContainerProperty("Tanggal",String.class,null);
				addContainerProperty("Pemasok", String.class,null);
				addContainerProperty("Produsen", String.class,null);
				addContainerProperty("Nama Barang", String.class,null);
				addContainerProperty("Asuransi", String.class,null);
				addContainerProperty("Jumlah", String.class,null);
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
		Map<PurchaseOrder, List<PurchaseOrderItem>> dataContent = new HashMap<PurchaseOrder, List<PurchaseOrderItem>>(); 
		if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_OBAT.toString())){
			goodsType="Obat";
			dataContent=model.getPurchaseOrder(data.getDateMonth(), true);
			isObat=true;
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			dataContent=model.getPurchaseOrder(data.getDateMonth(), false);
			goodsType="Alkes & BMHP";
			isObat=false;
		}
		periode="Periode "+date.dateToText(data.getDateMonth());
		

		//		
		
		construct(data.getReportContent(), dataContent, isObat, data.getDateMonth());
	}
	
	
	private void construct(ReportContent reportContent, Map<PurchaseOrder, List<PurchaseOrderItem>> data, boolean isObat, Date dateMonth){
		this.removeAllComponents();
		this.addComponent(buttonBack);
		title.setValue("<h2 style='text-align:center'>Laporan Pengadaan "+goodsType+"</h3>");
		subTitle.setValue("<h5 style='text-align:center'>"+periode+"</h5>");
		
		this.addComponents(title, subTitle);
		if(reportContent==ReportContent.CHART){
			chart=generateChartData(model.getChartPurchaseOrderData(dateMonth, isObat));
			this.addComponent(chart);
		}else if(reportContent==ReportContent.TABLE){
			generateTabelData(data);
			this.addComponent(table);
		}else if(reportContent==ReportContent.TABLE_CHART){
			chart=generateChartData(model.getChartPurchaseOrderData(dateMonth, isObat));
			generateTabelData(data);
			this.addComponent(chart);
			this.addComponent(table);			
		}
		
	}
	
	public void generateTabelData(Map<PurchaseOrder, List<PurchaseOrderItem>> data){
		table.setVisible(true);

		tableContainer.removeAllItems();
		System.out.println(data.size());		
		String returnValue="";		
		int i=0;

		for(Map.Entry<PurchaseOrder, List<PurchaseOrderItem>> entry:data.entrySet()){
			i++;
			if(entry.getValue().size()!=0){
				Item item=tableContainer.addItem(entry.getKey().getIdPurchaseOrder()+"");
				item.getItemProperty("No").setValue(i+"");
				item.getItemProperty("Nomor SP").setValue(entry.getKey().getPurchaseOrderNumber()+"");
				item.getItemProperty("Tanggal").setValue(date.dateToText(entry.getKey().getDate(), true));
				item.getItemProperty("Pemasok").setValue(entry.getValue().get(0).getSupplierGoods().getSupplier().getSupplierName());
				item.getItemProperty("Produsen").setValue(entry.getValue().get(0).getSupplierGoods().getManufacturer().getManufacturerName());
				
				int x=0;
				for(PurchaseOrderItem purchaseOrderItem:entry.getValue()){
					if(x==0){
						item.getItemProperty("Nama Barang").setValue(purchaseOrderItem.getSupplierGoods().getGoods().getName());
						item.getItemProperty("Asuransi").setValue(purchaseOrderItem.getSupplierGoods().getGoods().getInsurance().getName());
						item.getItemProperty("Jumlah").setValue(function.getTextManipulator().intToAngka(purchaseOrderItem.getQuantity()));

					}else{
						item=tableContainer.addItem(entry.getKey().getIdPurchaseOrder()+"-"+purchaseOrderItem.getIdPurchaseOrderItem());
						item.getItemProperty("Nama Barang").setValue(purchaseOrderItem.getSupplierGoods().getGoods().getName());
						item.getItemProperty("Asuransi").setValue(purchaseOrderItem.getSupplierGoods().getGoods().getInsurance().getName());
						item.getItemProperty("Jumlah").setValue(function.getTextManipulator().intToAngka(purchaseOrderItem.getQuantity()));
					}
					x++;
				}
			}
			
		}
		
	}
	public Map<String, Integer> filterData(Map<PurchaseOrder, List<PurchaseOrderItem>> data){
		Map<String, Integer> returnValue=new HashMap<String, Integer>();
		
		return returnValue;
	}
	public Component generateChartData(SortedMap<Date, Integer> data){

		if(data.size()==0){
			return new Label("Data Kosong");
		}

		DataSeries series=new DataSeries();
		series.newSeries();
		int i=1;

		for(Map.Entry<Date, Integer> entry:data.entrySet()){
			series.add(i,entry.getValue());
			i++;
		}
		

		AxesDefaults axesDefaults = new AxesDefaults()
		.setLabelRenderer(LabelRenderers.CANVAS);		
		
		Axes axes = new Axes()
		.addAxis(
			new XYaxis()
				.setLabel("Hari")
				.setPad(0))
		.addAxis(new XYaxis(XYaxes.Y)
				.setLabel("Jumlah Pengadaan"));
		Highlighter highlighter = new Highlighter()
		.setShow(true)
		.setSizeAdjust(10)
		.setTooltipLocation(TooltipLocations.NORTH)
		.setTooltipAxes(TooltipAxes.Y)
		.setTooltipFormatString("%.2f")
		.setUseAxesFormatters(false);

		Cursor cursor = new Cursor()
		.setShow(true);

		
		Options options = new Options()
		.setAxesDefaults(axesDefaults)
		.setAxes(axes)
		.setHighlighter(highlighter)
		.setCursor(cursor)
		.setAnimate(true);
		
		DCharts chart=new DCharts().setOptions(options).setDataSeries(series).show();
		chart.setCaption("Grafik Jumlah Pengadaan "+goodsType+" Harian");
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
