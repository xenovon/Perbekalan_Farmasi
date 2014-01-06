package com.binar.core.inventoryManagement.deletionList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TableFilter;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.Window;

public class DeletionListViewImpl extends VerticalLayout implements DeletionListView,  ClickListener, ValueChangeListener{



	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void construct() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setListener(DeletionListListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateTableData(List<DeletedGoods> data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showDetailWindow(DeletedGoods deletedGoods) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getSelectedStartRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getSelectedEndRange() {
		// TODO Auto-generated method stub
		return null;
	}



}
