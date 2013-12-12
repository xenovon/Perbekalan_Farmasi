package com.binar;

import com.binar.core.help.HelpContent;
import com.binar.core.help.HelpManager;
import com.binar.entity.Insurance;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout{

	HelpManager help;
	MainUI ui;
	GeneralFunction generalFunction;
	CssLayout root;
	PasswordField password;
	TextField username;
	Button signin;
	CssLayout loginPanel;
	ShortcutListener enter;
	
	public LoginView(MainUI mainUi, GeneralFunction function, CssLayout rootLayout) {
		this.generalFunction=function;
		this.root=rootLayout;
		this.ui=mainUi;
		help=new HelpManager(mainUi);
        help.closeAll();
        help.addOverlay("Aplikasi Perbekalan Farmasi", 
        				"<p>Gunakan username dan password yang sudah disediakan, hubungi administrator jika mengalami kesulitan untuk login</p>"
        				, "login");
        help.show();
        root.addComponent(this);
        addStyleName("login");

        this.setSizeFull();
        this.addStyleName("login-layout");

        loginPanel =new CssLayout();
        loginPanel.addStyleName("login-panel");

        VerticalLayout labels = new VerticalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        loginPanel.addComponent(labels);

        Label welcome = new Label("Selamat Datang");
        welcome.setSizeUndefined();
        welcome.addStyleName("h4");
        labels.addComponent(welcome);
        labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

        Label title = new Label("Aplikasi Perbekalan Farmasi");
        title.setSizeUndefined();
        title.addStyleName("h2");
        title.addStyleName("light");
        labels.addComponent(title);
        labels.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        username= new TextField("Username");
        username.focus();
        fields.addComponent(username);

        password= new PasswordField("Password");
  
        fields.addComponent(password);

        signin = new Button("Masuk");
        signin.addStyleName("default");
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        enter = new ShortcutListener("Masuk",
                KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                signin.click();
            }
        };
        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	loginClick();
            }
        });

        signin.addShortcutListener(enter);

        loginPanel.addComponent(fields);

        this.addComponent(loginPanel);
        this.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
        username.setValue("dsad");
        password.setValue("fas");
//        signin.click();
	}
	
	private void loginClick(){
		
		String sUsername=username.getValue();
		String sPassword=password.getValue();
		
		if(sUsername.equals("") || sPassword.equals("")){
            if (loginPanel.getComponentCount() > 2) {
                loginPanel.removeComponent(loginPanel.getComponent(2));
            }
			Label fill = new Label(
                    "Isikan username dan password",
                    ContentMode.HTML);
            fill.addStyleName("error");
            fill.setSizeUndefined();
            fill.addStyleName("light");
            // Tambah animasi
            fill.addStyleName("v-animate-reveal");
            loginPanel.addComponent(fill);
            username.focus();
		}else{
			if(generalFunction.getLogin().login(sUsername, sPassword)){
	            signin.removeShortcutListener(enter);
	            ui.constructAfterLogin();
	            help.closeAll();				
			}else{
	            if (loginPanel.getComponentCount() > 2) {
	                loginPanel.removeComponent(loginPanel.getComponent(2));
	            }
	            Label error = new Label(
	                    "Username atau password salah",
	                    ContentMode.HTML);
	            error.addStyleName("error");
	            error.setSizeUndefined();
	            error.addStyleName("light");
	            // Tambah animasi
	            error.addStyleName("v-animate-reveal");
	            loginPanel.addComponent(error);
	            username.focus();
				
			}
		}
		
	}
	
	
}
