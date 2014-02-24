package com.binar.core.dashboard.dashboardItem.farmationExpiredGoodsStatus;

import java.util.List;

import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.generalFunction.AcceptancePyramid;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class FarmationExpiredGoodsStatusViewImpl  extends Panel implements FarmationExpiredGoodsStatusView, ClickListener{
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
	private Label labelEmpty;
	private AcceptancePyramid accept;
	public FarmationExpiredGoodsStatusViewImpl(GeneralFunction function) {
		this.function=function;
		this.accept=function.getAcceptancePyramid();
		this.text=function.getTextManipulator();
		this.date=function.getDate();
	}
	
	@Override
	public void init(String month) {
		table=new Table();
		table.setSizeFull();
		table.setPageLength(6);
		table.setWidth(function.DASHBOARD_TABLE_WIDTH);
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Tanggal Pengajuan", String.class,null);
				addContainerProperty("Nama Barang", String.class,null);
				addContainerProperty("Satuan",String.class,null);
				addContainerProperty("Status Pengajuan", String.class,null);
				addContainerProperty("Jumlah",String.class,null);
			}
		};
		table.setContainerDataSource(tableContainer);
		buttonGo=new Button("Ke Halaman Pengajuan Penghapusan");
		buttonGo.addClickListener(this);
		
		labelEmpty=new Label("Belum ada pengajuan penghapusan barang");
		labelEmpty.setVisible(false);
		
		buttonRefresh=new Button("Refresh");
		buttonRefresh.addClickListener(this);

		construct(month);
	}

	@Override
	public void construct(String month) {
		setCaption("Status Pengajuan Penghapusan Barang Periode <br/>"+month);
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
				addComponent(labelEmpty);
				addComponent(table);
				addComponent(layout);
			}
		});
		
	}
	public void setEmptyDataView() {
		tableContainer.removeAllItems();
		labelEmpty.setVisible(true);
		table.setVisible(false);
	}

	@Override
	public void updateTable(List<DeletedGoods> data) {
		labelEmpty.setVisible(false);
		table.setVisible(true);

		tableContainer.removeAllItems();
		System.out.println(data.size());

		for(DeletedGoods datum:data){
			Item item=tableContainer.addItem(datum.getIdDeletedGoods());
			item.getItemProperty("Tanggal Pengajuan").setValue(date.dateToText(datum.getDeletionDate(),true));
			item.getItemProperty("Nama Barang").setValue(datum.getGoods().getName());
			item.getItemProperty("Satuan").setValue(datum.getGoods().getUnit());
			item.getItemProperty("Status Pengajuan").setValue(accept.acceptedBy(datum.getAcceptance()));
			item.getItemProperty("Jumlah").setValue(text.intToAngka(datum.getQuantity()));
		}
	}
	private FarmationExpiredGoodsStatusListener listener;
	public void setListener(FarmationExpiredGoodsStatusListener listener) {
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
