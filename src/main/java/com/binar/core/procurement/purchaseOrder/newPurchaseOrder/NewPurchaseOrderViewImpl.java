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
	private Label labelError;
	
	private Button buttonNext;
	private Button buttonBack;
	private Button buttonCreate;
	private Button buttonCancel;
	private Button buttonCancelPo;
	
	private FormViewEnum position;
	
	@Override
	public void init() {
		center();
		setClosable(false);
		setWidth("700px");
		setHeight("80%");

		comboPeriod =new ComboBox("Periode Rencana Kebutuhan", 
						function.getListFactory().createMonthListFromNow(5, 2));
					comboPeriod.setImmediate(true);
					comboPeriod.addValueChangeListener(this);
		inputDate =new DateField("Tanggal Surat", new Date());
				   inputDate.setImmediate(true);
				   inputDate.setDateFormat("dd-MM-yyyy");	
				   inputDate.addValueChangeListener(this);
			
	    comboUserResponsible = new ComboBox("Penanggung Jawab");
	    				       comboUserResponsible.setImmediate(true);
	    				     comboUserResponsible.addValueChangeListener(this);
	    				     comboUserResponsible.setNullSelectionAllowed(false);
	    				     comboUserResponsible.setTextInputAllowed(false);
	    reqPlanningTable=new Table();
			reqPlanningTable.setSizeFull();
			reqPlanningTable.setWidth("100%");
			reqPlanningTable.setHeight("300px");
			reqPlanningTable.setSortEnabled(true);
			reqPlanningTable.setImmediate(true);
			reqPlanningTable.setRowHeaderMode(RowHeaderMode.INDEX);
				
		tableContainer=new IndexedContainer(){
		{
    		addContainerProperty("Pilih", CheckBox.class, new CheckBox("", true));
    		addContainerProperty("Nama Barang", String.class, null);
    		addContainerProperty("Satuan", String.class, null);
    		addContainerProperty("Distributor", String.class, null);
    		addContainerProperty("Asuransi", String.class, null);
    		addContainerProperty("Kebutuhan Disetujui", String.class, null);
		}
		};
		reqPlanningTable.setContainerDataSource(tableContainer);
		buttonCheckAll =new Button("Pilih Semua");
						buttonCheckAll.addClickListener(this);
		buttonUncheckAll =new Button("Hapus Semua Pilihan");
						buttonUncheckAll.addClickListener(this);
		
		labelPurchaseDate =new Label();
		labelPurchaseOrderCount=new Label();
		labelUserResponsible=new Label();
		labelRayon =new Label();
		labelError=new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);

			}
		};
		buttonNext = new Button("Selanjutnya");
					buttonNext.addClickListener(this);
		buttonBack =new Button("Kembali");
					buttonBack.addClickListener(this);
		buttonCreate =new Button("Buat Surat Pesanan");
					buttonCreate.addClickListener(this);
		buttonCancel =new Button("Batal");
					buttonCancel.addClickListener(this);
		buttonCancelPo=new Button("Batal");
				buttonCancelPo.addClickListener(this);
		comboPeriod.setValue(function.getListFactory().createMonthListFromNow(2, 5).get(3));

		construct();
		
	}

	GridLayout gridLayout;
	VerticalLayout purchaseListLayout;
	@Override
	public void construct() {
		this.setCaption("Buat Surat Pesanan");
		formPeriode =new FormLayout(){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(labelError);
				addComponent(comboPeriod);
				addComponent(inputDate);
				addComponent(comboUserResponsible);
				
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
						setSpacing(true);
						setMargin(true);
						setStyleName("float-right");
						addComponent(buttonNext);
						addComponent(buttonCancel);
						
					}
				});
				
			}
		};
		
		gridLayout=new GridLayout(2,5){
			{
				setMargin(true);
				setSpacing(true);
				labelPurchaseDate =new Label();
				labelPurchaseOrderCount=new Label();
				labelUserResponsible=new Label();
				addComponent(new Label("<h3>Daftar Surat Pesanan</h3>", ContentMode.HTML), 0,0,1,0);
				addComponent(labelPurchaseOrderCount,0,1, 1, 1);
				addComponent(new Label("Tanggal Pesanan"), 0, 2);
				addComponent(new Label("Penanggung Jawab"), 0, 3);
				addComponent(new Label("Rayon"), 0, 4);
				
				addComponent(labelPurchaseDate, 1,2);
				addComponent(labelUserResponsible, 1, 3);
				addComponent(labelRayon,1,4);
			}
		};
		final CssLayout buttonGroup=new CssLayout(){
			{
				
				setStyleName("float-right");
				addComponent(buttonBack);
				addComponent(buttonCreate);
				addComponent(buttonCancelPo);
				
			}
	
		};
		purchaseListLayout=new VerticalLayout(){
			{
				setSpacing(true);
				setMargin(true);
			}
		};
		viewPO =new VerticalLayout(){
			{
				addComponent(gridLayout);
				addComponent(purchaseListLayout);
				addComponent(buttonGroup);
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
		System.out.println("Set form view " +formView.toString());
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
			item.getItemProperty("Pilih").setValue(new CheckBox("", true));
			item.getItemProperty("Nama Barang").setValue(reqPlanning.getSupplierGoods().getGoods().getName());
			item.getItemProperty("Satuan").setValue(reqPlanning.getSupplierGoods().getGoods().getUnit());
			item.getItemProperty("Distributor").setValue(reqPlanning.getSupplierGoods().getSupplier().getSupplierName());
			item.getItemProperty("Asuransi").setValue(reqPlanning.getSupplierGoods().getGoods().getInsurance().getName());
			item.getItemProperty("Kebutuhan Disetujui").setValue(function.getTextManipulator().intToAngka(reqPlanning.getAcceptedQuantity()));
		}
	}

	//mengembalikan ID req planning yang diseleksi
	private List<Integer> getReqPlanningSelected() {
		List<Integer> returnValue=new ArrayList<Integer>();
		List<Integer> itemIds=(List<Integer>) tableContainer.getItemIds();
		System.out.println("Get req selected" + itemIds.size());
		for(Integer itemId:itemIds){
			Item item=tableContainer.getItem(itemId);
			CheckBox choice=(CheckBox)item.getItemProperty("Pilih").getValue();
			if(choice.getValue()){
				returnValue.add(itemId);
			}
		}
		System.out.println("returnValue "+returnValue.size());
		return returnValue;
	}
	@Override
	public FormData getFormData() {
		FormData formData=new FormData();
		formData.setIdUser((Integer)comboUserResponsible.getValue());
		formData.setPurchaseDate(inputDate.getValue());
		formData.setReqPlanningId(getReqPlanningSelected());
		return formData;
	}
	private List<FormLayout> formLayout;
	private List<PurchaseOrder> purchaseOrders;
	
	@Override
	public boolean generatePurchaseOrderListView(List<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders=purchaseOrders;
		if(purchaseOrders==null){
			return false;
		}
		clearPurchaseOrderList();
		if(purchaseOrders.size()!=0){
			labelPurchaseDate.setValue(function.getDate().dateToText(purchaseOrders.get(0).getDate(), true));
			labelPurchaseOrderCount.setValue(purchaseOrders.size() +" surat pesanan baru siap dibuat,  "
					+ "klik \"Buat Surat Pesanan\" untuk menyimpan surat pesanan");
			labelRayon.setValue(purchaseOrders.get(0).getRayon());
			labelUserResponsible.setValue(purchaseOrders.get(0).getUserResponsible().getUsername()+" - "
					+purchaseOrders.get(0).getUserResponsible().getTitle());
			int position=1;
			System.out.println("Purchase size ="+purchaseOrders.size());
			for(PurchaseOrder purchaseOrder:purchaseOrders){
				final PurchaseOrder purchaseFinal=purchaseOrder;
				
				purchaseListLayout.addComponent(new GridLayout(2, 5){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(new Label("<b>"+purchaseFinal.getPurchaseOrderName()+"</b>", 
								ContentMode.HTML), 0, 0, 1, 0);
						addComponent(new Label("Nomor Surat Pesanan"), 0, 1);
						addComponent(new Label("Tipe"), 0, 2);
						addComponent(new Label("Jumlah Barang"), 0, 3);
						addComponent(new Label("-----------------------------------------"), 0, 4, 1,4);	
						
						addComponent(new Label(": "+purchaseFinal.getPurchaseOrderNumber()+""), 1,1);
						addComponent(new Label(": "+purchaseFinal.getPurchaseOrderType().toString()), 1,2);
						addComponent(new Label(": "+purchaseFinal.getPurchaseOrderItem().size()+""), 1,3);
						
					}
				});
			}			
		}else{
			System.out.println("purchaseOrder nol");
			labelPurchaseDate.setValue("Tidak ada surat pesanan yang dibuat");
		}
		return true;
	}
	
	private void clearPurchaseOrderList(){
		purchaseListLayout.removeAllComponents();
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
		}else if(event.getButton()==buttonCancel || event.getButton()==buttonCancelPo){
			listener.buttonClick("buttonCancel");
		}else if(event.getButton()==buttonCheckAll){
			listener.buttonClick("buttonCheckAll");
		}else if(event.getButton()==buttonUncheckAll){
			listener.buttonClick("buttonUncheckAll");
		}
	}
	@Override
	public void checkReqPlanning(boolean isChecked) {
		List<Integer> listReqPlanning=(List<Integer>) tableContainer.getItemIds();
		System.out.println(listReqPlanning.size() + "Check req planning");
		if(listReqPlanning.size()!=0){
			for(Integer id:listReqPlanning){
				System.out.println(listReqPlanning.size() + "Check req planning value +"+isChecked);
				tableContainer.getItem(id).getItemProperty("Pilih").setValue(new CheckBox("", isChecked));
			}			
		}
	}
	private NewPurchaseOrderListener listener;
	@Override
	public void setListener(NewPurchaseOrderListener listener) {
		this.listener=listener;
	}

	@Override
	public FormViewEnum getFormState() {
		return position;
	}

	@Override
	public void setComboUserData(Map<Integer, String> data) {
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
        	comboUserResponsible.addItem(entry.getKey());
        	comboUserResponsible.setItemCaption(entry.getKey(), entry.getValue());
        	comboUserResponsible.setValue(entry.getKey());
        }
        
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		labelError.setVisible(false);
		if(event.getProperty()==comboPeriod){
			listener.periodChange();
		}
	}

	@Override
	public String getSelectedPeriod() {
		return (String)comboPeriod.getValue();
	}
	public IndexedContainer getTableContainer() {
		return tableContainer;
	}
	public Button getButtonNext() {
		return buttonNext;
	}
	public Label getLabelError() {
		return labelError;
	}
}
