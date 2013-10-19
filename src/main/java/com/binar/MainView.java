package com.binar;

import com.vaadin.navigator.Navigator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MainView extends HorizontalLayout {

	private Navigator nav;
	private CssLayout content;
	private CssLayout root;
	private VerticalLayout sidebar;
	private CssLayout appTitle;
	private CssLayout menu;
	private Label title;
	private VerticalLayout profileArea;
	private Label  userName;
	private Label  role;
	public MainView(Navigator nav, CssLayout content, CssLayout root) {
		this.nav=nav;
		this.content=content;
		this.root=root;
	}	
	private void init(){
		this.setSizeFull();
		addStyleName("main-view");
		
		//inisiasi sidebar
		sidebar=new VerticalLayout();
		sidebar.addStyleName("sidebar");
		sidebar.setWidth(null);
		sidebar.setHeight("100%");
			//tambahkan title
			appTitle=new CssLayout();
			appTitle.addStyleName("branding");
				title=new Label(
						"<span>Aplikasi</span> Perbekalan Farmasi", 
						ContentMode.HTML);
				title.setSizeUndefined();
			appTitle.addComponent(title);	
		sidebar.addComponent(appTitle);
		//tambahkan manu
		initiateMenu();
		sidebar.addComponent(menu);
		
		sidebar.setExpandRatio(menu, 1);
		
		root.addComponent(this);
	}
	
	private void initiateMenu(){
		
	}
	private void initiateProfileArea(){
		profileArea=new VerticalLayout();
		profileArea.setSizeUndefined();
		profileArea.addStyleName("user");
		profileArea.addComponent(userName);
		profileArea.addComponent(role);
	}
	public void setProfileText(String name, String role)
	{
		this.userName.setValue(name);
		this.role.setValue(role);
	}
	
}
