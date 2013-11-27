package com.binar.core.procurement.purchaseOrder;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Table.RowHeaderMode;

public class PurchaseOrderViewImpl implements PurchaseOrderView, ValueChangeListener, ClickListener{


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
				addContainerProperty("Kode Barang", String.class, null);
				addContainerProperty("Nama Barang", String.class,null);
				addContainerProperty("Tipe Barang", String.class, null);
				addContainerProperty("Satuan", String.class, null);
				addContainerProperty("Stok Saat Ini", Integer.class, null);
				addContainerProperty("Stok Minimum", Integer.class, null);
				addContainerProperty("Kemasan", String.class,null);
				addContainerProperty("Kategori", String.class, null);
				addContainerProperty("HET", String.class, null);
				addContainerProperty("Barang Penting?", String.class, null);
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
				addComponent(buttonInput, 2,0);
			}
		});
		this.addComponent(table);
	}	

	@Override
	public boolean updateTableData(List<Goods> data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showDetailWindow(Goods goods) {
		// TODO Auto-generated method stub
		
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
