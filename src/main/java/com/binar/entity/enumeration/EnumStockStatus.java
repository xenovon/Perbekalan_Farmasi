package com.binar.entity.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum EnumStockStatus {
	@EnumValue("SAFE")
	SAFE,
	@EnumValue("WARNING")	
	WARNING,
	@EnumValue("LESS")
	LESS,
}
