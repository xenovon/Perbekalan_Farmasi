package com.binar.generalFunction;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class TextManipulator {
    public static NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
    public static DecimalFormat decimalFormat= (DecimalFormat)numberFormat;
    
    
    public static String intToRupiah(int input)
    {
            decimalFormat.applyPattern("###,###.###");
            return "Rp "+decimalFormat.format((long)input)+",00";
    }
    
    public static String doubleToRupiah(double input)
    {
            decimalFormat.applyPattern("###,###.###");
            return "Rp "+decimalFormat.format((long)input)+",00";
    }
    public static String intToAngka(int input){
            decimalFormat.applyPattern("###,###.###");
            return decimalFormat.format((long)input);

    }

}
