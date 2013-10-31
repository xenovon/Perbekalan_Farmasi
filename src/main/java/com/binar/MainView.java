package com.binar;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.binar.view.DashboardView;
import com.binar.view.DataManagementView;
import com.binar.view.InventoryManagementView;
import com.binar.view.ProcurementView;
import com.binar.view.ReportView;
import com.binar.view.RequirementPlanningView;
import com.binar.view.SettingView;
import com.binar.view.UserManagementView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class MainView extends HorizontalLayout {

	private Navigator nav;
	private CssLayout content;
	private VerticalLayout sidebar;
	private CssLayout appTitle;
	private CssLayout menu;
	private Label title;
	private HorizontalLayout profileArea;
	private NativeButton  userName;
	private Label  role;
	private MenuBar settingMenu;
	private MenuItem settingMenuItem;
	private Button logout;
	private Button userSetting;	

	
    private Map<String, String> buttonName = new TreeMap<String, String>() {
        {
            put("Adashboard", "Dashboard");
            put("Freport", "Laporan");
            put("Edatamanagement", "Manajemen Data");
            put("Dinventorymanagement", "Manajemen Inventory");
            put("Gusermanagement", "Manajemen Pengguna");
            put("Cprocurement", "Pengadaan");
            put("Brequirementplanning", "Rencana Kebutuhan");
            put("Hsetting", "Pengaturan Aplikasi");
        }
    };
	public MainView(Navigator nav, CssLayout content) {
		this.nav=nav;
		this.content=content;
	}	
	public void init(){
		this.setSizeFull();
		this.addStyleName("main-view");
		
		//inisiasi sidebar
		sidebar=new VerticalLayout();
		sidebar.addStyleName("sidebar");
		sidebar.setWidth("140px");
		
		sidebar.setHeight("100%");
//			tambahkan title
//			appTitle=new CssLayout();
//			appTitle.addStyleName("branding");
//				title=new Label(
//						"<span>Aplikasi</span> Perbekalan Farmasi", 
//						ContentMode.HTML);
//				title.setSizeUndefined();
//			appTitle.addComponent(title);	
//		sidebar.addComponent(appTitle);

		//tambahkan profil
		initiateProfileAreaAndMenu();
		sidebar.addComponent(profileArea);
		sidebar.addComponent(logout);

//		sidebar.addComponent(userSetting);

		//tambahkan manu
		initiateMenu();
		sidebar.addComponent(menu);
		sidebar.setExpandRatio(menu, 1);
		

		//setting layout content

		content.addStyleName("view-content");
		content.setSizeFull();

		this.addComponent(sidebar);
		this.addComponent(content);

		this.setExpandRatio(content, 1);
		
	}
	
	private void initiateMenu(){
		menu=new CssLayout();
        menu.removeAllComponents();
        for (Map.Entry<String, String> entry : buttonName.entrySet()) {
            final String key = entry.getKey().substring(1);
            final Object value = entry.getValue();
            
            Button b=new NativeButton(entry.getValue());
            b.addStyleName("icon-"+key);
            b.addClickListener(new Button.ClickListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					clearMenuSelection();
					event.getButton().addStyleName("selected");
					if(!nav.getState().equals("/"+key)){
						nav.navigateTo("/"+key);
					}
				}
			});
            menu.addComponent(b);
        }
        menu.addStyleName("menu");
        menu.setHeight("100%");
      

	}
	private void initiateProfileAreaAndMenu(){
		
		profileArea=new HorizontalLayout();
		userName=new NativeButton("Binar Candra");
		role=new Label("Petugas Gudang");
		role.addStyleName("role-style");
		userName.addStyleName("name-style");
		
		CssLayout nameRoleContainer=new CssLayout();
		nameRoleContainer.addComponents(userName, role);
		
		Image profilePic = new Image(
                null,
                new ThemeResource("img/profile-pic.png"));
        profilePic.setWidth("34px");

		profileArea.setSizeUndefined();
		profileArea.addStyleName("user");
		profileArea.addComponent(profilePic);
		profileArea.addComponent(nameRoleContainer);
		
		userName.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				clearMenuSelection();
				nav.navigateTo("/usersetting");
			}
		});
		
		//button logout
		logout = new NativeButton("Keluar");
		logout.addStyleName("v-button");
		logout.addStyleName("logout-button");
        logout.setDescription("Keluar Dari Aplikasi");
        logout.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	Notification.show("Logout Belum Di implementasikan");
            }
        });
        
	}
	public void setProfileText(String name, String role)
	{
		this.userName.setCaption(name);
		this.role.setValue(role);
	}
	private	void clearMenuSelection(){
		for (Component next : menu) {
	        next.removeStyleName("selected");
		}
	}
	
}
