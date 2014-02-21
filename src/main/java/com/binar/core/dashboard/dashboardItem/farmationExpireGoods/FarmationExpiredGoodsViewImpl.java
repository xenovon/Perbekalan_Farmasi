package com.binar.core.dashboard.dashboardItem.farmationExpireGoods;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
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

public class FarmationExpiredGoodsViewImpl  extends Panel implements FarmationExpiredGoodsView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan

 */
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private DateManipulator date;
	private TextManipulator text;
	public FarmationExpiredGoodsViewImpl(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
		this.text=function.getTextManipulator();
	}
	
	@Override
	public void init() {
		table=new Table();
		table.setSizeFull();
		table.setPageLength(5);
		table.setWidth(function.DASHBOARD_TABLE_WIDTH);
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Nama Barang", String.class,null);
				addContainerProperty("Jumlah Barang",String.class,null);
				addContainerProperty("Tanggal Masuk", String.class,null);
				addContainerProperty("Tanggal Kadaluarsa",String.class,null);
			}
		};
		table.setContainerDataSource(tableContainer);
		buttonGo=new Button("Ke Halaman Pengajuan Penghapusan");
		buttonGo.addClickListener(this);
		
		buttonRefresh=new Button("Refresh");
		buttonRefresh.addClickListener(this);

		construct();
	}

	@Override
	public void construct() {
		setCaption("Obat Mendekati Kadaluarsa");
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
	public void updateTable(List<GoodsReception> data) {
		tableContainer.removeAllItems();
		System.out.println(data.size());

		for(GoodsReception datum:data){


			Item item=tableContainer.addItem(datum.getIdGoodsReceipt());
			item.getItemProperty("Nama Barang").setValue(datum.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods().getName());
			item.getItemProperty("Jumlah Barang").setValue(text.intToAngka(datum.getQuantityReceived()));
			item.getItemProperty("Tanggal Masuk").setValue(date.dateToText(datum.getDate(), true));
			item.getItemProperty("Tanggal Kadaluarsa").setValue(date.dateToText(datum.getExpiredDate(), true));
		}
	}
	private FarmationExpiredGoodsListener listener;
	public void setListener(FarmationExpiredGoodsListener listener) {
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
