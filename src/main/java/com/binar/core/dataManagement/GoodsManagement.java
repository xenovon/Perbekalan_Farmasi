package com.binar.core.dataManagement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class GoodsManagement extends CssLayout {

	Label label=new Label("Manajemen Barang");
	public GoodsManagement() {
		this.setCaption("Manajemen Barang");
		this.addComponent(label);
		this.addStyleName("tab-content");
	}
}
