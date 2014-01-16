package com.binar.core.report.reportInterface.reportContent;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class ReportPrint extends UI{

	/*
	 * (non-Javadoc)
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 * 
	 * File UI untuk mencetak laporan, menggunakan file model yang ada di masing-masing package report
	 * 
	 */
	@Override
	protected void init(VaadinRequest request) {
		String label="";
		
		setContent(new Label(label, ContentMode.HTML));
		JavaScript.getCurrent().execute(
				"setTimeout(function() {" +
				" print(); self.close();}, 0);");

		
	}

}
