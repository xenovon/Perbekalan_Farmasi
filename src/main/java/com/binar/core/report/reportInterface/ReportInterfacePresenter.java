package com.binar.core.report.reportInterface;

import com.binar.core.report.reportInterface.ReportInterfaceView.ReportInterfaceListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentPresenter;
import com.binar.core.report.reportInterface.reportContent.ReportParameter;
import com.binar.generalFunction.GeneralFunction;

public class ReportInterfacePresenter implements ReportInterfaceListener{

	ReportInterfaceModel model;
	ReportInterfaceViewImpl view;
	GeneralFunction function;
	ReportContentPresenter reportPresenter; //kelas yang berfungsi mengatur form yang muncul ketika tombol laporan diklik
	ReportParameter parameter;  //Kelas untuk mengelola parameter data yang dikirimkan ke kelas ReportPrint
	public ReportInterfacePresenter(ReportInterfaceModel model, 
			ReportInterfaceViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.view.init();
		reportPresenter=new ReportContentPresenter(function, parameter);
	}

	@Override
	public void buttonRequirementClick() {
		try {
			view.displayForm(reportPresenter.getRequirement(), "Buat Laporan Rencana Kebutuhan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void buttonProcurementClick() {
		try {
			view.displayForm(reportPresenter.getProcurement(), "Buat Laporan Pengadaan");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void buttonDailyConsumptionClick() {
		try {
			view.displayForm(reportPresenter.getViewDailyConsumption(), "Buat Laporan Pengeluaran");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void buttonReceiptClick() {
		try {
			view.displayForm(reportPresenter.getReceipt(), "Buat Laporan Penerimaan");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void buttonConsumptionClick() {
		try {
			view.displayForm(reportPresenter.getViewConsumption(), "Buat Laporan Pengeluaran");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void buttonStockClick() {
		try {
			view.displayForm(reportPresenter.getStock(), "Buat Laporan Stok Opname");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void buttonExpiredGoodsClick() {
		try {
			view.displayForm(reportPresenter.getExpiredGoods(), "Buat Laporan Barang Kadaluarsa");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
