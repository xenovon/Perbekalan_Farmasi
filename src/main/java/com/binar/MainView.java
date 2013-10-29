package com.binar;

import java.util.HashMap;
import java.util.Map;

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
	private Label  userName;
	private Label  role;
	private MenuBar settingMenu;
	private MenuItem settingMenuItem;
	private Button logout;

    private HashMap<String, String> buttonName = new HashMap<String, String>() {
        {
            put("dashboard", "Dashboard");
            put("report", "Laporan");
            put("datamanagement", "Manajemen Data");
            put("inventorymanagement", "Manajemen Inventory");
            put("usermanagement", "Manajemen Pengguna");
            put("procurement", "Pengadaan");
            put("requirementplanning", "Rencana Kebutuhan");
        }
    };
	public MainView(Navigator nav, CssLayout content) {
		this.nav=nav;
		this.content=content;
	}	
	public void init(){
		this.setSizeFull();
//		this.addStyleName("main-view");
		
		//inisiasi sidebar
		sidebar=new VerticalLayout();
		sidebar.addStyleName("sidebar");
		sidebar.setWidth("140px");
		
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

		//tambahkan profil
		initiateProfileArea();
		sidebar.addComponent(profileArea);
		
		
		//setting layout content
		this.addComponent(sidebar);

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
            final String key = entry.getKey();
            final Object value = entry.getValue();
            
            Button b=new NativeButton(entry.getValue());
            b.addStyleName("icon-dashboard");
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
	private void initiateProfileArea(){
		profileArea=new HorizontalLayout();
		userName=new Label("Binar");
		role=new Label("Petugas Gudang");
        Image profilePic = new Image(
                null,
                new ThemeResource("img/profile-pic.png"));
        profilePic.setWidth("34px");

		profileArea.setSizeUndefined();
		profileArea.addStyleName("user");
		profileArea.addComponent(profilePic);
		profileArea.addComponent(userName);
		profileArea.addComponent(role);
		//command, nanti ganti
		
        Command settingCmd = new Command() {
            @Override
            public void menuSelected(
                    MenuItem selectedItem) {
            	nav.navigateTo("/setting");
            }
        };
		
		//inisiasi menu
		
		settingMenu = new MenuBar();
		settingMenuItem =settingMenu.addItem("", settingCmd);		
		profileArea.addComponent(settingMenu);
		
		//button logout
		logout = new NativeButton("Keluar");
        logout.addStyleName("icon-cancel");
        logout.setDescription("Keluar Dari Aplikasi");
        logout.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	Notification.show("Logout Belum Di implementasikan");
            }
        });
        profileArea.addComponent(logout);
        
	}
	public void setProfileText(String name, String role)
	{
		this.userName.setValue(name);
		this.role.setValue(role);
	}
	private	void clearMenuSelection(){
		for (Component next : menu) {
	        next.removeStyleName("selected");
		}
	}
	
}
