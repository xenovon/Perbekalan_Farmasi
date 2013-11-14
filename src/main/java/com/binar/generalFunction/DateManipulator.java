package com.binar.generalFunction;

import org.joda.time.DateTime;

/*
 * 
 * Kelas untuk memanipulasi tanggal
 */
public class DateManipulator {
	
	
	
	
	//Untuk mengubah  format Januari-2013 menjadi Date
	public DateTime parseDateMonth(String date){
		
		String monthString=date.split("-")[0];
		String yearString=date.split("-")[1];
		
		int month=1;
		int year=Integer.parseInt(yearString);
		
		if(monthString.equals("Januari")){
			month=1;
		}else if(monthString.equals("Februari")){
			month=2;			
		}else if(monthString.equals("Maret")){
			month=3;
		}else if(monthString.equals("April")){
			month=4;
		}else if(monthString.equals("Mei")){
			month=5;
		}else if(monthString.equals("Juni")){
			month=6;
		}else if(monthString.equals("Juli")){
			month=7;
		}else if(monthString.equals("Agustus")){
			month=8;
		}else if(monthString.equals("September")){
			month=9;
		}else if(monthString.equals("Oktober")){
			month=10;
		}else if(monthString.equals("November")){
			month=11;
		}else if(monthString.equals("Desember")){
			month=12;
		}
		return new DateTime(year, month, 1, 0, 0);
		
	}
	//testing
	public static void main(String[] args) {
		DateManipulator x=new DateManipulator();
		System.out.println(x.parseDateMonth("Desember-2014").toDate().toString());
	}
	

}
