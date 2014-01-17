package com.binar.core.report;

import com.binar.core.report.reportInterface.ReportInterfaceModel;
import com.binar.core.report.reportInterface.ReportInterfacePresenter;
import com.binar.core.report.reportInterface.ReportInterfaceViewImpl;
import com.binar.core.requirementPlanning.approval.ApprovalModel;
import com.binar.core.requirementPlanning.approval.ApprovalPresenter;
import com.binar.core.requirementPlanning.approval.ApprovalViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class Report  extends CssLayout {

	
	private VerticalLayout layout=new VerticalLayout();
	
	private GeneralFunction generalFunction;
	
	private ReportInterfaceModel model;
	private ReportInterfacePresenter presenter;
	private ReportInterfaceViewImpl view;
	
	public Report(GeneralFunction function) {
		generalFunction=function;
		
		model = new ReportInterfaceModel();
		view =new ReportInterfaceViewImpl(function);
		presenter = new  ReportInterfacePresenter(model, view, generalFunction);
		
		this.addComponent(view);
		this.setCaption("Laporan");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}
	
/*
YANG HANYA PER PERIODE (BULAN)!
	Laporan rencana kebutuhan obat per periode (bulan) (yg belum disetujui)
	Laporan rencana kebutuhan alkes&BMHP per periode (bulan) (yg belum disetujui)
	Laporan pengadaan obat (yang udah dicetak jadi SP, yang udah punya purchaseOrder) per periode (bulan)
	Laporan pengadaan alkes&BMHP (yang udah dicetak jadi SP, yang udah punya purchaseOrder) per periode (bulan)
	Daftar pengeluaran harian obat (1..2..3.. di gambar itu tanggal).
	Daftar pengeluaran harian alkes (1..2..3.. di gambar itu tanggal).

YANG BISA PERHARI&PERBULAN!
	Laporan penerimaan alkes&BMHP
	Laporan penerimaan obat 
	Laporan pemakaian obat
	Laporan pemakaian alkes&BMHP

YANG CUMA PERHARI
	Laporan stok opname alkes&BMHP
	Laporan stok opname obat

LAPORAN YANG TIDAK PERIODIK. CUMA DI-GENERATE SEKALI-SEKALI. BIASANYA SETAUN SEKALI
	Laporan pengajuan barang kadaluarsa (blm disetujui)
	Laporan barang kadaluarsa (udah disetujui)


 */

	
	
}
