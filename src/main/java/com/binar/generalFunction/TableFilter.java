package com.binar.generalFunction;

import java.util.Collection;
import java.util.regex.PatternSyntaxException;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class TableFilter implements Container.Filter {
	 protected String regex;

	 public TableFilter(String regex) {
	        this.regex      = regex.toLowerCase();
	 }
	public void updateData(String regex){
		this.regex=regex.toLowerCase();
	}
    public boolean passesFilter(Object itemId, Item item)
            throws UnsupportedOperationException {
    	boolean result=false;
        // Mendapatkan kumpulan properti dari tabel;
        Collection<String> properties = (Collection<String>) item.getItemPropertyIds();
        System.out.println(properties.size());
        for(String idProp:properties){
        	System.out.println("Perulangan" +idProp);
            // cek validitas, jika tidak valid, maka lanjut ke looping selanjutnya
        	Property<?> p=item.getItemProperty(idProp);
            if (p == null || !p.getType().equals(String.class))
                continue;
            String value = (String) p.getValue();
            try {
				value=value.toLowerCase();
			} catch (Exception e1) {
				continue;
			}
            System.out.println("Value ="+value);
            System.out.println("Regex ="+regex);
            // Pass all if regex not given
            if (regex.isEmpty()) {
            	System.out.println("Regex empty, return true");
                return true;
            }
            
            // logika filter, dengan regex
            try {
                result = value.contains(regex);
            	System.out.println("logika filter, return "+result);
            	if(result==true){
            		return true;
            	}
                
            } catch (PatternSyntaxException e) {
            	System.out.println("PatternSyntaxException, return true");

            	return true;
            }        	
        }
    	System.out.println("Nothing happened, return false");

        return result;
        
    }

    /** Tells if this filter works on the given property. */
    @Override
    public boolean appliesToProperty(Object propertyId) {
        return true;
    }


}
