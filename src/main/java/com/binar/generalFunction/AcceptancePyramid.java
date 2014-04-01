package com.binar.generalFunction;

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
	
	LoginManager login;
	public AcceptancePyramid(LoginManager login) {
		this.login=login;
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
	public int acceptedOrNot(boolean accept){
		if(accept){
			return accepted();
		}else{
			return unAccepted();
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
	
	//tidak lagi menyetujui penghapusan atau persetujuan
	private int unAccepted(){
		if(getRole()==RoleEnum.IFRS){
			return 0;
		}else if(getRole()==RoleEnum.PNJ){
			return 1;
		}else if(getRole()==RoleEnum.PPK){
			return 4;
		}else return 0;
	}
	
	
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
			return "Oleh IFRS";
		}else if(value==4){
			return "Oleh Kabid Penunjang";
		}else if(value==9){
			return "Oleh PPK";
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
	//Untuk masing-masing role, berapa nilai agar dianggap sebagai tidak diterima 	
	public int getUnacceptCriteria(){
		if(getRole()==RoleEnum.IFRS){
			return 0;
		}else if(getRole()==RoleEnum.PNJ){
			return 1;
		}else if(getRole()==RoleEnum.PPK){
			return 4;
		}
		return 0;
	}
	//mendapatkan kriteria jika sudah diterima oleh semua
	public int getAcceptedByAllCriteria(){
		return 9;
	}
	
	
}
