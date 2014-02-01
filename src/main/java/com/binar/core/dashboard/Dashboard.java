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
import com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval.IfrsDeletionApprovalModel;
import com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval.IfrsDeletionApprovalPresenter;
import com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval.IfrsDeletionApprovalViewImpl;
import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementModel;
import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementPresenter;
import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementViewImpl;
import com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary.IfrsGoodsReceptionSummaryModel;
import com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary.IfrsGoodsReceptionSummaryPresenter;
import com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary.IfrsGoodsReceptionSummaryViewImpl;
import com.binar.core.dashboard.dashboardItem.ifrsRequirementPlanning.IfrsRequirementPlanningPresenter;
import com.binar.core.dashboard.dashboardItem.ppkExpiredGoodsNonAccepted.PpkExpiredGoodsNonAcceptedModel;
import com.binar.core.dashboard.dashboardItem.ppkExpiredGoodsNonAccepted.PpkExpiredGoodsNonAcceptedPresenter;
import com.binar.core.dashboard.dashboardItem.ppkExpiredGoodsNonAccepted.PpkExpiredGoodsNonAcceptedViewImpl;
import com.binar.core.dashboard.dashboardItem.ppkGoodsProcurementSummary.PpkGoodsProcurementPresenter;
import com.binar.core.dashboard.dashboardItem.ppkRequirementPlanning.PpkRequirementPlanningPresenter;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class Dashboard extends VerticalLayout {
	 
	GeneralFunction function;
	GridLayout gridLayout;
	Panel panel;
	public Dashboard(GeneralFunction function) {
		this.function=function;
		init();
	}
	
	public void init(){
		this.setSpacing(true);
		this.setMargin(true);
		this.setWidth("100%");
//		this.setHeight("560px");		
		construct();
	}
	public void construct(){
		panel=new Panel();
		panel.setWidth("100%");
		panel.setHeight("550px");
		this.addComponent(new Label("<h2>Dashboard</h2>", ContentMode.HTML));
		gridLayout=new GridLayout(2,3);
		panel.setContent(gridLayout);
		this.addComponent(panel);
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
		gridLayout.removeAllComponents();
		if(farmationMinimumStockFastMovingModel == null){
			farmationMinimumStockFastMovingModel=new FarmationMinimumStockFastMovingModel(function);
			farmationMinimumStockFastMovingViewImpl=new FarmationMinimumStockFastMovingViewImpl(function);
			farmationMinimumStockFastMovingPresenter= new FarmationMinimumStockFastMovingPresenter(
					function ,farmationMinimumStockFastMovingViewImpl, farmationMinimumStockFastMovingModel);
		}
		gridLayout.addComponent(farmationMinimumStockFastMovingViewImpl,0,0);

		if(farmationMinimumStockModel==null){
			farmationMinimumStockModel = new FarmationMinimumStockModel(function);
			farmationMinimumStockViewImpl =new FarmationMinimumStockViewImpl(function);
			farmationMinimumStockPresenter = new FarmationMinimumStockPresenter(
					function, farmationMinimumStockViewImpl, farmationMinimumStockModel);
		}

		gridLayout.addComponent(farmationMinimumStockViewImpl, 0, 1);
		
		if(farmationGoodsConsumptionModel == null){
			farmationGoodsConsumptionModel =new FarmationGoodsConsumptionModel(function);
			farmationGoodsConsumptionViewImpl=new FarmationGoodsConsumptionViewImpl(function);
			farmationGoodsConsumptionPresenter=new FarmationGoodsConsumptionPresenter(
					function, farmationGoodsConsumptionViewImpl, farmationGoodsConsumptionModel);
		}
		
		gridLayout.addComponent(farmationGoodsConsumptionViewImpl, 1,0);

		if(farmationExpiredGoodsModel == null){
			farmationExpiredGoodsModel =new FarmationExpiredGoodsModel(function);
			farmationExpiredGoodsViewImpl = new FarmationExpiredGoodsViewImpl(function);
			farmationExpiredGoodsPresenter=new FarmationExpiredGoodsPresenter(
					function, farmationExpiredGoodsViewImpl, farmationExpiredGoodsModel);
		}
		
		gridLayout.addComponent(farmationExpiredGoodsViewImpl, 1,1);
		
		if(farmationExpiredGoodsStatusModel==null){
			farmationExpiredGoodsStatusModel=new FarmationExpiredGoodsStatusModel(function);
			farmationExpiredGoodsStatusViewImpl=new FarmationExpiredGoodsStatusViewImpl(function);
			farmationExpiredGoodsStatusPresenter=new FarmationExpiredGoodsStatusPresenter(
					function, farmationExpiredGoodsStatusViewImpl, farmationExpiredGoodsStatusModel);
		}
		gridLayout.addComponent(farmationExpiredGoodsStatusViewImpl, 1,2);
		
		if(farmationRequirementStatusModel == null){
			farmationRequirementStatusModel =new FarmationRequirementStatusModel(function);
			farmationRequirementStatusViewImpl =new FarmationRequirementStatusViewImpl(function);
			farmationRequirementStatusPresenter = new FarmationRequirementStatusPresenter(
					function, farmationRequirementStatusViewImpl, farmationRequirementStatusModel);
		}
		gridLayout.addComponent(farmationRequirementStatusViewImpl, 0,2);		
		
	}
	
	IfrsDeletionApprovalModel ifrsDeletionApprovalModel;
	IfrsDeletionApprovalPresenter ifrsDeletionApprovalPresenter;
	IfrsDeletionApprovalViewImpl ifrsDeletionApprovalViewImpl;
	
	IfrsGoodsProcurementViewImpl ifrsGoodsProcurementViewImpl;
	IfrsGoodsProcurementModel ifrsGoodsProcurementModel;
	IfrsGoodsProcurementPresenter ifrsGoodsProcurementPresenter;
	
	IfrsGoodsReceptionSummaryViewImpl ifrsGoodsReceptionSummaryViewImpl;
	IfrsGoodsReceptionSummaryPresenter ifrsGoodsReceptionSummaryPresenter;
	IfrsGoodsReceptionSummaryModel ifrsGoodsReceptionSummaryModel;
	
	IfrsRequirementPlanningPresenter ifrsRequirementPlanningPresenter;
	
	public void generateIfrsView(){
		gridLayout.removeAllComponents();
		if(ifrsDeletionApprovalModel == null){
			ifrsDeletionApprovalModel=new IfrsDeletionApprovalModel(function);
			ifrsDeletionApprovalViewImpl=new IfrsDeletionApprovalViewImpl(function);
			ifrsDeletionApprovalPresenter= new IfrsDeletionApprovalPresenter(
					function ,ifrsDeletionApprovalViewImpl, ifrsDeletionApprovalModel);
		}
		gridLayout.addComponent(ifrsDeletionApprovalViewImpl,0,0);

		if(ifrsRequirementPlanningPresenter == null){
			farmationRequirementStatusModel =new FarmationRequirementStatusModel(function);
			farmationRequirementStatusViewImpl =new FarmationRequirementStatusViewImpl(function);
			ifrsRequirementPlanningPresenter = new IfrsRequirementPlanningPresenter(
					function, farmationRequirementStatusViewImpl, farmationRequirementStatusModel);
		}
		gridLayout.addComponent(farmationRequirementStatusViewImpl,1,0);

		if(ifrsGoodsProcurementPresenter == null){
			ifrsGoodsProcurementModel =new IfrsGoodsProcurementModel(function);
			ifrsGoodsProcurementViewImpl =new IfrsGoodsProcurementViewImpl(function);
			ifrsGoodsProcurementPresenter = new IfrsGoodsProcurementPresenter(
					function, ifrsGoodsProcurementViewImpl, ifrsGoodsProcurementModel);
		}
		gridLayout.addComponent(ifrsGoodsProcurementViewImpl,0,1);


		if(ifrsGoodsReceptionSummaryPresenter == null){
			ifrsGoodsReceptionSummaryViewImpl =new IfrsGoodsReceptionSummaryViewImpl(function);
			ifrsGoodsReceptionSummaryModel =new IfrsGoodsReceptionSummaryModel(function);
			ifrsGoodsReceptionSummaryPresenter = new IfrsGoodsReceptionSummaryPresenter(
					function, ifrsGoodsReceptionSummaryViewImpl, ifrsGoodsReceptionSummaryModel);
		}
		gridLayout.addComponent(ifrsGoodsProcurementViewImpl,1,1);
	}
	
	PpkExpiredGoodsNonAcceptedModel ppkExpiredGoodsNonAcceptedModel;
	PpkExpiredGoodsNonAcceptedViewImpl ppkExpiredGoodsNonAcceptedViewImpl;
	PpkExpiredGoodsNonAcceptedPresenter ppkExpiredGoodsNonAcceptedPresenter;
	
	PpkGoodsProcurementPresenter ppkGoodsProcurementPresenter;
	
	PpkRequirementPlanningPresenter ppkRequirementPlanningPresenter;
	
	public void generatePPKView(){
		gridLayout.removeAllComponents();
		if(ppkExpiredGoodsNonAcceptedPresenter == null){
			ppkExpiredGoodsNonAcceptedModel =new PpkExpiredGoodsNonAcceptedModel(function);
			ppkExpiredGoodsNonAcceptedViewImpl =new PpkExpiredGoodsNonAcceptedViewImpl(function);
			ppkExpiredGoodsNonAcceptedPresenter = new PpkExpiredGoodsNonAcceptedPresenter(
					function, ppkExpiredGoodsNonAcceptedViewImpl, ppkExpiredGoodsNonAcceptedModel);
		}
		gridLayout.addComponent(ppkExpiredGoodsNonAcceptedViewImpl,0,0);

		if(ppkGoodsProcurementPresenter == null){
			ifrsGoodsProcurementModel =new IfrsGoodsProcurementModel(function);
			ifrsGoodsProcurementViewImpl =new IfrsGoodsProcurementViewImpl(function);
			ppkGoodsProcurementPresenter = new PpkGoodsProcurementPresenter(
					function, ifrsGoodsProcurementViewImpl, ifrsGoodsProcurementModel);
		}
		gridLayout.addComponent(ifrsGoodsProcurementViewImpl,1,0);
		if(ppkRequirementPlanningPresenter == null){
			farmationRequirementStatusModel =new FarmationRequirementStatusModel(function);
			farmationRequirementStatusViewImpl =new FarmationRequirementStatusViewImpl(function);
			ppkRequirementPlanningPresenter = new PpkRequirementPlanningPresenter(
					function, farmationRequirementStatusViewImpl, farmationRequirementStatusModel);
		}
		gridLayout.addComponent(farmationRequirementStatusViewImpl,0,1);

	}
}
