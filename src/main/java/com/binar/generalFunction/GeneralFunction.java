package com.binar.generalFunction;

import com.avaje.ebean.EbeanServer;
import com.binar.database.GetEbeanServer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickListener;

/* Kelas untuk agregrator fungsi */

public class GeneralFunction {

	//Konstanta untuk aplikasi
	
	//maksimal jumlah teks dalam input text area
	final int MAX_TEXTAREA_LENGTH = 300;
	
	private ListFactory listFactory;
	private ConfirmationWindow window;
	private GetEbeanServer ebeanServer;
	private LoginManager loginManager;
	private DateManipulator date;
	private TableFilter filter;
	private TextManipulator text;
	private GetSetting setting;
	
	//Method konstruktor kosong, jaga-jaga aja kalo butuh konstruktor
	public GeneralFunction() {
		
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
}
