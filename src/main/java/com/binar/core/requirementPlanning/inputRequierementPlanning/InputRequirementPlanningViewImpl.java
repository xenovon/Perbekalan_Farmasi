package com.binar.core.requirementPlanning.inputRequierementPlanning;

import java.util.List;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class InputRequirementPlanningViewImpl extends VerticalLayout
	implements InputRequirementPlanningView, ClickListener, ValueChangeListener {

	private GeneralFunction generalFunction;

	private Label labelSelectMonth;
	private Label labelTitle;
	private Table table;
	private Button buttonInput;
	private ComboBox selectMonth;
	
	private Container tableContainer;
	
	private InputRequirementListener listener;
	
	public InputRequirementPlanningViewImpl(GeneralFunction function){
		this.generalFunction=function;
	}
	
	public void init(){
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
        
        tableContainer=new IndexedContainer(){
        	{
        		table.addContainerProperty("No",Integer.class, null);
        		table.addContainerProperty("Nama Barang",String.class, null);
        		table.addContainerProperty("Kebutuhan",Integer.class, null);
        		table.addContainerProperty("Produsen",String.class, null);
        		table.addContainerProperty("Distributor",String.class, null);
        		table.addContainerProperty("Operasi", GridLayout.class, null);
        	}
        };
        table.setContainerDataSource(tableContainer);
        
		construct();
	}
	
	private void construct(){
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponent(labelTitle);
		this.addComponent(new GridLayout(2, 1){
			{
				addComponent(labelSelectMonth,0,0);
				addComponent(selectMonth,1,0);
			}
		});
		this.addComponent(buttonInput);
		this.addComponent(table);
	}
	Window window;
	public void displayForm(Component content){
		if(window==null){
			window=new Window("Masukan Rencana Kebutuhan"){
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
	public void setTableData(List<TableData> data){
		System.out.println(data.size());
		for(TableData datum:data){
			final TableData datumFinal=datum;
			Item item=tableContainer.addItem(datum.getIdReq());
			item.getItemProperty("Nama Barang").setValue(datum.getGoodsName());
			item.getItemProperty("Kebutuhan").setValue(datum.getReq());
			item.getItemProperty("Produsen").setValue(datum.getManufacturer());
			item.getItemProperty("Distributor").setValue(datum.getSupp());
			item.getItemProperty("Operasi").setValue(new GridLayout(){
				{
					Button buttonEdit=new Button();
					buttonEdit.setCaption("Ubah");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							showEdit(datumFinal.getReq());
						}
					});
					
					Button buttonShow=new Button();
					buttonShow.setCaption("Detail");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							showDetail(datumFinal.getIdReq());
						}
					});
					
					Button buttonDelete=new Button();
					buttonDelete.setCaption("Hapus");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							delete(datumFinal.getIdReq());
						}
					});
					
				}
			});
			
		}
	}
	public void showDetail(int reqId){
		System.out.println("Show detail invoked id : "+reqId);
	}
	public void showEdit(int reqId){
		System.out.println("Show Edit invoked id : "+reqId);		
	}
	public void delete(int reqId){
		System.out.println("Delete invoked id : "+reqId);		
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
			listener.buttonClick("buttonInput", selectMonth.getValue());
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

/*
 * 
 * 
 * //		table.addContainerProperty("No",Integer.class, null);
//		table.addContainerProperty("Nama Barang",String.class, null);
////		table.addContainerProperty("Perkiraan Harga",String.class, null);
//		table.addContainerProperty("Kebutuhan",Integer.class, null);
////		table.addContainerProperty("Perkiraan Jumlah Harga",String.class, null);
//		table.addContainerProperty("Produsen",String.class, null);
//		table.addContainerProperty("Distributor",String.class, null);
////		table.addContainerProperty("Disetujui?",CheckBox.class, null);
////		table.addContainerProperty("Jumlah Disetujui",Integer.class, null);
//		table.addContainerProperty("Operasi", CssLayout.class, null);

*/
