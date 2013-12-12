package com.binar.generalFunction;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

/*
 * 
 * Kelas untuk memanipulasi tanggal
 */
public class DateManipulator {
	
	SimpleDateFormat format;
	//mengubah date menjadi teks (November 2013)
	public String dateToText(Date date){
		if(format==null){
			format=new SimpleDateFormat("MMMMM");			
		}else{
			format.applyPattern("MMMMM");
		}
		String month=format.format(date);
		String monthIndo="";
		if(month.equals("January")){
			monthIndo="Januari";
		}else if(month.equals("February")){
			monthIndo="Februari";
		}else if(month.equals("March")){
			monthIndo="Maret";
		}else if(month.equals("June")){
			monthIndo="Juni";
		}else if(month.equals("July")){
			monthIndo="Juli";
		}else if(month.equals("August")){
			monthIndo="Agustus";
		}else if(month.equals("October")){
			monthIndo="Oktober";
		}else if(month.equals("December")){
			monthIndo="Desember";
		}
		System.out.println(monthIndo);
		if(monthIndo.equals("")){
			monthIndo=month;
		}
		format.applyPattern("yyyy");
		String year=format.format(date);
		return monthIndo+" "+year;
	}
	//full format dengan tanggal, 12 Oktober 2010
	public String dateToText(Date date, boolean fullFormat){
		if(fullFormat){
			if(format==null){
				format=new SimpleDateFormat("dd");
			}else{
				format.applyPattern("dd");
			}
			
			String returnValue=format.format(date);
			return returnValue+" "+dateToText(date);
		}else{
			return dateToText(date);
		}
	}
	
	
	//Untuk mengubah  format Januari-2013 menjadi Date
	public DateTime parseDateMonth(String date){
		
		try {
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
			}else{
				throw  new NumberFormatException();
			}
			return new DateTime(year, month, 1, 0, 0);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	//testing
	public static void main(String[] args) {
		DateManipulator x=new DateManipulator();
		System.out.println(x.parseDateMonth("Desember-2014").toDate().toString());
		System.out.println(x.dateToText(new Date(), true));
	}
	

}
