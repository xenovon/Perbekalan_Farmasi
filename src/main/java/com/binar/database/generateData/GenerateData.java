package com.binar.database.generateData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.text.Segment;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Insurance;
import com.binar.entity.Manufacturer;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Role;
import com.binar.entity.Setting;
import com.binar.entity.Supplier;
import com.binar.entity.SupplierGoods;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.entity.enumeration.EnumSettingGroup;
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
			initiateGoodsData();
			initiateSupplierData();
			initiatieManufacturerData();
			setSettingData();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void deleteData(){
		List<ReqPlanning> reqs=server.find(ReqPlanning.class).findList();
		for(ReqPlanning req:reqs){
			server.delete(req);
		}
		List<SupplierGoods> supps=server.find(SupplierGoods.class).findList();
		for(SupplierGoods supp:supps){
			server.delete(supp);
		}
 		List<User> users=server.find(User.class).findList();
		for(User user:users){
			server.delete(user);
		}
		List<Role> roles=server.find(Role.class).findList();
		for(Role role:roles){
			server.delete(role);
		}
		
		List<Goods> goods=server.find(Goods.class).findList();
		for(Goods good:goods){
			server.delete(good);
		}
		List<Supplier> suppliers=server.find(Supplier.class).findList();
		for(Supplier supplier:suppliers){
			server.delete(supplier);
		}
		List<Manufacturer> manufacturers=server.find(Manufacturer.class).findList();
		for(Manufacturer manufactur:manufacturers){
			server.delete(manufactur);
		}
		List<Setting> settings=server.find(Setting.class).findList();
		for(Setting setting:settings){
			server.delete(setting);
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
			insurance1.setShowInDropdown(true);
			insurance1.setDescription("Merupakan jaminan kesehatan dari pemerintah Indonesia ");
		server.save(insurance1);
		Insurance insurance2=new Insurance();
			insurance2.setName("Umum");
			insurance2.setShowInDropdown(false);
			insurance2.setDescription("Obat Tidak Memiliki Jaminan Kesehatan");
		server.save(insurance2);
		Insurance insurance3=new Insurance();
			insurance3.setName("Jamsostek");
			insurance3.setShowInDropdown(true);
			insurance3.setDescription("Merupakan Jaminan sosial tenaga kerja");
		server.save(insurance3);

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
				userFRM.setEmployeeNum("120912");
				userFRM.setName("Binar Candra Auni");
				userFRM.setPasswordHash("password");
				userFRM.setPhoneNumber("081327094933");
				userFRM.setUsername("binar");
				userFRM.setRole(roleFRM);
				userFRM.setActive(true);	
		server.save(userFRM);  
		
		User userIFRS=new User();
			userIFRS.setAddress("Jalan Sukabirus");
			userIFRS.setEmployeeNum("120982");
			userIFRS.setName("Nana Sumadewi Nautika");
			userIFRS.setPasswordHash("password");
			userIFRS.setPhoneNumber("32412321");
			userIFRS.setUsername("nana");
			userIFRS.setRole(roleIFRS);
			userIFRS.setActive(true);	
		
		server.save(userIFRS);  		
		

		User userPPK=new User();
			userPPK.setAddress("Jalan Buah Batu");
			userPPK.setEmployeeNum("120732");
			userPPK.setName("Michail Sumarjan");
			userPPK.setPasswordHash("password");
			userPPK.setPhoneNumber("091092444");
			userPPK.setUsername("michael");
			userPPK.setRole(rolePPK);
			userPPK.setActive(true);	
	
		server.save(userPPK);
		
		User userPNJ=new User();
			userPNJ.setAddress("Jalan Ragasemangsang");
			userPNJ.setEmployeeNum("120532");
			userPNJ.setName("Suhardiani");
			userPNJ.setPasswordHash("password");
			userPNJ.setPhoneNumber("080420912");
			userPNJ.setUsername("suha");
			userPNJ.setRole(rolePNJ);
			userPNJ.setActive(true);	

		server.save(userPNJ);		
		User userTPN=new User();
			userTPN.setAddress("Jalan Juanda ");
			userTPN.setEmployeeNum("120962");
			userTPN.setName("Nantia Ramansari");
			userTPN.setPasswordHash("password");
			userTPN.setPhoneNumber("9002093");
			userTPN.setUsername("nantia");
			userTPN.setRole(roleTPN);
			userTPN.setActive(true);	

		server.save(userTPN);			

		User userADM=new User();
			userADM.setAddress("Jalan Gatot Subroto ");
			userADM.setEmployeeNum("092003");
			userADM.setName("Administrator");
			userADM.setPasswordHash("password-admin");
			userADM.setPhoneNumber("etewt");
			userADM.setUsername("admin");
			userADM.setRole(roleADM);
			userADM.setActive(true);	

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
	private void initiatieManufacturerData(){
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
			goods.setIdGoods("BRG-1X");
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
			goods.setMinimumStock(100);
			goods.setHet(200000);
		server.save(goods);

		Goods goods1 =new Goods();
			goods1.setIdGoods("BRG-2X");
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
			goods1.setMinimumStock(20);
			goods1.setHet(100000);
		server.save(goods1);		
	}
	
	private void setSettingData(){
		Setting setting1=new Setting();
				setting1.setSettingDescription("Masukan data satuan barang, tiap satuan dibatasi oleh tanda koma (','). Singkatan dan nama kemasan dipisah dengan tanda sama dengan ('=') ");
				setting1.setSettingKey("satuan");
				setting1.setSettingName("Satuan Barang");
				setting1.setSettingValue("btl=botol, pcs=pieces, roll=roll, tube=tube, bag=bag, tab=tablet, amp=ampul,vial=vial, pair=pair, pack=pack");
		server.save(setting1);		
		
		Setting setting2=new Setting();
			setting2.setSettingDescription("Masukan data kemasan barang,  tiap kemasan dibatasi oleh tanda koma (',').  Singkatan dan nama satuan dipisah dengan tanda sama dengan ('=')");
			setting2.setSettingKey("package");
			setting2.setSettingName("Kemasan Barang");
			setting2.setSettingValue("box=boxs, strip=strip, dus=dus, botol=botol, blister=blister");
		server.save(setting2);	
		
		Setting setting3=new Setting();
			setting3.setSettingDescription("Besaran PPN untuk barang dalam persen");
			setting3.setSettingKey("ppn");
			setting3.setSettingName("Nilai PPN (%)");
			setting3.setSettingValue("10");
			setting3.setSettingGroup(EnumSettingGroup.KEUANGAN);
		server.save(setting3);
		
		Setting setting4=new Setting();
			setting4.setSettingDescription("Nilai besaran margin dalam persen");
			setting4.setSettingKey("margin");
			setting4.setSettingName("Nilai Margin (%)");
			setting4.setSettingValue("20");
			setting4.setSettingGroup(EnumSettingGroup.KEUANGAN);
		server.save(setting4);
		
		Setting setting5=new Setting();
			setting5.setSettingDescription("Nama Apotik dan Lembaga rumah sakit pemesan");
			setting5.setSettingKey("apotek");
			setting5.setSettingName("Nama Apotik Dan Lembaga");
			setting5.setSettingValue("Instalasi Farmasi RSUD Ajibarang");
			setting5.setSettingGroup(EnumSettingGroup.SURAT_PESANAN);
		Setting setting6=new Setting();
			setting6.setSettingDescription("Rayon rumah sakit");
			setting6.setSettingKey("rayon");
			setting6.setSettingName("Rayon");
			setting6.setSettingValue("Jawa Tengah I");
			setting6.setSettingGroup(EnumSettingGroup.SURAT_PESANAN);
		Setting setting7=new Setting();
			setting7.setSettingDescription("Prefiks Nomor Surat Pesanan Narkotika");
			setting7.setSettingKey("narkotika");
			setting7.setSettingName("Prefiks Narkotika");
			setting7.setSettingValue("NAR");
			setting7.setSettingGroup(EnumSettingGroup.SURAT_PESANAN);
		
		Setting setting8=new Setting();
			setting8.setSettingDescription("Prefiks Nomor Surat Pesanan Psikotropika");
			setting8.setSettingKey("psikotropika");
			setting8.setSettingName("Prefiks Psikotropika");
			setting8.setSettingValue("PSI");
			setting8.setSettingGroup(EnumSettingGroup.SURAT_PESANAN);
		
		Setting setting9=new Setting();
			setting9.setSettingDescription("Prefiks Nomor Surat Pesanan Barang Umum");
			setting9.setSettingKey("general");
			setting9.setSettingName("Prefiks Barang General");
			setting9.setSettingValue("GEN");
			setting9.setSettingGroup(EnumSettingGroup.SURAT_PESANAN);
		
		
			
	}
}
