package com.binar.core.setting.settingFinance;

import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class SettingFinanceViewImpl extends VerticalLayout implements SettingFinanceView, ClickListener, ValueChangeListener{

	
	private GeneralFunction function;
	private SettingFinanceListener listener;

	private Label title;
	private TextField inputPPN;
	private TextField inputMargin;
	private Button buttonSave;
	private Button buttonReset;
	private Button buttonResetDefault;
	private Label labelError;
	private TextManipulator text;
	private SettingData settingData;

	
	public SettingFinanceViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	
	@Override
	public void init() {
		title=new Label("<h2>Pengaturan Keuangan</h2>", ContentMode.HTML);

		inputPPN =new TextField();
			inputPPN.setImmediate(true);
			inputPPN.addValueChangeListener(this);
			inputPPN.setWidth(function.FORM_WIDTH);
		inputMargin =new TextField();
			inputMargin.setImmediate(true);
			inputMargin.addValueChangeListener(this);
			inputMargin.setWidth(function.FORM_WIDTH);
			  
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
				addComponent(inputPPN);
				addComponent(inputMargin);
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
		inputMargin.setValue(settingData.getSettingMargin().getSettingValue());
		inputPPN.setValue(settingData.getSettingPPN().getSettingValue());
		inputMargin.setCaption(settingData.getSettingMargin().getSettingName());
		inputMargin.setDescription(settingData.getSettingMargin().getSettingDescription());
		
		inputPPN.setCaption(settingData.getSettingPPN().getSettingName());
		inputPPN.setDescription(settingData.getSettingPPN().getSettingDescription());
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
		this.settingData.getSettingMargin().setSettingValue(inputMargin.getValue());
		this.settingData.getSettingPPN().setSettingValue(inputPPN.getValue());
		
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
	public void setListener(SettingFinanceListener listener) {
		this.listener=listener;
	}


}
