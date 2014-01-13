package com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval;

import java.util.List;

import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
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

public class IfrsDeletionApprovalViewImpl  extends Panel implements IfrsDeletionApprovalView, ClickListener{
/*
<<Tanggal Pengajuan>>
<<Nama barang>>
<<Satuan>>
<<Jumlah>>
 */
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private DateManipulator date;
	public IfrsDeletionApprovalViewImpl(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
	}
	
	@Override
	public void init() {
		table=new Table();
		table.setSizeFull();
		table.setPageLength(5);
		table.setWidth("340px");
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Tanggal", String.class,null);
				addContainerProperty("Nama Barang",String.class,null);
				addContainerProperty("Satuan", String.class,null);
				addContainerProperty("Jumlah",String.class,null);
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
		setCaption("Daftar Penghapusan Obat");
		setHeight("350px");
		setWidth("470px");
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
	public void updateTable(List<DeletedGoods> data) {
		tableContainer.removeAllItems();
		System.out.println(data.size());

		for(DeletedGoods datum:data){
			Item item=tableContainer.addItem(datum.getIdDeletedGoods());
			item.getItemProperty("Tanggal").setValue(date.dateToText(datum.getDeletionDate(), true));
			item.getItemProperty("Nama Barang").setValue(datum.getGoods().getName());
			item.getItemProperty("Satuan").setValue(datum.getGoods().getUnit());
			item.getItemProperty("Jumlah").setValue(datum.getQuantity());
		}
	}
	private IfrsDeletionApprovalListener listener;
	public void setListener(IfrsDeletionApprovalListener listener) {
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
