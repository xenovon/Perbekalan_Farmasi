package com.binar.core.setting.settingGeneral;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Setting;
import com.binar.generalFunction.GeneralFunction;

public interface SettingGeneralView {
	interface SettingGeneralListener{
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
	public void setListener(SettingGeneralListener listener);

	public class SettingData{
	
		private Setting settingPhone;
		private Setting settingAddress;
		private Setting settingCity;
		private Setting settingInstalasi;
		private Setting settingApotek;
		private Setting settingRayon;
		
		
		private GeneralFunction function;
		private EbeanServer server;
		public SettingData(GeneralFunction function) {
			this.function=function;
			this.server=function.getServer();
			init();
		}
		
		private void init(){
			settingPhone = server.find(Setting.class).where().eq("settingKey", "rs_number").findUnique();
			settingAddress =server.find(Setting.class).where().eq("settingKey", "rs_address").findUnique();
			settingCity =server.find(Setting.class).where().eq("settingKey", "rs_city").findUnique();
			settingInstalasi =server.find(Setting.class).where().eq("settingKey", "instalasi").findUnique();
			settingApotek =server.find(Setting.class).where().eq("settingKey", "apotek").findUnique();
			settingRayon =server.find(Setting.class).where().eq("settingKey", "rayon").findUnique();
		}
		public List<String> validate(){
			List<String> error=new ArrayList<String>();
			String settingPhoneValue=settingPhone.getSettingValue();
			String settingAddressValue=settingAddress.getSettingValue();
			String settingCityValue=settingCity.getSettingValue();
			String settingInstalasiValue=settingInstalasi.getSettingValue();
			String settingApotekValue=settingInstalasi.getSettingValue();
			String settingRayonValue=settingRayon.getSettingValue();
			
			if(settingPhoneValue==null){
				error.add("Nomor Telepon tidak boleh kosong");
			}
			if(settingCityValue==null){
				error.add("Kota tidak boleh kosong");
			}
			
			if(settingInstalasiValue==null){
				error.add("Instalasi tidak boleh kosong");
			}
			if(settingAddressValue==null){
				error.add("Alamat tidak boleh kosong");
			}
			if(settingApotekValue==null){
				error.add("Nama apotik dan lembaga tidak boleh kosong");
			}
			
			if(settingRayonValue==null){
				settingRayon.setSettingValue("");
			}
			
			
			return error.size()==0?null:error;
		}

		public Setting getSettingPhone() {
			return settingPhone;
		}

		public Setting getSettingAddress() {
			return settingAddress;
		}

		public Setting getSettingCity() {
			return settingCity;
		}

		public Setting getSettingInstalasi() {
			return settingInstalasi;
		}

		public GeneralFunction getFunction() {
			return function;
		}

		public EbeanServer getServer() {
			return server;
		}
		
		public Setting getSettingApotek() {
			return settingApotek;
		}
		
		public Setting getSettingRayon() {
			return settingRayon;
		}

	}

}
