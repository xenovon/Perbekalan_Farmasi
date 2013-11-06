package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;

public class InputFormModel {

	public List<String> validate(FormLayout layout){
		//inisiasi variabel
		
		
		return null;
	}
	
	public boolean insertData(FormLayout layout){
		return true;
	}
	//Untuk mengubah  format January-2013 menjadi Date
	private DateTime parseDate(String date){
		
		String monthString=date.split("-")[0];
		String yearString=date.split("-")[1];
		
		int month=0;
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
	
	
	
}
