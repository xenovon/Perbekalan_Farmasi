package com.binar.core.dashboard.dashboardItem.farmationRequirementStatus;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.AcceptancePyramid;
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

public class FarmationRequirementStatusViewImpl  extends Panel implements FarmationRequirementStatusView, ClickListener{
/*
 * Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan

 */
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonRefresh;
	private Button buttonGo;
	private GeneralFunction function;
	private Label labelEmpty;
	private TextManipulator text;
	private AcceptancePyramid accept;
	public FarmationRequirementStatusViewImpl(GeneralFunction function) {
		this.function=function;
		this.accept=function.getAcceptancePyramid();
		this.text=function.getTextManipulator();
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
				addContainerProperty("Status",String.class,null);
			}
		};
		table.setContainerDataSource(tableContainer);
		buttonGo=new Button("Ke Halaman Rencana Kebutuhan");
		buttonGo.addClickListener(this);
		labelEmpty=new Label("Belum ada rencana kebutuhan untuk bulan ini");
		labelEmpty.setVisible(false);
		
		buttonRefresh=new Button("Refresh");
		buttonRefresh.addClickListener(this);

		construct(month);
	}

	@Override
	public void construct(String month) {
		setCaption("Status Rencana Kebutuhan Bulan "+month);
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
				addComponent(labelEmpty);
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
	public void updateTable(List<ReqPlanning> data) {
		labelEmpty.setVisible(false);
		table.setVisible(true);
		
		tableContainer.removeAllItems();
		System.out.println(data.size());

		for(ReqPlanning datum:data){
			Item item=tableContainer.addItem(datum.getIdReqPlanning());
			item.getItemProperty("Nama Barang").setValue(datum.getSupplierGoods().getGoods().getName());
			item.getItemProperty("Satuan").setValue(datum.getSupplierGoods().getGoods().getUnit());
			item.getItemProperty("Jumlah Pengajuan").setValue(text.intToAngka(datum.getQuantity()));
			item.getItemProperty("Status").setValue(accept.acceptedBy(datum.getAcceptance()));
			item.getItemProperty("Jumlah disetujui").setValue(text.intToAngka(datum.getAcceptedQuantity()));
			
			
		}
	}
	private FarmationRequirementStatusListener listener;
	public void setListener(FarmationRequirementStatusListener listener) {
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
