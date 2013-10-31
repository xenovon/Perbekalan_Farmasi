package com.binar.core.procurement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class GoodsOrderReport extends CssLayout {

	Label label=new Label("Laporan Pemesanan Barang");
	public GoodsOrderReport() {
		this.setCaption("Laporan Pemesanan Barang");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}
}
