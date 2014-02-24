package com.binar;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;


import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.binar.database.GetEbeanServer;
import com.binar.database.generateData.GenerateData;
import com.binar.entity.Insurance;
import com.binar.entity.User;
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
import com.vaadin.annotations.Widgetset;
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
@Widgetset(value="com.binar.AppWidgetSet")
public class MainUI extends UI
{
	CssLayout root =new CssLayout();
	VerticalLayout loginLayout=new VerticalLayout();
	CssLayout menu=new CssLayout();
	CssLayout content=new CssLayout();
	LoginManager loginManager;
	GeneralFunction generalFunction;
	LoginView loginView;
	/*
	 * Manajemen Role untuk Level MENU
	 * 
	 */
    //Method Untuk Memilih menu apa saja yang ditampilkan 
    private HashMap<String, Class<? extends View>> getRoutes(){
    	String role=loginManager.getRoleId();
    	HashMap<String, Class<? extends View>> routes;
    	if(role.equals(loginManager.ADM)){
            routes= new HashMap<String, Class<? extends View>>() {
                {
                    put("/usermanagement", UserManagementView.class);
                    put("/setting", SettingView.class);
                    put("/usersetting", UserSettingView.class);
                }
            };     		
    	}else{
            routes= new HashMap<String, Class<? extends View>>() {
                {
                    put("/dashboard", DashboardView.class);
                    put("/requirementplanning", RequirementPlanningView.class);
                    put("/procurement", ProcurementView.class);
                    put("/inventorymanagement", InventoryManagementView.class);
                    put("/report", ReportView.class);
                    put("/datamanagement", DataManagementView.class);
                    put("/setting", SettingView.class);
                }
            };     		
    	}
    	
    	return routes;
   	
   	
    }
    
    
//    HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();
    private Navigator nav;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {    
    	generalFunction =new GeneralFunction();
    	
    	loginManager=generalFunction.getLogin();
    	GenerateData dataGenerator=new GenerateData(generalFunction);
//    	dataGenerator.insertData();

    	root.addStyleName("root");
    	root.setSizeFull();
    	setContent(root);   
    	if(loginManager.isLogin()){
            constructMainView();   		
    	}else{
    		constructLoginForm();
    	}
    }
    private void constructLoginForm(){
    	root.removeAllComponents();
    	loginView = new LoginView(this, generalFunction, root);
    }
    public void constructAfterLogin(){
    	root.removeAllComponents();
    	constructMainView();
    }
    public void constructLogout(){
    	loginManager.logout();
    	constructLoginForm();
    }
    private void constructMainView(){
    	nav = new Navigator(this, content);
    	
    	//Add view untuk ""
    	nav.addView("", DashboardView.class);
    	nav.addView("/", DashboardView.class);
    
    	HashMap<String, Class<? extends View>> routes=getRoutes();
    	
    	for(String route: routes.keySet()){
    		nav.addView(route, routes.get(route));
    	}
    	
    	MainView mainView=new MainView(nav, content, this, generalFunction);
    	mainView.init();
    	User userLogin=loginManager.getUserLogin();
    	mainView.setProfileText(userLogin.getName(), userLogin.getRole().getRoleName());
    	Header header=new Header();
    	root.addComponent(header);
    	root.addComponent(mainView);
    	//menambahkan dasboard ke konten
    	
    	DashboardView dashboard=new DashboardView();
    	dashboard.init(null);
    	content.addComponent(dashboard);
    }

}