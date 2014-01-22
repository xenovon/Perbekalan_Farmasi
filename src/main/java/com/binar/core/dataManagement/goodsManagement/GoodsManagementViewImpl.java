package com.binar.core.dataManagement.goodsManagement;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GoodsManagementViewImpl extends VerticalLayout 
		implements GoodsManagementView, ClickListener {

	private GeneralFunction function;
	private GoodsManagementListener listener;

	private Label title;
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonInput;
	private Label labelFilter;
	private TextField inputFilter;
	
	private TableFilter filter;
	private TextManipulator text;
	public GoodsManagementViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
	}
	
	public void init(){
		title=new Label("<h2>Manajemen Barang</h2>", ContentMode.HTML);
		
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
		buttonInput=new Button("Input data Barang Baru");
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
				addContainerProperty("Kode Barang", String.class, null);
				addContainerProperty("Nama Barang", String.class,null);
				addContainerProperty("Tipe Barang", String.class, null);
				addContainerProperty("Asuransi", String.class, null);
				addContainerProperty("Satuan", String.class, null);
				addContainerProperty("Stok Saat Ini", Integer.class, null);
				addContainerProperty("Stok Minimum", Integer.class, null);
				addContainerProperty("Kemasan", String.class,null);
				addContainerProperty("Kategori", String.class, null);
				addContainerProperty("HET", String.class, null);
				addContainerProperty("Fast Moving?", String.class, null);
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

	
	Label labelId;
	Label labelInsurance;
	Label labelName;
	Label labelDescription;
	Label labelType;
	Label labelUnit;
	Label labelCurrentStock;
	Label labelInformation;
	Label labelPackage;
	Label labelCategory;
	Label labelMinimumStock;
	Label labelIsImportant;
	Label labelHet;
	
	GridLayout layoutDetail;
	Window windowDetail;
	
	public void showDetailWindow(Goods goods){
		if(layoutDetail==null){
			//buat konten 

			layoutDetail= new GridLayout(2,14){
				{
					setSpacing(true);
					setMargin(true);
					setWidth("450px");
					addComponent(new Label("Id Barang"), 0, 1);
					addComponent(new Label("Nama Barang"), 0,2);
					addComponent(new Label("Deskripsi"), 0, 3);
					addComponent(new Label("Asuransi"), 0, 4);
					addComponent(new Label("Tipe Barang"), 0,5);
					addComponent(new Label("Satuan"), 0,6);
					addComponent(new Label("Stok Saat Ini"), 0, 7);
					addComponent(new Label("Kemasan Barang"), 0,8);
					addComponent(new Label("Kategori"), 0, 9);
					addComponent(new Label("Stok Minimum"), 0,10);
					addComponent(new Label("Fast Moving?"), 0, 11);
					addComponent(new Label("HET"), 0, 12);
					addComponent(new Label("Keterangan"), 0, 13);
				}	
			};
			//instantiasi label
			 labelId= new Label();
			 labelName= new Label();
			 labelDescription= new Label();
			 labelInsurance= new Label();
			 labelType= new Label();
			 labelUnit= new Label();
			 labelCurrentStock= new Label();
			 labelPackage= new Label();
			 labelCategory= new Label();
			 labelMinimumStock= new Label();
			 labelIsImportant= new Label();
			 labelHet=new Label();			
			 labelInformation= new Label();
			//add Component konten ke layout
			 
			 layoutDetail.addComponent(labelId,1,1);
			 layoutDetail.addComponent(labelName,1,2);
			 layoutDetail.addComponent(labelDescription,1,3);
			 layoutDetail.addComponent(labelInsurance,1,4);
			 layoutDetail.addComponent(labelType,1,5);
			 layoutDetail.addComponent(labelUnit,1,6);
			 layoutDetail.addComponent(labelCurrentStock,1,7);
			 layoutDetail.addComponent(labelPackage,1,8);
			 layoutDetail.addComponent(labelCategory,1,9);
			 layoutDetail.addComponent(labelMinimumStock,1,10);
			 layoutDetail.addComponent(labelIsImportant,1, 11);
			 layoutDetail.addComponent(labelHet,1 , 12);		
			 layoutDetail.addComponent(labelInformation,1,13);			 
		}
		
		setLabelData(goods);
		if(windowDetail==null){
			windowDetail=new Window("Detail Data Barang"){
				{
					center();
					setWidth("500px");
					
				}
			};
		}
		windowDetail.setContent(layoutDetail);
		this.getUI().addWindow(windowDetail);
	}
	
	public void setLabelData(Goods goods){
		 labelId.setValue(goods.getIdGoods());
		 labelInsurance.setValue(goods.getInsurance().getName());
		 labelInsurance.setDescription(goods.getInsurance().getDescription());
		 labelName.setValue(goods.getName());
		 labelDescription.setValue(goods.getDescription());
		 labelType.setValue(goods.getType().toString());
		 labelUnit.setValue(goods.getUnit());
		 labelCurrentStock.setValue(goods.getCurrentStock()+" "+goods.getUnit());
		 labelInformation.setValue(goods.getInformation());
		 labelPackage.setValue(goods.getGoodsPackage());
		 labelCategory.setValue(goods.getCategory().toString());
		 labelMinimumStock.setValue(goods.getMinimumStock()+" "+goods.getUnit());
		 labelIsImportant.setValue(goods.isImportant()?"Ya":"Tidak");
		 labelHet.setValue(text.doubleToRupiah(goods.getHet()));
	}
	public boolean updateTableData(List<Goods> data){
		tableContainer.removeAllItems();
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data Obat kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(Goods datum:data){
			final Goods datumFinal=datum;
			Item item = tableContainer.addItem(datum.getIdGoods());
			
			item.getItemProperty("Kode Barang").setValue(datum.getIdGoods());
			item.getItemProperty("Nama Barang").setValue(datum.getName());
			item.getItemProperty("Tipe Barang").setValue(datum.getType().toString());
			item.getItemProperty("Asuransi").setValue(datum.getInsurance().getName());
			item.getItemProperty("Satuan").setValue(datum.getUnit());
			item.getItemProperty("Stok Saat Ini").setValue(datum.getCurrentStock());
			item.getItemProperty("Stok Minimum").setValue(datum.getMinimumStock());
			item.getItemProperty("Kemasan").setValue(datum.getGoodsPackage());
			item.getItemProperty("Kategori").setValue(datum.getCategory().toString());
			item.getItemProperty("HET").setValue(text.doubleToRupiah(datum.getHet()));
			item.getItemProperty("Fast Moving?").setValue(datum.isImportant()?"Ya":"Tidak");			
			
			item.getItemProperty("Operasi").setValue(new GridLayout(3,1){
			{
					Button buttonEdit=new Button();
					buttonEdit.setDescription("Ubah data ini");
					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
					buttonEdit.addStyleName("button-table");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.editClick(datumFinal.getIdGoods());
						}
					});
				
					Button buttonShow=new Button();
					buttonShow.setDescription("Lihat Lebih detail");
					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
					buttonShow.addStyleName("button-table");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showClick(datumFinal.getIdGoods());;
						}
					});
					
					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.deleteClick(datumFinal.getIdGoods());
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
	public void setListener(GoodsManagementListener listener) {
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
		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
	}

	
	
	
	
}
