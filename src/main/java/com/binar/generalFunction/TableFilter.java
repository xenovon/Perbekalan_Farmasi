package com.binar.generalFunction;

import java.util.Collection;
import java.util.regex.PatternSyntaxException;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Label;
/*
 * Kelas untuk memfilter tabel
 */
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
            // Pass all if regex not given
            if (regex.isEmpty()) {
                return true;
            }
            
            // logika filter, dengan regex
            try {
                result = value.contains(regex);
            	if(result==true){
            		return true;
            	}
            } catch (PatternSyntaxException e) {
            	return true;
            }        	
        }
    	System.out.println("Nothing happened, return false");
        return result;
    }

    @Override
    public boolean appliesToProperty(Object propertyId) {
        return true;
    }

    public class TableFilterLabel implements Container.Filter{
   	 protected String regex;

   	public TableFilterLabel(String regex) {
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
           	// cek validitas, jika tidak valid, maka lanjut ke looping selanjutnya
           	Property<?> p=item.getItemProperty(idProp);
               if (p == null || !p.getType().equals(Label.class))
                   continue;
               Label label=(Label)p.getValue();
               String value = label.getValue();
               try {
   				value=value.toLowerCase();
   			} catch (Exception e1) {
   				continue;
   			}
               // Pass all if regex not given
               if (regex.isEmpty()) {
                   return true;
               }
               
               // logika filter, dengan regex
               try {
                   result = value.contains(regex);
               	if(result==true){
               		return true;
               	}
               } catch (PatternSyntaxException e) {
               	return true;
               }        	
           }
       	System.out.println("Nothing happened, return false");
           return result;
       }

       @Override
       public boolean appliesToProperty(Object propertyId) {
           return true;
       }
    }

}
