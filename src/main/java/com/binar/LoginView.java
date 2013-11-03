package com.binar;

import com.binar.core.help.HelpContent;
import com.binar.core.help.HelpManager;
import com.binar.entity.Insurance;
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
	UI ui;
	CssLayout root;
	public LoginView(UI ui, CssLayout root) {
		this.ui=ui;
		this.root=root;
        help.closeAll();
        HelpContent w = help
                .addOverlay(
                        "Aplikasi Perbekalan Farmasi",
                        "<p>Gunakan username dan password yang sudah disediakan, hubungi administrator jika mengalami kesulitan untuk login</p>",
                        "login");
        w.center();
        ui.addWindow(w);

        addStyleName("login");

        this.setSizeFull();
        this.addStyleName("login-layout");
        root.addComponent(this);

        final CssLayout loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");

        HorizontalLayout labels = new HorizontalLayout();
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
        labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.focus();
        fields.addComponent(username);

        final PasswordField password = new PasswordField("Password");
        fields.addComponent(password);

        final Button signin = new Button("Masuk");
        signin.addStyleName("default");
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        final ShortcutListener enter = new ShortcutListener("Masuk",
                KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                signin.click();
            }
        };
        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (username.getValue() != null
                        && username.getValue().equals("")
                        && password.getValue() != null
                        && password.getValue().equals("")) {
                    signin.removeShortcutListener(enter);
                } else {
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
        });

        signin.addShortcutListener(enter);

        loginPanel.addComponent(fields);

        this.addComponent(loginPanel);
        this.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
        
	}
	
	
}
