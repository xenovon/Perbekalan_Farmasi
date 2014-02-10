package com.binar.core.dashboard.dashboardItem.procurementDueDate;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.Invoice;
import com.vaadin.ui.Panel;

interface  ProcurementDueDateView {
	
	public interface ProcurementDueDateListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init(String month);
	public void construct();
	public void updateTable(List<Invoice> goods);
}
