package com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption;

import java.util.List;

import com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption.FarmationGoodsConsumptionView.FarmationGoodsConsumptionListener;
import com.binar.entity.Goods;
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

public class FarmationGoodsConsumptionViewImpl  extends Panel implements FarmationGoodsConsumptionView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan

 */
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	public FarmationGoodsConsumptionViewImpl(GeneralFunction function) {
		this.function=function;
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
				addContainerProperty("Nama Barang", String.class,null);
				addContainerProperty("Jumlah Stok",String.class,null);
				addContainerProperty("Stok Minimal", String.class,null);
				addContainerProperty("Satuan",String.class,null);
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
		setCaption("Obat Fast-Moving dengan Stok Minimum");
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
	public void updateTable(List<Goods> data) {
		tableContainer.removeAllItems();
		System.out.println(data.size());

		for(Goods datum:data){
			Item item=tableContainer.addItem(datum.getIdGoods());
			item.getItemProperty("Nama Barang").setValue(datum.getName());
			item.getItemProperty("Jumlah Stok").setValue(datum.getCurrentStock());
			item.getItemProperty("Stok Minimal").setValue(datum.getMinimumStock());
			item.getItemProperty("Satuan").setValue(datum.getUnit());
		}
	}
	private FarmationGoodsConsumptionListener listener;
	public void setListener(FarmationGoodsConsumptionListener listener) {
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
