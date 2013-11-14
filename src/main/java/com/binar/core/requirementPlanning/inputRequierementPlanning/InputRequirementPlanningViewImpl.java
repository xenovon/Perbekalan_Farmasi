package com.binar.core.requirementPlanning.inputRequierementPlanning;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.binar.entity.ReqPlanning;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseListener;

public class InputRequirementPlanningViewImpl extends VerticalLayout
	implements InputRequirementPlanningView, ClickListener, ValueChangeListener {

	private GeneralFunction generalFunction;

	private Label labelSelectMonth;
	private Label labelTitle;
	private Table table;
	private Button buttonInput;
	private ComboBox selectMonth;
	private TextField inputFilter;
	private IndexedContainer tableContainer;
	private GridLayout layoutDetail;
	
	private InputRequirementListener listener;
	
	public InputRequirementPlanningViewImpl(GeneralFunction function){
		this.generalFunction=function;
	}
	//Table Filter
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
				            tableContainer.removeContainerFilter(filter);
				        
				        //set filter baru
				        filter.updateData(event.getText());
				        tableContainer.addContainerFilter(filter);
					}
				});

			}
		};
		
		
		labelSelectMonth = new Label("<b>Pilih Periode Bulan : &nbsp</b>", ContentMode.HTML);
		labelTitle =new Label("<h2>Input Rencana Kebutuhan</h2>", ContentMode.HTML);
		
		buttonInput =new Button("Input Data Baru");
		buttonInput.setDescription("Input Data baru untuk bulan terpilih");
		buttonInput.addClickListener(this);
		
		List<String> contentList=generalFunction.getListFactory().createMonthListFromNow(10);
		selectMonth =new ComboBox("", contentList);
		selectMonth.setNullSelectionAllowed(false);
		selectMonth.addStyleName("non-caption-form");
		selectMonth.setWidth("180px");
		selectMonth.setImmediate(true);
		selectMonth.setValue(contentList.iterator().next());
		selectMonth.addValueChangeListener(this);

		table=new Table();

		table.setSizeFull();
        table.setWidth("100%");
        table.setSortEnabled(true);
		table.setImmediate(true);
        table.setRowHeaderMode(RowHeaderMode.INDEX);
        tableContainer=new IndexedContainer(){
        	{
        		addContainerProperty("Nama Barang",String.class, null);
        		addContainerProperty("Kebutuhan",Integer.class, null);
        		addContainerProperty("Produsen",String.class, null);
        		addContainerProperty("Distributor",String.class, null);
        		addContainerProperty("Operasi", GridLayout.class, null);
        	}
        };
        table.setContainerDataSource(tableContainer);
        
		construct();
	}
	
	private void construct(){
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponent(labelTitle);
		this.addComponent(new GridLayout(3, 1){
			{
				addComponent(labelSelectMonth,0,0);
				addComponent(selectMonth,1,0);
				addComponent(buttonInput, 2,0);
				setSpacing(true);
				
			}
		});
		this.addComponent(inputFilter);
		this.addComponent(table);
	}
	Window window;

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
				}
			};

		}
		window.setContent(content);
		this.getUI().addWindow(window);
	}
	
	public Container getTableContainer() {
		return tableContainer;
	}
	public boolean setTableData(List<TableData> data){
		tableContainer.removeAllItems();
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data kebutuhan kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(TableData datum:data){
			final TableData datumFinal=datum;
			Item item = tableContainer.addItem(datum.getIdReq());
						
			item.getItemProperty("Nama Barang").setValue(datum.getGoodsName());
			item.getItemProperty("Kebutuhan").setValue(datum.getReq());
			item.getItemProperty("Produsen").setValue(datum.getManufacturer());
			item.getItemProperty("Distributor").setValue(datum.getSupp());
			item.getItemProperty("Operasi").setValue(new GridLayout(3,1){
				{
					//jika sudah diaccept, maka button ubah  tidak ditampilkan
					if(!datumFinal.isAccepted()){
						Button buttonEdit=new Button();
						buttonEdit.setDescription("Ubah data ini");
						buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
						buttonEdit.addStyleName("button-table");
						buttonEdit.addClickListener(new ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								listener.edit(datumFinal.getIdReq());
							}
						});
						this.addComponent(buttonEdit, 1, 0);

					}
					
					Button buttonShow=new Button();
					buttonShow.setDescription("Lihat Lebih detail");
					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
					buttonShow.addStyleName("button-table");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showDetail(datumFinal.getIdReq());
						}
					});
					
					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.delete(datumFinal.getIdReq());
						}
					});
					this.setSpacing(true);
					this.setMargin(false);
					this.addComponent(buttonShow, 0, 0);
					if(datumFinal.isAccepted()){
						this.addComponent(buttonDelete, 1, 0);						
					}else{
						this.addComponent(buttonDelete, 2, 0);
					}
					
				}
			});
			
		}
		return true;
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
	Window windowDetail;
	//menampilkan jendela untuk detail
	public void showDetailWindow(ReqPlanning data){
		if(layoutDetail==null){
			//buat konten 
			layoutDetail= new GridLayout(2,12){
				{
					setSpacing(true);
					setMargin(true);
					addComponent(new Label("Id Rencana Kebutuhan"), 0, 1);
					addComponent(new Label("Nama Barang"), 0,2);
					addComponent(new Label("Distributor"), 0, 3);
					addComponent(new Label("Produsen"), 0, 4);
					addComponent(new Label("Periode"), 0,5);
					addComponent(new Label("Kuantitas"), 0,6);
					addComponent(new Label("Diterima?"), 0, 7);
					addComponent(new Label("Kuantitas Diterima"), 0,8);
					addComponent(new Label("Keterangan"), 0, 9);
					addComponent(new Label("Waktu Input"), 0,10);
					addComponent(new Label("Estimasi Harga"), 0, 11);
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
			labelPriceEstimation =new Label();	
			
			//add Component konten ke layout
			layoutDetail.addComponent(labelId, 1,1);
			layoutDetail.addComponent(labelName, 1,2);
			layoutDetail.addComponent(labelSupplier, 1,3);
			layoutDetail.addComponent(labelManufacturer, 1,4);
			layoutDetail.addComponent(labelPeriode, 1,5);
			layoutDetail.addComponent(labelQuantity, 1,6);
			layoutDetail.addComponent(labelIsAccepted, 1,7);
			layoutDetail.addComponent(labelAcceptedQuantity, 1,8);
			layoutDetail.addComponent(labelInformation, 1,9);
			layoutDetail.addComponent(labelTimestamp, 1,10);
			layoutDetail.addComponent(labelPriceEstimation, 1,11);
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
			labelIsAccepted.setValue(data.isAccepted()?"Ya":"Belum");
			labelAcceptedQuantity.setValue(String.valueOf(data.getAcceptedQuantity()));
			labelInformation.setValue(data.getInformation());
			labelTimestamp.setValue(data.getTimestamp().toString());
			labelPriceEstimation.setValue("Rp "+data.getPriceEstimation());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public Window getWindow(){
		return window;
	}
	@Override
	public void addListener(InputRequirementListener listener) {
		this.listener=listener;
		
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonInput){
			Object select=selectMonth.getValue();
			if(select!=null){
				listener.buttonClick("buttonInput", select);				
			}else{
				Notification.show("Terjadi Kesalahan, Pilih bulan lagi");
			}
		}
		System.err.println("Invoked in view");
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectMonth){
			listener.selectChange(event.getProperty().getValue());
		}
	}
	
	public String getPeriodeValue(){
		return (String) selectMonth.getValue();
	}
}


