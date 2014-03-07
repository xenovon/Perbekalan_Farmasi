package com.binar.core.setting.settingInsurance;

import java.util.List;

import com.binar.core.requirementPlanning.reqPlanningList.ReqPlanningListView.ReqPlanningListListener;
import com.binar.entity.Goods;
import com.binar.entity.Insurance;
import com.binar.entity.ReqPlanning;

interface  InsuranceManagementView {
  
	interface InsuranceManagementListener{
		public void buttonClick(String buttonName);
		public void editClick(int idInsurance);
		public void deleteClick(int idInsurance);
		public void showClick(int idInsurance);

	}
	public void init();
	public void construct();
	public void setListener(InsuranceManagementListener listener);
	public boolean updateTableData(List<Insurance> data);

}

