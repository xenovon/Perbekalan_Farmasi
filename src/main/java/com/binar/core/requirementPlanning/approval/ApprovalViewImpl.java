package com.binar.core.requirementPlanning.approval;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.binar.core.requirementPlanning.approval.ApprovalModel.AcceptData;
import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.AcceptancePyramid;
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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ApprovalViewImpl extends VerticalLayout implements
							  ApprovalView, Button.ClickListener, ValueChangeListener {

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

	AcceptancePyramid accept;
	public ApprovalViewImpl(GeneralFunction function){
		generalFunction=function;
		text=generalFunction.getTextManipulator();
		this.accept=function.getAcceptancePyramid();
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
        		addContainerProperty("Nama Barang", String.class, null);
        		addContainerProperty("Satuan", String.class, null);
        		addContainerProperty("HNA + PPN 10%", String.class, null);
        		addContainerProperty("Kebutuhan", String.class, null);
        		addContainerProperty("Total Harga", String.class, null);
        		addContainerProperty("Produsen", String.class, null);
        		addContainerProperty("Distributor", String.class, null);
        		addContainerProperty("Detail", Button.class, null);
        		addContainerProperty("Disetujui?", ComboBox.class, new CheckBox("Disetujui"){{setValue(false);}});
        		addContainerProperty("Jumlah Disetujui", TextField.class, null);        		
        		addContainerProperty("Keterangan", TextArea.class, null);        		
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
		
        
        labelTitle =new Label("<h2>Persetujuan Rencana Kebutuhan</h2>", ContentMode.HTML);
		
		buttonAccept =new Button("Simpan Perubahan");
		buttonAccept.addClickListener(this);
		
		buttonReset=new Button("Reset Pilihan");
		buttonReset.addClickListener(this);
		construct();
		
	}
	private void construct(){
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
	ApprovalListener listener;
	public void setListener(ApprovalListener listener){
		this.listener=listener;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonAccept){
			listener.buttonClick("buttonAccept");
		}else if(event.getSource()==buttonReset){
			listener.updateTable(getSelectedPeriod());
		}
	}
	@Override
	public boolean updateTableData(List<ReqPlanning> data) {
		container.removeAllItems();
		System.out.println(data.size());
		
		//Untuk data combobox 
		List<Integer> comboData=new ArrayList<Integer>();
		comboData.add(0);
		comboData.add(1);
		comboData.add(2);
		
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

			/* add data dari database */
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
			item.getItemProperty("Detail").setValue(new Button(){
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
			
			item.getItemProperty("Disetujui?").setValue(new ComboBox("Disetujui", comboData){
				{
					setNullSelectionAllowed(false);
					setItemCaption(0, "Belum Disetujui");
					setItemCaption(1, "Tidak Disetujui");
					setItemCaption(2, "Disetujui");
					
					setValue(accept.getDropdownChoice(datumFinal.getAcceptance()));
				}
			});
			
//			item.getItemProperty("Disetujui?").setValue(new CheckBox("Disetujui"){{
//				setValue(accept.isAcceptedBy(datumFinal.getAcceptance()));
//			}});
			
			//Set kebutuhan disetujui
			int currentAccepted=datum.getAcceptedQuantity();
			if(currentAccepted==0){
				currentAccepted=datum.getQuantity();
			}
			TextField field=new TextField();
			field.setValue(String.valueOf(currentAccepted));
			item.getItemProperty("Jumlah Disetujui").setValue(field);
			
			TextArea textarea=new TextArea();
			textarea.setWidth("150px");
			textarea.setHeight("60px");
			System.out.println("Isi Data "+accept.getCommentReqPlanning(datum.getIdReqPlanning()));
			textarea.setValue(accept.getCommentReqPlanning(datum.getIdReqPlanning()));
			item.getItemProperty("Keterangan").setValue(textarea);
			
			
		}
		return true;
		
	}
	//menampilkan jendela untuk detail
	
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
	Label labelPriceEstimationPPN;
	Label labelComment;
	Label labelTotalPrice;
	Window windowDetail;
	GridLayout layoutDetail;
	
	//menampilkan jendela untuk detail
	public void showDetailWindow(ReqPlanning data){
		if(layoutDetail==null){
			//buat konten 
			layoutDetail= new GridLayout(2,16){
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
					addComponent(new Label("Estimasi Harga + PPN"), 0, 13);
					addComponent(new Label("Total Harga Dengan PPN"), 0, 14);
					addComponent(new Label("Komentar"), 0, 15);
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
			labelPriceEstimationPPN =new Label();
			labelTotalPrice=new Label();
			labelComment=new Label("", ContentMode.HTML);

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
			layoutDetail.addComponent(labelPriceEstimationPPN, 1,13);
			layoutDetail.addComponent(labelTotalPrice,1, 14);
			layoutDetail.addComponent(labelComment, 1,15);
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
		this.getUI().removeWindow(windowDetail);
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
			labelPriceEstimation.setValue(text.doubleToRupiah(data.getPriceEstimation()));
			labelPriceEstimationPPN.setValue(text.doubleToRupiah(data.getPriceEstimationPPN()));
			labelComment.setValue(accept.getCommentReqPlanningFormat(data.getIdReqPlanning()));
			labelTotalPrice.setValue(text.doubleToRupiah(data.getPriceEstimationPPN()*data.getQuantity()));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public String getSelectedPeriod() {
		return (String) selectMonth.getValue();		
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectMonth)
			listener.updateTable(getSelectedPeriod());		
	}

	@Override
	public IndexedContainer getContainer() {
		return container;
	}
	
	public String getPeriodeValue(){
		return (String) selectMonth.getValue();
	}

}
