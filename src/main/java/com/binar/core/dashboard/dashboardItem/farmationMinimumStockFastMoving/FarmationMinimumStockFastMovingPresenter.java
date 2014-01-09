package com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving;

import com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving.FarmationMinumumStockFastMovingView.FarmationMinimumStockFastMovingViewListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class FarmationMinimumStockFastMovingPresenter implements FarmationMinimumStockFastMovingViewListener {

	FarmationMinimumStockFastMovingModel model;
	FarmationMinimumStockFastMovingViewImpl view;
	GeneralFunction function;
	public FarmationMinimumStockFastMovingPresenter(GeneralFunction function
			, FarmationMinimumStockFastMovingViewImpl view, FarmationMinimumStockFastMovingPresenter presenter) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		
		
	}
	@Override
	public void updateTable() {
		view.updateTable(model.getGoodsList());
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("");
	}

}
