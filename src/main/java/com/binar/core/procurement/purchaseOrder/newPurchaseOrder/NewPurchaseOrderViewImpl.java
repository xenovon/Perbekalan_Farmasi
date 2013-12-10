package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;

import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.google.web.bindery.autobean.shared.AutoBeanFactory.NoWrap;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NewPurchaseOrderViewImpl extends Window implements NewPurchaseOrderView, ClickListener, ValueChangeListener{

	
	private GeneralFunction function;
	
	private FormLayout formPeriode;
	private VerticalLayout viewReqPlanning;
	private VerticalLayout viewPO;

	public NewPurchaseOrderViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	private ComboBox comboPeriod;
	private DateField inputDate;
	private ComboBox comboUserResponsible;
	private Table reqPlanningTable;
	private IndexedContainer tableContainer;
	private Button buttonCheckAll;
	private Button buttonUncheckAll;
	
	private Label labelPurchaseDate;
	private Label labelPurchaseOrderCount;
	private Label labelRayon;
	private Label labelUserResponsible;
	
	private Button buttonNext;
	private Button buttonBack;
	private Button buttonCreate;
	
	private FormViewEnum position;
	
	@Override
	public void init() {
		comboPeriod =new ComboBox("Periode Rencana Kebutuhan", 
						function.getListFactory().createMonthListFromNow(2, 5));
		inputDate =new DateField("Tanggal Surat", new Date());
				   inputDate.setImmediate(true);
		
	    comboUserResponsible = new ComboBox("Penanggung Jawab");
	    				       comboUserResponsible.setImmediate(true);
	    				       
	    reqPlanningTable=new Table();
			reqPlanningTable.setSizeFull();
			reqPlanningTable.setWidth("100%");
			reqPlanningTable.setHeight("439px");
			reqPlanningTable.setSortEnabled(true);
			reqPlanningTable.setImmediate(true);
			reqPlanningTable.setRowHeaderMode(RowHeaderMode.INDEX);
				
		tableContainer=new IndexedContainer(){
		{
    		addContainerProperty("Pilih", CheckBox.class, new CheckBox("", true));
    		addContainerProperty("Nama Barang", String.class, null);
    		addContainerProperty("Satuan", String.class, null);
    		addContainerProperty("Distributor", String.class, null);
    		addContainerProperty("Kebutuhan Disetujui", String.class, null);
		}
		};
		reqPlanningTable.setContainerDataSource(tableContainer);
		buttonCheckAll =new Button("Pilih Semua");
		buttonCheckAll =new Button("Hapus Semua Pilihan");
		
		labelPurchaseDate =new Label();
		labelPurchaseOrderCount=new Label();
		labelUserResponsible=new Label();
		
		buttonNext = new Button("Selanjutnya");
		buttonBack =new Button("Kembali");
		buttonCreate =new Button("Buat Surat Pesanan");
		
		
		construct();
		this.getUI().addWindow(this);

	}

	@Override
	public void construct() {
		this.setCaption("Buat Surat Pesanan");
		formPeriode =new FormLayout(){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(new Label("<h4>Pilih Periode dan Tanggal Surat</h4>", ContentMode.HTML));
				addComponent(comboPeriod);
				addComponent(inputDate);
			}
		};
		//periode 
		//tanggal surat
		
		viewReqPlanning =new VerticalLayout(){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(new Label("<h3>Pilih Rencana Kebutuhan</h3>", ContentMode.HTML));
				addComponent(formPeriode);
				addComponent(new HorizontalLayout(){
					{
						addComponent(buttonCheckAll);
						addComponent(buttonUncheckAll);
					}
				});
				
				addComponent(reqPlanningTable);
				addComponent(new CssLayout(){
					{
						setStyleName("float-right");
						addComponent(buttonNext);
						
					}
				});
				
			}
		};
		
		viewPO =new VerticalLayout(){
			{
				addComponent(new CssLayout(){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(new Label("<h3>Daftar Surat Pesanan</h3>", ContentMode.HTML));
						setStyleName("float-right");
						addComponent(new GridLayout(2,3){
							{
								labelPurchaseDate =new Label();
								labelPurchaseOrderCount=new Label();
								labelUserResponsible=new Label();
								this.addComponent(labelPurchaseOrderCount,0,0, 1, 0 );
								this.addComponent(new Label("Tanggal Pesanan"), 0, 1);
								this.addComponent(new Label("Penanggung Jawab"), 0, 2);
								this.addComponent(new Label("Rayon"), 0, 3);
								
								this.addComponent(labelPurchaseDate, 1,1);
								this.addComponent(labelUserResponsible, 1, 2);
								this.addComponent(labelRayon,1,3);
							}
						});
						addComponent(buttonBack);
						addComponent(buttonCreate);
					}
				});
				setVisible(false);
			}
		};
		this.setContent(new CssLayout(){
			{
				addComponent(viewReqPlanning);
				addComponent(viewPO);
			}
		});
	}


	@Override
	public void setFormView(FormViewEnum formView) {
		if(formView==FormViewEnum.VIEW_REQPLANNING){
			viewPO.setVisible(false);
			viewReqPlanning.setVisible(true);
		}else{
			viewPO.setVisible(true);
			viewReqPlanning.setVisible(false);			
		}
	}

	@Override
	public Date getPurchaseDate() {
		return inputDate.getValue();
	}

	@Override
	public void generateReqPlanningTable(List<ReqPlanning> reqPlannings) {
		tableContainer.removeAllItems();
		for(ReqPlanning reqPlanning:reqPlannings){
			Item item=tableContainer.addItem(reqPlanning.getIdReqPlanning());
			item.getItemProperty("Pilih").setValue(new CheckBox("", false));
			item.getItemProperty("Nama Barang").setValue(reqPlanning.getSupplierGoods().getGoods().getName());
			item.getItemProperty("Satuan").setValue(reqPlanning.getSupplierGoods().getGoods().getUnit());
			item.getItemProperty("Distributor").setValue(reqPlanning.getSupplierGoods().getSupplier().getSupplierName());
			item.getItemProperty("Kebutuhan Disetujui").setValue(function.getTextManipulator().intToAngka(reqPlanning.getAcceptedQuantity()));
		}
	}

	//mengembalikan ID req planning yang diseleksi
	@Override
	public List<Integer> getReqPlanningSelected() {
		List<Integer> returnValue=new ArrayList<Integer>();
		List<Integer> itemIds=(List<Integer>) tableContainer.getItemIds();
		for(Integer itemId:itemIds){
			Item item=tableContainer.getItem(itemId);
			CheckBox choice=(CheckBox)item.getItemProperty("Pilih").getValue();
			if(choice.getValue()){
				returnValue.add(itemId);
			}
		}
		return returnValue;
	}
	
	private List<FormLayout> formLayout;
	private List<PurchaseOrder> purchaseOrders;
	@Override
	public void generatePurchaseOrderListView(List<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders=purchaseOrders;
		clearPurchaseOrderList();
		if(purchaseOrders.size()!=0){
			labelPurchaseDate.setValue(function.getDate().dateToText(purchaseOrders.get(0).getDate(), true));
			labelPurchaseOrderCount.setValue(purchaseOrders.size() +" surat pesanan telah dibuat, "
					+ "klik simpan untuk menyimpan surat pesanan");
			labelRayon.setValue(purchaseOrders.get(0).getRayon());
			labelUserResponsible.setValue(purchaseOrders.get(0).getUserResponsible().getUsername()+" - "
					+purchaseOrders.get(0).getUserResponsible().getTitle());
			int position=4;
			
			for(PurchaseOrder purchaseOrder:purchaseOrders){
				final PurchaseOrder purchaseFinal=purchaseOrder;
				viewPO.addComponent(new GridLayout(2, 5){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(new Label("<b>"+purchaseFinal.getPurchaseOrderName()+"</b>", 
								ContentMode.HTML), 0, 0, 1, 0);
						addComponent(new Label("Nomor Surat Pesanan"), 0, 1);
						addComponent(new Label("Tipe"), 0, 2);
						addComponent(new Label("Jumlah Item"), 0, 3);
						addComponent(new Label("----------------"), 0, 4);												
					}
				});
				
				position++;
			}			
		}else{
			labelPurchaseDate.setValue("Tidak ada surat pesanan yang dibuat");
		}
	}
	
	private void clearPurchaseOrderList(){
		viewPO.removeAllComponents();
		labelPurchaseDate.setValue("");
		labelPurchaseOrderCount.setValue("");
		labelRayon.setValue("");
		labelUserResponsible.setValue("");
	}
	@Override
	public List<PurchaseOrder> getPurchaseOrderListData() {
		return purchaseOrders;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonNext){
			listener.buttonClick("buttonNext");
		}else if(event.getButton()==buttonBack){
			listener.buttonClick("buttonBack");
		}else if(event.getButton()==buttonCreate){
			listener.buttonClick("buttonCreate");
		}else if(event.getButton()==buttonCheckAll){
			listener.buttonClick("buttonCheckAll");
		}else if(event.getButton()==buttonUncheckAll){
			listener.buttonClick("buttonUncheckAll");
		}
	}
	private NewPurchaseOrderListener listener;
	@Override
	public void setListener(NewPurchaseOrderListener listener) {
		this.listener=listener;
	}

	@Override
	public FormViewEnum getFormState() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public void setComboUserData(Map<Integer, String> data) {
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
        	comboUserResponsible.addItem(entry.getKey());
        	comboUserResponsible.setItemCaption(entry.getKey(), entry.getValue());
        }
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==comboPeriod){
			listener.periodChange();
		}
	}

}
