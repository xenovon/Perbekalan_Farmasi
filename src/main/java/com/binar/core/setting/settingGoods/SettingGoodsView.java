package com.binar.core.setting.settingGoods;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Setting;
import com.binar.generalFunction.GeneralFunction;

public interface SettingGoodsView {
	interface SettingGoodsListener{
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
	public void setListener(SettingGoodsListener listener);
	
	
	public class SettingData{
	
		private Setting settingSatuan;
		private Setting settingPackage;
		
		private GeneralFunction function;
		private EbeanServer server;
		public SettingData(GeneralFunction function) {
			this.function=function;
			this.server=function.getServer();
			init();
		}
		
		private void init(){
			settingSatuan = server.find(Setting.class).where().eq("settingKey", "satuan").findUnique();
			settingPackage =server.find(Setting.class).where().eq("settingKey", "package").findUnique();
		}
		public List<String> validate(){
			List<String> error=new ArrayList<String>();
			String settingSatuanValue=settingSatuan.getSettingValue();
			String settingPackageValue=settingPackage.getSettingValue();
			
			if(settingSatuanValue==null){
				error.add("Satuan tidak boleh kosong");
			}
			
			if(settingPackageValue==null){
				error.add("Kemasan tidak boleh kosong");
			}
			
			return error.size()==0?null:error;
		}
		
		public Setting getSettingPackage() {
			return settingPackage;
		}
		public Setting getSettingSatuan() {
			return settingSatuan;
		}
	}

}
