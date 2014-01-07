package com.binar.core.dashboard;

public class DashboardPresenter {

	/*
	 * ISI DASHBOARD
	 * BARANG FAST MOVING MENDEKATI STOK MINIMUM
	 * BARANG MENDEKATI STOK MINIMUM
	 * 
	 * BARANG MENDEKATI KADALUARSA
	 * RENCANA KEBUTUHAN BULAN X DITERIMA
	 * DAFTAR BARANG KADALUARSA DITERIMA ??
	 * Dashboard Petugas Gudang Farmasi
Barang dengan stok mendekati minimum
> Nama barang, jumlah stok, satuan

Barang mendekati kadaluarsa
> Nama barang, jumlah stok, satuan, expired date

Stok barang 'fast moving' dengan stok hampir atau mendekati stok minimum. 
> Nama barang, jumlah stok, satuan

Status daftar kebutuhan bulan X diterima atau ditolak
Format :

Pengajuan Rencana Kebutuhan tanggal <<Tanggal Pengajuan>>

<<Nama barang>>	<<Satuan>>	<<Jumlah pengajuan>>	<<Jumlah disetujui>>	<<Oleh*>>






*yang posisinya lebih tinggi. Misal sudah disetujui oleh Ka IFRS sama kabis penunjang, yg ditampilin kabid penunjang

Status daftar obat kadaluarsa diterima atau ditolak.

<<Nama barang>>	<<Satuan>>	<<Status pengajuan>>	<<Oleh>>	








Dashboard tim pengadaan
Pengajuan rencana kebutuhan yang sudah disetujui (maksudnya daftar barang yang perlu dibeli)
Surat pesanan yang belum dicetak 
> Bisa nggak ya ditampilin?

Barang dengan jatuh tempo paling dekat 
> nama produsen, nama supplier, tanggal jatuh tempo, jumlah hutang

Supplier yang belum dibayarkan hutangnya beserta jumlah hutang 
> nama produsen, nama supplier, jumlah hutang

Summary daftar penerimaan barang (Berapa % dari pengadaan yg udah dilakukan barangny sudah diterima di gudang farmasi?)
> 

Dashboard Ka IFRS
Pengajuan rencana kebutuhan yang belum disetujui 
Daftar barang kadaluarsa yang belum disetujui
Barang yang sudah disetujui di perencanaan kebutuhan tapi belum ada di daftar pengadaan > Nama barang, supplier, jumlah
Barang yang sudah ada di daftar pengadaan tapi belum diterima > Nama barang, supplier, jumlah
Summary daftar penerimaan barang > Berapa % dari rencana kebutuhan yg udah disetujui barangny sudah diterima di gudang farmasi?

Dashboard Kabid Penunjang
Pengajuan rencana kebutuhan yang belum disetujui
Daftar barang kadaluarsa yang belum disetujui

Dashboard PPK
Pengajuan rencana kebutuhan yang belum disetujui
Daftar barang kadaluarsa yang belum disetujui

	 * 
	 */
	
	public DashboardPresenter() {
		
	}
}
