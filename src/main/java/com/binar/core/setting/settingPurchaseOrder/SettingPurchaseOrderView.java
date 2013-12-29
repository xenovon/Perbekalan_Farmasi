package com.binar.core.setting.settingPurchaseOrder;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Setting;
import com.binar.generalFunction.GeneralFunction;

public interface SettingPurchaseOrderView {
	interface SettingPurchaseOrderListener{
		public void buttonSave();
		public void resetChange();
		public void resetToDefault();
		public void valueChange();
	}
	
	public void init();
	public void construct();
	public void setData(SettingData data);
	public void showError(String content);
	public void hideError();
	public SettingData getData();
	public void setListener(SettingPurchaseOrderListener listener);
	
	
	public class SettingData{
	
		private Setting settingPPN;
		private Setting settingMargin;
		
		private GeneralFunction function;
		private EbeanServer server;
		public SettingData(GeneralFunction function) {
			this.function=function;
			this.server=function.getServer();
			init();
		}
		
		private void init(){
			settingPPN = server.find(Setting.class).where().eq("settingKey", "ppn").findUnique();
			settingMargin =server.find(Setting.class).where().eq("settingKey", "margin").findUnique();
		}
		public List<String> validate(){
			List<String> error=new ArrayList<String>();
			String settingPPNValue=settingPPN.getSettingValue();
			String settingMarginValue=settingMargin.getSettingValue();
			
			if(settingPPNValue==null){
				error.add("Angka PPN tidak boleh kosong");
			}else{
				try {
					Double ppn=Double.parseDouble(settingPPNValue);
					if(!(ppn<=100 && ppn>=0)){
						error.add("PPN harus diantara 0-100");
					}
				} catch (NumberFormatException e) {
					error.add("PPN harus berupa angka");
				}
			}
			
			if(settingMarginValue==null){
				error.add("Angka margin tidak boleh kosong");
			}else{
				try {
					Double ppn=Double.parseDouble(settingMarginValue);
					if(!(ppn<=100 && ppn>=0)){
						error.add("Margin harus diantara 0-100");
					}
				} catch (NumberFormatException e) {
					error.add("Margin harus berupa angka");
				}
			}
			
			return error.size()==0?null:error;
		}
		
		public Setting getSettingMargin() {
			return settingMargin;
		}
		public Setting getSettingPPN() {
			return settingPPN;
		}
	}

}
