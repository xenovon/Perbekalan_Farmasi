package com.binar.core.requirementPlanning.inputRequierementPlanning;

public class TableData {
	//array, array 0 untuk no, 1 nama barang, 2 kebutuhan, 3 produsen, 4 distributor
	private int idReq;
	private String goodsName;
	private int req;
	private String manufacturer;
	private String supp;
	private int acceptance;
	
	protected int getAcceptance(){
		return acceptance;
	}
	protected void setAcceptance(int acceptance){
		this.acceptance=acceptance;
	}
	protected int getIdReq() {
		return idReq;
	}
	protected void setIdReq(int idReq) {
		this.idReq = idReq;
	}
	protected String getGoodsName() {
		return goodsName;
	}
	protected void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	protected int getReq() {
		return req;
	}
	protected void setReq(int req) {
		this.req = req;
	}
	protected String getManufacturer() {
		return manufacturer;
	}
	protected void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	protected String getSupp() {
		return supp;
	}
	protected void setSupp(String supp) {
		this.supp = supp;
	}
	
}
