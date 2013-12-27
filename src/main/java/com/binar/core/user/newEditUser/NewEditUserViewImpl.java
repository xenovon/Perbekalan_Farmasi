package com.binar.core.user.newEditUser;

import java.util.Map;

import com.binar.core.user.newEditUser.NewEditUserView.NewEditUserListener;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class NewEditUserViewImpl extends FormLayout implements NewEditUserView, ClickListener , ValueChangeListener{
	private GeneralFunction function;
	private GetSetting setting;
	private TextManipulator text;
	
	//inisialisasi Form
	private TextField inputUserName;
	private TextField inputTitle;
	private ComboBox selectRole;
	private TextField inputEmployeeNum;
	private TextField inputName;
	private TextField inputPhoneNumber;
	private TextArea inputAddress;
	private PasswordField inputPassword1;
	private PasswordField inputPassword2;
	
	
	private Label labelError;
	private Button buttonSubmit;
	private Button buttonSaveEdit;
	private Button buttonReset;
	private Button buttonCancel;
	private Button buttonResetPassword;
	private Button buttonActivation;
	
	private NewEditUserListener listener;
	private boolean editMode;
	
	public NewEditUserViewImpl(GeneralFunction function){
		this.function=function;
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	@Override
	public void init() {
		inputUserName= new TextField("Nama Untuk Log In");
			inputUserName.setDescription("Nama yang digunakan untuk log in");
			inputUserName.setImmediate(true);
			inputUserName.setWidth(function.FORM_WIDTH);
			inputUserName.setMaxLength(10);
			inputUserName.addValueChangeListener(this);
		inputName= new TextField("Nama Lengkap");
			inputName.setDescription("Nama lengkap pengguna");
			inputName.setImmediate(true);
			inputName.addValueChangeListener(this);
			inputName.setWidth(function.FORM_WIDTH);
		selectRole=new ComboBox("Role Pengguna");
			selectRole.setImmediate(true);
			selectRole.setDescription("Role dari pengguna");
			selectRole.addValueChangeListener(this);
			selectRole.setWidth(function.FORM_WIDTH);
			selectRole.setNullSelectionAllowed(false);
		inputEmployeeNum= new TextField("Nomor pegawai");
			inputEmployeeNum.setDescription("Nomor pegawai");
			inputEmployeeNum.addValueChangeListener(this);
			inputEmployeeNum.setImmediate(true);
			inputEmployeeNum.setWidth(function.FORM_WIDTH);
		inputTitle= new TextField("Jabatan");
			inputTitle.setDescription("Jabatan dari pengguna");
			inputTitle.addValueChangeListener(this);
			inputTitle.setImmediate(true);
			inputTitle.setWidth(function.FORM_WIDTH);
		inputPhoneNumber= new TextField("Nomor Telepon");
			inputPhoneNumber.setDescription("Nomor Telepon yang bisa dihubungi");
			inputPhoneNumber.setImmediate(true);
			inputPhoneNumber.addValueChangeListener(this);
			inputPhoneNumber.setWidth(function.FORM_WIDTH);
		inputAddress= new TextArea("Alamat");
			inputAddress.setDescription("Alamat pengguna");
			inputAddress.addValueChangeListener(this);
			inputAddress.setImmediate(true);
			inputAddress.setWidth(function.FORM_WIDTH);
		inputPassword1 =new PasswordField("Password");
			inputPassword1.setDescription("Masukan password untuk pengguna");
			inputPassword1.addValueChangeListener(this);
			inputPassword1.setImmediate(true);
			inputPassword1.setWidth(function.FORM_WIDTH);
		inputPassword2 =new PasswordField("Ulangi Password");
			inputPassword2.setDescription("Masukan password untuk pengguna");
			inputPassword2.addValueChangeListener(this);
			inputPassword2.setImmediate(true);
			inputPassword2.setWidth(function.FORM_WIDTH);
		
		labelError = new Label(){
				{
					setVisible(false);
					addStyleName("form-error");
					setContentMode(ContentMode.HTML);
				}
			};
		 buttonSubmit=new Button("Simpan");
		  buttonSubmit.addClickListener(this);
		 
		 buttonSaveEdit=new Button("Simpan Perubahan");
		  buttonSaveEdit.addClickListener(this);
		 buttonReset=new Button("Reset Form");
		  buttonReset.addClickListener(this);
		 buttonCancel=new Button("Batalkan");
		  buttonCancel.addClickListener(this);
		 buttonActivation=new Button("");
		 buttonActivation.addClickListener(this);
		 buttonResetPassword=new Button("Reset Password");
		 buttonResetPassword.addClickListener(this);
		  construct();
	}
	@Override
	public void construct() {
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponent(new HorizontalLayout(){
			{
				setSpacing(true);
				setStyleName("float-right");
				addComponent(buttonActivation);
				addComponent(buttonResetPassword);

			}
		});
		this.addComponent(inputUserName);
		this.addComponent(inputName);
		this.addComponent(inputTitle);
		this.addComponent(inputAddress);
		this.addComponent(inputEmployeeNum);
		this.addComponent(selectRole);
		this.addComponent(inputPhoneNumber);
		this.addComponent(inputPassword1);
		this.addComponent(inputPassword2);
		this.addComponent(labelError);
		this.addComponent(new GridLayout(4,1){
			{
				this.setMargin(true);
				this.setSpacing(true);
				this.addComponent(buttonSaveEdit, 0,0);
				this.addComponent(buttonSubmit, 1, 0);
				this.addComponent(buttonReset, 2,0);
				this.addComponent(buttonCancel,3 ,0);
			}
		});
		this.setEditMode(false);
		
	}
	@Override
	public void setListener(NewEditUserListener listener) {
		this.listener=listener;
	}
	@Override
	public void setFormData(User data) {
		inputUserName.setValue(data.getUsername());
		inputName.setValue(data.getName());
		inputTitle.setValue(data.getTitle());
		inputAddress.setValue(data.getAddress());
		inputEmployeeNum.setValue(data.getEmployeeNum());
		selectRole.setValue(data.getRole().getIdRole());
		System.out.println(data.getRole().getRoleName());
		inputPhoneNumber.setValue(data.getPhoneNumber());
		changeButtonActivation(data.isActive());
	}
	public void changeButtonActivation(boolean active){
		if(active){
			buttonActivation.setCaption("Non Aktifkan Pengguna");
		}else{
			buttonActivation.setCaption("Aktifkan Pengguna");
		}
		
	}
	@Override
	public FormData getFormData() {
		FormData data=new FormData(function);
		data.setAddress(inputAddress.getValue());
		data.setEmployeeNum(inputEmployeeNum.getValue());
		data.setName(inputName.getValue());
		data.setPassword1(inputPassword1.getValue());
		data.setPassword2(inputPassword2.getValue());
		data.setPhoneNumber(inputPhoneNumber.getValue());
		data.setRole((String)selectRole.getValue());
		data.setTitle(inputTitle.getValue());
		data.setUserName(inputUserName.getValue());
		data.setEditMode(editMode);
		return data;
	}
	@Override
	public void setEditMode(boolean editMode) {
		System.out.println("Edit mode " + editMode) ;
		this.editMode=editMode;
		if(editMode){
			buttonSaveEdit.setVisible(true);
			buttonSubmit.setVisible(false);
			inputPassword1.setVisible(false);
			inputPassword2.setVisible(false);
			buttonActivation.setVisible(true);
			buttonResetPassword.setVisible(true);
			inputUserName.setEnabled(false);
			buttonReset.setVisible(false);
		}else{
			buttonSaveEdit.setVisible(false);
			buttonSubmit.setVisible(true);
			inputPassword1.setVisible(true);
			inputPassword2.setVisible(true);
			buttonActivation.setVisible(false);
			inputUserName.setEnabled(true);
			buttonReset.setVisible(true);
			buttonResetPassword.setVisible(false);			
		}
		
	}
	@Override
	public void resetForm() {
		inputUserName.setValue("");
		inputName.setValue("");
		inputTitle.setValue("");
		inputAddress.setValue("");
		inputEmployeeNum.setValue("");
		inputPhoneNumber.setValue("");
		inputPassword1.setValue("");
		inputPassword2.setValue("");
	}
	@Override
	public void showError(String content) {
		labelError.setVisible(true);
		labelError.setValue(content);
	}
	@Override
	public void hideError() {
		labelError.setVisible(false);
	}
	@Override
	public void setComboBoxData(ComboDataList list) {
		Map<String, String> roleList=list.getRoleList();
		
        for (Map.Entry<String, String> entry : roleList.entrySet()) {
        	selectRole.addItem(entry.getKey());
        	selectRole.setItemCaption(entry.getKey(), entry.getValue());
        }
        
		
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonActivation){
			listener.activationClick();
		}else if(event.getButton()==buttonCancel){
			listener.cancelClick();
		}else if(event.getButton()==buttonReset){
			listener.resetClick();
		}else if(event.getButton()==buttonResetPassword){
			listener.resetPasswordClick();
		}else if(event.getButton()==buttonSaveEdit){
			listener.updateClick();
			System.out.println("Button save edit tekan");
		}else if(event.getButton()==buttonSubmit){
			listener.saveClick();
			System.out.println("Button submit tekan");
		}
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		listener.valueChange();
	}

}
