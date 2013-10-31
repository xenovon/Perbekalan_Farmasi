package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class InputFormViewImpl extends FormLayout implements InputFormView {

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
	//untuk label unit di samping jumlah kebutuhan, isi sesuai jenis barang
	private Label labelUnit;
	
	public void init(){
		//inisiasi
		inputGoodsQuantity=new TextField("Jumlah Kebutuhan");
		inputGoodsSelect=new ComboBox("Nama Barang");
		inputProducer =new ComboBox("Produsen");
		inputSupplier =new ComboBox("Distributor");
		inputPrice= new TextField("Harga Obat");
		
		
		inputGoodsSelect.addItem("Panadol");
		inputGoodsSelect.addItem("Sariman");
		inputGoodsSelect.addItem("Anu");
		inputGoodsSelect.addItem("Procold");
		
		inputProducer.addItem("Kimia Farma");
		inputProducer.addItem("Kimia Farmi");
		inputProducer.addItem("Sanbe");
		inputProducer.addItem("Tolak Angin");
		
		inputSupplier.addItem("CV Jaya Permana");
		inputSupplier.addItem("PT Tenia Jaya");
		inputSupplier.addItem("PT Maumamu");
		inputSupplier.addItem("CV Jaya Permani");
		
		construct();
		
	}
	/* untuk menkonstruksi tampilan */
	public void construct(){
		setMargin(true);
		this.setSpacing(true);
		
		this.addComponent(inputGoodsSelect);
		this.addComponent(inputGoodsQuantity);
		this.addComponent(inputSupplier);
		this.addComponent(inputProducer);
		this.addComponent(inputPrice);
	}
	
	
}
