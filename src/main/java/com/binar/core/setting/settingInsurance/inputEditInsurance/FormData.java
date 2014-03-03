package com.binar.core.setting.settingInsurance.inputEditInsurance;


import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;

public class FormData {

	private int id;
	private String name="";
	private String description="";
	private boolean show;
	
	private GeneralFunction function;
	private EbeanServer server;
	public FormData(GeneralFunction function) {
		this.function=function;
		server=function.getServer();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	

}
