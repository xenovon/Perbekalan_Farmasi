package com.binar.core.inventoryManagement.stockList;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.PurchaseOrder;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.Window;

public class StockListViewImpl  extends VerticalLayout implements StockListView, ClickListener,  ValueChangeListener{


	private GeneralFunction function;
	private StockListListener listener;

	private Label title;
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonNewPurchase;
	private Label labelFilter;
	private TextField inputFilter;
	private ComboBox selectGoods;
	
	private Label labelGoodsName;
	private Label labelGoodsQuantity;
	
	private TableFilter filter;
	private TextManipulator text;
	private ComboBox selectYear;
	private ComboBox selectMonth;
	private Label labelYear;
	private Label labelMonth;
	private DateManipulator date;
	public StockListViewImpl(GeneralFunction function){
		this.function=function;
		this.date=function.getDate();
		
	}
	
	@Override
	public void init() {
		title=new Label("<h2>Stok Barang Gudang Farmasi</h2>", ContentMode.HTML);
		labelFilter=new Label("<b style='font-size:14px'>Filter : </b> ", ContentMode.HTML);
		labelYear=new Label("Pilih Tahun  :");
		labelMonth=new Label("Pilih Bulan  :");
		
		List<String> yearList=function.getListFactory().createYearList(8, 3, false);
		List<String> monthList=function.getListFactory().createMonthList(true);
		selectYear = new ComboBox("", yearList);
		selectMonth =new ComboBox("", monthList);

		selectYear.setImmediate(true);
		selectMonth.setImmediate(true);

		
		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectMonth.setValue(monthList.get(Calendar.getInstance().get(Calendar.MONTH)));
		selectYear.setValue(yearList.get(2));
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
		
		selectGoods=new ComboBox("Pilih Barang");
		selectGoods.addValueChangeListener(this);
		selectGoods.setWidth("200px");
		selectGoods.setImmediate(true);
		labelGoodsName=new Label();
		labelGoodsQuantity=new Label();

		
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
		table.setHeight("450px");
		table.setPageLength(10);
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Nomor Surat", String.class,null);
				addContainerProperty("Nama", Label.class,null);
				addContainerProperty("Distributor",String.class,null);
				addContainerProperty("Jenis Surat", String.class,null);
				addContainerProperty("Tanggal",String.class,null);
				addContainerProperty("Jumlah Barang",Integer.class,null);
				addContainerProperty("Tanggal Dibuat", String.class, null);
				addContainerProperty("Operasi", GridLayout.class,null);
			}
		};
		table.setContainerDataSource(tableContainer);
		selectMonth.setValue("Semua Bulan");

		construct();
	}
	
	@Override
	public void construct() {
		this.addComponent(title);
		this.addComponent(selectGoods);

		GridLayout layoutFilter =new GridLayout(5,1);
			layoutFilter.setMargin(true);
			layoutFilter.setSpacing(true);
			layoutFilter.addComponent(labelFilter, 0, 0);
			layoutFilter.addComponent(labelYear, 1, 0);
			layoutFilter.addComponent(selectYear, 2, 0);
			layoutFilter.addComponent(labelMonth, 3, 0);
			layoutFilter.addComponent(selectMonth, 4, 0);
			
		this.addComponent(layoutFilter);
		this.addComponent(new GridLayout(3,3){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(labelFilter, 0, 0);
				addComponent(inputFilter, 1, 0);
				addComponent(buttonNewPurchase, 2,0);
				addComponent(new Label("Nama Barang"), 0,1);
				addComponent(new Label("Stok Saat Ini"), 0,2);
				addComponent(labelGoodsName, 1,1);
				addComponent(labelGoodsQuantity, 1,2);
			}
		});
		this.addComponent(table);
	}


	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectGoods){
			listener.valueChange("selectGoods");
		}
		if(event.getProperty()==selectMonth){
			listener.valueChange("selectMonth");
		}
		if(event.getProperty()==selectYear ){
			listener.valueChange("selectYear");
		}
		
	}

	@Override
	public void setListener(StockListListener listener) {
		this.listener=listener;
	}

	@Override
	public boolean updateTableData(List<GoodsConsumption> consumption, List<GoodsReception> reception) {
		
		return false;
	}


	@Override
	public String getSelectedPeriod() {
		return selectMonth.getValue()+"-"+selectYear.getValue();
	}

	private Window windowDetail;
	private Label labelGoods; 
	private Label labelQuantity;
	private Label labelConsumptionDate;
	private Label labelInformation;	
	private Label labelWard;
	private GridLayout layoutDetailConsumption;
	
	@Override
	public void showDetailConsumption(GoodsConsumption consumption) {
		if(layoutDetailConsumption==null){
			layoutDetailConsumption=new GridLayout(2,5){
				{
					setSpacing(true);
					setMargin(true);
					setWidth("450px");
					addComponent(new Label("Nama Barang"), 0, 0);
					addComponent(new Label("Jumlah"), 0,1);
					addComponent(new Label("Tanggal"), 0, 2);
					addComponent(new Label("Instalasi"), 0,3);
					addComponent(new Label("Keterangan"), 0,4);
	
				}
			};
			
			labelGoods=new Label();
			labelQuantity=new Label();
			labelConsumptionDate=new Label();
			labelInformation=new Label();
			labelWard=new Label();
			
			layoutDetailConsumption.addComponent(labelGoods, 1,0);
			layoutDetailConsumption.addComponent(labelQuantity, 1,1);
			layoutDetailConsumption.addComponent(labelConsumptionDate, 1,2);
			layoutDetailConsumption.addComponent(labelInformation, 1,3);
			layoutDetailConsumption.addComponent(labelWard, 1, 4);
		}
		setLabelData(consumption);
		if(windowDetail==null){
			windowDetail=new Window("Detail Konsumsi Barang"){
				{
					center();
					setWidth("500px");
					
				}
			};
		}
		windowDetail.setContent(layoutDetailConsumption);
		this.getUI().addWindow(windowDetail);

	}
	private void setLabelData(GoodsConsumption consumption){
		labelGoods.setValue(consumption.getGoods().getName());
		labelQuantity.setValue(consumption.getQuantity()+ " "+consumption.getGoods().getUnit());
		labelConsumptionDate.setValue(date.dateToText(consumption.getConsumptionDate(), true));
		labelInformation.setValue(consumption.getInformation());
		String ward=function.getSetting().getWard().get(consumption.getWard());
		if(ward==null){
			ward=consumption.getWard();
		}
		labelWard.setValue(ward);

	}
	private Label labelGoodsReception; 
	private Label labelSupplierName;
	private Label labelQuantityReception;
	private Label labelReceptionDate;
	private Label labelInformationReception;	
	private Label labelExpiredDate;
	private GridLayout layoutDetailReception;

	@Override
	public void showDetailReception(GoodsReception reception) {
		if(layoutDetailReception==null){
			layoutDetailReception=new GridLayout(2,6){
				{
					setSpacing(true);
					setMargin(true);
					setWidth("450px");
					addComponent(new Label("Nama Barang"), 0, 0);
					addComponent(new Label("Nama Distributor"), 0,1);
					addComponent(new Label("Jumlah"), 0, 2);
					addComponent(new Label("Tanggal Penerimaan"), 0,3);
					addComponent(new Label("Keterangan"), 0,4);
					addComponent(new Label("Tanggal Kadaluarsa"), 0,5);
				}
			};
			 labelGoodsReception=new Label(); 
			 labelSupplierName=new Label();
			 labelQuantityReception=new Label();
			 labelReceptionDate=new Label();
			 labelInformationReception=new Label();	
			 labelExpiredDate=new Label();			
			layoutDetailReception.addComponent(labelGoodsReception, 1,0);
			layoutDetailReception.addComponent(labelSupplierName, 1,1);
			layoutDetailReception.addComponent(labelQuantityReception, 1,2);
			layoutDetailReception.addComponent(labelReceptionDate, 1,3);
			layoutDetailReception.addComponent(labelInformationReception, 1,4);
			layoutDetailReception.addComponent(labelExpiredDate, 1, 5);
		}
		setLabelDataReception(reception);
		if(windowDetail==null){
			windowDetail=new Window("Detail Penerimaan Barang"){
				{
					center();
					setWidth("500px");
				}
			};
		}
		windowDetail.setContent(layoutDetailReception);
		this.getUI().addWindow(windowDetail);


	}
	private void setLabelDataReception(GoodsReception reception){
		 labelGoodsReception.setValue(reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods().getName()); 
		 labelSupplierName.setValue(reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getSupplier().getSupplierName());
		 labelQuantityReception.setValue(reception.getQuantityReceived() + " "+reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods().getUnit());
		 labelReceptionDate.setValue(date.dateToText(reception.getDate(), true));
		 labelInformationReception.setValue(reception.getInformation());	
		 labelExpiredDate.setValue(date.dateToText(reception.getDate(), true));			

	}
	@Override
	public String getSelectedGoods() {
		return (String) selectGoods.getValue();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonNewPurchase){
			listener.buttonClick("buttonNewPurchase");
		}
	}	

	@Override
	public void setComboGoodsData(Map<String, String> data) {		
        for (Map.Entry<String, String> entry : data.entrySet()) {
        	selectGoods.addItem(entry.getKey());
        	selectGoods.setItemCaption(entry.getKey(), entry.getValue());
        }
		
	}
}
