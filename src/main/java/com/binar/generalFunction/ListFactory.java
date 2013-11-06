package com.binar.generalFunction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ListFactory {

	public List<String> createYearList(int yearSpan){
		List<String> list=new ArrayList();
		int now = getCurrentYear();
		int currentYear=now;
		while(now-currentYear!=yearSpan){
			list.add(Integer.toString(currentYear));
			currentYear--;
		}
		return list;
		
	}
	public List<String> createMonthList(){
		List<String> list=new ArrayList<String>(){
			{
				add("Januari");
				add("Februari");
				add("Maret");
				add("April");
				add("Mei");
				add("Juni");
				add("Juli");
				add("Agustus");
				add("September");
				add("Oktober");
				add("November");
				add("Desember");
			}
		};
		
		return list;
	}
	/* untuk membuat daftar bulan dari bulan sekarang, hingga monthSpan kedepan */
	public List<String> createMonthListFromNow(int monthSpan){
		List<String> list=new ArrayList();
		Calendar calendar=Calendar.getInstance();
		int now=calendar.get(Calendar.MONTH);
		
		//inisiasi
		int currentMonth=now;
		int yearPassed=0;
		int monthCount=0;
		
		while(monthCount<monthSpan){
			String input;
			switch(currentMonth){
				case 0 :input="Januari";break;
				case 1:input="Februari";break;
				case 2:input="Maret";break;
				case 3:input="April";break;
				case 4:input="Mei";break;
				case 5:input="Juni";break;
				case 6:input="Juli";break;
				case 7:input="Agustus";break;
				case 8:input="September";break;
				case 9:input="Oktober";break;
				case 10:input="November";break;
				case 11:input="Desember";break;
				default:input="Januari";break;
			}
			input=input+"-"+(getCurrentYear()+yearPassed);
			list.add(input);
			if(currentMonth==11){
				currentMonth=0;
				yearPassed++;
			}else{
				currentMonth++;
			}
			
			monthCount++;
		}	
		
		return list;
	}
	
	private int getCurrentYear(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
}
