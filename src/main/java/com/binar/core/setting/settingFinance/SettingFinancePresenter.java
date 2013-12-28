package com.binar.core.setting.settingFinance;

import java.util.Collection;
import java.util.List;

import com.binar.core.setting.settingFinance.SettingFinanceView.SettingData;
import com.binar.core.setting.settingFinance.SettingFinanceView.SettingFinanceListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window;

public class SettingFinancePresenter implements SettingFinanceListener{
	
	
	private SettingFinanceModel model;
	private SettingFinanceViewImpl view;
	private GeneralFunction function;
	private boolean editMode;

	
	public SettingFinancePresenter(GeneralFunction function, 
			SettingFinanceModel model, SettingFinanceViewImpl view) {
		this.function=function;
		this.view=view;
		this.model=model;
		
	}
	@Override
	public void buttonSave() {
		view.hideError();
		SettingData settingData=view.getData();
		List<String> error=model.save(settingData);
		if(error==null){
			Notification.show("Data pengaturan dirubah", Type.TRAY_NOTIFICATION);
		}else{
			String errors="<b>Tidak bisa menyimpan data, perbaiki kesalahan berikut :</b><br> <br>";
			for(String err:error){
				errors=errors+err+"<br>";
			}
			view.showError(errors);
		}
	}
	
	@Override
	public void resetChange() {
		SettingData resetData=model.reset();
		view.setData(resetData);
	}

	@Override
	public void resetToDefault() 
	{ 	
		function.showDialog("Ubah Ke Pengaturan Awal", 
				"Anda yakin untuk mengubah ke pengaturan awal?",
				new ClickListener() {
					public void buttonClick(ClickEvent event) {
						resetExecution();
					}
				}, view.getUI());
		
	}
	
	public void resetExecution(){
		view.hideError();
		List<String> error=model.resetDefault(view.getData());
		if(error==null){
			Notification.show("Data pengaturan dirubah ke settingan awal", Type.TRAY_NOTIFICATION);
		}else{
			String errors="<b>Tidak bisa mereset data</b><br> <br>";
			for(String err:error){
				errors=errors+err+"<br>";
			}
			view.showError(errors);
		}
		
	}
	@Override
	public void valueChange() {
		view.hideError();
	}
	public void closeWindow(){
		Collection<Window> list=view.getUI().getWindows();
		for(Window w:list){
			view.getUI().removeWindow(w);
		}
		
	}


}
