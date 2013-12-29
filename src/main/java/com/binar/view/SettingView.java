package com.binar.view;

import com.binar.core.procurement.Invoice;
import com.binar.core.procurement.PurchaseOrder;
import com.binar.core.setting.SettingFinance;
import com.binar.core.setting.SettingGeneral;
import com.binar.core.setting.SettingGoods;
import com.binar.core.setting.SettingPurchaseOrder;
import com.binar.core.setting.UserSetting;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class SettingView extends CustomComponent  implements View {

	Label label=new Label("Procurement Management ");
	TabSheet tabSheet=new TabSheet();
	SettingFinance finance;
	SettingPurchaseOrder purchaseOrder;
	SettingGeneral general;
	SettingGoods goods;
	UserSetting user;
	GeneralFunction function;
	
	@Override
	public void enter(ViewChangeEvent event) {
		function=new GeneralFunction();
		finance=new SettingFinance(function);
		purchaseOrder =new SettingPurchaseOrder(function);
		general = new SettingGeneral(function);
		goods =new SettingGoods(function);
		user=new UserSetting(function);
		
		tabSheet.addTab(user).setCaption("Pengaturan Akun");
		tabSheet.addTab(general).setCaption("Pengaturan Umum");
		tabSheet.addTab(goods).setCaption("Pengaturan Barang");
		tabSheet.addTab(finance).setCaption("Pengaturan Keuangan");
		tabSheet.addTab(purchaseOrder).setCaption("Pengaturan Surat Pesanan");
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
	}

}
