package com.binar.core.dashboard.dashboardItem.procurementSupplierRank;

import java.util.List;
import java.util.Map;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;
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
		buttonGo=new Button("Ke Halaman Daftar Distributor");
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
		
		DataSeries dataSeries = new DataSeries()
		.newSeries();

		//inisialisasi data
		for(Map.Entry<Integer, String> entry:data.entrySet()){
			dataSeries.add(entry.getValue(), entry.getKey());
		}		
		SeriesDefaults seriesDefaults = new SeriesDefaults()
		.setRenderer(SeriesRenderers.BAR)
		.setRendererOptions(
			new PieRenderer()
				.setShowDataLabels(true));


	Highlighter highlighter = new Highlighter()
		.setShow(true)
		.setShowTooltip(true)
		.setTooltipAlwaysVisible(true)
		.setKeepTooltipInsideChart(true);

	Options options = new Options()
		.setSeriesDefaults(seriesDefaults)
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
