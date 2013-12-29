package com.binar.core.setting.settingPurchaseOrder;

import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SettingPurchaseOrderViewImpl extends VerticalLayout implements SettingPurchaseOrderView, ClickListener, ValueChangeListener{

	
	private GeneralFunction function;
	private SettingPurchaseOrderListener listener;

	private Label title;
	private TextField inputNarkotika;
	private TextField inputPsikotropika;
	private TextField inputGeneral;
	
	private Button buttonSave;
	private Button buttonReset;
	private Button buttonResetDefault;
	private Label labelError;
	private TextManipulator text;
	private SettingData settingData;

	
	public SettingPurchaseOrderViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	
	@Override
	public void init() {
		title=new Label("<h2>Pengaturan Keuangan</h2>", ContentMode.HTML);

		inputNarkotika =new TextField();
			inputNarkotika.setImmediate(true);
			inputNarkotika.addValueChangeListener(this);
			inputNarkotika.setWidth(function.FORM_WIDTH);
		inputPsikotropika =new TextField();
			inputPsikotropika.setImmediate(true);
			inputPsikotropika.addValueChangeListener(this);
			inputPsikotropika.setWidth(function.FORM_WIDTH);

		inputGeneral =new TextField();
			inputGeneral.setImmediate(true);
			inputGeneral.addValueChangeListener(this);
			inputGeneral.setWidth(function.FORM_WIDTH);

			
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
				addComponent(inputNarkotika);
				addComponent(inputPsikotropika);
				addComponent(inputGeneral);
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
		inputPsikotropika.setValue(settingData.getSettingPsikotropika().getSettingValue());
		inputNarkotika.setValue(settingData.getSettingNarkotika().getSettingValue());
		inputGeneral.setValue(settingData.getSettingGeneral().getSettingValue());
		
		inputPsikotropika.setCaption(settingData.getSettingPsikotropika().getSettingName());
		inputPsikotropika.setDescription(settingData.getSettingPsikotropika().getSettingDescription());
		
		inputNarkotika.setCaption(settingData.getSettingNarkotika().getSettingName());
		inputNarkotika.setDescription(settingData.getSettingNarkotika().getSettingDescription());

		inputGeneral.setCaption(settingData.getSettingGeneral().getSettingName());
		inputGeneral.setDescription(settingData.getSettingGeneral().getSettingDescription());

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
		this.settingData.getSettingPsikotropika().setSettingValue(inputPsikotropika.getValue());
		this.settingData.getSettingNarkotika().setSettingValue(inputNarkotika.getValue());
		this.settingData.getSettingGeneral().setSettingValue(inputGeneral.getValue());
		
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
	public void setListener(SettingPurchaseOrderListener listener) {
		this.listener=listener;
	}


}
