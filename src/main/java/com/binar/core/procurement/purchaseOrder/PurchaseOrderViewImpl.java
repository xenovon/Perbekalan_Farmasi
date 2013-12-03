package com.binar.core.procurement.purchaseOrder;

import java.util.Calendar;
import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.Supplier;
import com.binar.entity.enumeration.EnumGoodsType;
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
import com.vaadin.ui.ComboBox;
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
	private ComboBox selectYear;
	private ComboBox selectMonth;
	private Label labelYear;
	private Label labelMonth;
	public PurchaseOrderViewImpl(GeneralFunction function){
		this.function=function;
		
	}
	
	@Override
	public void init() {
		title=new Label("<h2>Daftar Surat Pesanan</h2>", ContentMode.HTML);
		labelFilter=new Label("<b style='font-size:14px'>Filter : </b> ", ContentMode.HTML);
		labelYear=new Label("Pilih Tahun  :");
		labelMonth=new Label("Pilih Bulan  :");
		
		List<String> yearList=function.getListFactory().createYearList(6, 2, true);
		List<String> monthList=function.getListFactory().createMonthList(true);
		selectYear = new ComboBox("", yearList);
		selectMonth =new ComboBox("", monthList);

		selectYear.setImmediate(true);
		selectMonth.setImmediate(true);

		
		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectYear.setValue(yearList.get(0));
		selectMonth.setValue(monthList.get(Calendar.getInstance().get(Calendar.MONTH)));
		for(String month:monthList){
			System.out.println("Bulan "+month);
		}
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		selectMonth.setTextInputAllowed(false);
		selectYear.setTextInputAllowed(false);
		selectYear.addStyleName("non-caption-form");
		selectMonth.addStyleName("non-caption-form");

		selectMonth.setWidth("120px");
		selectYear.setWidth("120px");
	
		selectMonth.addValueChangeListener(this);
		selectYear.addValueChangeListener(this);
		
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
				addContainerProperty("Nomor Surat Pesanan", String.class, null);
				addContainerProperty("Nama", String.class, null);
				addContainerProperty("Distributor", String.class, null);
				addContainerProperty("Jenis Surat Pesanan", String.class,null);
				addContainerProperty("Tanggal Surat", String.class, null);
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
		GridLayout layoutFilter =new GridLayout(5,1);
			layoutFilter.setMargin(true);
			layoutFilter.setSpacing(true);
			
			layoutFilter.addComponent(labelFilter, 0, 0);
			layoutFilter.addComponent(labelYear, 1, 0);
			layoutFilter.addComponent(selectYear, 2, 0);
			layoutFilter.addComponent(labelMonth, 3, 0);
			layoutFilter.addComponent(selectMonth, 4, 0);
			
		this.addComponent(layoutFilter);
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
			Notification.show("Data Surat Pesanan kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(PurchaseOrder datum:data){
			
			final PurchaseOrder datumFinal=datum;
			Item item=tableContainer.addItem(datum.getIdPurchaseOrder());
			item.getItemProperty("Nomor Surat Pesanan").setValue(datum.getPurchaseOrderNumber());
			item.getItemProperty("Nama").setValue(datum.getPurchaseOrderName());
			item.getItemProperty("Distributor").setValue(getSupplierName(datum));
			item.getItemProperty("Jenis Surat Pesanan").setValue(getPurchaseType(datum));
			item.getItemProperty("Tanggal Surat").setValue(datum.getDate_format());
			item.getItemProperty("Jumlah Barang").setValue(datum.getPurchaseOrderItem().size());
			item.getItemProperty("Tanggal Dibuat").setValue(datum.getTimeStamp_format());
			item.getItemProperty("Operasi").setValue(new GridLayout(3,1){
			{
					Button buttonEdit=new Button();
					buttonEdit.setDescription("Ubah data ini");
					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
					buttonEdit.addStyleName("button-table");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.editClick(datumFinal.getIdPurchaseOrder());
						}
					});
				
					Button buttonShow=new Button();
					buttonShow.setDescription("Lihat Lebih detail");
					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
					buttonShow.addStyleName("button-table");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showClick(datumFinal.getIdPurchaseOrder());;
						}
					});
					
					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.deleteClick(datumFinal.getIdPurchaseOrder());
						}
					});
					this.setSpacing(true);
					this.setMargin(false);
					this.addComponent(buttonShow, 0, 0);
					this.addComponent(buttonDelete, 2, 0);						
					this.addComponent(buttonEdit, 1, 0);					
				}
			});
			
		}

		return false;
	}
	private EnumGoodsType getPurchaseTypeEnum(PurchaseOrder purchaseOrder){
		return purchaseOrder.getPurchaseOrderItem().
				get(0).getSupplierGoods().getGoods().getType();		
	}
	private String getPurchaseType(PurchaseOrder purchaseOrder){
		String goodsType;
		try {
			EnumGoodsType enumGoodsType=purchaseOrder.getPurchaseOrderItem().
					get(0).getSupplierGoods().getGoods().getType();
			if(enumGoodsType==EnumGoodsType.NARKOTIKA ||
					enumGoodsType==EnumGoodsType.PSIKOTOPRIKA){
				goodsType=enumGoodsType.toString().toLowerCase();
			}else{
				goodsType="Biasa";
			}
		} catch (Exception e) {
			goodsType="Biasa";
		}
		return goodsType;
	}
	private String getSupplierName(PurchaseOrder purchaseOrder){
		String supplierName;
		try {
			supplierName=purchaseOrder.getPurchaseOrderItem().
					get(0).getSupplierGoods().getSupplier().getSupplierName();
		} catch (Exception e) {
			supplierName="";
		}		
		return supplierName;
	}
	private String generateDescription(PurchaseOrder purchaseOrder){
		String goodsType=getPurchaseType(purchaseOrder);
		String supplierName=getSupplierName(purchaseOrder);
		String date=function.getDate().dateToText(purchaseOrder.getDate());
		return "Surat Pesanan "+goodsType+", bulan "+date+","
				+ " Untuk Distributor "+supplierName;
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
		if(event.getButton()==buttonNewPurchase){
			listener.buttonClick("buttonNewPurchase");
		}
	}
	@Override
	public String getSelectedPeriod() {
		return selectMonth.getValue()+"-"+selectYear.getValue();
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectMonth){
			listener.valueChange("selectMonth");
		}else if(event.getProperty()==selectYear){
			listener.valueChange("selectYear");
		}
	}

}
