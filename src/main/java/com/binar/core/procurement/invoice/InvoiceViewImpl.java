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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
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
		table.setWidth("100%");
		table.setHeight("450px");
		table.setPageLength(10);
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
	public boolean updateTableData(List<Invoice> data) {
		tableContainer.removeAllItems();
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
					this.addComponent(buttonDelete, 2, 0);						
					this.addComponent(buttonEdit, 1, 0);					
				}
			});
			
		}

		return true;
	}
	
	
	@Override
	public void showDetailWindow(Invoice invoice) {
		
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
			listener.buttonClick("buttonNewPurchase");
		}		
	}

}
