package com.binar.database.generateData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Insurance;
import com.binar.entity.Manufacturer;
import com.binar.entity.Role;
import com.binar.entity.Setting;
import com.binar.entity.Supplier;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;

public class GenerateData {

	GeneralFunction generalFunction;
	EbeanServer server;
	public  GenerateData(GeneralFunction function){
		this.generalFunction=function;
		server=generalFunction.getServer();
	}

	
	public void insertData(){
		deleteData();
		try {
			insertRole();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			insertUserData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			insertInsurance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteData(){
		List<User> users=server.find(User.class).findList();
		for(User user:users){
			server.delete(user);
		}
		List<Role> roles=server.find(Role.class).findList();
		for(Role role:roles){
			server.delete(role);
		}
		List<Insurance> insurances=server.find(Insurance.class).findList();
		for(Insurance insurance:insurances){
			server.delete(insurance);
		}
	}
	public void insertRole(){
		Role roleFarmasi=new Role();
		roleFarmasi.setIdRole("FRM");
		roleFarmasi.setDescription("Petugas gudang farmasi "
								  +"merupakan pengguna yang bertugas mengelola"
								  + "proses perbekalan farmasi dalam aplikasi");
		roleFarmasi.setRoleName("Petugas Gudang </br> Farmasi");
		
		server.save(roleFarmasi);
		
		Role roleKepala=new Role();
		roleKepala.setIdRole("IFRS");
		roleKepala.setDescription("Kepala IFRS adalah pengguna yang memberi approval dokumen yang berasal dari gudang farmasi");
		roleKepala.setRoleName("Kepala IFRS");
		
		server.save(roleKepala);
		
		Role rolePPK=new Role();
		rolePPK.setIdRole("PPK");
		rolePPK.setDescription("PPK adalah pejabat yang bertanggung jawab atas pelaksanaan pengadaan barang dan pemberi approval pada pengadaan barang");
		rolePPK.setRoleName("PPK");
		
		server.save(rolePPK);
		
		Role rolePenunjang=new Role();
		rolePenunjang.setIdRole("PNJ");
		rolePenunjang.setDescription("Kabid penunjang memberikan approval dalam pelaksanaan kebijakan pelayanan terkait pengelolaan perbekalan farmasi");
		rolePenunjang.setRoleName("Kabid Penunjang");
		
		server.save(rolePenunjang);
		
		Role rolePengadaan=new Role();
		rolePengadaan.setIdRole("TPN");
		rolePengadaan.setDescription("Tim pengadaan merupakan tim dibentuk untuk membantu proses administrasi pengadaan perbekalan farmasi");
		rolePengadaan.setRoleName("Tim Pengadaan");
		
		server.save(rolePengadaan);
		
		Role roleAdministrator=new Role();
		roleAdministrator.setIdRole("ADM");
		roleAdministrator.setDescription("Administrator memiliki hak untuk mengelola pengguna aplikasi");
		roleAdministrator.setRoleName("Administrator");
		
		server.save(roleAdministrator);
		
			
		
	}
	public void insertInsurance(){
		Insurance insurance1=new Insurance();
			insurance1.setName("Jamkesmas");
			insurance1.setDescription("Merupakan jaminan kesehatan dari pemerintah Indonesia ");
		server.save(insurance1);
		Insurance insurance2=new Insurance();
			insurance2.setName("Umum");
			insurance2.setDescription("Merupakan jaminan kesehatan umum ");
		server.save(insurance2);
	}
	public void insertUserData(){
		//data user dummy
		final Role roleFRM=server.find(Role.class, "FRM");
		final Role roleIFRS=server.find(Role.class, "IFRS");
		final Role rolePPK=server.find(Role.class, "PPK");
		final Role rolePNJ=server.find(Role.class, "PNJ");
		final Role roleTPN=server.find(Role.class, "TPN");
		final Role roleADM=server.find(Role.class, "ADM");
		
		User userFRM=new User();
				userFRM.setAddress("Jalan Palem Indah Blok A2 Nomor 10");
				userFRM.setDateOfBirth(new DateTime(1991, 9, 21, 18, 30).toDate());
				userFRM.setEmail("youhavetochoose@yahoo.com");
				userFRM.setEmployeeNum("120912");
				userFRM.setName("Binar Candra Auni");
				userFRM.setPasswordHash("password");
				userFRM.setPhoneNumber("081327094933");
				userFRM.setPlaceBirth("Wonosobo");
				userFRM.setUsername("binar");
				userFRM.setRole(roleFRM);
				
		server.save(userFRM);  
		
		User userIFRS=new User();
			userIFRS.setAddress("Jalan Sukabirus");
			userIFRS.setDateOfBirth(new Date());
			userIFRS.setEmail("nanasumadewi@gmail.com");
			userIFRS.setEmployeeNum("120982");
			userIFRS.setName("Nana Sumadewi Nautika");
			userIFRS.setPasswordHash("password");
			userIFRS.setPhoneNumber("32412321");
			userIFRS.setUsername("nana");
			userIFRS.setPlaceBirth("Bandung");
			userIFRS.setRole(roleIFRS);
		
		server.save(userIFRS);  		
		

		User userPPK=new User();
			userPPK.setAddress("Jalan Buah Batu");
			userPPK.setDateOfBirth(new DateTime(1991, 12, 14, 11, 12).toDate());
			userPPK.setEmail("michaelsumarjan@gmail.com");
			userPPK.setEmployeeNum("120732");
			userPPK.setName("Michail Sumarjan");
			userPPK.setPasswordHash("password");
			userPPK.setPhoneNumber("091092444");
			userPPK.setPlaceBirth("Tangerang");
			userPPK.setUsername("michael");
			userPPK.setRole(rolePPK);
	
		server.save(userPPK);
		
		User userPNJ=new User();
			userPNJ.setAddress("Jalan Ragasemangsang");
			userPNJ.setDateOfBirth(new DateTime(1981, 5, 3, 11, 12).toDate());
			userPNJ.setEmail("suhari@gmail.com");
			userPNJ.setEmployeeNum("120532");
			userPNJ.setName("Suhardiani");
			userPNJ.setPasswordHash("password");
			userPNJ.setPhoneNumber("080420912");
			userPNJ.setPlaceBirth("Purwokerto");
			userPNJ.setUsername("suha");
			userPNJ.setRole(rolePNJ);

		server.save(userPNJ);		
		User userTPN=new User();
			userTPN.setAddress("Jalan Juanda ");
			userTPN.setDateOfBirth(new DateTime(1971, 4, 8, 11, 12).toDate());
			userTPN.setEmail("nansamia@gmail.com");
			userTPN.setEmployeeNum("120962");
			userTPN.setName("Nantia Ramansari");
			userTPN.setPasswordHash("password");
			userTPN.setPhoneNumber("9002093");
			userTPN.setPlaceBirth("Madiun");
			userTPN.setUsername("nantia");
			userTPN.setRole(roleTPN);

		server.save(userTPN);			

		User userADM=new User();
			userADM.setAddress("Jalan Gatot Subroto ");
			userADM.setDateOfBirth(new DateTime(1985, 4, 8, 11, 12).toDate());
			userADM.setEmail("rs-ajibarang@gmail.com");
			userADM.setEmployeeNum("092003");
			userADM.setName("Administrator");
			userADM.setPasswordHash("password-admin");
			userADM.setPhoneNumber("etewt");
			userADM.setPlaceBirth("Someplace");
			userADM.setUsername("admin");
			userADM.setRole(roleADM);

		server.save(userADM);	
	}
	
	private void initiateSupplierData(){
		Supplier supplier1 =new Supplier();
			supplier1.setSupplierAbbr("KF");
			supplier1.setEmail("customer@kimiafarma.com");
			supplier1.setSupplierName("Kimia Farma");
			supplier1.setSupplierAddress("Jakarta Pusat");
			supplier1.setFax("0921090312");
			supplier1.setPhoneNumber("08092022");
			supplier1.setDescription("Supplier ini merupakan supplier yang sangat menjunjung tinggi integritas");
		server.save(supplier1);

		Supplier supplier2 =new Supplier();
			supplier2.setSupplierAbbr("SS");
			supplier2.setEmail("customer@saptasari.com");
			supplier2.setSupplierName("Sapta Sari");
			supplier2.setSupplierAddress("Jalan Bulalale 21, Semarang");
			supplier2.setFax("453535");
			supplier2.setPhoneNumber("545345");
			supplier2.setDescription("Supplier yang suka memberikan banyak diskon");
			
		server.save(supplier2);		
		
	}
	private void initiatieDistributorData(){
		Manufacturer manufacturer1=new Manufacturer();
			manufacturer1.setAddress("Jalan Bangsa Merderka, Jakarta Barat");
			manufacturer1.setDescription("Manufaktur untuk obat-obat utama");
			manufacturer1.setFax("090923042");
			manufacturer1.setManufacturerName("Widatra");
			manufacturer1.setEmail("info@widatra.co.id");
			manufacturer1.setPhoneNumber("090909032");
		server.save(manufacturer1);
		Manufacturer manufacturer2=new Manufacturer();
			manufacturer2.setAddress("Jalan Jenderal Soedirman, Jogjakarta");
			manufacturer2.setDescription("Manufaktur untuk sampingan");
			manufacturer2.setFax("4543534532");
			manufacturer2.setManufacturerName("Biofarma");
			manufacturer2.setEmail("info@biofarma.com");
			manufacturer2.setPhoneNumber("390924");
		server.save(manufacturer2);
		
		
	}
	private void initiateGoodsData(){
		Goods goods =new Goods();
			goods.setCategory(EnumGoodsCategory.PATEN);
			goods.setCurrentStock(0);
			goods.setDescription("Obat untuk menangani masalah akut pada pasien dengan gejala sindrom aspargiluss ekstritis");
			goods.setGoodsPackage("box");
			goods.setImportant(false);
			goods.setInformation("Obat keras, berhati hatilah");
			goods.setInsurance(server.find(Insurance.class).where().eq("name", "Umum").findUnique());
			goods.setMinimumStock(12);
			goods.setName("CLOPEDIN INJ 5's");
			goods.setType(EnumGoodsType.OBAT);
			goods.setUnit("box");
		server.save(goods);

		Goods goods1 =new Goods();
			goods1.setCategory(EnumGoodsCategory.GENERIK);
			goods1.setCurrentStock(0);
			goods1.setDescription("Untuk Membalut luka");
			goods1.setGoodsPackage("pack");
			goods1.setImportant(false);
			goods1.setInformation("Barang ini mudah sobek");
			goods1.setInsurance(server.find(Insurance.class).where().eq("name", "Umum").findUnique());
			goods1.setMinimumStock(12);
			goods1.setName("Kassa Gulung 10CM");
			goods1.setType(EnumGoodsType.BMHP);
			goods1.setUnit("bag");
		server.save(goods1);		
	}
	
	private void setSettingData(){
		Setting setting1=new Setting();
				setting1.setSettingDescription("Masukan data satuan barang, tiap satuan dibatasi oleh tanda koma (',')");
				setting1.setSettingKey("satuan");
				setting1.setSettingName("Satuan Barang");
				setting1.setSettingValue("btl, pcs, roll, tube, bag, tab, amp");
		server.save(setting1);		
		
		Setting setting2=new Setting();
			setting2.setSettingDescription("Masukan data kemasan barang,  tiap kemasan dibatasi oleh tanda koma (',')");
			setting2.setSettingKey("package");
			setting2.setSettingName("Kemasan Barang");
			setting2.setSettingValue("btl, pcs, roll, tube, bag, tab, amp");
		server.save(setting2);				
	}
}
