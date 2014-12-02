/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.common;

import com.checkup.common.data.Record2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.Query;

/**
 *
 * @author lin
 */
public class MR {
    
    
     public static <T> Map<T,Long> count(List<T> values){
         Map<T,Long> _map=new HashMap();
          return values.stream()
                .map(word->{_map.clear();_map.put(word, 1l);return _map; })
                .reduce(new HashMap<T,Long>(),(map0,temp)->{
                      T key= (T)temp.keySet().iterator().next();
                      map0.put(key, map0.getOrDefault(key, 0l)+1);
                      return map0;
           });
    }
    
     public static <T,N extends Number> Map<T,Double> sum(List<Record2<T,N>> records){
            Map<T,Double> _map=new HashMap<>();
            return records.stream()
                    .map(record->{_map.clear();_map.put(record.getR0(), record.getR1().doubleValue());return _map; })
                    .reduce(new HashMap<T, Double>(), (map0,temp)->{
                        T key= (T)temp.keySet().iterator().next();
                        map0.put(key, map0.getOrDefault(key,0d)+ temp.get(key));
                        return map0;
                    })
                    ;
     }
     
      public static <T,N extends Comparable> Map<T,N> max(List<Record2<T,N>> records){
            Map<T,N> _map=new HashMap<>();
            return records.stream()
                    .map(record->{_map.clear();_map.put(record.getR0(), record.getR1());return _map; })
                    .reduce(new HashMap<T, N>(), (map0,temp)->{
                        T key= (T)temp.keySet().iterator().next();
                        N tempval=temp.get(key),mapval=map0.getOrDefault(key, null);
                        if(mapval==null){
                            map0.put(key,tempval );
                        }else if(tempval.compareTo(mapval)>0 ){
                            map0.put(key,tempval );
                        }
                        
                        return map0;
                    })
                    ;
     }
      
           public static <T,N extends Comparable> Map<T,N> min(List<Record2<T,N>> records){
            Map<T,N> _map=new HashMap<>();
            return records.stream()
                    .map(record->{_map.clear();_map.put(record.getR0(), record.getR1());return _map; })
                    .reduce(new HashMap<T, N>(), (map0,temp)->{
                        T key= (T)temp.keySet().iterator().next();
                        N tempval=temp.get(key),mapval=map0.getOrDefault(key, null);
                        if(mapval==null){
                            map0.put(key,tempval );
                        }else if(tempval.compareTo(mapval)<0 ){
                            map0.put(key,tempval );
                        }
                        
                        return map0;
                    })
                    ;
     }
     
}
