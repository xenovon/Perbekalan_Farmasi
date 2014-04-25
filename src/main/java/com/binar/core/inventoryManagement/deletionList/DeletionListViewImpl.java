package com.binar.core.inventoryManagement.deletionList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.Invoice;
import com.binar.generalFunction.AcceptancePyramid;
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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.Window;

public class DeletionListViewImpl extends VerticalLayout implements DeletionListView,  ClickListener, ValueChangeListener{

	private Table table;
	private Label title;
	private ComboBox selectFilter;
	private DateField selectStartDate;
	private DateField selectEndDate;
	private GridLayout layoutFilter;
	private IndexedContainer container;
	private TextField inputFilter;
	//untuk fungsi tambah rupiah
	private TextManipulator text;
	private DateManipulator date;
	GeneralFunction function;
	private DeletionListListener listener;
	private Button buttonNew;
	
	AcceptancePyramid accept;
	
	public DeletionListViewImpl(GeneralFunction function) {
		this.function=function;
		text=function.getTextManipulator();
		date=function.getDate();
		this.accept=function.getAcceptancePyramid();
	}
	TableFilter filter;

	@Override
	public void init() {
		title=new Label("<h2>Daftar Penghapusan Barang</h2>", ContentMode.HTML);

		filter=function.getFilter("");
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
		table.setHeight("420px");
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
       
        container=new IndexedContainer(){
        	{
        		addContainerProperty("Tanggal", String.class, null);
        		addContainerProperty("Nama Barang", String.class, null);
        		addContainerProperty("Jumlah", String.class, null);
        		addContainerProperty("Satuan", String.class, null);
        		addContainerProperty("Harga", String.class, null);
        		addContainerProperty("Total Harga", String.class, null);
        		addContainerProperty("Disetujui?", String.class, null);
        		addContainerProperty("Tanggal Disetujui", String.class, null);
        		addContainerProperty("Operasi", GridLayout.class, null);
        	}
        };
        table.setContainerDataSource(container);		
		
		selectStartDate = new DateField("Rentang Awal", DateTime.now().minusMonths(10).toDate());
		selectEndDate =new DateField("Rentang Awal",DateTime.now().plusMonths(2).toDate());

		selectStartDate.setImmediate(true);
		selectEndDate.setImmediate(true);

		
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		selectEndDate.setWidth("120px");
		selectStartDate.setWidth("120px");
	
		selectEndDate.addValueChangeListener(this);
		selectStartDate.addValueChangeListener(this);
		

		
		selectFilter=new ComboBox("Pilih Persetujuan ");
		selectFilter.addItem("Semua");
		selectFilter.addItem("Telah Disetujui");
		selectFilter.addItem("Belum Disetujui");
		selectFilter.setValue("Semua");
		selectFilter.setImmediate(true);
		selectFilter.addValueChangeListener(this);
		buttonNew=new Button("Ajukan Penghapusan Barang");
		buttonNew.addClickListener(this);
		construct();
		
	}

	@Override
	public void construct() {
		this.addComponent(title);
		
		GridLayout layoutFilter =new GridLayout(7,1);
			layoutFilter.setMargin(true);
			layoutFilter.setSpacing(true);
			layoutFilter.addComponent(selectStartDate, 0, 0);
			layoutFilter.addComponent(new Label("<div style='padding-left:20px'></div>", ContentMode.HTML), 1,0);
			layoutFilter.addComponent(selectEndDate, 2,0);
			layoutFilter.addComponent(new Label("<div style='padding-left:20px'></div>", ContentMode.HTML), 3,0);
			layoutFilter.addComponent(selectFilter, 4, 0);
			layoutFilter.addComponent(new Label("<div style='padding-left:20px'></div>", ContentMode.HTML), 5,0);
			layoutFilter.addComponent(inputFilter, 6, 0);
			
		this.addComponent(layoutFilter);
		this.addComponent(new GridLayout(){
			{	
				this.setMargin(true);
				addComponent(buttonNew);
			}
		});
		this.addComponent(table);
		
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectEndDate
				|| event.getProperty()==selectStartDate
				|| event.getProperty()==selectFilter){
			listener.dateRangeChange();
		}
		
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonNew){
			listener.newDeletion();
		}
	}
	@Override
	public void setListener(DeletionListListener listener) {
		this.listener=listener;
	}

	@Override
	public boolean updateTableData(List<DeletedGoods> data, final boolean withEditDeletion) {
		container.removeAllItems();
		System.out.println("Ukuran data"+data.size());

		for(DeletedGoods datum:data){
			final DeletedGoods datumFinal=datum;
			
			Item item=container.addItem(datum.getIdDeletedGoods());			

			item.getItemProperty("Tanggal").setValue(date.dateToText(datum.getDeletionDate(), true));
			item.getItemProperty("Nama Barang").setValue(datum.getGoods().getName());
			item.getItemProperty("Jumlah").setValue(text.intToAngka(datum.getQuantity()));
			item.getItemProperty("Satuan").setValue(datum.getGoods().getUnit());
			item.getItemProperty("Harga").setValue(text.doubleToRupiah(datum.getPrice()));
			item.getItemProperty("Total Harga").setValue(text.doubleToRupiah(datum.getPrice()*datum.getQuantity()));
			item.getItemProperty("Disetujui?").setValue(accept.acceptedBy(datum.getAcceptance()));
			if(datum.getApprovalDate()!=null){
				item.getItemProperty("Tanggal Disetujui").setValue(date.dateToText(datum.getApprovalDate(), true));				
			}
			item.getItemProperty("Operasi").setValue(new GridLayout(3,1){
			{
					if(!accept.isAccepted(datumFinal.getAcceptance())){
						Button buttonEdit=new Button();
						buttonEdit.setDescription("Ubah data ini");
						buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
						buttonEdit.addStyleName("button-table");
						buttonEdit.addClickListener(new ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								listener.editClick(datumFinal.getIdDeletedGoods());
							}
						});
					
						Button buttonDelete=new Button();
						buttonDelete.setDescription("Hapus Data");
						buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
						buttonDelete.addStyleName("button-table");
						buttonDelete.addClickListener(new ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								listener.deleteClick(datumFinal.getIdDeletedGoods());
							}
						});	
						
						if(withEditDeletion){
							this.addComponent(buttonDelete, 2, 0);						
							this.addComponent(buttonEdit, 1, 0);												
						}

					}

					Button buttonShow=new Button();
					buttonShow.setDescription("Lihat Lebih detail");
					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
					buttonShow.addStyleName("button-table");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showClick(datumFinal.getIdDeletedGoods());;
						}
					});
					

					this.setSpacing(true);
					this.setMargin(false);
					this.addComponent(buttonShow, 0, 0);
				}
			});
			
		}

		return true;

	}
	
	
	private Label labelDeletionDate;
	private Label labelGoodsName;
	private Label labelQuantity;
	private Label labelPrice;
	private Label labelTotalPrice;
	private Label labelAccepted;
	private Label labelAcceptedDate;
	private Label labelTimeStamp;
	private Label labelInformation;
	private Label labelComment;
	private Window windowDetail;
	private Window windowInputEdit;
	private  GridLayout layoutData;
	@Override
	public void showDetailWindow(DeletedGoods deletedGoods) {
		if(layoutData==null){ //jika layout null
			//buat konten 
			layoutData= new GridLayout(2,10){ 
				{
					setSpacing(true);
					setMargin(true);
					addComponent(new Label("Tanggal  "), 0,0);
					addComponent(new Label("Nama Barang  "), 0, 1);
					addComponent(new Label("Jumlah : "), 0, 2);
					addComponent(new Label("Harga : "), 0, 3);
					addComponent(new Label("Total Harga : "), 0, 4);
					addComponent(new Label("Disetujui? : "), 0, 5);
					addComponent(new Label("Tanggal Disetujui : "), 0, 6);
					addComponent(new Label("Waktu input : "), 0, 7);
					addComponent(new Label("Keterangan : "), 0, 8);
					addComponent(new Label("Komentar : "), 0, 9);
				}	
			};
			//instantiasi label
			 labelDeletionDate=new Label("");
			 labelGoodsName=new Label("");
			 labelQuantity=new Label("");
			 labelPrice=new Label("");
			 labelTotalPrice=new Label("");
			 labelAccepted=new Label("");
			 labelAcceptedDate=new Label("");
			 labelTimeStamp=new Label("");
			 labelInformation=new Label("");
			 labelComment=new Label("", ContentMode.HTML);
						
			//add Component konten ke layout
			layoutData.addComponent(labelDeletionDate, 1,0);
			layoutData.addComponent(labelGoodsName, 1,1);
			layoutData.addComponent(labelQuantity, 1,2);
			layoutData.addComponent(labelPrice, 1,3);
			layoutData.addComponent(labelTotalPrice, 1, 4);
			layoutData.addComponent(labelAccepted, 1,5);
			layoutData.addComponent(labelAcceptedDate, 1,6);
			layoutData.addComponent(labelTimeStamp, 1,7);
			layoutData.addComponent(labelInformation, 1,8);
			layoutData.addComponent(labelComment, 1,9);
				        
		}//menutup jika layoutdetail null
	    setLabelData(deletedGoods);
	    if(windowDetail==null){
			windowDetail=new Window("Detail Penghapusan Data"){
				{
					center();
					setWidth("800px");					
				}
			};					
		}//menutup jika windowDetail null
		
		windowDetail.setContent(layoutData);
		this.getUI().addWindow(windowDetail);	
		
	}
	private void setLabelData(DeletedGoods data){
		 labelDeletionDate.setValue(date.dateToText(data.getDeletionDate(), true));
		 labelGoodsName.setValue(data.getGoods().getName());
		 labelQuantity.setValue(data.getQuantity()+" "+data.getGoods().getUnit());
		 labelPrice.setValue(text.doubleToRupiah(data.getPrice()));
		 labelTotalPrice.setValue(text.doubleToRupiah(data.getPrice()*data.getQuantity()));
		 labelQuantity.setValue(data.getQuantity()+" "+data.getGoods().getUnit());
		 labelAccepted.setValue(accept.acceptedBy(data.getAcceptance()));
		 if(data.getApprovalDate()!=null){
			 labelAcceptedDate.setValue(date.dateToText(data.getApprovalDate(), true));
		 }
		 labelTimeStamp.setValue(data.getTimestamp().toString());
		 labelInformation.setValue(data.getInformation());
		 labelComment.setValue(accept.getCommentDeletionFormat(data.getIdDeletedGoods()));
	}
	@Override
	public Date getSelectedStartRange() {
		return selectStartDate.getValue();
	}

	@Override
	public Date getSelectedEndRange() {
		return selectEndDate.getValue();
	}

	public ApprovalFilter getApprovalFilter(){
		if(selectFilter.getValue().equals("Belum Disetujui")){
			return ApprovalFilter.NON_ACCEPTED;
		}else if(selectFilter.getValue().equals("Telah Disetujui")){
			return ApprovalFilter.ACCEPTED;
		}else{
			return ApprovalFilter.ALL;
		}
	}

	@Override
	public void displayForm(Component content, String title) {
		for(Window window:this.getUI().getWindows()){
			window.close();
		}
		if(windowInputEdit==null){
			windowInputEdit=new Window();
			windowInputEdit.center();
			windowInputEdit.setClosable(false);
			windowInputEdit.setWidth("500px");					

		}
		windowInputEdit.setCaption(title);
		windowInputEdit.setContent(content);
		this.getUI().addWindow(windowInputEdit);
		
	}
	public void hideButtonNew(){
		buttonNew.setVisible(false);
	}
	public void showButtonNew(){
		buttonNew.setVisible(true);
	}	

}
