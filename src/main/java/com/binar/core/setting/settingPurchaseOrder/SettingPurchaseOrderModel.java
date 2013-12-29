package com.binar.core.setting.settingPurchaseOrder;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.core.setting.settingPurchaseOrder.SettingPurchaseOrderView.SettingData;
import com.binar.database.generateData.DefaultSetting;
import com.binar.generalFunction.GeneralFunction;

public class SettingPurchaseOrderModel {
	GeneralFunction function;
	EbeanServer server;

	public SettingPurchaseOrderModel(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
	}
	public SettingData reset(){
		return new SettingData(function);
	}
	public List<String> save(SettingData setting){
		List<String> error;
		error=setting.validate();
		if(error==null){
			error=new ArrayList<String>();
			try {
				server.update(setting.getSettingNarkotika());
				server.update(setting.getSettingPsikotropika());
				server.update(setting.getSettingGeneral());
			} catch (Exception e) {
				error.add("Kesalahan menyimpan data " + e.getMessage());
			}
		}
		
		return error.size()==0?null:error;
		
	}
	public List<String> resetDefault(SettingData setting){
		setting.getSettingNarkotika().setSettingValue(DefaultSetting.NARKOTIKA);
		setting.getSettingPsikotropika().setSettingValue(DefaultSetting.PSIKOTROPIKA);
		setting.getSettingGeneral().setSettingValue(DefaultSetting.GENERAL);
		List<String> error;
		error=new ArrayList<String>();
		try {
			server.update(setting.getSettingNarkotika());
			server.update(setting.getSettingPsikotropika());
			server.update(setting.getSettingGeneral());

		} catch (Exception e) {
			error.add("Kesalahan menyimpan data " + e.getMessage());
		}
		
		return error.size()==0?null:error;
		
	}
}
