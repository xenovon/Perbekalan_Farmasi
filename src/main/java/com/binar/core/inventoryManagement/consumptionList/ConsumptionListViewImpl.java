package com.binar.core.inventoryManagement.consumptionList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.StockFunction;
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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.RowHeaderMode;

public class ConsumptionListViewImpl extends VerticalLayout implements ConsumptionListView,  ClickListener, ValueChangeListener {

	private GeneralFunction generalFunction;
	private Table table;
	private Label labelFilter;
	private Label labelYear;
	private Label labelMonth;
	private Label labelTitle;
	private ComboBox selectYear;
	private ComboBox selectMonth;
	private GridLayout layoutFilter;
	private GridLayout layoutInput;
	private IndexedContainer container;
	private IndexedContainer containerByDate;
	private TextField inputFilter;
	private TextManipulator text;
	private ConsumptionListListener listener;
	private Table tableDetail;
	private Table tableDetailByDate;
	private Table tableByDate;
	private Button buttonInput;
	private ComboBox viewMode;
	private Label labelSearch;
	private Label labelMode;
	private DateManipulator dateMan;
	private StockFunction stock;
	
	public ConsumptionListViewImpl (GeneralFunction function) {
		this.generalFunction=function;
		text=generalFunction.getTextManipulator();
		this.dateMan=generalFunction.getDate();
		this.stock=generalFunction.getStock();
	}
	
	private TableFilter filter;
	public void init(){
		filter=generalFunction.getFilter("");
		inputFilter= new TextField(){
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
		table=new Table(); //buat tabel untuk menampung data untuk halaman list
		table.setSizeFull();
		table.setWidth("100%");
		table.setHeight("420px");
		table.setSortEnabled(true);
        table.setRowHeaderMode(RowHeaderMode.INDEX);
       
       container=new IndexedContainer(){
        	{ //inisialisasi 
        		addContainerProperty("Nama Barang", String.class, null);
        		addContainerProperty("Asuransi", String.class, null);
        		addContainerProperty("Satuan", String.class, null);
        		addContainerProperty("Total Pengeluaran", String.class, null);
        		addContainerProperty("Operasi", Button.class, null);
        	}
        };
    	table.setContainerDataSource(container);
    	
		tableByDate=new Table(); //buat tabel untuk menampung data untuk halaman list
		tableByDate.setSizeFull();
		tableByDate.setWidth("100%");
		tableByDate.setHeight("420px");
		tableByDate.setSortEnabled(true);
        tableByDate.setRowHeaderMode(RowHeaderMode.INDEX);
	
    	containerByDate=new IndexedContainer(){
        	{ //inisialisasi 
        		addContainerProperty("Tanggal Pengeluaran", String.class, null);
        		addContainerProperty("Total Item", String.class, null);
        		addContainerProperty("Operasi", Button.class, null);
        	}
        };
    	tableByDate.setContainerDataSource(containerByDate);
		window=new Window(){
			{
				center();
				setClosable(false);
				setWidth("500px");					
			}
		};
		windowEdit=new Window(){
			{
				center();
				setClosable(false);
				setWidth("500px");					
			}
		};

    	tableByDate.setVisible(false);
		labelFilter=new Label("<b style='font-size:14px'>Filter : </b> ", ContentMode.HTML);
		labelYear=new Label("Pilih Tahun  :");
		labelMonth=new Label("Pilih Bulan  :");
		labelSearch = new Label("Filter tabel : ");
		labelMode = new Label("Lihat berdasarkan : ");
		
		List<String> yearList=generalFunction.getListFactory().createYearList(5);
		List<String> monthList=generalFunction.getListFactory().createMonthList();
		selectYear = new ComboBox("", yearList);
		selectMonth =new ComboBox("", monthList);
		
		buttonInput=new Button("Input Pengeluaran");
		buttonInput.setDescription("Input pengeluaran harian untuk bulan terpilih");
		buttonInput.addClickListener(this);

		selectYear.setImmediate(true);
		selectMonth.setImmediate(true);
		
		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectYear.setValue(yearList.get(0));
		selectMonth.setValue(monthList.get(Calendar.getInstance().get(Calendar.MONTH)));
		for(String month:monthList){
			System.out.println("Bulan "+month);
		}
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		selectMonth.setTextInputAllowed(false);
		selectYear.setTextInputAllowed(false);
		selectYear.addStyleName("non-caption-form");
		selectMonth.addStyleName("non-caption-form");

		selectMonth.setWidth("120px");
		selectYear.setWidth("120px");
	
		selectMonth.addValueChangeListener(this);
		selectYear.addValueChangeListener(this);

		labelTitle =new Label("<h2>Daftar Pengeluaran Harian</h2>", ContentMode.HTML);
		viewMode = new ComboBox(){
			{
				setImmediate(true);
				setWidth(generalFunction.FORM_WIDTH);
				addItem("barang");
				addItem("tanggal_pengeluaran");
				setItemCaption("barang", "Sort berdasarkan barang");
				setItemCaption("tanggal_pengeluaran", "Sort berdasarkan tanggal");
				setValue("barang");
			}
		};
		
		viewMode.addValueChangeListener(this);	
		construct();		
	}
	
	private void construct(){
		layoutFilter =new GridLayout(6,1);
		layoutFilter.setMargin(true);
		layoutFilter.setSpacing(true);
		
		layoutFilter.addComponent(labelYear, 0, 0);
		layoutFilter.addComponent(selectYear, 1, 0);
		layoutFilter.addComponent(labelMonth, 2, 0);
		layoutFilter.addComponent(selectMonth, 3, 0);
		layoutFilter.addComponent(labelMode, 4, 0);
		layoutFilter.addComponent(viewMode, 5, 0);
		
		layoutInput = new GridLayout(3, 1);
		layoutInput.setSpacing(true);
		layoutInput.addComponent(labelSearch, 0, 0);
		layoutInput.addComponent(inputFilter, 1, 0 );
		layoutInput.addComponent(buttonInput, 2, 0);
		
		this.setSpacing(true);
		this.addComponents(labelTitle, layoutFilter, layoutInput, table, tableByDate);		
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectMonth ||
				event.getProperty()==selectYear){
			listener.updateTable(getSelectedPeriod(), false);
			listener.updateTableByDate(getSelectedPeriod(), false);
		}
		if (event.getProperty()==viewMode){
			listener.getViewMode(getSelectedViewMode());
		}
	}
	
	
	public void setViewMode(String mode){
		if(mode.equals("barang")){
			table.setVisible(true);
			tableByDate.setVisible(false);
		}else{
			table.setVisible(false);
			tableByDate.setVisible(true);			
		}
	}
	
	@Override
	public boolean updateTableData(Map<Goods, Integer> dataTable) {
		//mengupdate tabel dengan key Goods dan value jumlah pengeluaran
		container.removeAllItems();
		System.out.println(dataTable.size());

		System.out.println("Jumlah data updateTableData ="+dataTable.size());
        for (Map.Entry<Goods, Integer> entry : dataTable.entrySet()) { //untuk setiap data goods dan quantity
        	final Goods goods=entry.getKey(); //dapatkan key
        	final int quantity=entry.getValue(); //dan value     
        	if(goods==null){
            	System.out.println("Quantity untuk goods "+ goods.getName() +" = "+ quantity);        		
        	}
			Item item = container.addItem(goods.getIdGoods()); //membuat item untuk diisi ke container
			/* add data ke database */
			item.getItemProperty("Nama Barang").setValue(goods.getName()); //mengeset nama barang
			item.getItemProperty("Asuransi").setValue(goods.getInsurance().getName()); //mengeset nama barang
			item.getItemProperty("Satuan").setValue(goods.getUnit());
			item.getItemProperty("Total Pengeluaran").setValue(text.intToAngka(quantity));	
			item.getItemProperty("Operasi").setValue(new Button(){ //mengeset operasi
			{
				{
					setDescription("Lihat Lebih detail");
					setIcon(new ThemeResource("icons/image/icon-detail.png"));
					addStyleName("button-table");
					
					addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.showDetail(goods.getIdGoods(), quantity ); //menambahkan operasi showDetail
						}
					});
				}
			}
			});
		}
		return true;		
	}

	@Override
	public boolean updateTableDataByDate(Map<DateTime, Integer> dataByDate) {
		//mengupdate tabel dengan key Goods dan value jumlah pengeluaran
				containerByDate.removeAllItems();
				System.out.println(dataByDate.size());
				
		        for (Map.Entry<DateTime, Integer> entry : dataByDate.entrySet()) { //untuk setiap data goods dan quantity
		        	final DateTime date=entry.getKey(); //dapatkan key
		        	final int itemQuantity=entry.getValue(); //dan value	        	
		        	
//		        	SimpleDateFormat format=new SimpleDateFormat("dd MMMMM yyyy");
					Item item = containerByDate.addItem(date);
					/* add data ke database */
					item.getItemProperty("Tanggal Pengeluaran").setValue(dateMan.dateToText(date.toDate(), true)); //mengeset nama barang
					item.getItemProperty("Total Item").setValue(text.intToAngka(itemQuantity));	
					item.getItemProperty("Operasi").setValue(new Button(){ //mengeset operasi
					{
						{
							setDescription("Lihat Lebih detail");
							setIcon(new ThemeResource("icons/image/icon-detail.png"));
							addStyleName("button-table");
							
							addClickListener(new ClickListener() {
								
								@Override
								public void buttonClick(ClickEvent event) {
									listener.showDetailByDate(date); //menambahkan operasi showDetail
								}
							});
						}
					}
					});
				}
				return true;		
	}
	
	@Override
	public void setListener(ConsumptionListListener listener) {
		this.listener=listener;
	}
	
	private Label labelName;
	private Label labelUnit;
	private Label labelQuantity;
	private Window windowDetail;
	private VerticalLayout layoutDetail;
	private GridLayout layoutData;
	private IndexedContainer containerDetail;

	@Override
	public void showDetailWindow(List<GoodsConsumption> data, int quantity, boolean withEditConsumption) { //membuat tampilan untuk showDetail
		if(layoutDetail==null){ //jika layout null
			//buat konten 
			layoutDetail = new VerticalLayout();
			layoutData= new GridLayout(2,3){ 
				{
					setSpacing(true);
					setMargin(true);
					addComponent(new Label("Nama Barang : "), 0,0);
					addComponent(new Label("Satuan : "), 0, 1);
					addComponent(new Label("Total Pengeluaran : "), 0, 2);
				}	
			};
			//instantiasi label
			labelName=new Label();
			labelQuantity = new Label();
			labelUnit =new Label();	
			
			//add Component konten ke layout
			layoutData.addComponent(labelName, 1,0);
			layoutData.addComponent(labelUnit, 1,1);
			layoutData.addComponent(labelQuantity, 1,2);
			
			tableDetail = new Table(); //tabel untuk menampilkan jumlah harian
			tableDetail.setSizeFull();
			tableDetail.setWidth("100%");
			tableDetail.setHeight("420px");
			tableDetail.setSortEnabled(true);
	        tableDetail.setRowHeaderMode(RowHeaderMode.INDEX);
	        
	        containerDetail = new IndexedContainer(){ //deklarasi kontainer harian
	        	{
	        		addContainerProperty("Tanggal", String.class, null);
	        		addContainerProperty("Jumlah", String.class, null);
	        		addContainerProperty("Instalasi", String.class, null);
	        		addContainerProperty("Informasi", String.class, null);
	        		addContainerProperty("Operasi", GridLayout.class, null);
	        	}
	        };        
	        tableDetail.setContainerDataSource(containerDetail); //set kontainer harian
	        layoutDetail.addComponent(layoutData);
	        layoutDetail.addComponent(tableDetail);
	        
		}//menutup jika layoutdetail null
	    setLabelData(data, quantity,withEditConsumption);
	    if(windowDetail==null){
			windowDetail=new Window("Detail Pengeluaran Harian"){
				{
					center();
					setWidth("800px");					
				}
			};
						
		}//menutup jika windowDetail null

		
		windowDetail.setContent(layoutDetail);
		this.getUI().addWindow(windowDetail);		
	}
	
	private Window windowDetailByDate;
	private IndexedContainer containerDetailByDate;
	private VerticalLayout layoutDetailByDate;
	private Label labelDate;
	private Label labelItemQuantity;
	private DateTime currentDate;
	public DateTime getCurrentDate() {
		return currentDate;
	}
	public void showDetailWindowByDate(List<GoodsConsumption> list, DateTime date,  boolean withEditConsumption) {
		this.currentDate=date;
		if(layoutDetailByDate==null){ //jika layout null
			//buat konten 

			layoutDetailByDate= new VerticalLayout(){
				{
					setSpacing(true);
					setMargin(true);
				}
			};

			GridLayout layoutDetailGrid= new GridLayout(2,2){ 
					{
					setSpacing(true);
					setMargin(true);
					addComponent(new Label("Tanggal Pengeluaran"), 0,0);
					addComponent(new Label("Total Item"), 0, 1);
				}	
			};
			//instantiasi label
			labelDate=new Label();
			labelItemQuantity = new Label();
			
			//add Component konten ke layout
			layoutDetailGrid.addComponent(labelDate, 1,0);
			layoutDetailGrid.addComponent(labelItemQuantity, 1,1);
			
			tableDetailByDate = new Table(); //tabel untuk menampilkan jumlah harian
			tableDetailByDate.setSizeFull();
			tableDetailByDate.setWidth("100%");
			tableDetailByDate.setHeight("420px");
			tableDetailByDate.setSortEnabled(true);
	        tableDetailByDate.setRowHeaderMode(RowHeaderMode.INDEX);
	        
	        containerDetailByDate = new IndexedContainer(){ //deklarasi kontainer harian
	        	{
	        		addContainerProperty("Nama barang", String.class, null);
	        		addContainerProperty("Jumlah Pengeluaran", String.class, null);	        		
	        		addContainerProperty("Instalasi", String.class, null);
	        		addContainerProperty("Informasi", String.class, null);
	        		addContainerProperty("Operasi", GridLayout.class, null);
	        	}
	        };        
	        tableDetailByDate.setContainerDataSource(containerDetailByDate); //set kontainer harian  
	        layoutDetailByDate.addComponents(layoutDetailGrid, tableDetailByDate);
		}//menutup jika layoutdetail null
	    setLabelDataByDate(list, withEditConsumption);
	    if(windowDetailByDate==null){
			windowDetailByDate=new Window("Detail Pengeluaran Harian"){
				{
					center();
					setWidth("800px");					
				}
			};
						
		}//menutup jika windowDetail null		
		windowDetailByDate.setContent(layoutDetailByDate);
		this.getUI().addWindow(windowDetailByDate);		
	}
	
	public boolean setLabelDataByDate(List<GoodsConsumption> consumptions, final boolean withEditConsumption ) {
		if(consumptions.size()==0){
			return false;
		}
		if(layoutDetailByDate==null){
			return false;
		}
		
		containerDetailByDate.removeAllItems();
		try {
			SimpleDateFormat format=new SimpleDateFormat("dd MMMMM yyyy");
			labelDate.setValue(format.format(consumptions.get(0).getConsumptionDate()));
			labelItemQuantity.setValue(consumptions.size()+"");	
						
			for (GoodsConsumption consumption:consumptions){
				final GoodsConsumption consumptionFinal=consumption;
				Item itemDetailByDate = containerDetailByDate.addItem(consumption); 
				itemDetailByDate.getItemProperty("Nama barang").setValue(consumption.getGoods().getName());
				itemDetailByDate.getItemProperty("Jumlah Pengeluaran").setValue(text.intToAngka(consumption.getQuantity()));
				itemDetailByDate.getItemProperty("Instalasi").setValue(consumption.getWard());
        		itemDetailByDate.getItemProperty("Informasi").setValue(consumption.getInformation());;
				
				itemDetailByDate.getItemProperty("Operasi").setValue(new GridLayout(2,1){{
					Button buttonEdit=new Button();
					buttonEdit.setDescription("Ubah data ini");
					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
					buttonEdit.addStyleName("button-table");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.edit(consumptionFinal.getIdGoodsConsumption());
						}
					});

					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.delete(consumptionFinal.getIdGoodsConsumption());
						}
					});
					this.setSpacing(true);
					if(withEditConsumption){
						this.addComponent(buttonEdit, 0, 0);							
						this.addComponent(buttonDelete, 1, 0);																			

						if(stock.isAnyNewestItem(consumptionFinal)){
							buttonDelete.setEnabled(false);
						}
					}
				}});		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return false;
	}
	public Window getWindowEdit() {
		return windowEdit;
	}
	public void setLabelData(final List<GoodsConsumption> data, int quantity, final boolean withEditConsumption) { //mengeset label data untuk tabel header
		containerDetail.removeAllItems();
		if(data.size()==0){
			return;
		}
		try {
			labelName.setValue(data.get(0).getGoods().getName());
			labelUnit.setValue(data.get(0).getGoods().getUnit());
			labelQuantity.setValue(text.intToAngka(quantity));
			
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMMM yyyy");
			
			for (GoodsConsumption consumption : data){
				final GoodsConsumption consumptionFinal=consumption;
				Item itemDetail = containerDetail.addItem(consumption.getIdGoodsConsumption()); 
				itemDetail.getItemProperty("Tanggal").setValue(dateFormat.format(consumption.getConsumptionDate()));
				itemDetail.getItemProperty("Jumlah").setValue(text.intToAngka(consumption.getQuantity()));
				itemDetail.getItemProperty("Instalasi").setValue(consumption.getWard());
        		itemDetail.getItemProperty("Informasi").setValue(consumption.getInformation());;
				itemDetail.getItemProperty("Operasi").setValue(new GridLayout(2,1){{
					Button buttonEdit=new Button();
					buttonEdit.setDescription("Ubah data ini");
					buttonEdit.setIcon(new ThemeResource("icons/image/icon-edit.png"));
					buttonEdit.addStyleName("button-table");
					buttonEdit.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.edit(consumptionFinal.getIdGoodsConsumption());
						}
					});

					Button buttonDelete=new Button();
					buttonDelete.setDescription("Hapus Data");
					buttonDelete.setIcon(new ThemeResource("icons/image/icon-delete.png"));
					buttonDelete.addStyleName("button-table");
					buttonDelete.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							listener.delete(consumptionFinal.getIdGoodsConsumption());
						}
					});
					this.setSpacing(true);
					if(withEditConsumption){
						this.addComponent(buttonEdit, 0, 0);
						this.addComponent(buttonDelete, 1, 0);	
						if(stock.isAnyNewestItem(consumptionFinal)){
							buttonDelete.setEnabled(false);
						}
					}
				}});		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
//			listener.updateTable(this.getSelectedPeriod());
//			listener.updateTableByDate(this.getSelectedPeriod());
		}		
	}
	
	@Override
	public String getSelectedPeriod() {
		return selectMonth.getValue()+"-"+selectYear.getValue();
	}		

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getSource()==buttonInput){
			String select=getSelectedPeriod();
			if(select!=null){
				listener.buttonClick("buttonInput", select);				
			}else{
				Notification.show("Terjadi Kesalahan, Pilih bulan lagi");
			}
		}
		System.err.println("Invoked in view");
	}
	
	Window window;
	@Override
	public void displayForm(Component content, String title) {		
		//menghapus semua window terlebih dahulu
		for(Window window:this.getUI().getWindows()){
			window.close();
		}

		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
	}
	Window windowEdit;
	public void displayFormEdit(Component content, String title) {		
		//menghapus semua window terlebih dahulu
		
		if(windowEdit==null){
		}
		windowEdit.setCaption(title);
		windowEdit.setContent(content);
		this.getUI().addWindow(windowEdit);
	}

	@Override
	public Window getWindow() {
		return window;
	}

	@Override
	public String getSelectedViewMode() {
		return (String) viewMode.getValue();
	}
	
	public void hideButtonNew(){
		buttonInput.setVisible(false);
	}
	public void showButtonNew(){
		buttonInput.setVisible(true);
	}	
}
