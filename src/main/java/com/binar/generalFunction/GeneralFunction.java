package com.binar.generalFunction;

import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickListener;

/* Kelas untuk agregrator fungsi */

public class GeneralFunction {

	ListFactory listFactory;
	ConfirmationWindow window;
	
	public GeneralFunction() {
		
	}
	
	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}
	
	public ListFactory getListFactory(){
		return listFactory;
	}
	public void showDialog(String caption, String content, ClickListener listener, UI ui){
		if(window==null){
			window =new ConfirmationWindow(caption, content, listener, ui);
		}else{
			window.show(caption, content, listener, ui);
		}
	}
}
