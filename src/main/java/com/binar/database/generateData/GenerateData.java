package com.binar.database.generateData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.text.Segment;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.Insurance;
import com.binar.entity.Invoice;
import com.binar.entity.InvoiceItem;
import com.binar.entity.Manufacturer;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
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
            initiateConsumptionData();
            initiateDeletionData();
            initiateReceptionData();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void deleteData(){
		List<ReqPlanning> reqs=server.find(ReqPlanning.class).findList();
		for(ReqPlanning req:reqs){
			server.delete(req);
		}
		List<Invoice> invoice=server.find(Invoice.class).findList();
		server.delete(invoice);
		server.delete(server.find(PurchaseOrder.class).findList());
        List<GoodsReception> goodsRecs=server.find(GoodsReception.class).findList();
        for(GoodsReception goodsRec:goodsRecs){
               server.delete(goodsRec);
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
        List<DeletedGoods> dels= server.find(DeletedGoods. class).findList();
        for(DeletedGoods del:dels){
               server.delete(dels);
        }
		
        List<GoodsConsumption> consumptions=server.find(GoodsConsumption.class).findList();
        for(GoodsConsumption consumption:consumptions){
               server.delete(consumption);
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
		roleFarmasi.setRoleName("Petugas Gudang Farmasi");
		
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
			userIFRS.setTitle("Kepala IFRS");
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
			userTPN.setTitle("Penanggung Jawab");

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
			supplier1.setCity("Jakarta Pusat");
			supplier1.setDescription("Supplier ini merupakan supplier yang sangat menjunjung tinggi integritas");
		server.save(supplier1);

		Supplier supplier2 =new Supplier();
			supplier2.setSupplierAbbr("SS");
			supplier2.setEmail("customer@saptasari.com");
			supplier2.setSupplierName("Sapta Sari");
			supplier2.setSupplierAddress("Jalan Bulalale 21, Semarang");
			supplier2.setFax("453535");
			supplier2.setPhoneNumber("545345");
			supplier2.setCity("Semarang");
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
		Goods goods2 =new Goods();
		goods2.setIdGoods("BRG-3X");
		goods2.setCategory(EnumGoodsCategory.PATEN);
		goods2.setCurrentStock(0);
		goods2.setDescription("Untuk Anti Depressan");
		goods2.setGoodsPackage("pack");
		goods2.setImportant(false);
		goods2.setInformation("Barang ini mudah sobek");
		goods2.setInsurance(server.find(Insurance.class).where().eq("name", "Umum").findUnique());
		goods2.setMinimumStock(12);
		goods2.setName("Closenin RX2");
		goods2.setType(EnumGoodsType.NARKOTIKA);
		goods2.setUnit("bag");
		goods2.setMinimumStock(20);
		goods2.setHet(100000);
	server.save(goods2);		

	}
	
	private void setSettingData(){
		Setting setting1=new Setting();
				setting1.setSettingDescription("Masukan data satuan barang, tiap satuan dibatasi oleh tanda koma (','). Singkatan dan nama kemasan dipisah dengan tanda sama dengan ('=') ");
				setting1.setSettingKey("satuan");
				setting1.setSettingName("Satuan Barang");
				setting1.setSettingValue("btl=botol, pcs=pieces, roll=roll, tube=tube, bag=bag, tab=tablet, amp=ampul,vial=vial, pair=pair, pack=pack");
				setting1.setSettingGroup(EnumSettingGroup.BARANG);
		server.save(setting1);		
		
		Setting setting2=new Setting();
			setting2.setSettingDescription("Masukan data kemasan barang,  tiap kemasan dibatasi oleh tanda koma (',').  Singkatan dan nama satuan dipisah dengan tanda sama dengan ('=')");
			setting2.setSettingKey("package");
			setting2.setSettingName("Kemasan Barang");
			setting2.setSettingValue("box=boxs, strip=strip, dus=dus, botol=botol, blister=blister");
			setting2.setSettingGroup(EnumSettingGroup.BARANG);
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
			setting5.setSettingGroup(EnumSettingGroup.UMUM);
		server.save(setting5);
		Setting setting6=new Setting();
			setting6.setSettingDescription("Rayon rumah sakit");
			setting6.setSettingKey("rayon");
			setting6.setSettingName("Rayon");
			setting6.setSettingValue("Jawa Tengah I");
			setting6.setSettingGroup(EnumSettingGroup.UMUM);
		server.save(setting6);
		Setting setting7=new Setting();
			setting7.setSettingDescription("Prefiks Nomor Surat Pesanan Narkotika");
			setting7.setSettingKey("narkotika");
			setting7.setSettingName("Prefiks Narkotika");
			setting7.setSettingValue("NAR");
			setting7.setSettingGroup(EnumSettingGroup.SURAT_PESANAN);
		server.save(setting7);		
		Setting setting8=new Setting();
			setting8.setSettingDescription("Prefiks Nomor Surat Pesanan Psikotropika");
			setting8.setSettingKey("psikotropika");
			setting8.setSettingName("Prefiks Psikotropika");
			setting8.setSettingValue("PSI");
			setting8.setSettingGroup(EnumSettingGroup.SURAT_PESANAN);
			server.save(setting8);		
		Setting setting9=new Setting();
			setting9.setSettingDescription("Prefiks Nomor Surat Pesanan Barang Umum");
			setting9.setSettingKey("general");
			setting9.setSettingName("Prefiks Barang General");
			setting9.setSettingValue("GEN");
			setting9.setSettingGroup(EnumSettingGroup.SURAT_PESANAN);
			server.save(setting9);
		Setting setting10=new Setting();
			setting10.setSettingDescription("Nomor Telepon Rumah Sakit");
			setting10.setSettingKey("rs_number");
			setting10.setSettingName("Nomor Telepon");
			setting10.setSettingValue("(0281)6570004 Ext. 116");
			setting10.setSettingGroup(EnumSettingGroup.UMUM);
			server.save(setting10);
		Setting setting11=new Setting();
			setting11.setSettingDescription("Alamat Rumah Sakit");
			setting11.setSettingKey("rs_address");
			setting11.setSettingName("Alamat Rumah Sakit");
			setting11.setSettingValue("Jl Raya Pancasan - Ajibarang");
			setting11.setSettingGroup(EnumSettingGroup.UMUM);
			server.save(setting11);		
		Setting setting12=new Setting();
			setting12.setSettingDescription("Kota rumah sakit");
			setting12.setSettingKey("rs_city");
			setting12.setSettingName("Kota");
			setting12.setSettingValue("Ajibarang");
			setting12.setSettingGroup(EnumSettingGroup.UMUM);
			server.save(setting12);		
		Setting setting13= new Setting();
            setting13.setSettingDescription( "Instalasi yang membutuhkan perbekalan farmasi");
            setting13.setSettingKey( "instalasi" );
            setting13.setSettingName( "Instalasi Rumah Sakit" );
            setting13.setSettingValue( "farmasi=Instalasi Farmasi, ibs=Instalasi Bedah Sentral, camar=Rawat Inap Camar, Kepodang=Rawat Inap Kepodang, kenari=Rawat Inap Kenari, perinatologi=Rawat Inap Perinatologi, cendrawasih=Rawat Inap Cendrawasih, nuri=Rawat Inap Nuri, IPJ=Instalasi Pemulasaran Jenazah, IPAL=Instalasi Pengelolaan Air Limbah, laundry=Instalasi Laundry, gizi=Instalasi Gizi, laboratorium=Instalasi Laboratorium");
            setting13.setSettingGroup(EnumSettingGroup.UMUM);
           server.save(setting13);
     
		
			
	}
	
	
	private void initiateConsumptionData(){
		System.out.println("Masukkan data konsumsi");
		GoodsConsumption consumption1 = new GoodsConsumption();
		Goods goods1 = server.find(Goods.class, "BRG-1X");
		
		consumption1.setGoods(goods1);
		consumption1.setInformation("Tes info");
		consumption1.setQuantity(55);
		consumption1.setStockQuantity(200);
		consumption1.setConsumptionDate(new DateTime(2013, 12, 11, 11, 12).toDate());
		consumption1.setTimestamp(new Date());
		consumption1.setWard("laboratorium");
		server.save(consumption1);
		
		GoodsConsumption consumption = new GoodsConsumption();
		Goods goods2 =server.find(Goods.class, "BRG-2X");
		
		consumption.setGoods(goods2);
		consumption.setInformation("Tes info");
		consumption.setQuantity(55);
		consumption.setStockQuantity(200);
		consumption.setConsumptionDate(new DateTime(2013, 12, 11, 11, 12).toDate());
		consumption.setTimestamp(new Date());
		consumption.setWard("camar");
		server.save(consumption);
	}
	
	private void initiateDeletionData(){
		System.out.println("Masukkan data penghapusan");
		DeletedGoods deletion1 = new DeletedGoods();
		SupplierGoods supGoods1 = server.find(SupplierGoods.class, "1");
		
		deletion1.setSupplierGoods(supGoods1);
		deletion1.setInformation("Tes info");
		deletion1.setQuantity(55);
		deletion1.setStockQuantity(200);
		deletion1.setDeletionDate(new DateTime(2013, 12, 11, 11, 12).toDate());
		deletion1.setTimestamp(new Date());
		server.save(deletion1);
		
		DeletedGoods deletion2 = new DeletedGoods();
		SupplierGoods supGoods2 = server.find(SupplierGoods.class, "2");
		
		deletion2.setSupplierGoods(supGoods2);
		deletion2.setInformation("Tes woohoo");
		deletion2.setQuantity(20);
		deletion2.setStockQuantity(60);
		deletion2.setDeletionDate(new DateTime(2013, 12, 12, 11, 12).toDate());
		deletion2.setTimestamp(new Date());
		server.save(deletion2);
		
		DeletedGoods deletion3 = new DeletedGoods();
		SupplierGoods supGoods3 = server.find(SupplierGoods.class, "3");
		
		deletion3.setSupplierGoods(supGoods2);
		deletion3.setInformation("Tes woohoo");
		deletion3.setQuantity(90);
		deletion3.setStockQuantity(10);
		deletion3.setDeletionDate(new DateTime(2013, 11, 12, 11, 12).toDate());
		deletion3.setTimestamp(new Date());
		server.save(deletion3);
	}
	
	private void initiateReceptionData(){
		System.out.println("Masukkan data penerimaan");
		GoodsReception reception1 = new GoodsReception();
		InvoiceItem invoItem1 = server.find(InvoiceItem.class,"1");
		
		reception1.setInformation("Tes info");
		reception1.setQuantityReceived(95);
		reception1.setStockQuantity(200);
		reception1.setDate(new DateTime(2013, 12, 11, 11, 12).toDate());
		reception1.setTimestamp(new Date());
		reception1.setExpiredDate(new DateTime(2015, 1, 1, 11, 11).toDate());
		reception1.setInvoiceItem(invoItem1);
		server.save(reception1);
		
	}
	
	private void initiateInvoiceItemData(){
		System.out.println("Masukkan data invoice Item");
		InvoiceItem invoItem1 = new InvoiceItem();
		Invoice invoice = server.find(Invoice.class, "1");
		PurchaseOrderItem purc1 = server.find(PurchaseOrderItem.class, "1");
		
		invoItem1.setDiscount(10);
		invoItem1.setBatch("1A");
		invoItem1.setPrice(20000);
		invoItem1.setPricePPN(220000);
		invoItem1.setPurchaseOrderItem(purc1);
		invoItem1.setQuantity(200);
		invoItem1.setInvoice(invoice);
		server.save(invoItem1);
		
	}
	
	private void initiateInvoiceData(){
		System.out.println("Masukkan data invoice");
		Invoice invoice1 = new Invoice();
		
		invoice1.setAmountPaid(0);
		invoice1.setInvoiceName("0io");;
		invoice1.setTimestamp(new Date());
		invoice1.setInvoiceNumber("inv-1");
		server.save(invoice1);
		
	}
	
	private void initiatePurchaseOrderItem(){
		System.out.println("Masukkan data penerimaan");
		PurchaseOrderItem purc1 = new PurchaseOrderItem();
		SupplierGoods suppgoods1 = server.find(SupplierGoods.class,"1");
		
		purc1.setInformation("Tes info");
		purc1.setQuantity(99);
		purc1.setSupplierGoods(suppgoods1);
		
		server.save(purc1);
		
	}
	
	private void initiateSupplierGoods(){
		System.out.println("Masukkan data supplier goods");
		SupplierGoods suppGoods1 = new SupplierGoods();
		Goods goods2 = server.find(Goods.class, "BRG-2X");
		Supplier supp1 = server.find(Supplier.class, "2");
		Manufacturer man1=server.find(Manufacturer.class, "1");
		
		suppGoods1.setLastPrice(90000);
		suppGoods1.setGoods(goods2);
		suppGoods1.setSupplier(supp1);
		suppGoods1.setLastUpdate(new DateTime(2015, 1, 1, 11, 11).toDate());
		suppGoods1.setManufacturer(man1);
		server.save(suppGoods1);
		
		SupplierGoods suppGoods2 = new SupplierGoods();
		Goods goods1 = server.find(Goods.class, "BRG-1X");
		Supplier supp2 = server.find(Supplier.class, "1");
		Manufacturer man2=server.find(Manufacturer.class, "2");
		
		suppGoods2.setLastPrice(8500);
		suppGoods2.setGoods(goods1);
		suppGoods2.setSupplier(supp2);
		suppGoods2.setLastUpdate(new DateTime(2015, 11, 1, 11, 11).toDate());
		suppGoods2.setManufacturer(man2);
		server.save(suppGoods2);
		
		SupplierGoods suppGoods3 = new SupplierGoods();
		Goods goods3 = server.find(Goods.class, "BRG-1X");
		Supplier supp3 = server.find(Supplier.class, "2");
		Manufacturer man3=server.find(Manufacturer.class, "1");
		
		suppGoods3.setLastPrice(47000);
		suppGoods3.setGoods(goods3);
		suppGoods3.setSupplier(supp3);
		suppGoods3.setLastUpdate(new DateTime(2015, 2, 2, 11, 11).toDate());
		suppGoods3.setManufacturer(man3);
		server.save(suppGoods3);
		
	}
	

}
