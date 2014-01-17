package com.binar.core.report.reportInterface;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class ReportInterfaceViewImpl extends VerticalLayout implements ReportInterfaceView, ClickListener{

	private GeneralFunction function;
	private Button buttonRequirement;
	private Button buttonProcurement;
	private Button buttonDailyConsumption;
	private Button buttonReceipt;
	private Button buttonConsumption;
	private Button buttonStock;
	private Button buttonExpiredGoods;
	private Label title;
	
/*
 * YANG HANYA PER PERIODE (BULAN)!
D	Laporan rencana kebutuhan obat per periode (bulan) (yg belum disetujui) 
D	Laporan rencana kebutuhan alkes&BMHP per periode (bulan) (yg belum disetujui)
D	Laporan pengadaan obat (yang udah dicetak jadi SP, yang udah punya purchaseOrder) per periode (bulan)
D	Laporan pengadaan alkes&BMHP (yang udah dicetak jadi SP, yang udah punya purchaseOrder) per periode (bulan)
D	Daftar pengeluaran harian obat (1..2..3.. di gambar itu tanggal).
D	Daftar pengeluaran harian alkes (1..2..3.. di gambar itu tanggal).

YANG BISA PERHARI&PERBULAN!
D	Laporan penerimaan alkes&BMHP
D	Laporan penerimaan obat 
D	Laporan pemakaian obat
D	Laporan pemakaian alkes&BMHP

YANG CUMA PERHARI
D	Laporan stok opname alkes&BMHP
D	Laporan stok opname obat

LAPORAN YANG TIDAK PERIODIK. CUMA DI-GENERATE SEKALI-SEKALI. BIASANYA SETAUN SEKALI
D	Laporan pengajuan barang kadaluarsa (blm disetujui)
D	Laporan barang kadaluarsa (udah disetujui)


 */
	
	
	public ReportInterfaceViewImpl(GeneralFunction function) {
		this.function=function;
	}
	@Override
	public void init() {
		title=new Label("<h2>Laporan</h2>", ContentMode.HTML);

		buttonConsumption= new Button("Laporan Pemakaian Barang");
		buttonConsumption.addClickListener(this);
		
		buttonDailyConsumption=new Button("Pemakaian Barang Harian");
		buttonDailyConsumption.addClickListener(this);
		
		buttonExpiredGoods=new Button("Laporan Barang Kadaluarsa");
		buttonExpiredGoods.addClickListener(this);
		
		buttonProcurement=new Button("Laporan Pengadaan Barang");
		buttonProcurement.addClickListener(this);
		
		buttonReceipt=new Button("Laporan Penerimaan Barang");
		buttonReceipt.addClickListener(this);
		
		buttonRequirement = new Button("Laporan Rencana Kebutuhan");
		buttonRequirement.addClickListener(this);
		
		buttonStock=new Button("Laporan Stock");
		buttonStock.addClickListener(this);
		
		construct();
	}

	@Override
	public void construct() {
		this.setMargin(true);
		this.setSpacing(true);
		
		addComponents(title, buttonConsumption, buttonDailyConsumption, buttonProcurement, buttonReceipt, buttonRequirement, buttonStock);
	}
	ReportInterfaceListener listener;
	
	@Override
	public void setListener(ReportInterfaceListener listener) {
		this.listener=listener;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonConsumption){
			listener.buttonConsumptionClick();
		}
		if(event.getButton()==buttonDailyConsumption){
			listener.buttonDailyConsumptionClick();
		}
		if(event.getButton()==buttonExpiredGoods){
			listener.buttonExpiredGoodsClick();
		}
		if(event.getButton()==buttonProcurement){
			listener.buttonProcurementClick();
		}
		if(event.getButton()==buttonReceipt){
			listener.buttonReceiptClick();
		}
		
		if(event.getButton()==buttonRequirement){
			listener.buttonRequirementClick();
		}
		if(event.getButton()==buttonStock){
			listener.buttonStockClick();
		}
	}

	Window window;
	
	public void displayForm(Component content, String title, String height){
		displayForm(content, title);
		window.setHeight(height);
	}
	public void displayForm(Component content, String title){
		//menghapus semua window terlebih dahulu
		for(Window window:this.getUI().getWindows()){
			window.close();
		}
		if(window==null){
			window=new Window(title){
				{
					center();
					setClosable(false);
					setWidth("500px");
					setHeight("80%");
				}
			};
		}
		this.getUI().removeWindow(window);
		window.setCaption(title);
		window.setContent(content);
		this.getUI().addWindow(window);
	}
	
	
	
}
