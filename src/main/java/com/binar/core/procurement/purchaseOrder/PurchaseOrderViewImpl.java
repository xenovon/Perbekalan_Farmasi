package com.binar.core.procurement.purchaseOrder;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.Supplier;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class PurchaseOrderViewImpl extends VerticalLayout implements PurchaseOrderView, ValueChangeListener, ClickListener{


	private GeneralFunction function;
	private PurchaseOrderListener listener;

	private Label title;
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonNewPurchase;
	private Label labelFilter;
	private TextField inputFilter;
	
	private TableFilter filter;
	private TextManipulator text;

	public PurchaseOrderViewImpl(GeneralFunction function){
		this.function=function;
		
	}
	
	@Override
	public void init() {
		title=new Label("<h2>Daftar Surat Pesanan</h2>", ContentMode.HTML);
		
		filter=function.getFilter("");
		inputFilter=new TextField(){
			{
				setImmediate(true);
				addTextChangeListener(new TextChangeListener() {
					
					@Override
					public void textChange(TextChangeEvent event) {
						if(filter!=null){
							tableContainer.removeAllContainerFilters();
						}
						//set filter baru
						filter.updateData(event.getText());
						tableContainer.addContainerFilter(filter);
					}
				});
			}
		};
		labelFilter=new Label("Filter Data");
		buttonNewPurchase=new Button("Buat Surat Pesanan");
		buttonNewPurchase.addClickListener(this);
		
		table=new Table();
		table.setSizeFull();
		table.setWidth("100%");
		table.setHeight("439px");
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Nama Surat Pesanan", String.class, null);
				addContainerProperty("Jenis Surat Pesanan", String.class,null);
				addContainerProperty("Periode", String.class, null);
				addContainerProperty("Jumlah Barang", String.class, null);
				addContainerProperty("Tanggal Dibuat", Integer.class, null);
				addContainerProperty("Operasi", GridLayout.class,null);
				
			}
		};
		table.setContainerDataSource(tableContainer);
		construct();
	}
	@Override
	public void construct() {
		this.addComponent(title);
		this.addComponent(new GridLayout(3,1){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(labelFilter, 0, 0);
				addComponent(inputFilter, 1, 0);
				addComponent(buttonNewPurchase, 2,0);
			}
		});
		this.addComponent(table);
	}	

	@Override
	public boolean updateTableData(List<PurchaseOrder> data) {
		tableContainer.removeAllItems();
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data Supplier kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(PurchaseOrder datum:data){
//			final Supplier datumFinal=datum;
//			Item item = tableContainer.addItem(datum.getIdSupplier());
//			item.getItemProperty("Singkatan Distributor").setValue(datum.getSupplierAbbr());
//			item.getItemProperty("Nama Distributor").setValue(datum.getSupplierName());
//			item.getItemProperty("Faksimile").setValue(datum.getFax());
//			item.getItemProperty("Telepon").setValue(datum.getPhoneNumber());			
//			item.getItemProperty("Operasi").setValue(new GridLayout(3,1){
//			{
//					Button buttonEdit=new Button();
//					buttonEdit.setDescription("Ubah data ini");
//					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
//					buttonEdit.addStyleName("button-table");
//					buttonEdit.addClickListener(new ClickListener() {
//						
//						@Override
//						public void buttonClick(ClickEvent event) {
//							listener.editClick(datumFinal.getIdSupplier()+"");
//						}
//					});
//				
//					Button buttonShow=new Button();
//					buttonShow.setDescription("Lihat Lebih detail");
//					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
//					buttonShow.addStyleName("button-table");
//					buttonShow.addClickListener(new ClickListener() {
//						
//						@Override
//						public void buttonClick(ClickEvent event) {
//							listener.showClick(datumFinal.getIdSupplier()+"");;
//						}
//					});
//					
//					Button buttonDelete=new Button();
//					buttonDelete.setDescription("Hapus Data");
//					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
//					buttonDelete.addStyleName("button-table");
//					buttonDelete.addClickListener(new ClickListener() {
//						
//						@Override
//						public void buttonClick(ClickEvent event) {
//							listener.deleteClick(datumFinal.getIdSupplier()+"");
//						}
//					});
//					this.setSpacing(true);
//					this.setMargin(false);
//					this.addComponent(buttonShow, 0, 0);
//					this.addComponent(buttonDelete, 2, 0);						
//					this.addComponent(buttonEdit, 1, 0);					
//				}
//			});
			
		}

		return false;
	}

	@Override
	public void showDetailWindow(PurchaseOrder purchaseOrder) {

	}

	@Override
	public void setListener(PurchaseOrderListener listener) {
		this.listener=listener;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
