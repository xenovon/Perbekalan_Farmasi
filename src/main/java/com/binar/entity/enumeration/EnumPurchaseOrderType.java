package com.binar.entity.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum EnumPurchaseOrderType {
	@EnumValue("NARKOTIKA")
	NARKOTIKA,
	@EnumValue("PSIKOTROPIKA")	
	PSIKOTROPIKA,
	@EnumValue("GENERAL")
	GENERAL
}
