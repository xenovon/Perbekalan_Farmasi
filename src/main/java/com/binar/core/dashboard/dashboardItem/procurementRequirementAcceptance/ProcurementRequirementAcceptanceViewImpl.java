package com.binar.core.dashboard.dashboardItem.procurementRequirementAcceptance;

import java.util.List;

import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusView.FarmationRequirementStatusListener;
import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
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

public class ProcurementRequirementAcceptanceViewImpl  extends Panel implements ProcurementRequirementAcceptanceView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan

DONE

 */
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private TextManipulator text;
	public ProcurementRequirementAcceptanceViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
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
			{ /*
			<<Nama barang>>
<<Satuan>>
<<Jumlah pengajuan>>
<<Jumlah disetujui>>
<<Oleh*>>
			*/
				addContainerProperty("Nama Barang", String.class,null);
				addContainerProperty("Satuan",String.class,null);
				addContainerProperty("Jumlah Pengajuan", String.class,null);
				addContainerProperty("Jumlah disetujui",String.class,null);
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
		setCaption("Rencana Kebutuhan Yang Sudah Disetujui");
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
	public void updateTable(List<ReqPlanning> data) {
		tableContainer.removeAllItems();
		System.out.println(data.size());

		for(ReqPlanning datum:data){
			Item item=tableContainer.addItem(datum.getIdReqPlanning());
			item.getItemProperty("Nama Barang").setValue(datum.getSupplierGoods().getGoods().getName());
			item.getItemProperty("Satuan").setValue(datum.getSupplierGoods().getGoods().getUnit());
			item.getItemProperty("Jumlah Pengajuan").setValue(text.intToAngka(datum.getQuantity()));
			item.getItemProperty("Jumlah disetujui").setValue(text.intToAngka(datum.getAcceptedQuantity()));
			
			
		}
	}
	private ProcurementRequirementAcceptanceListener listener;
	public void setListener(ProcurementRequirementAcceptanceListener listener) {
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
