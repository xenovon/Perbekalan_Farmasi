package com.binar.core.requirementPlanning.forecast;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ForecastViewImpl extends VerticalLayout implements ForecastView, ClickListener{

	private GeneralFunction function;
	private Label labelGuide;
	private Label labelTitle;
	private Button buttonStart;
	private ForecastListener listener;
	public ForecastViewImpl(GeneralFunction function) {
		this.function=function;
		
	}
	@Override
	public void init() {
		labelGuide=new Label();
		labelGuide.setContentMode(ContentMode.HTML);
		labelGuide.setWidth("450px");
		labelGuide.setValue("Tab Peramalan digunakan untk memperkirakan jumlah obat "
							+ "yang perlu dibeli pada pengadaan selanjutnya."+
							"Peramalan dibuat berdasarkan data pengeluaran harian "+
							 "dan data pengadaan sebelumnya. Angka yang diperoleh dari  peramalan dapat" +
							"membantu petugas gudang farmasi dalam mengajukan rencana kebutuhan");
		
		buttonStart=new Button("Mulai buat Peramalan");
		labelTitle =new Label("<h2>Input Rencana Kebutuhan</h2>", ContentMode.HTML);
		
		construct();
	}
	@Override
	public void construct() {
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponent(labelTitle);
		this.addComponent(labelGuide);
		this.addComponent(buttonStart);
		
	}
	@Override
	public void showWindow() {
		
	}
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	public void setListener(ForecastListener listener){
		this.listener=listener;
	}
	
	
}
