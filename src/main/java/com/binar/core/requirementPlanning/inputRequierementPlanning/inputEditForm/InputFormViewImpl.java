package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class InputFormViewImpl extends FormLayout implements 
									   InputFormView, Button.ClickListener {

	/*
	 * Form dibutuhkan
	 * -> Select Obat
	 * -> jumlah kebutuhan + tombol cek forecasting  forecasting
	 * -> produsen
	 * -> distributor
	 * -> Harga (input sendiri jika belum tersedia )
	 * -> 
	 * 
	 */
	
	private ComboBox inputGoodsSelect;
	private TextField inputGoodsQuantity;
	private Button buttonCheckForecast;
	private ComboBox inputProducer;
	private ComboBox inputSupplier;
	private TextField inputPrice;
	private TextArea inputInformation;
	private Label labelSatuan;
	private Button buttonReset;
	private Button buttonSubmit;
	private Button buttonCancel;
	
	private InputFormListener listener;

	//untuk label unit di samping jumlah kebutuhan, isi sesuai jenis barang
	private Label labelUnit;
	
	public void init(){
		final String WIDTH="250px";
		//inisiasi
		inputGoodsQuantity=new TextField("Jumlah Kebutuhan"){
			{
				setWidth(WIDTH);
			}
		};
		inputGoodsSelect=new ComboBox("Nama Barang"){
			{
				setWidth(WIDTH);
				addItem("Panadol");
				addItem("Sariman");
				addItem("Anu");
				addItem("Procold");
			}
		};
		inputProducer =new ComboBox("Produsen"){
			{
				setWidth(WIDTH);
				addItem("Kimia Farma");
				addItem("Kimia Farmi");
				addItem("Sanbe");
				addItem("Tolak Angin");				
			}
		};
		inputSupplier =new ComboBox("Distributor"){
			{
				setWidth(WIDTH);
				addItem("CV Jaya Permana");
				addItem("PT Tenia Jaya");
				addItem("PT Maumamu");
				addItem("CV Jaya Permani");
			}
		};
		inputPrice= new TextField("Harga Obat"){
			{
				setWidth(WIDTH);
			}
		};
		buttonCheckForecast=new Button("Cek Peramalan");
		buttonCheckForecast.addClickListener(this);
		
		inputInformation =new TextArea("Keterangan"){
			{
				setWidth(WIDTH);
			}
		};

		labelSatuan =new Label("Satuan");
		
		buttonReset=new Button("Reset");
		buttonReset.addClickListener(this);
		
		buttonSubmit=new Button("Submit");
		buttonSubmit.addClickListener(this);
		buttonSubmit.addStyleName("primary");
		
		buttonCancel =new Button("Batal");
		buttonCancel.addClickListener(this);
		construct();
	}
	
	/* untuk menkonstruksi tampilan */
	private void construct(){
		setMargin(true);
		this.setSpacing(true);
		
		this.addComponent(inputGoodsSelect);
		this.addComponent(inputGoodsQuantity);
		this.addComponent(new GridLayout(2,1){
			{
				addComponent(buttonCheckForecast, 0, 0);
				addComponent(labelSatuan, 1,0);
				setSpacing(true);
			}
		});
		this.addComponent(inputSupplier);
		this.addComponent(inputProducer);
		this.addComponent(inputPrice);
		this.addComponent(inputInformation);
		this.addComponent(new GridLayout(3, 1){
			{
				addComponent(buttonSubmit, 0,0);
				addComponent(buttonReset, 1, 0);
				addComponent(buttonCancel, 2, 0);
				this.setMargin(true);
				this.setSpacing(true);
				
			}
		});
	}
	
	public void addListener(InputFormListener listener){
		this.listener=listener;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonCheckForecast){
			listener.buttonClick("forecast");
		}else if(event.getSource()==buttonReset){
			listener.buttonClick("reset");
		}else if(event.getSource()==buttonSubmit){
			listener.buttonClick("submit");
		}else if(event.getSource()==buttonCancel){
			listener.buttonClick("cancel");
		}
		
	}
	
}
