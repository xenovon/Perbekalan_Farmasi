package com.binar.generalFunction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ListFactory {

	public List<String> createYearList(int yearSpan){
		List<String> list=new ArrayList();
		Calendar calendar=Calendar.getInstance();
		int now=calendar.get(Calendar.YEAR);
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
}
