package com.binar.core.help;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class HelpManager {

    private UI ui;
    private List<HelpContent> contents = new ArrayList<HelpContent>();

    public HelpManager(UI ui) {
        this.ui = ui;
    }

    public void closeAll() {
        for (HelpContent overlay : contents) {
            overlay.close();
        }
        contents.clear();
    }


    public HelpContent addOverlay(String caption, String text, String style) {
        HelpContent o = new HelpContent();
        o.setCaption(caption);
        o.addComponent(new Label(text, ContentMode.HTML));
        o.setStyleName(style);
        contents.add(o);
        return o;
    }

}
