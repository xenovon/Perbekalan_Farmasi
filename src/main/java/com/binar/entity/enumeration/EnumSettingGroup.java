package com.binar.entity.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum EnumSettingGroup {
	
	@EnumValue(value="KEUANGAN")
	KEUANGAN{
	public String toString() {
		return "Keuangan";
	}
	}, 
	@EnumValue(value="SURAT_PESANAN")
	SURAT_PESANAN{
	public String toString() {
		return "Surat Pesanan";
	}},
	
	@EnumValue(value="UMUM")
	UMUM{
		public String toString() {
			return "Umum";
		}
	},
	@EnumValue(value="BARANG")
	BARANG{
		public String toString() {
			return "Barang";
		}
	},
	
}
