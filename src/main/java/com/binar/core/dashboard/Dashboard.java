package com.binar.core.dashboard;

import com.binar.core.dashboard.dashboardItem.farmationExpireGoods.FarmationExpiredGoodsModel;
import com.binar.core.dashboard.dashboardItem.farmationExpireGoods.FarmationExpiredGoodsPresenter;
import com.binar.core.dashboard.dashboardItem.farmationExpireGoods.FarmationExpiredGoodsViewImpl;
import com.binar.core.dashboard.dashboardItem.farmationExpiredGoodsStatus.FarmationExpiredGoodsStatusModel;
import com.binar.core.dashboard.dashboardItem.farmationExpiredGoodsStatus.FarmationExpiredGoodsStatusPresenter;
import com.binar.core.dashboard.dashboardItem.farmationExpiredGoodsStatus.FarmationExpiredGoodsStatusViewImpl;
import com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption.FarmationGoodsConsumptionModel;
import com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption.FarmationGoodsConsumptionPresenter;
import com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption.FarmationGoodsConsumptionViewImpl;
import com.binar.core.dashboard.dashboardItem.farmationMinimumStock.FarmationMinimumStockModel;
import com.binar.core.dashboard.dashboardItem.farmationMinimumStock.FarmationMinimumStockPresenter;
import com.binar.core.dashboard.dashboardItem.farmationMinimumStock.FarmationMinimumStockViewImpl;
import com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving.FarmationMinimumStockFastMovingModel;
import com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving.FarmationMinimumStockFastMovingPresenter;
import com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving.FarmationMinimumStockFastMovingViewImpl;
import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusModel;
import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusPresenter;
import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Dashboard extends GridLayout {
	 
	GeneralFunction function;
	public Dashboard(GeneralFunction function) {
		this.function=function;
		this.setSpacing(true);
		this.setColumns(2);
		this.setRows(5);
		this.setMargin(true);
		this.setWidth("100%");
		this.setHeight("450px");
		this.addComponent(new Label("<h2>Dashboard</h2>", ContentMode.HTML), 0,0,1,0);
		generateFarmationView();
	}
	
	FarmationMinimumStockFastMovingModel farmationMinimumStockFastMovingModel;
	FarmationMinimumStockFastMovingPresenter farmationMinimumStockFastMovingPresenter;
	FarmationMinimumStockFastMovingViewImpl farmationMinimumStockFastMovingViewImpl;
	
	FarmationGoodsConsumptionModel farmationGoodsConsumptionModel;
	FarmationGoodsConsumptionViewImpl farmationGoodsConsumptionViewImpl;
	FarmationGoodsConsumptionPresenter farmationGoodsConsumptionPresenter;
	
	FarmationExpiredGoodsModel 	farmationExpiredGoodsModel; 
	FarmationExpiredGoodsPresenter 	farmationExpiredGoodsPresenter; 
	FarmationExpiredGoodsViewImpl farmationExpiredGoodsViewImpl; 

	FarmationExpiredGoodsStatusModel farmationExpiredGoodsStatusModel;
	FarmationExpiredGoodsStatusViewImpl farmationExpiredGoodsStatusViewImpl;
	FarmationExpiredGoodsStatusPresenter farmationExpiredGoodsStatusPresenter;
	
	FarmationMinimumStockModel 	farmationMinimumStockModel; 
	FarmationMinimumStockViewImpl 	farmationMinimumStockViewImpl; 
	FarmationMinimumStockPresenter 	farmationMinimumStockPresenter; 

	FarmationRequirementStatusModel farmationRequirementStatusModel;
	FarmationRequirementStatusPresenter farmationRequirementStatusPresenter;
	FarmationRequirementStatusViewImpl farmationRequirementStatusViewImpl;
	
	
	public void generateFarmationView(){
		
		//Farmation Minimum Stock
		this.removeAllComponents();
		if(farmationMinimumStockFastMovingModel == null){
			farmationMinimumStockFastMovingModel=new FarmationMinimumStockFastMovingModel(function);
			farmationMinimumStockFastMovingViewImpl=new FarmationMinimumStockFastMovingViewImpl(function);
			farmationMinimumStockFastMovingPresenter= new FarmationMinimumStockFastMovingPresenter(
					function ,farmationMinimumStockFastMovingViewImpl, farmationMinimumStockFastMovingModel);
		}
		this.addComponent(farmationMinimumStockFastMovingViewImpl,0,1);

		if(farmationMinimumStockModel==null){
			farmationMinimumStockModel = new FarmationMinimumStockModel(function);
			farmationMinimumStockViewImpl =new FarmationMinimumStockViewImpl(function);
			farmationMinimumStockPresenter = new FarmationMinimumStockPresenter(
					function, farmationMinimumStockViewImpl, farmationMinimumStockModel);
		}

		this.addComponent(farmationMinimumStockViewImpl, 0, 2);
		
		if(farmationGoodsConsumptionModel == null){
			farmationGoodsConsumptionModel =new FarmationGoodsConsumptionModel(function);
			farmationGoodsConsumptionViewImpl=new FarmationGoodsConsumptionViewImpl(function);
			farmationGoodsConsumptionPresenter=new FarmationGoodsConsumptionPresenter(
					function, farmationGoodsConsumptionViewImpl, farmationGoodsConsumptionModel);
		}
		
		this.addComponent(farmationGoodsConsumptionViewImpl, 1,1);

		if(farmationExpiredGoodsModel == null){
			farmationExpiredGoodsModel =new FarmationExpiredGoodsModel(function);
			farmationExpiredGoodsViewImpl = new FarmationExpiredGoodsViewImpl(function);
			farmationExpiredGoodsPresenter=new FarmationExpiredGoodsPresenter(
					function, farmationExpiredGoodsViewImpl, farmationExpiredGoodsModel);
		}
		
		this.addComponent(farmationExpiredGoodsViewImpl, 1,2);
		
		if(farmationExpiredGoodsStatusModel==null){
			farmationExpiredGoodsStatusModel=new FarmationExpiredGoodsStatusModel(function);
			farmationExpiredGoodsStatusViewImpl=new FarmationExpiredGoodsStatusViewImpl(function);
			farmationExpiredGoodsStatusPresenter=new FarmationExpiredGoodsStatusPresenter(
					function, farmationExpiredGoodsStatusViewImpl, farmationExpiredGoodsStatusModel);
		}
		this.addComponent(farmationExpiredGoodsStatusViewImpl, 0,3);
		
		if(farmationRequirementStatusModel == null){
			farmationRequirementStatusModel =new FarmationRequirementStatusModel(function);
			farmationRequirementStatusViewImpl =new FarmationRequirementStatusViewImpl(function);
			farmationRequirementStatusPresenter = new FarmationRequirementStatusPresenter(
					function, farmationRequirementStatusViewImpl, farmationRequirementStatusModel);
		}
		this.addComponent(farmationRequirementStatusViewImpl, 1,3);		
		
	}
}
