package com.binar.core.dataManagement.manufacturerManagement;

import java.util.List;

import com.binar.core.dataManagement.manufacturerManagement.ManufacturerManagementView.ManufacturerManagementListener;
import com.binar.entity.Goods;
import com.binar.entity.Manufacturer;
import com.binar.entity.Supplier;

public interface ManufacturerManagementView {

	interface ManufacturerManagementListener{
		public void buttonClick(String buttonName);
		public void editClick(String idManufacturer);
		public void deleteClick(String idManufacturer);
		public void showClick(String idManufacturer);	
		
	}
	
	public void init();
	public void construct();
	public boolean updateTableData(List<Manufacturer> data, final boolean withEditManufacturer);
	public void showDetailWindow(Manufacturer manufacturer, String goods);
	public void setListener(ManufacturerManagementListener listener);
}
