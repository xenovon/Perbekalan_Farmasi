package com.binar.core.requirementPlanning.inputRequierementPlanning;

import java.util.Collection;

import com.binar.core.PresenterInterface;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormModel;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormPresenter;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormViewImpl;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

import elemental.events.MouseEvent.Button;

public class InputRequirementPlanningPresenter 
		implements PresenterInterface, InputRequirementPlanningView.InputRequirementListener {

	private InputRequirementPlanningViewImpl view;
	private InputRequirementPlanningModel model;
	
	//form input
	private InputFormModel formModel;
	private InputFormPresenter formPresenter;
	private InputFormViewImpl formView;

	//presenter untuk edit edit
	private InputFormPresenter formPresenterEdit;
	private InputFormModel formModelEdit;
	private InputFormViewImpl formViewEdit;
	GeneralFunction generalFunction;
	
	public InputRequirementPlanningPresenter(InputRequirementPlanningViewImpl view, 
			InputRequirementPlanningModel model, GeneralFunction function ){
		this.view=view;
		this.model=model;
		view.init();
		view.addListener(this);
		this.generalFunction=function;
		this.updateTable(view.getPeriodeValue());
	}

	@Override
	public void buttonClick(String source) {

	}
	@Override
	public void buttonClick(String source, Object data) {
		if(source.equals("buttonInput")){
			if(formModel==null){
				formModel=new InputFormModel(generalFunction);
				formView=new InputFormViewImpl();
				formPresenter =new InputFormPresenter(formModel, formView,
						generalFunction, (String)data);
				System.out.println("Form model view presenter instantiasi");
			}else{
				System.out.println("Data = "+data.toString());
				formPresenter.setPeriode((String)data);				
			}
			//form yang ditampilkan, dan judul jendelanya
			view.displayForm(formView,"Masukan Rencana Kebutuhan");
			
			//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
			Collection<Window> windows=view.getUI().getWindows();
			for(Window window:windows){
				window.addCloseListener(new CloseListener() {
					public void windowClose(CloseEvent e) {
						updateTable(view.getPeriodeValue());
					}
				});
			}
			
		}
	}

	@Override
	public void selectChange(Object data) {
		String stringData=(String)data;
		String[] splitted=stringData.split("-");
		for(String x:splitted){
			System.out.println("data "+x);
		}
		updateTable((String)data);
	}
	
	public void updateTable(String data){
		this.view.setTableData(model.getTableData(
				generalFunction.getDate().parseDateMonth((String)data)));
		
	}

	@Override
	public void showDetail(int reqId) {
		ReqPlanning data=model.getSingleReqPlanning(reqId);
		view.showDetailWindow(data);
	}

	@Override
	public void delete(int reqId) {
		final int reqIdFinal=reqId;
		generalFunction.showDialog("Yakin Hapus Data?",
				"Yakin akan menghapus data rencana kebutuhan?", 
				new ClickListener() {
					
					public void buttonClick(ClickEvent event) {
						boolean success=model.deleteReqPlanning(reqIdFinal);
						if(success){
							Notification.show("Data telah dihapus");
						}else{
							Notification.show("Data gagal dihapus", Type.ERROR_MESSAGE);
						}
						//update tampilan tabel
						updateTable(view.getPeriodeValue());

						
					}
				}, 
				view.getUI());
	}

	@Override
	public void edit(int reqId) {
		if(formModel==null){
			formModelEdit=new InputFormModel(generalFunction);
			formViewEdit=new InputFormViewImpl();
			formPresenterEdit =new InputFormPresenter(formModelEdit, formViewEdit,
					generalFunction, view.getPeriodeValue(), reqId);
			System.out.println("Form model view presenter instantiasi");
		}else{
			formPresenterEdit.setPeriode(view.getPeriodeValue());				
		}
		view.displayForm(formViewEdit, "Ubah Data Rencana Kebutuhan");
		
		//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
		Collection<Window> windows=view.getUI().getWindows();
		for(Window window:windows){
			window.addCloseListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					updateTable(view.getPeriodeValue());
				}
			});
		}
		
		
	}

	

	

	
	
}
