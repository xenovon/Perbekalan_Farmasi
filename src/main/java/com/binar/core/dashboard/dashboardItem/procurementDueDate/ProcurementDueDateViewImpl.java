package com.binar.core.dashboard.dashboardItem.procurementDueDate;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.Invoice;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class ProcurementDueDateViewImpl  extends Panel implements ProcurementDueDateView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> nama supplier, tanggal jatuh tempo, jumlah hutang

 */
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private DateManipulator date;
	private TextManipulator text;
	public ProcurementDueDateViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
		this.date=function.getDate();
	}
	
	@Override
	public void init() {
		table=new Table();
		table.setSizeFull();
		table.setPageLength(6);
		table.setWidth(function.DASHBOARD_TABLE_WIDTH);
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Nomor", String.class,null);
				addContainerProperty("Nama Tagihan", String.class,null);
				addContainerProperty("Jatuh Tempo",String.class,null);
				addContainerProperty("Total Harga", String.class,null);
				addContainerProperty("Harga Dibayar",String.class,null);
				addContainerProperty("Hutang",String.class,null);
			}
		};
		table.setContainerDataSource(tableContainer);
		buttonGo=new Button("Lebih Lanjut");
		buttonGo.addClickListener(this);
		
		buttonRefresh=new Button("Refresh");
		buttonRefresh.addClickListener(this);

		construct();
	}

	@Override
	public void construct() {
		setCaption("Barang Belum Lunas");
		setHeight(function.DASHBOARD_LAYOUT_HEIGHT);
		setWidth(function.DASHBOARD_TABLE_LAYOUT_WIDTH);
		final GridLayout layout=new GridLayout(2,1){
			{
				setSpacing(true);
				addComponent(buttonGo, 0,0);
				addComponent(buttonRefresh, 1, 0);
			}
		};
		setContent(new VerticalLayout(){
			{
				setSpacing(true);
				setMargin(true);
				addComponent(table);
				addComponent(layout);
			}
		});
		
	}

	@Override
	public void updateTable(List<Invoice> data) {
		tableContainer.removeAllItems();
		System.out.println(data.size());

		for(Invoice datum:data){
			Item item=tableContainer.addItem(datum.getIdInvoice());
			item.getItemProperty("Nomor").setValue(datum.getInvoiceNumber());
			item.getItemProperty("Nama Tagihan").setValue(datum.getInvoiceName());
			item.getItemProperty("Jatuh Tempo").setValue(date.dateToText(datum.getDueDate(), true));
			item.getItemProperty("Total Harga").setValue(text.doubleToRupiah(datum.getTotalPrice()));
			item.getItemProperty("Harga Dibayar").setValue(text.doubleToRupiah(datum.getAmountPaid()));
			item.getItemProperty("Hutang").setValue(text.doubleToRupiah(datum.getTotalPrice()-datum.getAmountPaid()));
		}
	}
	private ProcurementDueDateListener listener;
	public void setListener(ProcurementDueDateListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonGo){
			listener.buttonGo();
		}else if(event.getButton()==buttonRefresh){
			listener.updateTable();
		}
	}
}
