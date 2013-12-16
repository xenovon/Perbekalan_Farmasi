package com.binar.core.procurement.invoice;

import com.avaje.ebean.EbeanServer;
import com.binar.generalFunction.GeneralFunction;

public class InvoiceModel {

	GeneralFunction function;
	EbeanServer server;
	public InvoiceModel(GeneralFunction function) {
		this.function=function;
		this.server=server;
	}
}
