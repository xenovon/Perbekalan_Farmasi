package com.binar.core.dashboard.dashboardItem.procurementGoodsProcurement;

import java.util.Map;

import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementModel;
import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementViewImpl;
import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementView.IfrsGoodsProcurementListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class ProcurementGoodsProcurementPresenter implements IfrsGoodsProcurementListener {
/*
 * Summary daftar pengadaan barang (Berapa % dari jumlah yg sudah disetujui yg udah dilakukan pengadaan barang?)
> Pie chart

DONE

 */
	IfrsGoodsProcurementModel model;
	IfrsGoodsProcurementViewImpl view;
	GeneralFunction function;
	public ProcurementGoodsProcurementPresenter(GeneralFunction function
			, IfrsGoodsProcurementViewImpl view, IfrsGoodsProcurementModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		view.setListener(this);
		updateChart();
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/procurement/"+function.VIEW_PROCUREMENT_PURCHASE);
	}
	@Override
	public void updateChart() {
		Map<String, Integer> data=model.getReceptionRequirementCount();
		if(data==null){
			view.setEmptyDataView();
		}else{
			view.generateChart(data);			
		}
	}

}
