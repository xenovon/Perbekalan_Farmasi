package com.binar.core.setting.settingGoods;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.core.setting.settingGoods.SettingGoodsView.SettingData;
import com.binar.database.generateData.DefaultSetting;
import com.binar.generalFunction.GeneralFunction;

public class SettingGoodsModel {
	GeneralFunction function;
	EbeanServer server;

	public SettingGoodsModel(GeneralFunction function){
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
				server.update(setting.getSettingMargin());
				server.update(setting.getSettingPPN());
			} catch (Exception e) {
				error.add("Kesalahan menyimpan data " + e.getMessage());
			}
		}
		
		return error.size()==0?null:error;
		
	}
	public List<String> resetDefault(SettingData setting){
		setting.getSettingMargin().setSettingValue(DefaultSetting.MARGIN);
		setting.getSettingPPN().setSettingValue(DefaultSetting.PPN);
		List<String> error;
		error=new ArrayList<String>();
		try {
			server.update(setting.getSettingMargin());
			server.update(setting.getSettingPPN());
		} catch (Exception e) {
			error.add("Kesalahan menyimpan data " + e.getMessage());
		}
		
		return error.size()==0?null:error;
		
	}
}
