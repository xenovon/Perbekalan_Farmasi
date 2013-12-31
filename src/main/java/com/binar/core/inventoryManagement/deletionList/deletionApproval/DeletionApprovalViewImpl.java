package com.binar.core.inventoryManagement.deletionList.deletionApproval;

import java.text.SimpleDateFormat;
import java.util.List;

import com.binar.core.requirementPlanning.approval.ApprovalView;
import com.binar.entity.DeletedGoods;
import com.binar.entity.ReqPlanning;
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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class DeletionApprovalViewImpl extends VerticalLayout implements
DeletionApprovalView, Button.ClickListener, ValueChangeListener{
	
	private Table table;

	private Label labelTitle;
	private ComboBox selectMonth;
	private GridLayout layoutFilter;
	private Button buttonAccept;
	private Button buttonReset;
	private GeneralFunction generalFunction;
	
	TextManipulator text;
	IndexedContainer container;
	TextField inputFilter;
	
	public DeletionApprovalViewImpl(GeneralFunction function) {
		this.generalFunction=function;
		text=generalFunction.getTextManipulator();
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
		table.setHeight("439px");
        table.setSortEnabled(true);
        table.setRowHeaderMode(RowHeaderMode.INDEX);
       
        container=new IndexedContainer(){
        	{
        		addContainerProperty("Tanggal", String.class, null);
        		addContainerProperty("Nama Barang", String.class, null);
        		addContainerProperty("Satuan", String.class, null);
        		addContainerProperty("Jumlah", String.class, null);
        		addContainerProperty("Detail", Button.class, null);
        		addContainerProperty("Disetujui?", CheckBox.class, new CheckBox("Disetujui"){{setValue(false);}});
        		addContainerProperty("Jumlah Disetujui", TextField.class, null);        		
        	}
        };
        table.setContainerDataSource(container);		
		List<String> contentList=generalFunction.getListFactory().createMonthListFromNow(10);
		selectMonth =new ComboBox("", contentList);
		selectMonth.setNullSelectionAllowed(false);
		selectMonth.addStyleName("non-caption-form");
		selectMonth.setImmediate(true);
		selectMonth.setValue(contentList.iterator().next());
		selectMonth.addValueChangeListener(this);
		selectMonth.setWidth("169px");
		
        
        labelTitle =new Label("<h2>Persetujuan Penghapusan Barang</h2>", ContentMode.HTML);
		
		buttonAccept =new Button("Simpan Perubahan");
		buttonAccept.addClickListener(this);
		
		buttonReset=new Button("Reset Pilihan");
		buttonReset.addClickListener(this);
		construct();		
	}
	
	private void construct() {
		GridLayout filterLayout=new GridLayout(2,1){
			{
				this.addComponent(new Label("Pilih Bulan : "), 0,0);
				this.addComponent(selectMonth);
				this.setSpacing(true);
			}
		};
		GridLayout buttonLayout=new GridLayout(2,1){
			{
				this.addComponent(buttonAccept,0,0);
				this.addComponent(buttonReset,1,0);
				this.setSpacing(true);
				this.setMargin(true);
				this.addStyleName("float-right");
			}
		};
		this.addComponents(labelTitle, filterLayout, inputFilter, buttonLayout, table );
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectMonth)
			listener.updateTable(getSelectedPeriod());	
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonAccept){
			listener.buttonClick("buttonAccept");
		}else if(event.getSource()==buttonReset){
			listener.updateTable(getSelectedPeriod());
		}
	}
	
	DeletionApprovalListener listener;
	@Override
	public void setListener(DeletionApprovalListener listener) {
		this.listener=listener;
	}

	@Override
	public boolean updateTableData(List<DeletedGoods> data) {
		container.removeAllItems();
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data persetujuan kosong", Type.WARNING_MESSAGE);
			return false;
		}
		
		for(DeletedGoods datum:data){
			final DeletedGoods datumFinal=datum;
			Item item = container.addItem(datum.getIdDeletedGoods());
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMMM yyyy");
			
			/* add data dari database */
			item.getItemProperty("Tanggal").setValue(dateFormat.format(datum.getDeletionDate()));
			item.getItemProperty("Nama Barang").setValue(datum.getSupplierGoods().getGoods().getName());
			item.getItemProperty("Satuan").setValue(datum.getSupplierGoods().getGoods().getUnit());
			item.getItemProperty("Jumlah").setValue(text.intToAngka(datum.getQuantity()));
			item.getItemProperty("Detail").setValue(new Button(){
				{
					{
						setDescription("Lihat Lebih detail");
						setIcon(new ThemeResource("icons/image/icon-detail.png"));
						addStyleName("button-table");
						
						addClickListener(new ClickListener() {						
							@Override
							public void buttonClick(ClickEvent event) {
								listener.showDetail(datumFinal.getIdDeletedGoods());
							}
						});
					}
				}
			});
			item.getItemProperty("Disetujui?").setValue(new CheckBox("Disetujui"){{
				setValue(datumFinal.isAccepted());
			}});
			
		}
		return true;
	}

	Label labelName;
	Label labelSupplier;
	Label labelProposalDate;
	Label labelQuantity;
	Label labelIsAccepted;
	Label labelAcceptedQuantity;
	Label labelInformation;
	Label labelTimestamp;
	Label labelPrice;	
	Label labelUnit;
	Label labelTotalPrice;
	
	
	Window windowDetail;
	GridLayout layoutDetail;
	
	@Override
	public void showDetailWindow(DeletedGoods data) {
				for(Window window:this.getUI().getWindows()){
					window.close();
				}
				if(layoutDetail==null){
					//buat konten 
					layoutDetail= new GridLayout(2,13){
						{
							setSpacing(true);
							setMargin(true);
							addComponent(new Label("Tanggal"), 0, 1);
							addComponent(new Label("Nama Barang"), 0,2);
							addComponent(new Label("Satuan"), 0, 3);
							addComponent(new Label("Jumlah"), 0,4);
							addComponent(new Label("PBF"), 0, 5);
							addComponent(new Label("Harga"), 0,6);
							addComponent(new Label("Total Harga"), 0,7);
							addComponent(new Label("Keterangan"), 0, 8);
							addComponent(new Label("Waktu Input Pengajuan"), 0,9);
							addComponent(new Label("Disetujui?"), 0, 10);
							addComponent(new Label("Jumlah yang Disetujui"), 0,11);
						}	
					};
					//instantiasi label
					labelProposalDate=new Label();
					labelName=new Label();
					labelSupplier=new Label();
					labelUnit =new Label();
					labelQuantity = new Label();
					labelIsAccepted =new Label();
					labelPrice=new Label();
					labelTotalPrice=new Label();
					labelAcceptedQuantity =new Label();
					labelInformation =new Label();
					labelTimestamp =new Label();						
					
					//add Component konten ke layout
					layoutDetail.addComponent(labelProposalDate, 1,1);
					layoutDetail.addComponent(labelName, 1,2);
					layoutDetail.addComponent(labelUnit, 1,3);
					layoutDetail.addComponent(labelQuantity, 1,4);
					layoutDetail.addComponent(labelSupplier, 1,5);
					layoutDetail.addComponent(labelPrice,1,6);
					layoutDetail.addComponent(labelTotalPrice, 1,7);
					layoutDetail.addComponent(labelInformation, 1,8);
					layoutDetail.addComponent(labelTimestamp, 1,9);			
					layoutDetail.addComponent(labelIsAccepted, 1,10);
					layoutDetail.addComponent(labelAcceptedQuantity, 1,11);
					
				}			
				setLabelData(data);
				if(windowDetail==null){
					windowDetail=new Window("Detail Persetujuan Penghapusan Barang"){
						{
							center();
							setWidth("500px");					
						}
					};
				}
				windowDetail.setContent(layoutDetail);
				this.getUI().addWindow(windowDetail);	
	}

	private void setLabelData(DeletedGoods data) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMMM yyyy");
		try {
			labelProposalDate.setValue(dateFormat.format((data.getDeletionDate())));
			labelName.setValue(data.getSupplierGoods().getGoods().getName());
			labelSupplier.setValue(data.getSupplierGoods().getSupplier().getSupplierName());
			labelQuantity.setValue(String.valueOf(data.getQuantity()));
			labelIsAccepted.setValue(data.isAccepted()?"Ya":"Belum");
			labelInformation.setValue(data.getInformation());
			labelTimestamp.setValue(data.getTimestamp().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public String getSelectedPeriod() {
		return (String) selectMonth.getValue();
	}

	@Override
	public IndexedContainer getContainer() {
		return container;
	}
	
	public String getPeriodeValue(){
		return (String) selectMonth.getValue();
	}

}
