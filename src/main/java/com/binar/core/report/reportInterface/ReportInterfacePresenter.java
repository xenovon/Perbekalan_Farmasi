package com.binar.core.report.reportInterface;

import com.binar.core.report.reportInterface.ReportInterfaceView.ReportInterfaceListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentPresenter;
import com.binar.core.report.reportInterface.reportContent.ReportParameter;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;

public class ReportInterfacePresenter implements ReportInterfaceListener{

	ReportInterfaceModel model;
	ReportInterfaceViewImpl view;
	GeneralFunction function;
	ReportContentPresenter reportPresenter; //kelas yang berfungsi mengatur form yang muncul ketika tombol laporan diklik
	ReportParameter parameter;  //Kelas untuk mengelola parameter data yang dikirimkan ke kelas ReportPrint
	LoginManager loginManager;

	public ReportInterfacePresenter(ReportInterfaceModel model, 
			ReportInterfaceViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.view.init();
		this.view.setListener(this);
		this.loginManager=function.getLogin();

		this.parameter=new ReportParameter(function);
		reportPresenter=new ReportContentPresenter(function, parameter);
		roleProcessor();
	}

	public void roleProcessor(){
		if(loginManager.getRoleId().equals(loginManager.FRM)){
			
			view.getButtonConsumption().setVisible(true);
			view.getButtonDailyConsumption().setVisible(true);
			view.getButtonExpiredGoods().setVisible(true);
			view.getButtonProcurement().setVisible(true);
			view.getButtonReceipt().setVisible(true);
			view.getButtonRequirement().setVisible(true);
			view.getButtonStock().setVisible(true);
			
		}else if(loginManager.getRoleId().equals(loginManager.IFRS)){
			view.getButtonConsumption().setVisible(true);
			view.getButtonDailyConsumption().setVisible(true);
			view.getButtonExpiredGoods().setVisible(true);
			view.getButtonProcurement().setVisible(true);
			view.getButtonReceipt().setVisible(true);
			view.getButtonRequirement().setVisible(true);
			view.getButtonStock().setVisible(true);

		}else if(loginManager.getRoleId().equals(loginManager.PNJ)){
			view.getButtonConsumption().setVisible(false);
			view.getButtonDailyConsumption().setVisible(false);
			view.getButtonExpiredGoods().setVisible(true);
			view.getButtonProcurement().setVisible(true);
			view.getButtonReceipt().setVisible(false);
			view.getButtonRequirement().setVisible(true);
			view.getButtonStock().setVisible(false);
		}else if(loginManager.getRoleId().equals(loginManager.PPK)){
			view.getButtonConsumption().setVisible(false);
			view.getButtonDailyConsumption().setVisible(false);
			view.getButtonExpiredGoods().setVisible(true);
			view.getButtonProcurement().setVisible(true);
			view.getButtonReceipt().setVisible(false);
			view.getButtonRequirement().setVisible(true);
			view.getButtonStock().setVisible(false);

		}else if(loginManager.getRoleId().equals(loginManager.TPN)){
			view.getButtonConsumption().setVisible(false);
			view.getButtonDailyConsumption().setVisible(false);
			view.getButtonExpiredGoods().setVisible(false);
			view.getButtonProcurement().setVisible(true);
			view.getButtonReceipt().setVisible(true);
			view.getButtonRequirement().setVisible(false);
			view.getButtonStock().setVisible(false);
		}else if(loginManager.getRoleId().equals(loginManager.ADM)){
			view.getButtonConsumption().setVisible(false);
			view.getButtonDailyConsumption().setVisible(false);
			view.getButtonExpiredGoods().setVisible(false);
			view.getButtonProcurement().setVisible(false);
			view.getButtonReceipt().setVisible(false);
			view.getButtonRequirement().setVisible(false);
			view.getButtonStock().setVisible(false);

		}		
		
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
			view.displayForm(reportPresenter.getViewDailyConsumption(), "Buat Laporan Pemakaian Harian");
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
			view.displayForm(reportPresenter.getViewConsumption(), "Buat Laporan Pemakaian Barang");
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
