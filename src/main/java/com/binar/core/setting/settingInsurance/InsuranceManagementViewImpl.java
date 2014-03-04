package com.binar.core.setting.settingInsurance;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.Insurance;
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

public class InsuranceManagementViewImpl extends VerticalLayout 
		implements InsuranceManagementView, ClickListener {

	private GeneralFunction function;
	private InsuranceManagementListener listener;

	private Label title;
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonInput;
	private Label labelFilter;
	private TextField inputFilter;
	
	private TableFilter filter;
	private TextManipulator text;
	public InsuranceManagementViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
	}
	
	public void init(){
		title=new Label("<h2>Pengaturan Data Asuransi</h2>", ContentMode.HTML);
		
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
		buttonInput=new Button("Input data Baru");
		buttonInput.addClickListener(this);
		
		table=new Table();
		table.setSizeFull();
		table.setWidth("70%");
		table.setHeight("439px");
		table.setSortEnabled(true);
		table.setImmediate(true);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		
		tableContainer=new IndexedContainer(){
			{
				addContainerProperty("Nama", String.class, null);
				addContainerProperty("Deskripsi", String.class,null);
				addContainerProperty("Tampil?", CheckBox.class, null);
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

	
	public boolean updateTableData(List<Insurance> data){
		tableContainer.removeAllItems();
		System.out.println(data.size());
		if(data.size()==0){
			Notification.show("Data Obat kosong", Type.WARNING_MESSAGE);
			return false;
		}
		for(Insurance datum:data){
			final Insurance datumFinal=datum;
			Item item = tableContainer.addItem(datum.getIdInsurance());
			
			item.getItemProperty("Nama").setValue(datum.getName());
			item.getItemProperty("Deskripsi").setValue(datum.getDescription());
			item.getItemProperty("Tampil?").setValue(new CheckBox("",datum.isShowInDropdown()){{setEnabled(false);}});
			
			item.getItemProperty("Operasi").setValue(new GridLayout(2,1){
			{
					Button buttonEdit=new Button();
					buttonEdit.setDescription("Ubah data ini");
					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
					buttonEdit.addStyleName("button-table");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.editClick(datumFinal.getIdInsurance());
						}
					});
				
					
					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.deleteClick(datumFinal.getIdInsurance());
						}
					});
					this.setSpacing(true);
					this.setMargin(false);
					this.addComponent(buttonEdit, 0, 0);
					this.addComponent(buttonDelete, 1, 0);
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
	public void setListener(InsuranceManagementListener listener) {
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
					setWidth("550px");
					setHeight("400px");
				}
			};

		}
		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
	}

	
	public void hideButtonNew(){
		buttonInput.setVisible(false);
	}
	public void showButtonNew(){
		buttonInput.setVisible(true);
	}	
	
	
}
