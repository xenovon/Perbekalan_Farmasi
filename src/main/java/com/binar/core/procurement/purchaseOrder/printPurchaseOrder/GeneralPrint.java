package com.binar.core.procurement.purchaseOrder.printPurchaseOrder;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class GeneralPrint extends UI {

	@Override
	protected void init(VaadinRequest request) {
		setContent(new Label("<b>Test</b> + "+request.getParameter("anu"), ContentMode.HTML));
		JavaScript.getCurrent().execute(
				"setTimeout(function() {" +
				" print(); self.close();}, 0);");
	}
	

}
