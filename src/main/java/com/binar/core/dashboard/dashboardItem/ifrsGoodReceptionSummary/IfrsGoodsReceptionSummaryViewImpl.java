package com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary;

import java.util.List;
import java.util.Map;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.locations.LegendLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.LegendRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.legend.EnhancedLegendRenderer;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;

import com.binar.entity.Goods;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class IfrsGoodsReceptionSummaryViewImpl  extends Panel implements IfrsGoodsReceptionSummaryView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan


 */
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private CssLayout layoutChart;
	private GridLayout layoutData;
	private TextManipulator text;
	private DateManipulator date;
	private String month;
	public IfrsGoodsReceptionSummaryViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
		this.date=function.getDate();
	}
	
	@Override
	public void init(String month) {
		this.month=month;
		buttonGo=new Button("Ke Halaman Penerimaan");
		buttonGo.addClickListener(this);
		
		buttonRefresh=new Button("Refresh");
		buttonRefresh.addClickListener(this);

		construct(month);
	}

	@Override
	public void construct(String month) {
		setCaption("Pengadaan dan Penerimaan Barang Bulan "+month);
		setHeight("430px");
		setWidth("500px");
		layoutData =new GridLayout(3,2);

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
				addComponent(layoutChart);	
				addComponent(layoutData);
				addComponent(layout);
			}
		});
		
	}

	private IfrsGoodsReceptionSummaryListener listener;
	public void setListener(IfrsGoodsReceptionSummaryListener listener) {
		this.listener = listener;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonGo){
			listener.buttonGo();
		}else if(event.getButton()==buttonRefresh){
			listener.updateChart();
		}
	}
	//untuk mengeset data di detail
	public void generateInformation(Map<String, Integer> data){
		int x=0;
		layoutData.removeAllComponents();
		for(Map.Entry<String, Integer> entry:data.entrySet()){
			layoutData.addComponent(new Label(entry.getKey()), 0,x);
			layoutData.addComponent(new Label(" : "), 1, x);
			layoutData.addComponent(new Label(text.intToAngka(entry.getValue())+" barang"), 2, x);
			x++;
		}		
	}	
	public void generateChart(Map<String, Integer> data){
		
		DataSeries dataSeries = new DataSeries()
		.newSeries();
		//inisialisasi data
		String[] labels=new String[data.size()];  //ngeset untuk data legend
		int x=0;
		for(Map.Entry<String, Integer> entry:data.entrySet()){
			dataSeries.add(entry.getKey(), entry.getValue());
			System.out.println("Map size "+entry.getValue()+" "+entry.getKey());
			labels[x]=entry.getKey();
			x++;

		}		
		SeriesDefaults seriesDefaults = new SeriesDefaults()
		.setRenderer(SeriesRenderers.PIE)
		.setRendererOptions(
			new PieRenderer()
				.setShowDataLabels(true));
		
		Legend legend = new Legend();
		legend.setShow(true);
		legend.setRenderer(LegendRenderers.ENHANCED);
		legend.setRendererOptions(
			new EnhancedLegendRenderer()
				.setNumberRows(2));
		legend.setPlacement(LegendPlacements.OUTSIDE_GRID);
		legend.setLabels("Rencana Kebutuhan Belum Diadakan", "Pengadaan Bulan Ini");
		
		legend.setLocation(LegendLocations.SOUTH);


	Highlighter highlighter = new Highlighter()
		.setShow(true)
		.setShowTooltip(true)
		.setTooltipAlwaysVisible(true)
		.setKeepTooltipInsideChart(true);

	Options options = new Options()
		.setSeriesDefaults(seriesDefaults)
		.setHighlighter(highlighter)
		;

	DCharts chart = new DCharts()
		.setDataSeries(dataSeries)
		.setOptions(options)
		.show();
	
	layoutChart.removeAllComponents();
	layoutChart.addComponent(chart);
	}
	
	@Override
	public void setEmptyDataView() {
		layoutData.removeAllComponents();
		layoutChart.removeAllComponents();
		layoutChart.addComponent(new Label("Data bulan "+month+" belum tersedia"));

	}
}
