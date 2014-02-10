package com.binar.core.procurement.invoice;

import java.util.Calendar;
import java.util.List;

import com.binar.entity.Invoice;
import com.binar.entity.InvoiceItem;
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
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class InvoiceViewImpl extends VerticalLayout implements InvoiceView, ClickListener, ValueChangeListener {

	
	/*  
	 * (non-Javadoc)
	 * @see com.binar.core.procurement.invoice.InvoiceView#init()
	 *  INVOICE
		idInvoice
		Nomor invoice
		nama invoice
		jatuh tempo
		timestamp
		total tagihan
		jumlah dibayar
		jumlah item
		
		
		INVOICE ITEM
		invoice
		batch
		discount
		price
		pricePPN
		quantity
	 */
	
	private GeneralFunction function;
	private InvoiceListener listener;

	private Label title;
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonNewInvoice;
	private Label labelFilter;
	private TextField inputFilter;
	
	private TableFilter filter;
	private TextManipulator text;
	private ComboBox selectYear;
	private ComboBox selectMonth;
	private Label labelYear;
	private Label labelMonth;
	private DateManipulator date;

	public InvoiceViewImpl(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
		this.text=function.getTextManipulator();
	}
	
	@Override
	public void init() {
		title=new Label("<h2>Daftar Faktur</h2>", ContentMode.HTML);
		labelFilter=new Label("<b style='font-size:14px'>Filter : </b> ", ContentMode.HTML);
		labelYear=new Label("Pilih Tahun  :");
		labelMonth=new Label("Pilih Bulan  :");
		
		List<String> yearList=function.getListFactory().createYearList(6, 2, false);
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
		buttonNewInvoice=new Button("Buat Faktur");
		buttonNewInvoice.addClickListener(this);
		
		table=new Table();
		table.setSizeFull();
		table.setWidth("98%");
		table.setHeight("420px");
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Nomor Faktur", String.class,null);
				addContainerProperty("Nama", Label.class,null);
				addContainerProperty("Distributor",String.class,null);
				addContainerProperty("Jatuh Tempo",String.class,null);
				addContainerProperty("Total Tagihan", String.class,null);
				addContainerProperty("Jumlah Dibayar", String.class,null);
				addContainerProperty("Lunas?", String.class,null);
				addContainerProperty("Jumlah Item", Integer.class, null);
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
				addComponent(buttonNewInvoice, 2,0);
			}
		});
		this.addComponent(table);
		
	}

	@Override
	public void setListener(InvoiceListener listener) {
		this.listener=listener;
	}

	@Override
	public boolean updateTableData(List<Invoice> data, boolean withEditInvoice) {
		tableContainer.removeAllItems();
		final boolean withEditInvoiceFinal=withEditInvoice;
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data Faktur kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(Invoice datum:data){
			final Invoice datumFinal=datum;
			Item item=tableContainer.addItem(datum.getIdInvoice());			
			item.getItemProperty("Nomor Faktur").setValue(datum.getInvoiceNumber());
			item.getItemProperty("Nama").setValue(new Label(datum.getInvoiceName()){{setWidth("200px");}});
			item.getItemProperty("Distributor").setValue(datum.getInvoiceItem().get(0).
					getPurchaseOrderItem().getSupplierGoods().getSupplier().getSupplierName());
			item.getItemProperty("Jatuh Tempo").setValue(date.dateToText(datum.getDueDate(), true));
			item.getItemProperty("Total Tagihan").setValue(text.doubleToRupiah(datum.getTotalPrice()));
			item.getItemProperty("Jumlah Dibayar").setValue(text.doubleToRupiah(datum.getAmountPaid()));
			if(datum.getTotalPrice()<=datum.getAmountPaid()){
				item.getItemProperty("Lunas?").setValue("Lunas");
			}else{
				item.getItemProperty("Lunas?").setValue("Belum Lunas");
			}
			item.getItemProperty("Jumlah Item").setValue(datum.getInvoiceItem().size());
			item.getItemProperty("Operasi").setValue(new GridLayout(3,1){
			{
					Button buttonEdit=new Button();
					buttonEdit.setDescription("Ubah data ini");
					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
					buttonEdit.addStyleName("button-table");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.editClick(datumFinal.getIdInvoice());
						}
					});
				
					Button buttonShow=new Button();
					buttonShow.setDescription("Lihat Lebih detail");
					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
					buttonShow.addStyleName("button-table");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showClick(datumFinal.getIdInvoice());;
						}
					});
					
					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.deleteClick(datumFinal.getIdInvoice());
						}
					});
					this.setSpacing(true);
					this.setMargin(false);
					this.addComponent(buttonShow, 0, 0);
					if(withEditInvoiceFinal){
						this.addComponent(buttonDelete, 2, 0);						
						this.addComponent(buttonEdit, 1, 0);											
					}
				}
			});
			
		}

		return true;
	}
	
	/*  
	 * (non-Javadoc)
	 * @see com.binar.core.procurement.invoice.InvoiceView#init()
	 *  INVOICE
		idInvoice
		Nomor invoice
		nama invoice
		jatuh tempo
		timestamp
		total tagihan
		jumlah dibayar
		jumlah item
		
		
		INVOICE ITEM
		invoice
		batch
		discount
		price
		pricePPN
		quantity
	 */
	

	
	private Label labelInvoiceNumber;
	private Label labelInvoiceName;
	private Label labelDueDate;
	private Label labelTimestamp;
	private Label labelTotalPrice;
	private Label labelAmountPaid;
	private Label labelItemCount;
	private Label labelInvoiceDate;
	
	private Table tableDetail;
	private Window detailWindow;
	private IndexedContainer containerDetail;
	
	private VerticalLayout layoutDetail;
	private GridLayout layoutContent;
	
	@Override
	public void showDetailWindow(Invoice invoice) {
		if(detailWindow==null){
			layoutDetail = new VerticalLayout(){
				{
					setSpacing(true);
					setMargin(true);
				}
			};
			 labelInvoiceNumber=new Label();
			 labelInvoiceName=new Label();
			 labelDueDate=new Label();
			 labelTimestamp=new Label();
			 labelTotalPrice=new Label();
			 labelAmountPaid=new Label();
			 labelItemCount=new Label();
			 labelInvoiceDate=new Label();
			
			layoutContent=new GridLayout(2,8){
				{
					setSpacing(true);
					setMargin(true);
					addComponent(new Label("Nomor Faktur"), 0,0 );
					addComponent(new Label("Nama Faktur"), 0,1 );
					addComponent(new Label("Tanggal Faktur"), 0,2 );
					addComponent(new Label("Jatuh Tempo"), 0,3 );
					addComponent(new Label("Total Harga"), 0,4 );
					addComponent(new Label("Jumlah dibayar"), 0,5 );
					addComponent(new Label("Jumlah Barang"), 0,6 );
					addComponent(new Label("Waktu input"), 0,7 );
					
					addComponent(labelInvoiceNumber, 1,0 );
					addComponent(labelInvoiceName, 1,1 );
					addComponent(labelInvoiceDate, 1,2 );
					addComponent(labelDueDate, 1,3 );
					addComponent(labelTotalPrice, 1,4 );
					addComponent(labelAmountPaid, 1,5 );
					addComponent(labelItemCount, 1,6);
					addComponent(labelTimestamp, 1,7);
					
				}
			};
			
			tableDetail =new Table("Daftar Item");
				tableDetail.setSizeFull();
				tableDetail.setWidth("98%");
				tableDetail.setHeight("250px");
				
				tableDetail.setSortEnabled(true);
				tableDetail.setImmediate(true);
				tableDetail.setRowHeaderMode(RowHeaderMode.INDEX);

			containerDetail=new IndexedContainer(){
				{
					addContainerProperty("Jumlah", String.class, null);
					addContainerProperty("Kode Barang", String.class, null);
					addContainerProperty("Nama Barang", String.class, null);
					addContainerProperty("Batch", String.class, null);
					addContainerProperty("ED", String.class, null);
					addContainerProperty("Harga Satuan", String.class, null);
					addContainerProperty("Harga + PPN", String.class, null);
					addContainerProperty("Diskon", String.class, null);
					addContainerProperty("Total", String.class, null);

				}
			};
			
			tableDetail.setContainerDataSource(containerDetail);
			layoutDetail.addComponent(layoutContent);
			layoutDetail.addComponent(tableDetail);
			
			detailWindow =new Window("Faktur"){
				{
					center();
					setClosable(true);
					setWidth("800px");
					setHeight("80%");
				}
			};
		}
		detailWindow.setContent(layoutDetail);
		setDetailData(invoice);
		this.getUI().addWindow(detailWindow);
	}

	public void setDetailData(Invoice invoice){
		 labelInvoiceNumber.setValue(invoice.getInvoiceNumber());
		 labelInvoiceName.setValue(invoice.getInvoiceName());
		 labelDueDate.setValue(date.dateToText(invoice.getDueDate(),true));
		 labelTimestamp.setValue(invoice.getTimestamp().toString());
		 labelTotalPrice.setValue(text.doubleToRupiah(invoice.getTotalPrice()));
		 labelAmountPaid.setValue(text.doubleToRupiah(invoice.getAmountPaid()));
		 labelItemCount.setValue(invoice.getInvoiceItem().size()+"");
		 labelInvoiceDate.setValue(date.dateToText(invoice.getInvoiceDate(), true));
		 containerDetail.removeAllItems();
		 for(InvoiceItem datum:invoice.getInvoiceItem()){
				Item item=containerDetail.addItem(datum.getIdInvoiceItem());			
				item.getItemProperty("Jumlah").setValue(text.intToAngka(datum.getQuantity()));
				item.getItemProperty("Kode Barang").setValue(datum.getPurchaseOrderItem().getSupplierGoods().getGoods().getIdGoods());
				item.getItemProperty("Nama Barang").setValue(datum.getPurchaseOrderItem().getSupplierGoods().getGoods().getName());
				item.getItemProperty("Batch").setValue(datum.getBatch());
				item.getItemProperty("ED").setValue(date.dateToText(datum.getExpiredDate(), true));
				item.getItemProperty("Harga Satuan").setValue(text.doubleToRupiah(datum.getPrice()));
				item.getItemProperty("Harga + PPN").setValue(text.doubleToRupiah(datum.getPricePPN()));
				item.getItemProperty("Total").setValue(text.doubleToRupiah(datum.getTotalPrice()));
				item.getItemProperty("Diskon").setValue(datum.getDiscount()+"%");
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

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonNewInvoice){
			listener.buttonClick("buttonNewInvoice");
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
	
	public void hideButtonNew(){
		buttonNewInvoice.setVisible(false);
	}
	public void showButtonNew(){
		buttonNewInvoice.setVisible(true);
	}
}
