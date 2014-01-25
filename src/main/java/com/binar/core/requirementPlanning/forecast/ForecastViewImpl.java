package com.binar.core.requirementPlanning.forecast;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
		buttonStart.addClickListener(this);
		labelTitle =new Label("<h2>Peramalan</h2>", ContentMode.HTML);
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
	Window window;

	public void displayForm(Component content, String title){
		//menghapus semua window terlebih dahulu
		for(Window window:this.getUI().getWindows()){
			window.close();
		}
		if(window==null){
			window=new Window(title){
				{
					center();
					setClosable(false);
					setWidth("70%");
					setHeight("80%");
				}
			};

		}
		this.getUI().removeWindow(window);
		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
	}
	@Override
	public void buttonClick(ClickEvent event) {
		listener.buttonStartClick();
	}
	public void setListener(ForecastListener listener){
		this.listener=listener;
	}
	
	
}
