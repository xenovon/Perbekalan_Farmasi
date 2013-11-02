package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.Collection;
import java.util.List;

import com.binar.core.PresenterInterface;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InputFormPresenter implements PresenterInterface, InputFormView.InputFormListener{

	GeneralFunction generalFunction;
	InputFormViewImpl view;
	InputFormModel model;
		
	public InputFormPresenter(InputFormModel model, 
			InputFormViewImpl view, GeneralFunction function) {
		this.view=view;
		this.model=model;
		view.init();
		view.addListener(this);
		this.generalFunction=function;
	}
	
	public void buttonClick(String source) {
		if(source.equals("forecast")){
			Notification.show("Tombol forecast ditekan");
		}else if(source.equals("reset")){
			Notification.show("Tombol Reset Ditekan");
		}else if(source.equals("submit")){
			Notification.show("Tombol Submit ditekan");
		}else if(source.equals("cancel")){
			generalFunction.showDialog("Batalkan", "Yakin Akan Membatalkan Memasukan Data?",
					new ClickListener() {
						public void buttonClick(ClickEvent event) {
							Collection<Window> list=view.getUI().getWindows();
							for(Window w:list){
								view.getUI().removeWindow(w);
							}
						}
					}, view.getUI());
					
		}
	}
}
