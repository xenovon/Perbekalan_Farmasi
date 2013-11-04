package com.binar.core.requirementPlanning.approval;

import java.util.Calendar;
import java.util.List;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ApprovalViewImpl extends VerticalLayout implements
							  ApprovalView, Button.ClickListener {

	private Table table;
	private Label labelFilter;
	private Label labelYear;
	private Label labelMonth;
	private Label labelTitle;
	private ComboBox selectYear;
	private ComboBox selectMonth;
	private GridLayout layoutFilter;
	private Button buttonAccept;
	private GeneralFunction generalFunction;
	

	public ApprovalViewImpl(GeneralFunction function){
		generalFunction=function;
	}
	public void init(){
		table=new Table();
        table.setWidth("100%");
        table.setSortEnabled(true);
        table.setHeight("400px");

		table.addContainerProperty("No",Integer.class, null);
		table.addContainerProperty("Nama",String.class, null);
		table.addContainerProperty("Satuan",String.class, null);
		table.addContainerProperty("HNA + PPN 10%",String.class, null);
		table.addContainerProperty("Kebutuhan",Integer.class, null);
		table.addContainerProperty("Perkiraan Jumlah Harga",String.class, null);
		table.addContainerProperty("Produsen",String.class, null);
		table.addContainerProperty("Distributor",String.class, null);
		table.addContainerProperty("Disetujui?",CheckBox.class, null);
		table.addContainerProperty("Jumlah Disetujui",TextField.class, null);
		
		//data dummy
		Integer no=new Integer(1);
		Integer kebutuhan=new Integer(100);
		String nama=new String("Panadol");
		String satuan=new String("box");
		String hna=new String("Rp 30.000");
		String perkiraanHarga=new String("Rp 30.000.000");
		String produsen=new String("Kimia Farma");
		String distributor=new String("PT Media Mantap");
		CheckBox disetujui=new CheckBox("Disetujui", false);
		TextField field=new TextField(){
			{
				setWidth("100px");
			}
		};
		
		
		table.addItem(new Object[]{no, nama, satuan, hna, kebutuhan, perkiraanHarga, produsen, distributor, disetujui, field}, 1);
		
		labelFilter=new Label("<b style='font-size:14px'>Filter Data Kebutuhan : </b> ", ContentMode.HTML);
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
		labelTitle =new Label("<h2>Persetujuan Rencana Kebutuhan</h2>", ContentMode.HTML);
		
		buttonAccept =new Button("Setujui Barang Terpilih");
		buttonAccept.addClickListener(this);
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
		
		CssLayout buttonLayout=new CssLayout(){
			{
				this.addComponent(buttonAccept);
				this.addStyleName("float-right");
			}
		};
		
		this.addComponents(labelTitle, layoutFilter, table, buttonLayout);
		
	}
	ApprovalListener listener;
	public void setListener(ApprovalListener listener){
		this.listener=listener;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonAccept){
			listener.buttonClick("buttonAccept", new String[]{"a","b"});
		}
	}

}
