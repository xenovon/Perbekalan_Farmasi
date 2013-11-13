package com.binar.generalFunction;

import com.avaje.ebean.EbeanServer;
import com.binar.database.GetEbeanServer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickListener;

/* Kelas untuk agregrator fungsi */

public class GeneralFunction {

	private ListFactory listFactory;
	private ConfirmationWindow window;
	private GetEbeanServer ebeanServer;
	private LoginManager loginManager;
	private DateManipulator date;
	private TableFilter filter;
	private TextManipulator text;
	public GeneralFunction() {
		
	}	
	public ListFactory getListFactory(){
		if(listFactory==null){
			listFactory =new ListFactory();
		}
		return listFactory;
	}
	public void showDialog(String caption, String content, ClickListener listener, UI ui){
		if(window==null){
			window =new ConfirmationWindow(caption, content, listener, ui);
		}else{
			window.show(caption, content, listener, ui);
		}
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
}
