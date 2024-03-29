package com.binar.core.report.reportInterface;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
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
	private final String BUTTON_WIDTH="280px";
	
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
		buttonConsumption.setWidth(BUTTON_WIDTH);
		buttonConsumption.addStyleName("button-report");
		buttonConsumption.setIcon(new ThemeResource("icons/image/cart2.png"));

		
		buttonDailyConsumption=new Button("Daftar Pengeluaran Harian");
		buttonDailyConsumption.addClickListener(this);
		buttonDailyConsumption.setWidth(BUTTON_WIDTH);
		buttonDailyConsumption.addStyleName("button-report");
		buttonDailyConsumption.setIcon(new ThemeResource("icons/image/daily11.png"));
		
		buttonExpiredGoods=new Button("Laporan Barang Kadaluarsa");
		buttonExpiredGoods.addClickListener(this);
		buttonExpiredGoods.setWidth(BUTTON_WIDTH);
		buttonExpiredGoods.addStyleName("button-report");
		buttonExpiredGoods.setIcon(new ThemeResource("icons/image/recycling.png"));
		
		buttonProcurement=new Button("Laporan Pengadaan Barang");
		buttonProcurement.addClickListener(this);
		buttonProcurement.setWidth(BUTTON_WIDTH);
		buttonProcurement.addStyleName("button-report");
		buttonProcurement.setIcon(new ThemeResource("icons/image/shopping8.png"));
		
		buttonReceipt=new Button("Laporan Penerimaan Barang");
		buttonReceipt.addClickListener(this);
		buttonReceipt.setWidth(BUTTON_WIDTH);
		buttonReceipt.addStyleName("button-report");
		buttonReceipt.setIcon(new ThemeResource("icons/image/credit13.png"));
	
		buttonRequirement = new Button("Laporan Rencana Kebutuhan");
		buttonRequirement.addClickListener(this);
		buttonRequirement.setWidth(BUTTON_WIDTH);
		buttonRequirement.addStyleName("button-report");
		buttonRequirement.setIcon(new ThemeResource("icons/image/time5.png"));
	
		buttonStock=new Button("Laporan Stok Opname");
		buttonStock.addClickListener(this);
		buttonStock.setWidth(BUTTON_WIDTH);
		buttonStock.addStyleName("button-report");
		buttonStock.setIcon(new ThemeResource("icons/image/factory3.png"));
		
		construct();
	}

	@Override
	public void construct() {
		this.setMargin(true);
		this.setSpacing(true);
		CssLayout layout=new CssLayout();
		layout.addStyleName("layout-report");
		layout.addComponents(buttonConsumption, buttonDailyConsumption, buttonExpiredGoods, buttonProcurement, buttonReceipt, buttonRequirement, buttonStock);
		addComponents(title, layout);
	}
	
	
	
	ReportInterfaceListener listener;
	
	@Override
	public void setListener(ReportInterfaceListener listener) {
		this.listener=listener;
	}
//	@Override
//	public void buttonClick(ClickEvent event) {
//		if(event.getButton()==buttonConsumption){
//			listener.buttonConsumptionClick();
//		}
//		if(event.getButton()==buttonExpiredGoods){
//			listener.buttonExpiredGoodsClick();
//		}
//		if(event.getButton()==buttonProcurement){
//			listener.buttonProcurementClick();
//		}
//		if(event.getButton()==buttonReceipt){
//			listener.buttonReceiptClick();
//		}
//		if(event.getButton()==buttonRequirement){
//			listener.buttonRequirementClick();
//		}
//	}

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
	public Button getButtonRequirement() {
		return buttonRequirement;
	}
	public Button getButtonProcurement() {
		return buttonProcurement;
	}
	public Button getButtonDailyConsumption() {
		return buttonDailyConsumption;
	}
	public Button getButtonReceipt() {
		return buttonReceipt;
	}
	public Button getButtonConsumption() {
		return buttonConsumption;
	}
	public Button getButtonStock() {
		return buttonStock;
	}
	public Button getButtonExpiredGoods() {
		return buttonExpiredGoods;
	}

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

}
