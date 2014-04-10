package com.binar.core.dashboard.dashboardItem.procurementSupplierRank;

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
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;
import org.dussan.vaadin.dcharts.renderers.series.animations.BarAnimation;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;
import org.joda.time.LocalDate;

import com.binar.entity.Goods;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
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

public class ProcurementSupplierRankViewImpl  extends Panel implements ProcurementSupplierRankView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan

 */

	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private CssLayout layoutChart;
	private DateManipulator date;
	public ProcurementSupplierRankViewImpl(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
	}
	
	@Override
	public void init() {
		buttonGo=new Button("Ke Halaman Daftar PBF");
		buttonGo.addClickListener(this);
		
		buttonRefresh=new Button("Refresh");
		buttonRefresh.addClickListener(this);

		construct();
	}

	@Override
	public void construct() {
		setCaption("PBF Dengan Jumlah Pembelian Terbanyak selama 3 bulan");
		
		setHeight("470px");
		setWidth("500px");
		
		final GridLayout layout=new GridLayout(2,2){
			{
				LocalDate baseDate=new LocalDate();
				LocalDate startDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMinimumValue()).minusMonths(3);
				LocalDate endDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMaximumValue());

				setSpacing(true);
				addComponent(new Label("Periode : "+date.dateToText(startDate.toDate())+" - "+date.dateToText(endDate.toDate())), 0, 0, 1, 0);
				addComponent(buttonGo, 0,1);
				addComponent(buttonRefresh, 1, 1);
			}
		};
		layoutChart=new CssLayout();
		setContent(new VerticalLayout(){
			{
				setSpacing(true);
				setMargin(true);
				addComponent(layoutChart);
				addComponent(layout);
			}
		});
		
	}

	private ProcurementManufacturRankListener listener;
	public void setListener(ProcurementManufacturRankListener listener) {
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
	
	public void generateChart(Map<Integer, String> data){
		
//		DataSeries dataSeries = new DataSeries()
//		.newSeries();
		Object[] arrayValue=new Object[data.size()];
		Object[] arrayText=new Object[data.size()];
		//inisialisasi data
		int i=0;
		for(Map.Entry<Integer, String> entry:data.entrySet()){
			arrayValue[i]=entry.getKey();
			arrayText[i]=entry.getValue();
		}		
		DataSeries dataSeries = new DataSeries();
		Series series = new Series();
		for(Map.Entry<Integer, String> entry:data.entrySet()){
			dataSeries.add(entry.getKey());
			series.addSeries(new XYseries().setLabel(entry.getValue()));
		}
	SeriesDefaults seriesDefaults = new SeriesDefaults()
		.setRenderer(SeriesRenderers.BAR).setRendererOptions(new BarRenderer().setAnimation(new BarAnimation(true, AnimationDirections.DOWN, 200)));

	Ticks ticks=new Ticks();
	ticks.add("PBF");
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
	
	layoutChart.removeAllComponents();
	layoutChart.addComponent(chart);
	}
	public void setEmptyDataView() {
		layoutChart.removeAllComponents();
		layoutChart.addComponent(new Label("Data konsumsi bulan ini belum tersedia"));
	}

}
