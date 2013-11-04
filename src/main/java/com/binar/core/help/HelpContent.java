package com.binar.core.help;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;

public class HelpContent extends Window {

    public HelpContent() {
        setContent(new CssLayout());
        setPrimaryStyleName("help-overlay");
        setDraggable(false);
        setResizable(false);
        center();
    }

    public void addComponent(Component c) {
        ((CssLayout) getContent()).addComponent(c);
    }

}
