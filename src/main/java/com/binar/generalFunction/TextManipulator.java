package com.binar.generalFunction;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class TextManipulator {
    public  NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
    public  DecimalFormat decimalFormat= (DecimalFormat)numberFormat;
    
    
    public  String intToRupiah(int input)
    {
            decimalFormat.applyPattern("###,###.###");
            return "Rp "+decimalFormat.format((long)input)+",00";
    }
    
    public  String doubleToRupiah(double input)
    {
            decimalFormat.applyPattern("###,###.###");
            return "Rp "+decimalFormat.format((long)input)+",00";
    }
    public  String intToAngka(int input){
            decimalFormat.applyPattern("###,###.###");
            return decimalFormat.format((long)input);

    }
    public String doubleToAngka(double input){
    	decimalFormat.applyPattern("###.##");
    	
    	return decimalFormat.format(input);
    }

    public static void main(String[] args) {
		TextManipulator text=new TextManipulator();
		
		System.out.println(text.doubleToAngka(2143242.310023423));
	}
}
