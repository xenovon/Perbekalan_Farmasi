package com.binar.generalFunction;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;

public class MapSorting
{
	//Descending
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
    
	//Descending
    public <K, V extends Comparable<? super V>> Map<K, V[]> sortByValueArray( Map<K, V[]> map )
    {
        List<Map.Entry<K, V[]>> list =
            new LinkedList<Map.Entry<K, V[]>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V[]>>()
        {
            public int compare( Map.Entry<K, V[]> o1, Map.Entry<K, V[]> o2 )
            {
                return (o2.getValue()[0]).compareTo( o1.getValue()[0] );
            }
        } );

        Map<K, V[]> result = new LinkedHashMap<K, V[]>();
        for (Map.Entry<K, V[]> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
    
    //order berdasarkan asuransi barang
	//Descending
    public  Map<Goods, Integer> sortByInsurance( Map<Goods, Integer> map )
    {
        List<Map.Entry<Goods, Integer>> list =
            new LinkedList<Map.Entry<Goods, Integer>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<Goods, Integer>>()
        {
            public int compare( Map.Entry<Goods, Integer> o1, Map.Entry<Goods, Integer> o2 )
            {
                return (o2.getKey().getInsurance().getIdInsurance()-o1.getKey().getInsurance().getIdInsurance());
            }
        } );

        Map<Goods, Integer> result = new LinkedHashMap<Goods, Integer>();
        for (Map.Entry<Goods, Integer> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
    
}