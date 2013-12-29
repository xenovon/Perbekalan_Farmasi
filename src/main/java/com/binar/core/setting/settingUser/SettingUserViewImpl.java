package com.binar.core.setting.settingUser;

import com.binar.core.user.newEditUser.NewEditUserView.FormData;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class SettingUserViewImpl extends VerticalLayout implements SettingUserView, ClickListener, ValueChangeListener{

	private GeneralFunction function;
	private GetSetting setting;
	private TextManipulator text;
	private Label title;
	//inisialisasi Form
	private TextField inputUserName;
	private TextField inputTitle;
	private TextField selectRole;
	private TextField inputEmployeeNum;
	private TextField inputName;
	private TextField inputPhoneNumber;
	private TextArea inputAddress;
	private PasswordField inputOldPassword;
	private PasswordField inputPassword1;
	private PasswordField inputPassword2;
	
	
	private Label labelError;
	private Button buttonSaveEdit;
	private Button buttonReset;
	
	private SettingUserListener listener;

	public SettingUserViewImpl(GeneralFunction function) {
		this.function=function;
		setting=function.getSetting();
		text=function.getTextManipulator();
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		listener.valueChange();
	}

	@Override
	public void init() {
		title=new Label("<h2>Pengaturan Data Akun</h2>", ContentMode.HTML);

		inputUserName= new TextField("Nama Untuk Log In");
		inputUserName.setDescription("Nama yang digunakan untuk log in");
		inputUserName.setImmediate(true);
		inputUserName.setWidth(function.FORM_WIDTH);
		inputUserName.setMaxLength(10);
		inputUserName.setEnabled(false);
	inputName= new TextField("Nama Lengkap");
		inputName.setDescription("Nama lengkap pengguna");
		inputName.setImmediate(true);
		inputName.addValueChangeListener(this);
		inputName.setWidth(function.FORM_WIDTH);
	selectRole=new TextField("Role");
		selectRole.setImmediate(true);
		selectRole.setDescription("Role");
		selectRole.addValueChangeListener(this);
		selectRole.setWidth(function.FORM_WIDTH);
		selectRole.setEnabled(false);
	inputEmployeeNum= new TextField("Nomor pegawai");
		inputEmployeeNum.setDescription("Nomor pegawai");
		inputEmployeeNum.addValueChangeListener(this);
		inputEmployeeNum.setImmediate(true);
		inputEmployeeNum.setWidth(function.FORM_WIDTH);
	inputTitle= new TextField("Jabatan");
		inputTitle.setDescription("Jabatan anda");
		inputTitle.addValueChangeListener(this);
		inputTitle.setImmediate(true);
		inputTitle.setWidth(function.FORM_WIDTH);
	inputPhoneNumber= new TextField("Nomor Telepon");
		inputPhoneNumber.setDescription("Nomor Telepon yang bisa dihubungi");
		inputPhoneNumber.setImmediate(true);
		inputPhoneNumber.addValueChangeListener(this);
		inputPhoneNumber.setWidth(function.FORM_WIDTH);
	inputAddress= new TextArea("Alamat");
		inputAddress.setDescription("Alamat anda");
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
	inputOldPassword =new PasswordField("Password Saat ini");
		inputOldPassword.setDescription("Password sebelum dirubah");
		inputOldPassword.addValueChangeListener(this);
		inputOldPassword.setImmediate(true);
		inputOldPassword.setWidth(function.FORM_WIDTH);
	
	labelError = new Label(){
			{
				setVisible(false);
				addStyleName("form-error");
				setContentMode(ContentMode.HTML);
			}
		};
	 
		buttonSaveEdit=new Button("Simpan Perubahan");
		buttonSaveEdit.addClickListener(this);
		buttonReset=new Button("Reset Form");
		buttonReset.addClickListener(this);

	  construct();

		
	}

	@Override
	public void construct() {
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponent(title);
		this.addComponent(new FormLayout(){
			{
				addComponent(inputUserName);
				addComponent(inputName);
				addComponent(inputTitle);
				addComponent(inputAddress);
				addComponent(inputEmployeeNum);
				addComponent(selectRole);
				addComponent(inputPhoneNumber);
				addComponent(new Label("<h4>Ubah Password</h4>", ContentMode.HTML));
				addComponent(new Label("Kosongkan jika tidak ingin merubah password", ContentMode.HTML));
				addComponent(inputOldPassword);
				addComponent(inputPassword1);
				addComponent(inputPassword2);
				addComponent(labelError);
				addComponent(new GridLayout(2,1){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(buttonSaveEdit, 0,0);
						addComponent(buttonReset, 1,0);
					}
				});		
				
			}
		});
	}

	@Override
	public void setListener(SettingUserListener listener) {
		this.listener=listener;
	}

	@Override
	public void setFormData(User data) {
		inputUserName.setValue(data.getUsername());
		inputName.setValue(data.getName());
		inputTitle.setValue(data.getTitle());
		inputAddress.setValue(data.getAddress());
		inputEmployeeNum.setValue(data.getEmployeeNum());
		selectRole.setValue(data.getRole().getRoleName());
		System.out.println(data.getRole().getRoleName());
		inputPhoneNumber.setValue(data.getPhoneNumber());

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
		data.setTitle(inputTitle.getValue());
		data.setUserName(inputUserName.getValue());
		data.setOldPassword(inputOldPassword.getValue());
		
		return data;
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
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonReset){
			listener.resetClick();
		}else if(event.getButton()==buttonSaveEdit){
			listener.updateClick();
			System.out.println("Button save edit tekan");
		}
		
	}


}
