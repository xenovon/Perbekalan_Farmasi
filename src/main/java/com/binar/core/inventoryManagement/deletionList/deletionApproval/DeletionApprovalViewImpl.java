package com.binar.core.inventoryManagement.deletionList.deletionApproval;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.inventoryManagement.deletionList.DeletionListView.DeletionListListener;
import com.binar.core.requirementPlanning.approval.ApprovalView;
import com.binar.entity.DeletedGoods;
import com.binar.entity.ReqPlanning;
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
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
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
	private Button buttonAccept;
	private Button buttonReset;
	private DateField selectStartDate;
	private DateField selectEndDate;
	private GeneralFunction function;
	private ComboBox selectFilter;
	private TextManipulator text;
	private IndexedContainer container;
	private TextField inputFilter;
	private DateManipulator date;
	
	AcceptancePyramid accept;
	public DeletionApprovalViewImpl(GeneralFunction function) {
		this.function=function;
		text=function.getTextManipulator();
		date=function.getDate();
		this.accept=function.getAcceptancePyramid();
	}	

	TableFilter filter;
	public void init(){
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
        		addContainerProperty("Disetujui?", ComboBox.class, null);
        		addContainerProperty("Keterangan", TextArea.class, null);        		
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
		
        
        labelTitle =new Label("<h2>Persetujuan Penghapusan Barang</h2>", ContentMode.HTML);
		
		buttonAccept =new Button("Simpan Perubahan");
		buttonAccept.addClickListener(this);
		
		buttonReset=new Button("Reset Pilihan");
		buttonReset.addClickListener(this);
		
		selectFilter=new ComboBox("Filter Persetujuan");
		selectFilter.addItem("Semua");
		selectFilter.addItem("Telah Disetujui");
		selectFilter.addItem("Belum Disetujui");
		selectFilter.setValue("Semua");
		selectFilter.setImmediate(true);
		selectFilter.addValueChangeListener(this);

		construct();		
	}
	
	private void construct() {
		GridLayout filterLayout=new GridLayout(7,1){
			{
				addComponent(selectStartDate, 0,0);
				addComponent(new Label("<div style='padding-left:20px'></div>", ContentMode.HTML), 1,0);
				addComponent(selectEndDate, 2,0);
				addComponent(new Label("<div style='padding-left:20px'></div>", ContentMode.HTML), 3,0);
				addComponent(selectFilter, 4, 0);
				addComponent(new Label("<div style='padding-left:20px'></div>", ContentMode.HTML), 5,0);
				addComponent(inputFilter, 6, 0);
				setSpacing(true);
				setMargin(true);

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
		this.addComponents(labelTitle, filterLayout, buttonLayout, table );
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectEndDate 
				|| event.getProperty()==selectFilter
				|| event.getProperty()==selectStartDate){
			listener.updateTable();
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonAccept){
			listener.approveClick();
		}else if(event.getSource()==buttonReset){
			listener.resetClick();
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
		//Untuk data combobox 
		List<Integer> comboData=new ArrayList<Integer>();
		comboData.add(0);
		comboData.add(1);
		comboData.add(2);

		for(DeletedGoods datum:data){
			final DeletedGoods datumFinal=datum;
			
			Item item=container.addItem(datum.getIdDeletedGoods());			

			item.getItemProperty("Tanggal").setValue(date.dateToText(datum.getDeletionDate(), true));
			item.getItemProperty("Nama Barang").setValue(datum.getGoods().getName());
			item.getItemProperty("Jumlah").setValue(text.intToAngka(datum.getQuantity()));
			item.getItemProperty("Satuan").setValue(datum.getGoods().getUnit());
			
			item.getItemProperty("Disetujui?").setValue(new ComboBox("Disetujui", comboData){
				{
					setNullSelectionAllowed(false);
					setItemCaption(0, "Belum Disetujui");
					setItemCaption(1, "Tidak Disetujui");
					setItemCaption(2, "Disetujui");
					
					setValue(accept.getDropdownChoice(datumFinal.getAcceptance()));
				}
			});
			
			item.getItemProperty("Operasi").setValue(new GridLayout(1,1){
			{
					Button buttonShow=new Button();
					buttonShow.setDescription("Lihat Lebih detail");
					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
					buttonShow.addStyleName("button-table");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showDetail(datumFinal.getIdDeletedGoods());
						}
					});
					this.setSpacing(true);
					this.setMargin(false);
					this.addComponent(buttonShow, 0, 0);
				}
			});
			TextArea textarea=new TextArea();
			textarea.setWidth("150px");
			textarea.setHeight("60px");
			textarea.setValue(accept.getCommentDeletion(datum.getIdDeletedGoods()));
			item.getItemProperty("Keterangan").setValue(textarea);

		}

		return true;
	}
	private Label labelDeletionDate;
	private Label labelGoodsName;
	private Label labelQuantity;
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
			layoutData= new GridLayout(2,8){ 
				{
					setSpacing(true);
					setMargin(true);
					addComponent(new Label("Tanggal  "), 0,0);
					addComponent(new Label("Nama Barang  "), 0, 1);
					addComponent(new Label("Jumlah : "), 0, 2);
					addComponent(new Label("Diterima? : "), 0, 3);
					addComponent(new Label("Tanggal Diterima : "), 0, 4);
					addComponent(new Label("Waktu input : "), 0, 5);
					addComponent(new Label("Keterangan : "), 0, 6);
					addComponent(new Label("Komentar : "), 0, 7);
					
				}	
			};
			//instantiasi label
			 labelDeletionDate=new Label("");
			 labelGoodsName=new Label("");
			 labelQuantity=new Label("");
			 labelAccepted=new Label("");
			 labelAcceptedDate=new Label("");
			 labelTimeStamp=new Label("");
			 labelInformation=new Label("");
			 labelComment=new Label("", ContentMode.HTML);
						
			//add Component konten ke layout
			layoutData.addComponent(labelDeletionDate, 1,0);
			layoutData.addComponent(labelGoodsName, 1,1);
			layoutData.addComponent(labelQuantity, 1,2);
			layoutData.addComponent(labelAccepted, 1,3);
			layoutData.addComponent(labelAcceptedDate, 1,4);
			layoutData.addComponent(labelTimeStamp, 1,5);
			layoutData.addComponent(labelInformation, 1,6);
			layoutData.addComponent(labelComment, 1,7);
				        
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
		 labelAccepted.setValue(accept.acceptedBy(data.getAcceptance()));
		 if(data.getApprovalDate()!=null){
			 labelAcceptedDate.setValue(date.dateToText(data.getApprovalDate(), true));			 
		 }else{
			 labelAcceptedDate.setValue("");			 		 
		 }
		 labelTimeStamp.setValue(date.dateTimeToText(data.getTimestamp()));
		 labelInformation.setValue(data.getInformation());
		 labelComment.setValue(accept.getCommentDeletionFormat(data.getIdDeletedGoods()));

	}

	@Override
	public IndexedContainer getContainer() {
		return container;
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
	public Date getSelectedStartRange() {
		return selectStartDate.getValue();
	}

	@Override
	public Date getSelectedEndRange() {
		return selectEndDate.getValue();
	}



}
