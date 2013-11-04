package com.binar;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.binar.database.GetEbeanServer;
import com.binar.entity.Insurance;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
import com.binar.view.DashboardView;
import com.binar.view.DataManagementView;
import com.binar.view.InventoryManagementView;
import com.binar.view.ProcurementView;
import com.binar.view.ReportView;
import com.binar.view.RequirementPlanningView;
import com.binar.view.SettingView;
import com.binar.view.UserManagementView;
import com.binar.view.UserSettingView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dashboard")
@SuppressWarnings("serial")
@Title("Perbekalan Farmasi RSUD Ajibarang")
public class MainUI extends UI
{
	CssLayout root =new CssLayout();
	VerticalLayout loginLayout=new VerticalLayout();
	CssLayout menu=new CssLayout();
	CssLayout content=new CssLayout();
	LoginManager loginManager;
	GeneralFunction generalFunction;
	LoginView loginView;
	
    HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
        {
            put("/dashboard", DashboardView.class);
            put("/requirementplanning", RequirementPlanningView.class);
            put("/procurement", ProcurementView.class);
            put("/inventorymanagement", InventoryManagementView.class);
            put("/report", ReportView.class);
            put("/datamanagement", DataManagementView.class);
            put("/usermanagement", UserManagementView.class);
            put("/setting", SettingView.class);
            put("/usersetting", UserSettingView.class);
        }
    };    	
	
    HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();
    private Navigator nav;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {    
    	generalFunction =new GeneralFunction();
    	
    	loginManager=generalFunction.getLogin();
    	
    	root.addStyleName("root");
    	root.setSizeFull();
    	setContent(root);   
    	if(loginManager.isLogin()){
            constructMainView();   		
    	}else{
    		constructLoginView();
    	}
    }
    private void constructLoginView(){
    	root.removeAllComponents();
    	loginView = new LoginView(this, generalFunction, root);
    }
    public void constructLogin(){
    	root.removeAllComponents();
    	constructMainView();
    }
    public void constructLogout(){
    	loginManager.logout();
    	constructLoginView();
    }
    private void constructMainView(){
    	nav = new Navigator(this, content);
    	
    	//Add view untuk ""
    	nav.addView("", DashboardView.class);
    	nav.addView("/", DashboardView.class);
    	
    	for(String route: routes.keySet()){
    		nav.addView(route, routes.get(route));
    	}
    	
    	MainView mainView=new MainView(nav, content, this, generalFunction);
    	mainView.init();
    	Header header=new Header();
    	root.addComponent(header);
    	root.addComponent(mainView);
    }

}