package com.binar.core.dataManagement.goodsManagement.inputEditGoods;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;

public class FormData {

	private String id="";
	private String name="";
	private String insurance="";
	private String description="";
	private EnumGoodsType type=EnumGoodsType.ALAT_KESEHATAN;
	private String unit="";
	private String goodsPackage="";
	private EnumGoodsCategory category=EnumGoodsCategory.GENERIK;
	private String minimalStock="";
	private String het="";
	private String information="";
	private boolean isImportant=false;
	private String initialStock="";
	
	private GeneralFunction function;
	private EbeanServer server;
	private boolean editMode;
	public FormData(GeneralFunction function, boolean editMode) {
		this.function=function;
		this.editMode=editMode;
		server=function.getServer();
	}
	
	public List<String> validate(){
		List<String> errorData=new ArrayList<String>();
		
		//validasi form kosong
		if(id.equals("")){
			errorData.add("Data id tidak boleh kosong");
			return errorData;			
		}
		
		//validasi Id
		String errorId=validateId();
		if(errorId!=null){
			errorData.add(errorId);
		}
		
		String errorMinimumStock=validateMinimalStock();
		if(errorMinimumStock != null){
			errorData.add(errorMinimumStock);
		}
		
		String errorHET=validateHET();
		if(errorHET!=null){
			errorData.add(errorHET);
		}
		return errorData.size()==0?null:errorData;
	}
	public String validateId(){

		//jika editmode, maka tidak perlu validasi ID
		if(editMode){
			return null;
		}
		if(id.equals("")){
			return "Id tidak boleh kosong";
		}
		if(id.contains(" ")){
			return "Kode barang tidak boleh mengandung spasi";
		}
		Goods goods=server.find(Goods.class, id);
		if(goods==null){
			return null;
		}else{
			return "Kode barang "+id+" sudah tercatat sebelumnya</br>Gunakan kode barang yang unik";
		}
	}
	public String validateMinimalStock(){
		try {
			int x=getMinimalStockInt();
			return null;
		} catch (NumberFormatException e) {
			return "Stok minimal harus berupa angka";
		}
	}
	public String validateHET(){
		try{
			double x=getHETDouble();
			return null;
		}catch(NumberFormatException e){
			return "HET harus berupa angka";
		}
	}
	public String validateInitialStock(){
		try {
			int x=getInitialStockInt();
			return null;
		} catch (NumberFormatException e) {
			return "Stok awal harus berupa angka";
		}
	}
	public double getHETDouble() throws NumberFormatException{
		if(!het.equals("")){
			return Double.parseDouble(het);			
		}
		return 0;
	}
	public int getMinimalStockInt() throws NumberFormatException{
		if(!minimalStock.equals("")){
			return Integer.parseInt(minimalStock);			
		}
		return 0;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getGoodsPackage() {
		return goodsPackage;
	}
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	public String getMinimalStock() {
		return minimalStock;
	}
	public void setMinimalStock(String minimalStock) {
		this.minimalStock = minimalStock;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getHet() {
		return het;
	}
	public void setHet(String het) {
		this.het = het;
	}
	public boolean isImportant() {
		return isImportant;
	}
	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public EnumGoodsType getType() {
		return type;
	}

	public void setType(EnumGoodsType type) {
		this.type = type;
	}

	public EnumGoodsCategory getCategory() {
		return category;
	}

	public void setCategory(EnumGoodsCategory category) {
		this.category = category;
	}
	
	public String getInitialStock() {
		return initialStock;
	}
	public void setInitialStock(String initialStock) {
		this.initialStock = initialStock;
	}
	public int getInitialStockInt() throws NumberFormatException{
		if(initialStock.equals("")){
			return Integer.parseInt(initialStock);			
		}
		return 0;
	}
}
