package com.binar.core.requirementPlanning.reqPlanningList;

import java.util.Calendar;
import java.util.List;

import com.binar.core.requirementPlanning.inputRequierementPlanning.TableData;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
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
	Container container;
	TextField inputFilter;
	//untuk fungsi tambah rupiah
	TextManipulator text;
	
	public ReqPlanningListViewImpl(GeneralFunction function) {
		this.generalFunction=function;
		text=generalFunction.getTextManipulator();
	}
	
	public void init(){
		table=new Table();
		table.setSizeFull();
        table.setWidth("100%");
        table.setSortEnabled(true);
        
        container=new IndexedContainer(){
        	{
        		addContainerProperty("Nama Barang", String.class, null);
        		addContainerProperty("Satuan", String.class, null);
        		addContainerProperty("HNA + PPN 10%", String.class, null);
        		addContainerProperty("Kebutuhan", String.class, null);
        		addContainerProperty("Total Harga", String.class, null);
        		addContainerProperty("Produsen", String.class, null);
        		addContainerProperty("Distributor", String.class, null);
        		addContainerProperty("Disetujui?", CheckBox.class, new CheckBox("Disetujui"){{setValue(false);}});
        		addContainerProperty("Kebutuhan Disetujui", String.class, null);
        		addContainerProperty("Operasi", Button.class, null);
        		
        	}
        };
        table.setContainerDataSource(container);		
		
        
		labelFilter=new Label("<b style='font-size:14px'>Filter : </b> ", ContentMode.HTML);
		labelYear=new Label("Pilih Tahun  :");
		labelMonth=new Label("Pilih Bulan  :");
		
		List<String> yearList=generalFunction.getListFactory().createYearList(5);
		List<String> monthList=generalFunction.getListFactory().createMonthList();
		selectYear = new ComboBox("", yearList);
		selectMonth =new ComboBox("", monthList);

		selectYear.setImmediate(true);
		selectMonth.setImmediate(true);

		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectYear.setValue(yearList.get(0));
		selectMonth.setValue(monthList.get(Calendar.getInstance().get(Calendar.MONTH)));

		selectMonth.setTextInputAllowed(false);
		selectYear.setTextInputAllowed(false);
		selectYear.addStyleName("non-caption-form");
		selectMonth.addStyleName("non-caption-form");

		selectMonth.setWidth("120px");
		selectYear.setWidth("120px");
		labelTitle =new Label("<h2>Daftar Rencana Kebutuhan</h2>", ContentMode.HTML);
		
		inputFilter=new TextField(){
			{
				setCaption("Filter Tabel");
				setImmediate(true);
			}
		};
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
		
		this.addComponents(labelTitle, layoutFilter, table);
		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectMonth ||
				event.getProperty()==selectYear){
			
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
/*
 *         		addContainerProperty("Nama", String.class, null);
        		addContainerProperty("Satuan", String.class, null);
        		addContainerProperty("HNA + PPN 10%", String.class, null);
        		addContainerProperty("Kebutuhan", String.class, null);
        		addContainerProperty("Total Harga", String.class, null);
        		addContainerProperty("Produsen", String.class, null);
        		addContainerProperty("Distributor", String.class, null);
        		addContainerProperty("Disetujui?", CheckBox.class, new CheckBox("Disetujui"){{setValue(false);}});
        		addContainerProperty("Kebutuhan Disetujui", String.class, null);
        		addContainerProperty("Operasi", Button.class, new Button("Detail"));
						
 */
			/* hitung harga barang dengan PPN */
			double hnaPPNdouble=datum.getPriceEstimation()+(datum.getPriceEstimation()*0.1);
			String hnaPPN=text.doubleToRupiah(hnaPPNdouble);
			String totalPrice=text.doubleToRupiah(hnaPPNdouble*datum.getQuantity());
			
			/* add data ke database */
			item.getItemProperty("Nama Barang").setValue(datum.getSupplierGoods().getGoods().getName());
			item.getItemProperty("Satuan").setValue(datum.getSupplierGoods().getGoods().getUnit());
			item.getItemProperty("HNA + PPN 10%").setValue(hnaPPN);
			item.getItemProperty("Kebutuhan").setValue(text.intToAngka(datum.getQuantity()));
			item.getItemProperty("Total Harga").setValue(totalPrice);
			item.getItemProperty("Produsen").setValue(datum.getSupplierGoods().getManufacturer().getManufacturerName());
			item.getItemProperty("Distributor").setValue(datum.getSupplierGoods().getSupplier().getSupplierName());
			item.getItemProperty("Disetujui").setValue(new CheckBox("Disetujui"){{
				setValue(datumFinal.isAccepted());
			}});
			item.getItemProperty("Jumlah Disetujui").setValue(datum.getAcceptedQuantity());
			
			
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
}
