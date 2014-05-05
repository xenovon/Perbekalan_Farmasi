package com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend;

import java.util.Calendar;
import java.util.Iterator;
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
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;
import org.dussan.vaadin.dcharts.renderers.series.animations.BarAnimation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;

import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class FarmationGoodsWithIncreasingTrendViewImpl  extends Panel implements FarmationGoodsWithIncreasingTrendView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan

 */
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private CssLayout layoutChart;
	private ComboBox selectYearStart;
	private ComboBox selectMonthStart;
	private ComboBox selectYearEnd;
	private ComboBox selectMonthEnd;
	
	private Label labelDateStart;
	private Label labelDateEnd;
	private final String FORM_WIDTH="100px";
	private Button buttonSubmit;
	public FarmationGoodsWithIncreasingTrendViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	@Override
	public void init(String month) {
		buttonGo=new Button("Ke Halaman Pengeluaran Harian");
		buttonGo.addClickListener(this);
		
		buttonRefresh=new Button("Refresh");
		buttonRefresh.addClickListener(this);

		
		List<String> yearList=function.getListFactory().createYearList(6, 2, false);
		DateTime dateTime=new DateTime().minusMonths(6);		
		selectYearStart = new ComboBox("", yearList);
		selectYearStart.setImmediate(true);
		selectYearStart.setNullSelectionAllowed(false);
		selectYearStart.setTextInputAllowed(false);
		selectYearStart.setWidth(FORM_WIDTH);
		selectYearStart.addStyleName("non-caption-form");
		selectYearStart.setValue(String.valueOf(dateTime.getYear()));

		List<String> monthList=function.getListFactory().createMonthList();
		selectMonthStart =new ComboBox("", monthList);
		selectMonthStart.setImmediate(true);
		selectMonthStart.setValue(monthList.get(dateTime.getMonthOfYear()-1));
		selectMonthStart.setNullSelectionAllowed(false);
		selectMonthStart.setTextInputAllowed(false);
		selectMonthStart.setWidth(FORM_WIDTH);
		selectMonthStart.addStyleName("non-caption-form");
		
		List<String> yearList2=function.getListFactory().createYearList(6, 2, false);
		selectYearEnd = new ComboBox("", yearList2);
		selectYearEnd.setImmediate(true);
		selectYearEnd.setNullSelectionAllowed(false);
		selectYearEnd.setTextInputAllowed(false);
		selectYearEnd.setWidth(FORM_WIDTH);
		selectYearEnd.addStyleName("non-caption-form");
		selectYearEnd.setValue(yearList.get(2));

		System.out.println(selectYearEnd.getItemIds().toString());
		System.out.println("Date now "+dateTime.getYear());
		List<String> monthList2=function.getListFactory().createMonthList();
		selectMonthEnd =new ComboBox("", monthList2);
		selectMonthEnd.setImmediate(true);
		selectMonthEnd.setNullSelectionAllowed(false);
		selectMonthEnd.setTextInputAllowed(false);
		selectMonthEnd.setWidth(FORM_WIDTH);		
		selectMonthEnd.addStyleName("non-caption-form");
		selectMonthEnd.setValue(monthList.get(Calendar.getInstance().get(Calendar.MONTH)));

		buttonSubmit =new Button("Submit");
		buttonSubmit.addClickListener(this);
		construct(month);
	}

	@Override
	public void construct(String month) {
		setCaption("Barang Dengan Trend Naik "+month);
		setHeight("600px");
		setWidth("890px");
		final GridLayout submitLayout=new GridLayout(7, 1){
			{
				setSpacing(true);
				setMargin(true);
				addComponent(new Label("Rentang Awal"));
				addComponent(selectMonthStart);
				addComponent(selectYearStart);
				addComponent(new Label("Rentang Akhir"));
				addComponent(selectMonthEnd);
				addComponent(selectYearEnd);
				addComponent(buttonSubmit);
			}
		};
		final GridLayout layout=new GridLayout(2,1){
			{
				setSpacing(true);
				addComponent(buttonGo, 0,0);
				addComponent(buttonRefresh, 1, 0);
			}
		};
		layoutChart=new CssLayout();
		setContent(new VerticalLayout(){
			{
				setSpacing(true);
				setMargin(true);
				addComponent(submitLayout);
				addComponent(layoutChart);
				addComponent(layout);
			}
		});
		
	}

	private FarmationGoodsWithIncreasingTrendListener listener;
	public void setListener(FarmationGoodsWithIncreasingTrendListener listener) {
		this.listener = listener;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonGo){
			listener.buttonGo();
		}else if(event.getButton()==buttonRefresh){
			listener.buttonSubmit();
		}else if(event.getButton()==buttonSubmit){
			listener.buttonSubmit();
		}
	}
	
	public void generateChart(Map<String, List<Integer>> data){
		if(data.size()!=0){
			DCharts chart=new DCharts().setOptions(generateOptions(data)).setDataSeries(generateDataSeries(data));
			chart.setCaption("Barang Dengan Tren Naik");
			chart.show();

			layoutChart.removeAllComponents();
			layoutChart.addComponent(chart);			
		}else{
			layoutChart.addComponent(new Label("Tidak Ada Barang Dengan Trend Naik"));
		}
	}
	
	private DataSeries generateDataSeries(Map<String, List<Integer>> data){
		DataSeries series=new DataSeries();
		for(Map.Entry<String, List<Integer>> entry:data.entrySet()){
			series.newSeries();
			int i=1;
			for(Integer value:entry.getValue()){
				series.add(i, value);
				i++;
			}
		}
		return series;
	}
	
	private Options generateOptions(Map<String, List<Integer>> data){
		AxesDefaults axesDefaults = new AxesDefaults()
		.setLabelRenderer(LabelRenderers.CANVAS);		
		
		Axes axes = new Axes()
		.addAxis(
			new XYaxis()
				.setLabel("Bulan")
				.setPad(0))
		.addAxis(new XYaxis(XYaxes.Y)
				.setLabel("Jumlah Konsumsi"));
		Highlighter highlighter = new Highlighter()
		.setShow(true)
		.setSizeAdjust(10)
		.setTooltipLocation(TooltipLocations.NORTH)
		.setTooltipAxes(TooltipAxes.Y)
		.setTooltipFormatString("%.2f")
		.setUseAxesFormatters(false);

		Cursor cursor = new Cursor()
		.setShow(true);
		Legend legend = new Legend();
		legend.setShow(true);
		legend.setRenderer(LegendRenderers.ENHANCED);
		legend.setRendererOptions(
			new EnhancedLegendRenderer()
				.setNumberRows(1));
		legend.setPlacement(LegendPlacements.OUTSIDE_GRID);
		String[] labelData=new String[data.size()];

		int i=0;
		Iterator<String> dataIt=data.keySet().iterator();
		while(dataIt.hasNext()){
			labelData[i]=dataIt.next();
			System.out.println(labelData[i]);
			i++;
		}
		legend.setLabels(labelData);			

		
		if(data.size()==1){
			String label1=labelData[0].toString();
			legend.setLabels(label1);			
			
		}else if(data.size()==2){
			String label1=labelData[0].toString();
			String label2=labelData[1].toString();
			legend.setLabels(label1,label2);			
			
		}else if(data.size()==3){
			String label1=labelData[0].toString();
			String label2=labelData[1].toString();
			String label3=labelData[2].toString();
			legend.setLabels(label1,label2,label3);			
			
		}else if(data.size()==4){
			String label1=labelData[0].toString();
			String label2=labelData[1].toString();
			String label3=labelData[2].toString();
			String label4=labelData[3].toString();
			legend.setLabels(label1,label2,label3,label4);			
			
		}else{
			String label1=labelData[0].toString();
			String label2=labelData[1].toString();
			String label3=labelData[2].toString();
			String label4=labelData[3].toString();
			String label5=labelData[4].toString();
			legend.setLabels(label1,label2,label3,label4,label5);						
		}

		System.out.println("Label : "+legend.getLabels().length+" "+legend.getLabels()[1]);
		legend.setLocation(LegendLocations.SOUTH);
		
		Options options = new Options()
		.setAxesDefaults(axesDefaults)
		.setAxes(axes)
		.setHighlighter(highlighter)
		.setCursor(cursor)
		.setAnimate(true)
		.setLegend(legend);
		return options;
	}
		

	@Override
	public void setEmptyDataView() {
		layoutChart.removeAllComponents();
		layoutChart.addComponent(new Label("Data konsumsi bulan ini belum tersedia"));
	}
	public String[] getSubmitData(){
		String[] returnValue=new String[2];
		returnValue[0]=selectMonthStart.getValue()+"-"+selectYearStart.getValue();
		returnValue[1]=selectMonthEnd.getValue()+"-"+selectYearEnd.getValue();
		System.out.println(returnValue[0].toString()+"---"+ returnValue[1].toString()+" Submit data");
		return returnValue;
	}
	public static void main(String[] args) {
		System.out.println(new DateTime().getMonthOfYear());
		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
	}
}
