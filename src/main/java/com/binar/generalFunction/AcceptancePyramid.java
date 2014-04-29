package com.binar.generalFunction;

import java.util.HashMap;
import java.util.Map;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Role;

public class AcceptancePyramid {

	/*
		Kelas untuk mengatur penerimaan rencana kebutuhan dan penghapusa barang
		Nilai penerimaan dsimpan dalam bentuk integer
		Role  PPK, PNJ, IFRS
		Tiap role mempunyai value unik dalam integer
		PPK = 5
		PNJ = 3
		IFRS = 1
		Tiap disetujui, maka nilai acceptance akan ditambahkan sesuai dengan value dari role
		Jika tidak disetujui, maka nilai acceptance dikurangi 1
		Maka : 
		Jika sudah disetujui sama ifrs , value = 1;
		jika sudah disetujui ama ifrs dan pnj = 4;
		jika sudah disetujui ama ifrs dan pnj dan ppk = 9;
		
		Jika tidak disetujui sama ifrs , value = -1;
		jika tidak disetujui ama  pnj = 3;
		jika tidak disetujui ama ppk = 8;
		
		
		
		
		
		
		Jika value == 9, maka rencana kebutuhan dianggap sudah disetujui
		
		Jika role dibawahnya mengubah persetujuannya, maka value diatasnya akan direset.
		ex :  value == 9, dan ifrs mengubah persetujuannya, maka value == 0;
		ex  value ==9 dan pnj mengubah persetujuan, maka value ==1
		dst
	*/
	
	public enum RoleEnum{
		PPK, IFRS, PNJ
	}
	EbeanServer server;
	LoginManager login;
	public AcceptancePyramid(GeneralFunction function) {
		this.login=function.getLogin();
		this.server=function.getServer();
	}
	
	private RoleEnum getRole(){
		String role=login.getRoleId();
		if(role.equals("PPK")){
			return RoleEnum.PPK;
		}else if(role.equals("PNJ")){
			return RoleEnum.PNJ;
		}else{
			return RoleEnum.IFRS;
		}
	}
	public int acceptedOrNot(int accept){
		System.out.println("Accepted or not" +accept);
		if(accept==0){
			return neutral();
		}else if(accept==1){
			return unAccepted();
		}else{
			return accepted();
		}
	}
	//menerima penghapusan atau persetujuan
	private int accepted(){
		if(getRole()==RoleEnum.IFRS){
			return 1;
		}else if(getRole()==RoleEnum.PNJ){
			return 4;
		}else if(getRole()==RoleEnum.PPK){
			return 9;
		}else return 0;
	}
	//belum disetujui
	private int neutral(){
		if(getRole()==RoleEnum.IFRS){
			return 0;
		}else if(getRole()==RoleEnum.PNJ){
			return 1;
		}else if(getRole()==RoleEnum.PPK){
			return 4;
		}else return 0;
	}
	
	//tidak lagi menyetujui penghapusan atau persetujuan
	private int unAccepted(){
		if(getRole()==RoleEnum.IFRS){
			return -1;
		}else if(getRole()==RoleEnum.PNJ){
			return 3;
		}else if(getRole()==RoleEnum.PPK){
			return 8;
		}else return 0;
	}
	//tidak lagi menyetujui penghapusan atau persetujuan
/*	private int unAccepted(){
		if(getRole()==RoleEnum.IFRS){
			return -1;
		}else if(getRole()==RoleEnum.PNJ){
			return 3;
		}else if(getRole()==RoleEnum.PPK){
			return 8;
		}else return 0;
	}	
	*/
	//apakah mesti ditampilin atau tidak 
	public boolean isShow(int value){
		if(getRole()==RoleEnum.IFRS){
			return true;
		}else if(getRole()==RoleEnum.PNJ){
			if(value>=1){
				return true;
			}else{
				return false;
			}
		}else if(getRole()==RoleEnum.PPK){
			if(value>=4){
				return true;
			}else{
				return false;
			}
		}
		return false;
		
	}
	//apakah dianggap diterima oleh orang tertentu 
	public boolean isAcceptedBy(int value){
		if(getRole()==RoleEnum.IFRS){
			if(value>=1){
				return true;
			}else{
				return false;
			}
		}else if(getRole()==RoleEnum.PNJ){
			if(value>=4){
				return true;
			}else{
				return false;
			}
		}else if(getRole()==RoleEnum.PPK){
			if(value>=9){
				return true;
			}else{
				return false;
			}
		}
		return false;
				
	}

	//mengembalikan nama role yang udah menerima
	public String acceptedBy(int value){
		if(value==0){
			return "Belum Disetujui";
		}else if(value==1){
			return "Disetujui Oleh IFRS";
		}else if(value==4){
			return "Disetujui Oleh Kabid Penunjang";
		}else if(value==9){
			return "Disetujui Oleh PPK";
		}else if(value==-1){
			return "Tidak Disetujui Oleh IFRS";
		}else if(value==3){
			return "Tidak Disetujui Oleh Kabid Penunjang";
		}else if(value==8){
			return "Tidak Disetujui Oleh PPK";
		}else{
			return "angka salah";
		}
		
		
	}
	//apakah sudah dianggap diterima atau belum oleh semua 
	public boolean isAcceptedByAll(int value){
		if(value==9){
			return true;
		}else return false;
	}
	
	//apakah sudah dianggap diterima atau belum oleh ifrs
	public boolean isAccepted(int value){
		if(value>=1){
			return true;
		}else return false;
	}
		
	//filter untuk deletion
	//Untuk masing-masing role, berapa nilai agar dianggap sebagai diterima 
	public int getAcceptCriteria(){
		if(getRole()==RoleEnum.IFRS){
			return 1;
		}else if(getRole()==RoleEnum.PNJ){
			return 4;
		}else if(getRole()==RoleEnum.PPK){
			return 9;
		}
		return 0;
	}
/*	public int getUnacceptCriteria(){
		if(getRole()==RoleEnum.IFRS){
			return 0;
		}else if(getRole()==RoleEnum.PNJ){
			return 1;
		}else if(getRole()==RoleEnum.PPK){
			return 4;
		}
		return 0;
	}
	*/
	//Untuk masing-masing role, berapa nilai agar dianggap sebagai belum disetujui	
	public int getUnacceptCriteria(){
		if(getRole()==RoleEnum.IFRS){
			return 0;
		}else if(getRole()==RoleEnum.PNJ){
			return 3;
		}else if(getRole()==RoleEnum.PPK){
			return 8;
		}
		return 0;
	}
	//untuk di dashboard, menentukan apakah rencana kebutuhan sudah bisa di atur2 oleh pengguna atau tidak
	//mirip dengan method isShow()
	public boolean isManipulable(int value){
		if(getRole()==RoleEnum.IFRS){
			if(value==0){
				return true;
			}else{
				return false;
			}

		}else if(getRole()==RoleEnum.PNJ){
			if(value==1){
				return true;
			}else{
				return false;
			}
		}else if(getRole()==RoleEnum.PPK){
			if(value==4){
				return true;
			}else{
				return false;
			}
		}
		return false;

	}
	
	
	//mendapatkan kriteria jika sudah diterima oleh semua
	public int getAcceptedByAllCriteria(){
		return 9;
	}
	//Berfungsi untuk memilih pilihan dropdown yang ada di halaman persetujuan
	//  0 untuk belum dipilih
	//  1 untuk ditolak
	//  2 untuk diterima
	public int getDropdownChoice(int value){
		if(getRole()==RoleEnum.IFRS){
			if(value==-1){
				return 1;
			}if(value==0){
				return 0;
			}else if(value>=1){
				return 2;
			}
		}else if(getRole()==RoleEnum.PNJ){
			if(value==3){
				return 1;
			}if(value<=2){
				return 0;
			}else if(value>=4){
				return 2;
			}
		}else if(getRole()==RoleEnum.PPK){
			if(value==8){
				return 1;
			}if(value<=7){
				return 0;
			}else if(value>=9){
				return 2;
			}
		}
		return 0;
	}
	
	
	//------------Kumpulan Fungsi untuk manipulasi data comment //
	
	//CARA KERJA
	/*	Data di database disimpan dalam bentuk :
	 * IFRS==Kurang bermakna -- PPK==Salah jumlah -- PNJ==Bagus 
	 * 
	 * 
	 */
	//UNTUK DATA ROLE, sesuai dengan idRole
	
	//-----------------------------------------Untuk ReqPlanning
	//Untuk mendapatkan comment req planning role tertentu
	
	public String getCommentReqPlanning(int idReqPlanning){
		String comment=getAllCommentReqPlanning(idReqPlanning);
		if(comment!=null){
			Map<String, String> data=split(comment);
			return data.get(getRole().toString());
		}
		return "";
	}
	private String getAllCommentReqPlanning(int idReqPlanning){
		try {
			ReqPlanning req=server.find(ReqPlanning.class, idReqPlanning);
			return req.getComment();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public String saveReqPlanning(String data, int idReqPlanning){
		String comment=getAllCommentReqPlanning(idReqPlanning);
		System.out.println("Current Data :");
		Map<String, String> dataMap=split(comment);
		dataMap.put(getRole().toString(), data);		
		return getAllReqPlanning(dataMap);
	}
	private String getAllReqPlanning(Map<String, String> data){
		System.out.println("Get All Deletion "+data.toString());
		try {
			String result=combine(data);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan aplikasi "+e.getMessage();
		}
	}
	public String getCommentReqPlanningFormat(int idReqPlanning){
		String result=getAllCommentReqPlanning(idReqPlanning);
		return formatOutput(result);
	}

	
	//-----------------------------------------Untuk Deletion
	//Untuk mendapatkan comment deletion role tertentu	
	public String getCommentDeletion(int idDeletion){
		String comment=getAllCommentDeletion(idDeletion);
		if(comment!=null){
			Map<String, String> data=split(comment);
			return data.get(getRole().toString());
		}
		return null;
		
	}
	
	private String getAllDeletion(Map<String, String> data){
		System.out.println("Get All Deletion "+data.toString());
		try {
			String result=combine(data);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan aplikasi "+e.getMessage();
		}

	}
	public String saveDeletion(String data, int idDeletion){
		String comment=getAllCommentDeletion(idDeletion);
		Map<String, String> dataMap=split(comment);
		dataMap.put(getRole().toString(), data);
		
		return getAllDeletion(dataMap);

	}
	private String getAllCommentDeletion(int idDeletion){
		try {
			DeletedGoods deletion=server.find(DeletedGoods.class, idDeletion);
			return deletion.getComment();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public String getCommentDeletionFormat(int idDeletion){
		String result=getAllCommentDeletion(idDeletion);
		return formatOutput(result);
	}

	//METHOD PENDUKUNG
	//Agar output Terlihat Cantik , dalam HTML
	private String formatOutput(String input){
		Map<String, String> map=split(input);
		String returnValue="<div style='max-width:300px;word-wrap:break-word;'>";
		for(Map.Entry<String, String> entry:map.entrySet()){
			String user=null;
			if(entry.getKey().equals("IFRS")){
				user="Kepala IFRS";
			}else if(entry.getKey().equals("PPK")){
				user="PPK";
			}else  if(entry.getKey().equals("PNJ")){
				user="Kabid Penunjang";
			}
			if(user!=null){
				returnValue=returnValue+"<h4 style='margin:0;'> <b>"+user+"</b></h4>";
				String value=entry.getValue();
				if(value==null || value.equals("")){
					value="-";
				}
				returnValue=returnValue+"<p style='white-space:normal;margin:0 0 10px 0;'>"+value+"</p>";				
			}
		}
		
		return returnValue+"</div>";
	}
	//Membagi String berdasarkan ROLE, konversi string terformat ke format Map 
	private Map<String, String> split(String input){
		Map<String, String> returnValue=new HashMap<String, String>();
		String[] mapItem=input.split("--");
		System.out.println("SPLIT "+input);
		System.out.println("SPLIT "+mapItem.length+" "+mapItem[0]);
		
		for(String item:mapItem){
			String[] comment=item.split("==");
			String content;
			if(comment.length>1){
				content=comment[1];
				returnValue.put(comment[0], content);
			}
		}
		if(returnValue.get(RoleEnum.IFRS.toString())==null){
			returnValue.put(RoleEnum.IFRS.toString(), "");
		}
		if(returnValue.get(RoleEnum.PPK.toString())==null){
			returnValue.put(RoleEnum.PPK.toString(), "");
		}
		if(returnValue.get(RoleEnum.PNJ.toString())==null){
			returnValue.put(RoleEnum.PNJ.toString(), "");
		}
		System.out.println("Method Split" +returnValue.toString());
		return returnValue;
	}
	private String combine(Map<String,String> data){
		Map<String,String> dataFilter=filter(data);
		String returnValue="";
		for(Map.Entry<String, String> value:dataFilter.entrySet()){
			returnValue=returnValue+value.getKey()+"==";
			returnValue=returnValue+value.getValue();
			returnValue=returnValue+"--";
		}
		return returnValue;
	}
	//sanitasi input, agar formatnya tidak kacau..
	//Menghilangka karakter = dan -- dalam map
	private Map<String, String> filter(Map<String, String> input){
		Map<String, String> returnValue=new HashMap<String, String>();
		for(Map.Entry<String, String> datum:input.entrySet()){
			String key=datum.getKey().replace("==", "").replace("--","");
			String value=datum.getValue().replace("==", "").replace("--","");
			returnValue.put(key, value);
		}
		
		return returnValue;
	}
	

	public static void main(String[] args) {
		String tes="PPK==234--PNJ==234--IFRS==Luarbiasa--";
		String[] mapItem=tes.split("--");
		for(String item:mapItem){
			String[] comment=item.split("==");
			String content;
			if(comment.length==1){
				content="";
			}else{
				content=comment[1];
			}
			System.out.println(comment[0]+" "+content);
		}
	
	}
}
