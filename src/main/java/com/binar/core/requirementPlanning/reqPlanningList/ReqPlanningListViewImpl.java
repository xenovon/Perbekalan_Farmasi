package com.binar.core.requirementPlanning.reqPlanningList;

import java.util.Calendar;
import java.util.List;

import com.binar.core.requirementPlanning.inputRequierementPlanning.TableData;
import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.AcceptancePyramid;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class ReqPlanningListViewImpl extends VerticalLayout 
	implements ReqPlanningListView, ValueChangeListener {

	GeneralFunction generalFunction;
	
	
	Table table;
	Label labelFilter;
	Label labelYear;
	Label labelMonth;
	Label labelTitle;
	ComboBox selectYear;
	ComboBox selectMonth;
	GridLayout layoutFilter;
	IndexedContainer container;
	TextField inputFilter;
	//untuk fungsi tambah rupiah
	TextManipulator text;
	DateManipulator date;
	
	AcceptancePyramid accept;
	
	ReqPlanningListListener listener;
	public ReqPlanningListViewImpl(GeneralFunction function) {
		this.generalFunction=function;
		text=generalFunction.getTextManipulator();
		this.accept=generalFunction.getAcceptancePyramid();
		this.date=generalFunction.getDate();
	}
	TableFilter filter;
	public void init(){
		filter=generalFunction.getFilter("");
		inputFilter= new TextField("Filter Tabel"){
			{
				setImmediate(true);
				addTextChangeListener(new TextChangeListener() {
					public void textChange(TextChangeEvent event) {
				        // buang filter lama
						System.out.println("Text change event fired");
				        if (filter != null)
				            container.removeContainerFilter(filter);
				        
				        //set filter baru
				        filter.updateData(event.getText());
				        container.addContainerFilter(filter);
					}
				});

			}
		};

		table=new Table();
		table.setSizeFull();
		table.setWidth("100%");
		table.setHeight("409px");
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
       
        container=new IndexedContainer(){
        	{
        		addContainerProperty("Nama Barang", String.class, null);
        		addContainerProperty("Satuan", String.class, null);
        		addContainerProperty("HNA + PPN 10%", String.class, null);
        		addContainerProperty("Kebutuhan", String.class, null);
        		addContainerProperty("Total Harga", String.class, null);
        		addContainerProperty("Produsen", String.class, null);
        		addContainerProperty("Distributor", String.class, null);
        		addContainerProperty("Disetujui?",String.class, null);
        		addContainerProperty("Kebutuhan Disetujui", String.class, null);
        		addContainerProperty("Operasi", Button.class, null);
        		
        	}
        };
        table.setContainerDataSource(container);		
		
        
		labelFilter=new Label("<b style='font-size:14px'>Filter : </b> ", ContentMode.HTML);
		labelYear=new Label("Pilih Tahun  :");
		labelMonth=new Label("Pilih Bulan  :");
		
		List<String> yearList=generalFunction.getListFactory().createYearList(6, 2, false);
		List<String> monthList=generalFunction.getListFactory().createMonthList();
		selectYear = new ComboBox("", yearList);
		selectMonth =new ComboBox("", monthList);

		selectYear.setImmediate(true);
		selectMonth.setImmediate(true);

		
		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectYear.setValue(yearList.get(2));
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

		labelTitle =new Label("<h2>Daftar Rencana Kebutuhan</h2>", ContentMode.HTML);
		
		construct();
		
	}
	private void construct(){
		layoutFilter =new GridLayout(5,1);
		layoutFilter.setMargin(true);
		layoutFilter.setSpacing(true);
		
		layoutFilter.addComponent(labelFilter, 0, 0);
		layoutFilter.addComponent(labelYear, 1, 0);
		layoutFilter.addComponent(selectYear, 2, 0);
		layoutFilter.addComponent(labelMonth, 3, 0);
		layoutFilter.addComponent(selectMonth, 4, 0);
		
		this.setSpacing(true);
		this.addComponents(labelTitle, layoutFilter, inputFilter, table);
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectMonth ||
				event.getProperty()==selectYear){
			listener.updateTable(getSelectedPeriod());
		}
	}

	@Override
	public boolean updateTableData(List<ReqPlanning> data) {
		container.removeAllItems();
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data kebutuhan kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(ReqPlanning datum:data){
			final ReqPlanning datumFinal=datum;
			Item item = container.addItem(datum.getIdReqPlanning());
			
			/* hitung harga barang dengan PPN */
			double hnaPPNdouble=datum.getPriceEstimation()+(datum.getPriceEstimation()*0.1);
			String hnaPPN=text.doubleToRupiah(hnaPPNdouble);
			String totalPrice=text.doubleToRupiah(hnaPPNdouble*datum.getQuantity());
			
			/* add data ke database */
			Goods goods=datum.getSupplierGoods().getGoods();
			String labelGoods;
			if(goods.getInsurance().isShowInDropdown()){
				labelGoods=goods.getName() + " - "+goods.getInsurance().getName();				
			}else{
				labelGoods=goods.getName();				
			}
			item.getItemProperty("Nama Barang").setValue(labelGoods);
			item.getItemProperty("Satuan").setValue(datum.getSupplierGoods().getGoods().getUnit());
			item.getItemProperty("HNA + PPN 10%").setValue(hnaPPN);
			item.getItemProperty("Kebutuhan").setValue(text.intToAngka(datum.getQuantity()));
			item.getItemProperty("Total Harga").setValue(totalPrice);
			item.getItemProperty("Produsen").setValue(datum.getSupplierGoods().getManufacturer().getManufacturerName());
			item.getItemProperty("Distributor").setValue(datum.getSupplierGoods().getSupplier().getSupplierName());
			item.getItemProperty("Disetujui?").setValue(accept.acceptedBy(datum.getAcceptance()));
			item.getItemProperty("Kebutuhan Disetujui").setValue(text.intToAngka(datum.getAcceptedQuantity()));
			
			
			item.getItemProperty("Operasi").setValue(new Button(){
			{
				{
					setDescription("Lihat Lebih detail");
					setIcon(new ThemeResource("icons/image/icon-detail.png"));
					addStyleName("button-table");
					
					addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showDetail(datumFinal.getIdReqPlanning());
						}
					});
				}
			}
			});
			
		}
		return true;
		
	}

	@Override
	public void setListener(ReqPlanningListListener listener) {
		this.listener=listener;
	}
	
	//Instantiasi label data;
	Label labelId;
	Label labelName;
	Label labelSupplier;
	Label labelManufacturer;
	Label labelPeriode;
	Label labelQuantity;
	Label labelIsAccepted;
	Label labelAcceptedQuantity;
	Label labelInformation;
	Label labelTimestamp;
	Label labelPriceEstimation;	
	Label labelDateAccepted;
	Window windowDetail;
	GridLayout layoutDetail;
	
	//menampilkan jendela untuk detail
	public void showDetailWindow(ReqPlanning data){
		if(layoutDetail==null){
			//buat konten 
			layoutDetail= new GridLayout(2,13){
				{
					setSpacing(true);
					setMargin(true);
					addComponent(new Label("Id Rencana Kebutuhan"), 0, 1);
					addComponent(new Label("Nama Barang"), 0,2);
					addComponent(new Label("Distributor"), 0, 3);
					addComponent(new Label("Produsen"), 0, 4);
					addComponent(new Label("Periode"), 0,5);
					addComponent(new Label("Kuantitas"), 0,6);
					addComponent(new Label("Disetujui?"), 0, 7);
					addComponent(new Label("Tanggal Disetujui"), 0,8);
					addComponent(new Label("Kuantitas Disetujui"), 0,9);
					addComponent(new Label("Keterangan"), 0, 10);
					addComponent(new Label("Waktu Input"), 0,11);
					addComponent(new Label("Estimasi Harga"), 0, 12);
				}	
			};
			//instantiasi label
			labelId=new Label();
			labelName=new Label();
			labelSupplier=new Label();
			labelManufacturer=new Label();
			labelPeriode =new Label();
			labelQuantity = new Label();
			labelIsAccepted =new Label();
			labelAcceptedQuantity =new Label();
			labelInformation =new Label();
			labelTimestamp =new Label();
			labelDateAccepted=new Label();
			labelPriceEstimation =new Label();	
			
			//add Component konten ke layout
			layoutDetail.addComponent(labelId, 1,1);
			layoutDetail.addComponent(labelName, 1,2);
			layoutDetail.addComponent(labelSupplier, 1,3);
			layoutDetail.addComponent(labelManufacturer, 1,4);
			layoutDetail.addComponent(labelPeriode, 1,5);
			layoutDetail.addComponent(labelQuantity, 1,6);
			layoutDetail.addComponent(labelIsAccepted, 1,7);
			layoutDetail.addComponent(labelDateAccepted, 1,8);
			layoutDetail.addComponent(labelAcceptedQuantity, 1,9);
			layoutDetail.addComponent(labelInformation, 1,10);
			layoutDetail.addComponent(labelTimestamp, 1,11);
			layoutDetail.addComponent(labelPriceEstimation, 1,12);
		}
		
		setLabelData(data);
		if(windowDetail==null){
			windowDetail=new Window("Detail Rencana Kebutuhan"){
				{
					center();
					setWidth("500px");					
				}
			};
		}
		windowDetail.setContent(layoutDetail);
		this.getUI().addWindow(windowDetail);		
	}
	private void setLabelData(ReqPlanning data){
		try {
			labelId.setValue(String.valueOf(data.getIdReqPlanning()));
			labelName.setValue(data.getSupplierGoods().getGoods().getName());
			labelSupplier.setValue(data.getSupplierGoods().getSupplier().getSupplierName());
			labelManufacturer.setValue(data.getSupplierGoods().getManufacturer().getManufacturerName());
			labelPeriode.setValue(data.getPeriodString());
			labelQuantity.setValue(String.valueOf(data.getQuantity()));
			labelIsAccepted.setValue(accept.acceptedBy(data.getAcceptance()));
			labelAcceptedQuantity.setValue(String.valueOf(data.getAcceptedQuantity()));
			labelInformation.setValue(data.getInformation());
			labelTimestamp.setValue(data.getTimestamp().toString());
			labelPriceEstimation.setValue("Rp "+data.getPriceEstimation());
			labelDateAccepted.setValue(date.dateToText(data.getDateAccepted(), true));
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	//untuk mendapatkan periode terpilih
	@Override
	public String getSelectedPeriod() {
		return selectMonth.getValue()+"-"+selectYear.getValue();
	}
}
