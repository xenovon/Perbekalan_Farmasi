package com.binar.core.requirementPlanning.reqPlanningList;

import java.util.Calendar;
import java.util.List;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;

public class ReqPlanningListViewImpl extends VerticalLayout implements ReqPlanningListView {

	GeneralFunction generalFunction;
	
	
	Table table;
	Label labelFilter;
	Label labelYear;
	Label labelMonth;
	Label labelTitle;
	ComboBox selectYear;
	ComboBox selectMonth;
	GridLayout layoutFilter;
	
	public ReqPlanningListViewImpl(GeneralFunction function) {
		this.generalFunction=function;
	}
	
	public void init(){
		table=new Table();
//		table.addStyleName("components-inside");
		table.setSizeFull();
        table.setWidth("100%");
        table.setSortEnabled(true);
        

		table.addContainerProperty("No",Integer.class, null);
		table.addContainerProperty("Nama",String.class, null);
		table.addContainerProperty("Satuan",String.class, null);
		table.addContainerProperty("HNA + PPN 10%",String.class, null);
		table.addContainerProperty("Kebutuhan",Integer.class, null);
		table.addContainerProperty("Perkiraan Jumlah Harga",String.class, null);
		table.addContainerProperty("Produsen",String.class, null);
		table.addContainerProperty("Distributor",String.class, null);
		table.addContainerProperty("Disetujui?",CheckBox.class, null);
		table.addContainerProperty("Jumlah Disetujui",Integer.class, null);
		
		//data dummy
		Integer no=new Integer(1);
		Integer kebutuhan=new Integer(100);
		String nama=new String("Panadol");
		String satuan=new String("box");
		String hna=new String("Rp 30.000");
		String perkiraanHarga=new String("Rp 30.000.000");
		String produsen=new String("Kimia Farma");
		String distributor=new String("PT Media Mantap");
		CheckBox disetujui=new CheckBox("Disetujui", true);
		disetujui.setReadOnly(true);
		Integer jumlahDisetujui=new Integer(100);
		
		
		table.addItem(new Object[]{no, nama, satuan, hna, kebutuhan, perkiraanHarga, produsen, distributor, disetujui, jumlahDisetujui}, 1);
		
		labelFilter=new Label("<b style='font-size:14px'>Filter : </b> ", ContentMode.HTML);
		labelYear=new Label("Pilih Tahun  :");
		labelMonth=new Label("Pilih Bulan  :");
		
		List<String> yearList=generalFunction.getListFactory().createYearList(5);
		List<String> monthList=generalFunction.getListFactory().createMonthList();
		selectYear = new ComboBox("", yearList);
		selectMonth =new ComboBox("", monthList);
		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectYear.setValue(yearList.get(0));
		selectMonth.setValue(monthList.get(Calendar.getInstance().get(Calendar.MONTH)));

		selectMonth.setTextInputAllowed(false);
		selectYear.setTextInputAllowed(false);
		selectYear.addStyleName("non-caption-form");
		selectMonth.addStyleName("non-caption-form");

		selectMonth.setWidth("120px");
		selectYear.setWidth("120px");
		labelTitle =new Label("<h2>Daftar Rencana Kebutuhan</h2>", ContentMode.HTML);
		construct();
		
	}
	private void construct(){
		layoutFilter =new GridLayout(5,1);
		layoutFilter.setMargin(true);
		layoutFilter.setSpacing(true);
		
		layoutFilter.addComponent(labelFilter, 0, 0);
		layoutFilter.addComponent(labelYear, 1, 0);
		layoutFilter.addComponent(selectYear, 2, 0);
		layoutFilter.addComponent(labelMonth, 3, 0);
		layoutFilter.addComponent(selectMonth, 4, 0);
		
		this.addComponents(labelTitle, layoutFilter, table);
		
	}
}
