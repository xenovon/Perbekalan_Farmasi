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
	
		private Setting settingNarkotika;
		private Setting settingPsikotropika;
		private Setting settingGeneral;
		
		private GeneralFunction function;
		private EbeanServer server;
		public SettingData(GeneralFunction function) {
			this.function=function;
			this.server=function.getServer();
			init();
		}
		
		private void init(){
			settingNarkotika = server.find(Setting.class).where().eq("settingKey", "narkotika").findUnique();
			settingPsikotropika =server.find(Setting.class).where().eq("settingKey", "psikotropika").findUnique();
			settingGeneral =server.find(Setting.class).where().eq("settingKey", "general").findUnique();
		}
		public List<String> validate(){
			List<String> error=new ArrayList<String>();
			String settingNarkotikaValue=settingNarkotika.getSettingValue();
			String settingPsikotropikaValue=settingPsikotropika.getSettingValue();
			String settingGeneralValue=settingGeneral.getSettingValue();
			
			if(settingNarkotikaValue==null){
				error.add("prefiks surat pesanan narkotika tidak boleh kosong");
			}
			
			if(settingPsikotropikaValue==null){
				error.add("prefiks surat pesanan psikotropika tidak boleh kosong");
			}
			if(settingGeneralValue==null){
				error.add("prefiks nomor surat pesanan umum tidak boleh kosong");
			}
	
			
			return error.size()==0?null:error;
		}
		
		public Setting getSettingPsikotropika() {
			return settingPsikotropika;
		}
		public Setting getSettingNarkotika() {
			return settingNarkotika;
		}
		
		public Setting getSettingGeneral() {
			return settingGeneral;
		}
	}

}
