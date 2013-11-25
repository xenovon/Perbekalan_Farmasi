package com.binar.core.dataManagement.supplierManagement;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import com.binar.entity.Goods;
import com.binar.entity.Supplier;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.RowHeaderMode;

public class SupplierManagementViewImpl extends VerticalLayout implements 
				SupplierManagementView, ClickListener{

	private GeneralFunction function;
	private SupplierManagementListener listener;

	private Label title;
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonInput;
	private Label labelFilter;
	private TextField inputFilter;
	
	private TableFilter filter;
	private TextManipulator text;
	public SupplierManagementViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
	}
	
	public void init(){
		title=new Label("<h2>Manajemen Distributor</h2>", ContentMode.HTML);
		
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
		buttonInput=new Button("Masukan Distributor Baru");
		buttonInput.addClickListener(this);
		
		table=new Table();
		table.setSizeFull();
		table.setWidth("100%");
		table.setHeight("439px");
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Singkatan Distributor", String.class, null);
				addContainerProperty("Nama Distributor", String.class,null);
				addContainerProperty("Faksimile", String.class, null);
				addContainerProperty("Telepon", String.class, null);
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
	
	private Label labelId;
	private Label labelAbbr;
	private Label labelName;
	private	Label labelDescription;
	private Label labelAddress;
	private Label labelPhoneNumber;
	private Label labelEmail;
	private Label labelFax;
	private Label labelGoods;
	
	GridLayout layoutDetail;
	Window windowDetail;
	
	public void showDetailWindow(Supplier supplier, String goods){
		if(layoutDetail==null){
			//buat konten 

			layoutDetail= new GridLayout(2,10){
				{
					setSpacing(true);
					setMargin(true);
					setWidth("450px");
					addComponent(new Label("ID Distributor"), 0, 1);
					addComponent(new Label("Singkatan"), 0,2);
					addComponent(new Label("Nama Distributor"), 0, 3);
					addComponent(new Label("Deskripsi"), 0, 4);
					addComponent(new Label("Alamat"), 0,5);
					addComponent(new Label("Nomor Telepon"), 0,6);
					addComponent(new Label("Email"), 0, 7);
					addComponent(new Label("Faksimile"), 0,8);
					addComponent(new Label("Daftar Barang"), 0, 9);
				}	
			};
			//instantiasi label
			 labelId=new Label("", ContentMode.HTML);
			 labelAbbr=new Label("", ContentMode.HTML);
			 labelName=new Label("", ContentMode.HTML);
			 labelDescription=new Label("", ContentMode.HTML);
			 labelAddress=new Label("", ContentMode.HTML);
			 labelPhoneNumber=new Label("", ContentMode.HTML);
			 labelEmail=new Label("", ContentMode.HTML);
			 labelFax=new Label("", ContentMode.HTML);
			 labelGoods=new Label("", ContentMode.HTML);

			//add Component konten ke layout
			 
			 layoutDetail.addComponent(labelId,1,1);
			 layoutDetail.addComponent(labelAbbr,1,2);
			 layoutDetail.addComponent(labelName,1,3);
			 layoutDetail.addComponent(labelDescription,1,4);
			 layoutDetail.addComponent(labelAddress,1,5);
			 layoutDetail.addComponent(labelPhoneNumber,1,6);
			 layoutDetail.addComponent(labelEmail,1,7);
			 layoutDetail.addComponent(labelFax,1,8);
			 layoutDetail.addComponent(labelGoods, 1, 9);
		}
		
		setLabelData(supplier, goods);
		if(windowDetail==null){
			windowDetail=new Window("Detail Data Distributor"){
				{
					center();
					setWidth("500px");
					setHeight("80%");
					
				}
			};
		}
		windowDetail.setContent(layoutDetail);
		this.getUI().addWindow(windowDetail);
	}
	
	public void setLabelData(Supplier supplier, String goods){
		 labelId.setValue(supplier.getIdSupplier()+"");
		 labelAbbr.setValue(supplier.getSupplierAbbr());
		 labelName.setValue(supplier.getSupplierName());
		 labelDescription.setValue(supplier.getDescription());
		 labelAddress.setValue(supplier.getSupplierAddress());
		 labelPhoneNumber.setValue(supplier.getPhoneNumber());
		 labelEmail.setValue(supplier.getEmail());
		 labelFax.setValue(supplier.getFax());
		 labelGoods.setValue(goods);

	}
	public boolean updateTableData(List<Supplier> data){
		tableContainer.removeAllItems();
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data Supplier kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(Supplier datum:data){
			final Supplier datumFinal=datum;
			Item item = tableContainer.addItem(datum.getIdSupplier());
			item.getItemProperty("Singkatan Distributor").setValue(datum.getSupplierAbbr());
			item.getItemProperty("Nama Distributor").setValue(datum.getSupplierName());
			item.getItemProperty("Faksimile").setValue(datum.getFax());
			item.getItemProperty("Telepon").setValue(datum.getPhoneNumber());			
			item.getItemProperty("Operasi").setValue(new GridLayout(3,1){
			{
					Button buttonEdit=new Button();
					buttonEdit.setDescription("Ubah data ini");
					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
					buttonEdit.addStyleName("button-table");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.editClick(datumFinal.getIdSupplier()+"");
						}
					});
				
					Button buttonShow=new Button();
					buttonShow.setDescription("Lihat Lebih detail");
					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
					buttonShow.addStyleName("button-table");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showClick(datumFinal.getIdSupplier()+"");;
						}
					});
					
					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.deleteClick(datumFinal.getIdSupplier()+"");
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
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonInput){
			listener.buttonClick("buttonInput");
		}
	}

	@Override
	public void setListener(SupplierManagementListener listener) {
		this.listener=listener;		
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
					setHeight("80%");
				}
			};

		}
		this.getUI().removeWindow(window);
		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
	}


	
	
	
}
