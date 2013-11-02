package com.binar.generalFunction;

import java.util.Collection;
import java.util.List;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class ConfirmationWindow extends Window implements ClickListener {

	Label labelContent;
	Button no;
	Button ok;
	public ConfirmationWindow(String caption, String content, ClickListener listener, UI ui) {
		this.setClosable(false);
		this.setModal(true);
		this.labelContent=new Label(content);
		this.no=new Button("Tidak");
		this.no.addClickListener(this);
		this.setCaption(caption);
		ok=new Button("Ya");
		ok.addClickListener(this);
		ok.addClickListener(listener);
		this.setWidth("250px");
		this.center();
		ui.addWindow(this);
		this.setContent(new VerticalLayout(){
			{
				addComponents(labelContent, new GridLayout(2, 1){
					{
						addComponent(ok, 0, 0);
						addComponent(no, 1,0);
						this.setMargin(true);
						this.setSpacing(true);
					}
				});
				setMargin(true);
				setSpacing(true);
			}
		});
		
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		getUI().removeWindow(this);
	}
	public void show(String caption, String content, ClickListener listener, UI ui){
		this.labelContent=new Label(content);
		removeClickListeners(ok);
		ok.addClickListener(this);
		ok.addClickListener(listener);
		setCaption(caption);
		ui.addWindow(this);
	}
	
	public void removeClickListeners(Button button){
		Collection<?> listeners= button.getListeners(ClickListener.class);
		for(Object listener:listeners){
			button.removeListener((Listener)listener);
		}
	}
}
