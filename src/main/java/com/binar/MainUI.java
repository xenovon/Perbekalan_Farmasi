package com.binar;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.binar.database.GetEbeanServer;
import com.binar.entity.Insurance;
import com.binar.view.DashboardView;
import com.binar.view.DataManagementView;
import com.binar.view.InventoryManagementView;
import com.binar.view.ProcurementView;
import com.binar.view.ReportView;
import com.binar.view.RequirementPlanningView;
import com.binar.view.UserManagementView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("perbekalantheme")
@SuppressWarnings("serial")
@Title("Perbekalan Farmasi RSUD Ajibarang")
public class MainUI extends UI
{
	CssLayout root =new CssLayout();
	VerticalLayout loginLayout=new VerticalLayout();
	CssLayout menu=new CssLayout();
	CssLayout content=new CssLayout();
	
    HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
        {
            put("/dashboard", DashboardView.class);
            put("/report", ReportView.class);
            put("/datamanagement", DataManagementView.class);
            put("/inventorymanagement", InventoryManagementView.class);
            put("/usermanagement", UserManagementView.class);
            put("/procurement", ProcurementView.class);
            put("/requirementplanning", RequirementPlanningView.class);
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
  	
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        EbeanServer server= GetEbeanServer.getServer();
        server.find(Insurance.class).findList();
        layout.addComponent(button);
    }

}

/*
 * 
 * 
*/