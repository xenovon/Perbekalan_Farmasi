package com.binar.database.generateData;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;

public class GenerateData {

	GeneralFunction generalFunction;
	EbeanServer server;
	public void GenerateData(GeneralFunction function){
		this.generalFunction=generalFunction;
		server=generalFunction.getServer();
	}
	
	public void insertData(){
		try {
			insertRole();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insertRole(){
		Role roleFarmasi=new Role();
		roleFarmasi.setCode("FRM");
		roleFarmasi.setDescription("Petugas gudang farmasi "
								  +"merupakan pengguna yang bertugas mengelola"
								  + "proses perbekalan farmasi dalam aplikasi");
		roleFarmasi.setRoleName("Petugas Gudang Farmasi");
		
		server.save(roleFarmasi);
		
		Role roleKepala=new Role();
		roleFarmasi.setCode("IFRS");
		roleFarmasi.setDescription("Kepala IFRS adalah pengguna yang memberi approval dokumen yang berasal dari gudang farmasi");
		roleFarmasi.setRoleName("Kepala IFRS");
		
		server.save(roleKepala);
		
		Role rolePPK=new Role();
		roleFarmasi.setCode("PPK");
		roleFarmasi.setDescription("PPK adalah pejabat yang bertanggung jawab atas pelaksanaan pengadaan barang dan pemberi approval pada pengadaan barang");
		roleFarmasi.setRoleName("PPK");
		
		server.save(rolePPK);
		
		Role rolePenunjang=new Role();
		roleFarmasi.setCode("PNJ");
		roleFarmasi.setDescription("Kabid penunjang memberikan approval dalam pelaksanaan kebijakan pelayanan terkait pengelolaan perbekalan farmasi");
		roleFarmasi.setRoleName("Kabid Penunjang");
		
		server.save(rolePenunjang);
		
		Role rolePengadaan=new Role();
		roleFarmasi.setCode("TPN");
		roleFarmasi.setDescription("Tim pengadaan merupakan tim dibentuk untuk membantu proses administrasi pengadaan perbekalan farmasi");
		roleFarmasi.setRoleName("Tim Pengadaan");
		
		server.save(rolePengadaan);
		
		Role roleAdministrator=new Role();
		roleFarmasi.setCode("ADM");
		roleFarmasi.setDescription("Administrator memiliki hak untuk mengelola pengguna aplikasi");
		roleFarmasi.setRoleName("Administrator");
		
		server.save(roleAdministrator);
		
			
		
	}
	public void insertUserData(){
		//data user dummy
		User userFRM=new User(){
			{
				setAddress("Jalan Kemuning");
			}
		};
	}
}
