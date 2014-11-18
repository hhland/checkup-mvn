/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.pig.udf;

import com.checkup.pig.Utils;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

/**
 *
 * @author lin
 */
public class LevenshteinDistance extends EvalFunc<Integer>{

    @Override
    public Integer exec(Tuple tuple) throws IOException {
        String str0=tuple.get(0).toString(),str1=tuple.get(0).toString();
      
        int distance=Utils.levenshteinDistance(str0, str1);
          
        return distance;
    }
    
}
