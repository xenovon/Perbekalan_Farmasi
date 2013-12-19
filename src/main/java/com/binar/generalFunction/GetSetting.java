package com.binar.generalFunction;

import java.util.HashMap;
import java.util.Map;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Setting;

/*
 * Untuk mendapatkan dan mengolah data setting yang ada di database
 */

public class GetSetting {

	EbeanServer server;
	
	public GetSetting(EbeanServer server) {
		this.server=server;
	}
	
	public Setting getSetting(String key){
		
		return server.find(Setting.class).where().eq("setting_key",key).findUnique();
		
	}
	//untuk mendapatkan list satuan dalam format map
	public Map<String, String> getUnit(){
		String value = getSetting("satuan").getSettingValue();
		return getMapFromString(value);
		
	}
	
	public Map<String, String> getPackage(){
		String value=getSetting("package").getSettingValue();
		return getMapFromString(value);
	}
	public double getPPN(){
		try {
			String ppnString= getSetting("ppn").getSettingValue();
			return Double.parseDouble(ppnString);
		} catch (NumberFormatException e) {
			e.printStackTrace();			
		}
		return 10;
	}
	public double getMargin(){
		try {
			String marginString= getSetting("margin").getSettingValue();
			return Double.parseDouble(marginString);
		} catch (NumberFormatException e) {
			e.printStackTrace();			
		}
		return 0;
	}
	
	/*
	 * Mengubah format setting satuan dan package menjadi map
	 * format setting <singkatan>=<isi data>, <singkatan2>=<isi data2>
	 * menjadi 
	 * No Map   key      value
	 * 1	 singkatan  isi data
	 * 2	 singkatan2 isi data2
	 * Contoh ada di generate data
	 */
	private  Map<String, String> getMapFromString(String input){
		input=cleanString(input);
		String mapValue[] = input.split(",");
		Map<String, String> returnValue=new HashMap<String, String>(mapValue.length);
		for(String v:mapValue){
			String key=v.split("=")[0];
			String value=v.split("=")[1];
			returnValue.put(key, value);
		}
		return returnValue;
		
	}
	//membersihkan spasi untuk input dan enter
	private  String cleanString(String input){
		input=input.replace("\n", "");
		input=input.replace(", ", ",");
		input=input.replace(",  ", ",");
		input=input.replace(",   ", ",");
		input=input.replace(",    ", ",");
		input=input.replace(",     ", ",");
		input=input.replace(" ,", ",");
		input=input.replace("  ,", ",");
		input=input.replace("   ,", ",");
		input=input.replace("    ,", ",");
		input=input.replace("     ,", ",");
		
		return input;
		
	}

}
