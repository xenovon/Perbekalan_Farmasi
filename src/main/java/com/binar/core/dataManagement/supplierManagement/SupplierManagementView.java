package com.binar.core.dataManagement.supplierManagement;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.Supplier;

public interface SupplierManagementView {

	interface SupplierManagementListener{
		public void buttonClick(String buttonName);
		public void editClick(String idSupplier);
		public void deleteClick(String idSupplier);
		public void showClick(String idSupplier);	
		
	}
	
	public void init();
	public void construct();
	public void setListener(SupplierManagementListener listener);
	public boolean updateTableData(List<Supplier> data, final boolean withEditSupplier);
	public void showDetailWindow(Supplier supplier, String goods);
}
