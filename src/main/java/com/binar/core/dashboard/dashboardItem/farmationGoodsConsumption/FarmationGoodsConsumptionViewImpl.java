package com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption;

import java.util.List;
import java.util.Map;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;

import com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption.FarmationGoodsConsumptionView.FarmationGoodsConsumptionListener;
import com.binar.entity.Goods;
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

public class FarmationGoodsConsumptionViewImpl  extends Panel implements FarmationGoodsConsumptionView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan

 */
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private CssLayout layoutChart;
	public FarmationGoodsConsumptionViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	@Override
	public void init(String month) {
		buttonGo=new Button("Ke Halaman Pengeluaran Harian");
		buttonGo.addClickListener(this);
		
		buttonRefresh=new Button("Refresh");
		buttonRefresh.addClickListener(this);

		construct(month);
	}

	@Override
	public void construct(String month) {
		setCaption("Konsumsi Barang Bulan "+month);
		setHeight("430px");
		setWidth("500px");
		
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
				addComponent(layout);
			}
		});
		
	}

	private FarmationGoodsConsumptionListener listener;
	public void setListener(FarmationGoodsConsumptionListener listener) {
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
		.setRenderer(SeriesRenderers.PIE)
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
	
	@Override
	public void setEmptyDataView() {
		layoutChart.removeAllComponents();
		layoutChart.addComponent(new Label("Data konsumsi bulan ini belum tersedia"));
	}
}
