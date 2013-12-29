package com.binar.core.setting.settingGoods;

import com.binar.core.setting.settingGoods.SettingGoodsView.SettingGoodsListener;
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

public class SettingGoodsViewImpl extends VerticalLayout implements SettingGoodsView, ClickListener, ValueChangeListener{

	
	private GeneralFunction function;
	private SettingGoodsListener listener;

	private Label title;
	private TextArea inputSatuan;
	private TextArea inputPackage;
	private Button buttonSave;
	private Button buttonReset;
	private Button buttonResetDefault;
	private Label labelError;
	private TextManipulator text;
	private SettingData settingData;

	
	public SettingGoodsViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	
	@Override
	public void init() {
		title=new Label("<h2>Pengaturan Keuangan</h2>", ContentMode.HTML);

		inputSatuan =new TextArea();
			inputSatuan.setImmediate(true);
			inputSatuan.addValueChangeListener(this);
			inputSatuan.setWidth(function.FORM_WIDTH);
		inputPackage =new TextArea();
			inputPackage.setImmediate(true);
			inputPackage.addValueChangeListener(this);
			inputPackage.setWidth(function.FORM_WIDTH);
			  
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
				addComponent(inputSatuan);
				addComponent(inputPackage);
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
		inputPackage.setValue(settingData.getSettingPackage().getSettingValue());
		inputSatuan.setValue(settingData.getSettingSatuan().getSettingValue());
		inputPackage.setCaption(settingData.getSettingPackage().getSettingName());
		inputPackage.setDescription(settingData.getSettingPackage().getSettingDescription());
		
		inputSatuan.setCaption(settingData.getSettingSatuan().getSettingName());
		inputSatuan.setDescription(settingData.getSettingSatuan().getSettingDescription());
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
		this.settingData.getSettingPackage().setSettingValue(inputPackage.getValue());
		this.settingData.getSettingSatuan().setSettingValue(inputSatuan.getValue());
		
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
	public void setListener(SettingGoodsListener listener) {
		this.listener=listener;
	}


}
