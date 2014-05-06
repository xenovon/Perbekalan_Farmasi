package com.binar.core.procurement.purchaseOrder;

import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.entity.Goods;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.Supplier;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.entity.enumeration.EnumPurchaseOrderType;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
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
	private DateManipulator date;
	public PurchaseOrderViewImpl(GeneralFunction function){
		this.function=function;
		this.date=function.getDate();
		
	}
	
	@Override
	public void init() {
		title=new Label("<h2>Daftar Surat Pesanan</h2>", ContentMode.HTML);
		labelFilter=new Label("<b style='font-size:14px'>Filter : </b> ", ContentMode.HTML);
		labelYear=new Label("Pilih Tahun  :");
		labelMonth=new Label("Pilih Bulan  :");
		
		List<String> yearList=function.getListFactory().createYearList(6, 2, false);
		List<String> monthList=function.getListFactory().createMonthList(true);
		selectYear = new ComboBox("", yearList);
		selectMonth =new ComboBox("", monthList);

		selectYear.setImmediate(true);
		selectMonth.setImmediate(true);

		
		String selectedValue=monthList.get(DateTime.now().getMonthOfYear());
		
		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectMonth.setValue(selectedValue);
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
		table.setHeight("422px");
//		table.setPageLength(10);
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
		 buttonPrint=new Button("Cetak");
			buttonPrint.setDescription("Cetak Surat Pesanan");
			buttonPrint.setIcon(new ThemeResource("icons/image/icon-print.png"));
			buttonPrint.addStyleName("button-table");
			buttonPrint.addClickListener(this);
//	 	 buttonSave=new Button("Simpan PDF");
//			buttonSave.setDescription("Simpan surat pesanan sebagai PDF");
//			buttonSave.setIcon(new ThemeResource("icons/image/icon-save.png"));
//			buttonSave.addStyleName("button-table");
//			buttonSave.addClickListener(this);

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
	public boolean updateTableData(List<PurchaseOrder> data, boolean withEditPurchaseOrder) {
		tableContainer.removeAllItems();
		final boolean withEditPurchaseOrderFinal=withEditPurchaseOrder;
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data Surat Pesanan kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(PurchaseOrder datum:data){
			final PurchaseOrder datumFinal=datum;
			Item item=tableContainer.addItem(datum.getIdPurchaseOrder());
			item.getItemProperty("Nomor Surat").setValue(datum.getPurchaseOrderNumber());
			item.getItemProperty("Nama").setValue(new Label(datum.getPurchaseOrderName()){{setWidth("200px");}});
			item.getItemProperty("Distributor").setValue(datum.getPurchaseOrderItem().get(0).getSupplierGoods().getSupplier().getSupplierName());
			item.getItemProperty("Jenis Surat").setValue(datum.getPurchaseOrderType().toString());
			item.getItemProperty("Tanggal").setValue(date.dateToText(datum.getDate(), true));
			item.getItemProperty("Jumlah Barang").setValue(datum.getPurchaseOrderItem().size());
			item.getItemProperty("Tanggal Dibuat").setValue(date.dateTimeToText(datum.getTimestamp()));
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
					if(withEditPurchaseOrderFinal){
						this.addComponent(buttonDelete, 2, 0);						
						this.addComponent(buttonEdit, 1, 0);											
					}
				}
			});
			
		}

		return true;
	}
	private Window detailWindow;
	private Table tableDetail;
	private IndexedContainer containerDetail;
	
	private VerticalLayout layoutDetail;
	private GridLayout layoutContent;

	//label
	private Label labelPoName;
	private Label labelPoNumber;
	private Label labelDate;
	private Label labelType;
	private Label labelUserResponsible;
	private Label labelRayon;
	private Label labelSupplier;
	private Button buttonPrint;
//	private Button buttonSave;
	
	@Override
	public void showDetailWindow(PurchaseOrder purchaseOrder) {
			layoutDetail = new VerticalLayout(){
				{
					setSpacing(true);
					setMargin(true);
				}
			};
			 labelPoName=new Label();
			 labelPoNumber=new Label();
			 labelDate=new Label();
			 labelType=new Label();
			 labelUserResponsible=new Label();
			 labelRayon=new Label();
			 labelSupplier=new Label();

			layoutContent=new GridLayout(2,7){
				{
					setSpacing(true);
					setMargin(true);
					addComponent(new Label("Nomor Surat "), 0,0 );
					addComponent(new Label("Nama Surat"), 0,1 );
					addComponent(new Label("Tanggal"), 0,2 );
					addComponent(new Label("Tipe Surat"), 0,3 );
					addComponent(new Label("Penanggung Jawab"), 0,4 );
					addComponent(new Label("Rayon"), 0,5 );
					addComponent(new Label("Distributor"), 0,6 );
					
					addComponent(labelPoNumber, 1,0 );
					addComponent(labelPoName, 1,1 );
					addComponent(labelDate, 1,2 );
					addComponent(labelType, 1,3 );
					addComponent(labelUserResponsible, 1,4 );
					addComponent(labelRayon, 1,5 );
					addComponent(labelSupplier, 1,6);
					
				}
			};
			
			tableDetail =new Table("Daftar Item");
				tableDetail.setSizeFull();
				tableDetail.setWidth("100%");
				tableDetail.setHeight("339px");
				
				tableDetail.setSortEnabled(true);
				tableDetail.setImmediate(true);
				tableDetail.setRowHeaderMode(RowHeaderMode.INDEX);

			containerDetail=new IndexedContainer(){
				{
					addContainerProperty("Nama Barang", String.class, null);
					addContainerProperty("Asuransi", String.class, null);
					addContainerProperty("Jumlah", Integer.class, null);
					addContainerProperty("Satuan", String.class, null);
					addContainerProperty("Keterangan", String.class, null);
				}
			};
			
			tableDetail.setContainerDataSource(containerDetail);
			buttonPrint=new Button("Cetak");
			buttonPrint.setDescription("Cetak Surat Pesanan");
			buttonPrint.setIcon(new ThemeResource("icons/image/icon-print.png"));
			buttonPrint.addStyleName("button-table");
			buttonPrint.addClickListener(this);

			layoutDetail.addComponent(new CssLayout(){
				{
					setStyleName("float-right");
					addComponent(buttonPrint);
//					addComponent(buttonSave);
				}
			});
			layoutDetail.addComponent(layoutContent);
			layoutDetail.addComponent(tableDetail);
			
			detailWindow =new Window("Surat Pesanan"){
				{
					center();
					setClosable(true);
					setWidth("700px");
					setHeight("80%");
				}
			};

		detailWindow.setContent(layoutDetail);
		setDetailData(purchaseOrder);
		this.getUI().addWindow(detailWindow);

	}
	
	public void setDetailData(PurchaseOrder purchaseOrder){
		
		labelPoName.setValue(purchaseOrder.getPurchaseOrderName());
		labelPoNumber.setValue(purchaseOrder.getPurchaseOrderNumber());
		labelDate.setValue(date.dateToText(purchaseOrder.getDate(), true));
		labelType.setValue(purchaseOrder.getPurchaseOrderType().toString());
		labelUserResponsible.setValue(purchaseOrder.getUserResponsible().getName());
		labelRayon.setValue(purchaseOrder.getRayon());
		labelSupplier.setValue(purchaseOrder.getPurchaseOrderItem().get(0).
				getSupplierGoods().getSupplier().getSupplierName());
		containerDetail.removeAllItems();
		for(PurchaseOrderItem datum:purchaseOrder.getPurchaseOrderItem()){
			Item item=containerDetail.addItem(datum.getIdPurchaseOrderItem());
			item.getItemProperty("Nama Barang").setValue(datum.getSupplierGoods().getGoods().getName());
			item.getItemProperty("Asuransi").setValue(datum.getSupplierGoods().getGoods().getInsurance().getName());
			item.getItemProperty("Jumlah").setValue(datum.getQuantity());
			item.getItemProperty("Satuan").setValue(datum.getSupplierGoods().getGoods().getUnit());
			item.getItemProperty("Keterangan").setValue(datum.getInformation());
		}
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
		else if(event.getButton()==buttonPrint){
			listener.buttonClick("buttonPrint");
		}
//		}else if(event.getButton()==buttonSave){
//			listener.buttonClick("buttonSave");
//		}
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
	
	Window window;
	
	public void displayForm(Component content, String title, String height){
		displayForm(content, title);
		window.setHeight(height);
	}
	public void displayForm(Component content, String title){
		//menghapus semua window terlebih dahulu
		for(Window window:this.getUI().getWindows()){
			window.close();
		}
		if(window==null){
			window=new Window(title){
				{
					center();
					setClosable(false);
					setWidth("500px");
					setHeight("80%");
				}
			};
		}
		this.getUI().removeWindow(window);
		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
	}
	
	public Button getButtonPrint() {
		return buttonPrint;
	}

	public void hideButtonNew(){
		buttonNewPurchase.setVisible(false);		
	}
	public void showButtonNew(){
		buttonNewPurchase.setVisible(true);
	}

}
