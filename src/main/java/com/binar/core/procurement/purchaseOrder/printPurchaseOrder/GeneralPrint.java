package com.binar.core.procurement.purchaseOrder.printPurchaseOrder;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class GeneralPrint extends UI {

	String psikotropika;
	String general;
	String narkotika;
	
	@Override
	protected void init(VaadinRequest request) {
		setData();
		setContent(new Label(psikotropika.replace("{{PONumber}}","12345"), ContentMode.HTML));
		JavaScript.getCurrent().execute(
				"setTimeout(function() {" +
				" print(); self.close();}, 0);");
	}
	
	public void setData(){
		psikotropika="<html> <head> <title> Print Purchase Order : {{PONumber}} </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border-collapse:collapse}table tr td:first-child{width:150px}table tr td:nth-child(2){width:20px}table.border tr td:first-child{width:auto}table.border tr td:nth-child(2){width:auto}table.border{border:1px solid black}table.border tr td,table tr th{border:1px solid black}table tr td,table tr th{padding:2px;margin:0}.footer{float:right;margin-top:60px;margin-right:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px}.coret{font-size:11px;float:left;margin-top:200px}</style> </head> <body> <div class='center'> <h1 class='title'>Surat Pesanan Psikotropika</h1> </div> <div class='kepada'> <table> <tr> <td>No SP</td> <td>:</td> <td>{{PONumber}}</td> </tr> </table> </div> <p>Yang bertanda tangan di bawah ini : </p> <table> <tr> <td>Nama</td> <td>:</td> <td>{{UserName}}</td> </tr> <tr> <td>Alamat Rumah</td> <td>:</td> <td>{{Address}}</td> </tr> <tr> <td>Jabatan</td> <td>:</td> <td>{{Title}}</td> </tr> </table> <p>Mengajukan permohonan kepada :</p> <table> <tr> <td>Nama Perusahaan</td> <td>:</td> <td>{{SupplierName}}</td> </tr> <tr> <td>Alamat</td> <td>:</td> <td>{{SupplierAddress}}</td> </tr> </table> <p>Jenis Psikotropika Sebagai Berikut : </p> <table class='border'> <tr> <th>No</th> <th>Nama Barang</th> <th>Jumlah</th> </tr> {{TableCode}} </table> <p>Untuk Keperluan : PBF / Apotik / Rumah Sakit / Sarana Penyimpanan sediaan farmasi pemerintah / Lembaga pendidikan. (*) </p> <table> <tr> <td>Nama </td> <td>:</td> <td>{{FarmasiName}}</td> </tr> <tr> <td>Alamat</td> <td>:</td> <td>{{FarmasiAddress}}</td> </tr> </table> <div class='footer'> {{city}} , {{PODate}} <div class='tapak-asma'> <div class='kepala'>Pemesan</div> <div>{{UserName}}</div> <div>No. SIK {{UserNum}}</div> </div> </div> <div class='coret'> <i>Catatan</i> : (*) Coret yang tidak perlu </div> </body> </html>";
		narkotika="<html> <head> <title> Print Purchase Order : {{PONumber}} </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border-collapse:collapse}table tr td,table tr th{padding:2px;margin:0}table tr td:first-child{width:150px}table tr td:nth-child(2){width:20px}.footer{float:right;margin-top:60px;margin-right:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px}</style> </head> <body> <div class='center'> <h1 class='title'>Surat Pesanan Narkotika</h1> </div> <div class='kepada'> <table> <tr> <td>Rayon</td> <td>:</td> <td>{{Rayon}}</td> </tr> <tr> <td>No SP</td> <td>:</td> <td>{{PONumber}}</td> </tr> </table> </div> <p>Yang bertanda tangan di bawah ini : </p> <table> <tr> <td>Nama</td> <td>:</td> <td>{{UserName}}</td> </tr> <tr> <td>Jabatan</td> <td>:</td> <td>{{Title}}</td> </tr> <tr> <td>Alamat Rumah</td> <td>:</td> <td>{{Address}}</td> </tr> </table> <p>Mengajukan pesanan narkotika kepada :</p> <table> <tr> <td>Nama Distributor</td> <td>:</td> <td>{{SupplierName}}</td> </tr> <tr> <td>Alamat dan Nomor Telepon</td> <td>:</td> <td>{{SupplierAddress}}</td> </tr> <tr> <td>Sebagai berikut</td> <td>:</td> </tr> <tr> <td collspan='3'>{{GoodsName}}</td> </tr> </table> <p>Narkotika tersebut akan dipergunakan untuk keperluan</p> <p>Apotik/Lembaga : {{FarmasiName}}</p> <div class='footer'> {{city}} , {{PODate}} <div class='tapak-asma'> <div class='kepala'>Pemesan</div> <div>{{UserName}}</div> <div>No. SIK {{UserNum}}</div> </div> </div> </body> </html>";
		general="<html> <head> <title> Print Purchase Order : {{PONumber}} </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{border-bottom:1px solid black;padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border:1px solid black;border-collapse:collapse}table tr td,table tr th{border:1px solid black;padding:2px;margin:0}.footer{float:right;margin-top:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px}</style> </head> <body> <div class='center'> <h1 class='title'>{{RSTitle}}</h1> <h2 class='address'>{{RSAddress}}</h2> <h2 class='address'>Telp : {{PhoneNumber}}</h2> </div> <h3>Surat Pesanan</h3> <div class='PONumber'> No : {{PONumber}} </div> <div class='kepada'> Kepada Yth. </br> {{SupplierName}}</br> di {{SupplierCity}} </br> </br> </div> <p>Mohon dikirim barang sbb : </p> <table> <tr> <th>No</th> <th>Nama Barang</th> <th>Jumlah</th> </tr> {{TableCode}} </table> <div class='footer'> {{city}} , {{PODate}} <div class='tapak-asma'> <div class='kepala'>Kepala IFRS</div> <div>{{UserName}}</div> <div>SP: {{UserNum}}</div> </div> </div> </body> </html>";
	}
	

}
