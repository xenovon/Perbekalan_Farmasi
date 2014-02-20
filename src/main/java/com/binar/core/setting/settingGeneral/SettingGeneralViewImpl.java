package com.binar.core.setting.settingGeneral;

import com.binar.core.setting.settingGeneral.SettingGeneralView.SettingGeneralListener;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SettingGeneralViewImpl extends VerticalLayout implements SettingGeneralView, ClickListener, ValueChangeListener{

	
	private GeneralFunction function;
	private SettingGeneralListener listener;

	private Label title;
	private TextField inputPhone;
	private TextArea inputAddress;
	private TextField inputCity;
	private TextArea inputInstalasi;
	private TextField inputApotek;
	private Button buttonSave;
	private Button buttonReset;
	private Button buttonResetDefault;
	private Label labelError;
	private TextManipulator text;
	private SettingData settingData;

	
	public SettingGeneralViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	
	@Override
	public void init() {
		title=new Label("<h2>Pengaturan Umum</h2>", ContentMode.HTML);

		inputPhone =new TextField();
			inputPhone.setImmediate(true);
			inputPhone.addValueChangeListener(this);
			inputPhone.setWidth(function.FORM_WIDTH);
		inputAddress =new TextArea();
			inputAddress.setImmediate(true);
			inputAddress.addValueChangeListener(this);
			inputAddress.setWidth(function.FORM_WIDTH);

		inputCity =new TextField();
			inputCity.setImmediate(true);
			inputCity.addValueChangeListener(this);
			inputCity.setWidth(function.FORM_WIDTH);

		inputInstalasi =new TextArea();
			inputInstalasi.setImmediate(true);
			inputInstalasi.addValueChangeListener(this);
			inputInstalasi.setWidth(function.FORM_WIDTH);
			inputInstalasi.setDescription(" Instalasi rumah sakit yang menggunakan perbekalan farmasi");
		inputApotek =new TextField();
			inputApotek.setImmediate(true);
			inputApotek.addValueChangeListener(this);
			inputApotek.setWidth(function.FORM_WIDTH);
			
			  
		buttonSave =new Button("Simpan");
			buttonSave.addClickListener(this);
		buttonReset =new Button("Reset");
			buttonReset.addClickListener(this);

		buttonResetDefault =new Button("Kembali Ke Pengaturan Awal");
			buttonResetDefault.addClickListener(this);
		labelError=new Label(){
				{
					setVisible(false);
					addStyleName("form-error");
					setContentMode(ContentMode.HTML);
				}
			};
			construct();
	}

	@Override
	public void construct() {
		this.addComponent(title);
		this.addComponent(new FormLayout(){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(inputPhone);
				addComponent(inputAddress);
				addComponent(inputApotek);
				addComponent(inputCity);
				addComponent(inputInstalasi);
				addComponent(new Label("<div style='max-width:518px'>Isi dengan format : singkatan instalasi=nama instalasi (tanpa spasi). "
						+ "Pisahkan satu instalasi dengan instalasi lain dengan tanda koma (,). "
						+ "<br>Contoh : <i>kepodang=rawat inap kepodang, ibs=instalasi bedah sentral, farmasi=instalasi farmasi</i></div>",

						ContentMode.HTML));
				addComponent(labelError);
				addComponent(new GridLayout(3, 1){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(buttonSave, 0,0);
						addComponent(buttonReset, 1,0);
						addComponent(buttonResetDefault, 2, 0);
					}
				});
			}
		});
	}

	@Override
	public void setData(SettingData data) {
		this.settingData = data;
		inputPhone.setValue(data.getSettingPhone().getSettingValue());
		inputAddress.setValue(data.getSettingAddress().getSettingValue());
		inputCity.setValue(data.getSettingCity().getSettingValue());
		inputInstalasi.setValue(data.getSettingInstalasi().getSettingValue());
		inputApotek.setValue(data.getSettingApotek().getSettingValue());
		
		inputPhone.setCaption(data.getSettingPhone().getSettingName());
		inputAddress.setCaption(data.getSettingAddress().getSettingName());
		inputCity.setCaption(data.getSettingCity().getSettingName());
		inputInstalasi.setCaption(data.getSettingInstalasi().getSettingName());
		inputApotek.setCaption(data.getSettingApotek().getSettingName());
		
		inputPhone.setDescription(data.getSettingPhone().getSettingDescription());
		inputAddress.setDescription(data.getSettingAddress().getSettingDescription());
		inputCity.setDescription(data.getSettingCity().getSettingDescription());
		inputInstalasi.setDescription(data.getSettingInstalasi().getSettingDescription());
		inputApotek.setDescription(data.getSettingApotek().getSettingDescription());
		
		
	}

	@Override
	public void showError(String content) {
		labelError.setValue(content);
		labelError.setVisible(true);
	}

	@Override
	public void hideError() {
		labelError.setVisible(false);
	}
	
	@Override
	public SettingData getData() {
		this.settingData.getSettingPhone().setSettingValue(inputPhone.getValue());
		this.settingData.getSettingAddress().setSettingValue(inputAddress.getValue());
		this.settingData.getSettingCity().setSettingValue(inputCity.getValue());
		this.settingData.getSettingInstalasi().setSettingValue(inputInstalasi.getValue());
		this.settingData.getSettingApotek().setSettingValue(inputApotek.getValue());

		return this.settingData;
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		listener.valueChange();
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonReset){
			listener.resetChange();
		}else if(event.getButton()==buttonResetDefault){
			listener.resetToDefault();
		}else if(event.getButton()==buttonSave){
			listener.buttonSave();
		}
	}
	@Override
	public void setListener(SettingGeneralListener listener) {
		this.listener=listener;
	}


}
