package com.binar.core.user.userList;

import java.util.List;

import com.binar.entity.User;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.Window;

public class UserListViewImpl extends VerticalLayout implements UserListView, ClickListener{


	private GeneralFunction function;
	private UserListViewListener listener;

	private Label title;
	private Table table;
	private IndexedContainer tableContainer;
	private Button buttonInput;
	private Label labelFilter;
	private TextField inputFilter;
	
	private TableFilter filter;
	private TextManipulator text;
	
	public UserListViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
	}
	
	
	public void init() {
		title=new Label("<h2>Manajemen Pengguna</h2>", ContentMode.HTML);
		
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
		buttonInput=new Button("Masukan Pengguna Baru");
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
				addContainerProperty("Nama", String.class, null);
				addContainerProperty("Nama Lengkap", String.class,null);
				addContainerProperty("Role", String.class, null);
				addContainerProperty("Nomor Pegawai", String.class, null);
				addContainerProperty("Nomor Telepon", String.class, null);
				addContainerProperty("Aktif?", String.class, null);
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

	@Override
	public boolean updateTableData(List<User> data) {
		tableContainer.removeAllItems();
		System.out.println(data.size());
		if(data.size()==0){
			return false;
		}
		for(User datum:data){
			final User datumFinal=datum;
			Item item = tableContainer.addItem(datum.getIdUser());
			
			item.getItemProperty("Nama").setValue(datum.getUsername());
			item.getItemProperty("Nama Lengkap").setValue(datum.getName());
			item.getItemProperty("Role").setValue(datum.getRole().getRoleName());
			item.getItemProperty("Nomor Pegawai").setValue(datum.getEmployeeNum());
			item.getItemProperty("Nomor Telepon").setValue(datum.getPhoneNumber());
			item.getItemProperty("Aktif?").setValue(datum.isActive()?"Aktif":"Tidak Aktif");
			
			item.getItemProperty("Operasi").setValue(new GridLayout(3,1){
			{
					Button buttonEdit=new Button();
					buttonEdit.setDescription("Ubah data ini");
					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
					buttonEdit.addStyleName("button-table");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.editClick(datumFinal.getIdUser());
						}
					});
				
					Button buttonShow=new Button();
					buttonShow.setDescription("Lihat Lebih detail");
					buttonShow.setIcon(new ThemeResource("icons/image/icon-detail.png"));
					buttonShow.addStyleName("button-table");
					buttonShow.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showClick(datumFinal.getIdUser());;
						}
					});
					
					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.deleteClick(datumFinal.getIdUser());
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

	Label labelUserName;
	Label labelName;
	Label labelRole;
	Label labelEmployeeNum;
	Label labelTitle;
	Label labelPhoneNumber;
	Label labelAddress;
	Label labelActive;
	
	GridLayout layoutDetail;
	Window windowDetail;
	/*
	 * Data Dibutuhkan
	 * username
	 * role
	 * employe Num
	 * title
	 * Name
	 * Phone number
	 * alamat
	 * active?
	 */			

	public void showDetailWindow(User user){
		if(layoutDetail==null){
			//buat konten 

			layoutDetail= new GridLayout(2,9){
				{

					setSpacing(true);
					setMargin(true);
					setWidth("450px");
					addComponent(new Label("Nama Pengguna"), 0, 1);
					addComponent(new Label("Nama Lengkap"), 0,2);
					addComponent(new Label("Role"), 0, 3);
					addComponent(new Label("Nomor Pegawai"), 0, 4);
					addComponent(new Label("Jabatan"), 0,5);
					addComponent(new Label("Nomor Telepon"), 0,6);
					addComponent(new Label("Alamat"), 0, 7);
					addComponent(new Label("Aktif?"), 0,8);
				}	
			};
			//instantiasi label
			 labelUserName=new Label();
			 labelName=new Label();
			 labelRole=new Label();
			 labelEmployeeNum=new Label();
			 labelTitle=new Label();
			 labelPhoneNumber=new Label();
			 labelAddress=new Label();
			 labelActive=new Label();
			//add Component konten ke layout

			 layoutDetail.addComponent(labelUserName,1,1);
			 layoutDetail.addComponent(labelName,1,2);
			 layoutDetail.addComponent(labelRole,1,3);
			 layoutDetail.addComponent(labelEmployeeNum,1,4);
			 layoutDetail.addComponent(labelTitle,1,5);
			 layoutDetail.addComponent(labelPhoneNumber,1,6);
			 layoutDetail.addComponent(labelAddress,1,7);
			 layoutDetail.addComponent(labelActive,1,8);
		}
		
		setLabelData(user);
		if(windowDetail==null){
			windowDetail=new Window("Detail Data Pengguna"){
				{
					center();
					setWidth("500px");
					
				}
			};
		}
		windowDetail.setContent(layoutDetail);
		this.getUI().addWindow(windowDetail);
	}
	
	public void setLabelData(User user){
		 labelUserName.setValue(user.getUsername());
		 labelName.setValue(user.getName());
		 labelRole.setValue(user.getRole().getRoleName());
		 labelEmployeeNum.setValue(user.getEmployeeNum());
		 labelTitle.setValue(user.getTitle());
		 labelPhoneNumber.setValue(user.getPhoneNumber());
		 labelAddress.setValue(user.getAddress());
		 labelActive.setValue(user.isActive()?"Aktif":"Tidak Aktif");
		
	}

	@Override
	public void setListener(UserListViewListener listener) {
		this.listener=listener;
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonInput){
			listener.buttonClick("buttonInput");
		}
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
					setWidth("600px");
					setHeight("80%");
				}
			};

		}
		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
	}


}
