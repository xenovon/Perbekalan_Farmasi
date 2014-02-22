package com.binar.generalFunction;

import java.text.DecimalFormat;

import com.avaje.ebean.EbeanServer;
import com.binar.database.GetEbeanServer;
import com.binar.view.DashboardView;
import com.binar.view.DataManagementView;
import com.binar.view.InventoryManagementView;
import com.binar.view.ProcurementView;
import com.binar.view.ReportView;
import com.binar.view.RequirementPlanningView;
import com.binar.view.SettingView;
import com.binar.view.UserManagementView;
import com.binar.view.UserSettingView;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickListener;

/* Kelas untuk agregrator fungsi */

public class GeneralFunction {

	//Konstanta untuk aplikasi
	
	//maksimal jumlah teks dalam input text area
	public final int MAX_TEXTAREA_LENGTH = 300;
	public final String FORM_WIDTH="250px";
	
	//konstanta untuk ke tab
	public final String VIEW_REQ_PLANNING_LIST="reqlist";
	public final String VIEW_REQ_PLANNING_INPUT="reqinput";
	public final String VIEW_REQ_PLANNING_APPROVAL="reqAppr";
	public final String VIEW_GOODS_MANAGEMENT="goods";
	public final String VIEW_SUPPLIER_MANAGEMENT="supplier";
	public final String VIEW_MANUFACTURER_MANAGEMENT="manufacturer";
	
	public final String VIEW_INVENTORY_DELETION="deletion";
	public final String VIEW_INVENTORY_DELETION_APPROVAL="approval";
	public final String VIEW_INVENTORY_CONSUMPTION="consumption";
	public final String VIEW_INVENTORY_RECEPTION="reception";
	public final String VIEW_INVENTORY_STOCK="stock";
	
	public final String VIEW_PROCUREMENT_INVOICE="invoice";
	public final String VIEW_PROCUREMENT_PURCHASE="purchaseOrder";
	
	
	public final String DASHBOARD_TABLE_WIDTH="430px";
	public final String DASHBOARD_TABLE_LAYOUT_WIDTH="470px";
	public final String DASHBOARD_LAYOUT_HEIGHT="320px";
	
//    put("/dashboard", DashboardView.class);
//    put("/requirementplanning/", RequirementPlanningView.class);
//    put("/procurement", ProcurementView.class);
//    put("/inventorymanagement", InventoryManagementView.class);
//    put("/report", ReportView.class);
//    put("/datamanagement", DataManagementView.class);
//    put("/usermanagement", UserManagementView.class);
//    put("/setting", SettingView.class);
//    put("/usersetting", UserSettingView.class);

	private ListFactory listFactory;
	private ConfirmationWindow window;
	private GetEbeanServer ebeanServer;
	private LoginManager loginManager;
	private DateManipulator date;
	private TableFilter filter;
	private TextManipulator text;
	private GetSetting setting;
	private EmailValidator emailValidator;
	private DecimalFormat format;
	private MinimumStockUpdater minimumStock;
	
	//Method konstruktor kosong, jaga-jaga  kalo butuh konstruktor
	public GeneralFunction() {
		
	}	
	public MinimumStockUpdater getMinimumStock() {
		if(minimumStock==null){
			minimumStock=new MinimumStockUpdater(this.getServer());
		}
		return minimumStock;
	}
	public ListFactory getListFactory(){
		if(listFactory==null){
			listFactory =new ListFactory();
		}
		return listFactory;
	}
	//khusus show dialog, tiap memanggil, bikin obyek baru
	public void showDialog(String caption, String content, ClickListener listener, UI ui){
			window =new ConfirmationWindow(caption, content, listener, ui);
			
	}
	public EbeanServer getServer(){
		if(ebeanServer==null){
			ebeanServer=new GetEbeanServer();
		}
		return ebeanServer.getServer();
	}
	public LoginManager getLogin(){
		if(loginManager==null){
			loginManager =new LoginManager(VaadinSession.getCurrent(), getServer());
		}
		return loginManager;
	}
	public DateManipulator getDate() {
		if(date==null){
			date=new DateManipulator();
		}
		return date;
	}
	//khusus untuk table filter, tiap memanggil get filter, maka akan membuat obyek baru
	//Alasan : Biar tiap tabel memiliki filter yang unik
	public TableFilter getFilter(String regex){
		return new TableFilter(regex);
	}
	public TextManipulator getTextManipulator(){
		if(text==null){
			text=new TextManipulator();
		}
		return text;
	}
	
	public GetSetting getSetting(){
		if(setting==null){
			setting=new GetSetting(getServer());
		}
		return setting;
	}
	public EmailValidator getEmailValidator(String message){
		if(emailValidator==null){
			emailValidator=new EmailValidator(message);
		}else{
			emailValidator.setErrorMessage(message);
		}
		return emailValidator;
	}
	public String formatDecimal(Double input){
		if(format==null){
			format=new DecimalFormat("#.##");
		}
		return format.format(input);
	}
	AcceptancePyramid acceptancePyramid;
	public AcceptancePyramid getAcceptancePyramid(){
		if(acceptancePyramid==null){
			acceptancePyramid= new AcceptancePyramid(getLogin());
		}
			return acceptancePyramid;
	}
	
}
