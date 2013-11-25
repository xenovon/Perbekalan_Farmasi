package com.binar.core.dataManagement.manufacturerManagement;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;

public class ManufacturerManagementModel {

	private GeneralFunction function;
	private EbeanServer server;
	public ManufacturerManagementModel(GeneralFunction function) {
		this.function=function;
		this.server=server;
	}

}
