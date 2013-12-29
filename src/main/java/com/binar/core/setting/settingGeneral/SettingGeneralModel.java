package com.binar.core.setting.settingGeneral;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.core.setting.settingGeneral.SettingGeneralView.SettingData;
import com.binar.database.generateData.DefaultSetting;
import com.binar.generalFunction.GeneralFunction;

public class SettingGeneralModel {

	GeneralFunction function;
	EbeanServer server;

	public SettingGeneralModel(GeneralFunction function){
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
				server.update(setting.getSettingAddress());
				server.update(setting.getSettingCity());
				server.update(setting.getSettingInstalasi());
				server.update(setting.getSettingPhone());
				server.update(setting.getSettingApotek());
				server.update(setting.getSettingRayon());
			} catch (Exception e) {
				error.add("Kesalahan menyimpan data " + e.getMessage());
			}
		}
		
		return error.size()==0?null:error;
		
	}
	public List<String> resetDefault(SettingData setting){
		setting.getSettingAddress().setSettingValue(DefaultSetting.RS_ADDRESS);
		setting.getSettingCity().setSettingValue(DefaultSetting.RS_CITY);
		setting.getSettingInstalasi().setSettingValue(DefaultSetting.INSTALASI);
		setting.getSettingPhone().setSettingValue(DefaultSetting.RS_NUMBER);
		setting.getSettingRayon().setSettingValue(DefaultSetting.RAYON);
		
		List<String> error;
		error=new ArrayList<String>();
		try {
			server.update(setting.getSettingAddress());
			server.update(setting.getSettingCity());
			server.update(setting.getSettingInstalasi());
			server.update(setting.getSettingPhone());
			server.update(setting.getSettingApotek());
			server.update(setting.getSettingRayon());
		} catch (Exception e) {
			error.add("Kesalahan menyimpan data " + e.getMessage());
		}
		
		return error.size()==0?null:error;
		
	}
}
