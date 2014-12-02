/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.common.test;

import com.checkup.common.MR;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lin
 */
public class MRTest {
    
    public MRTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
 
     @Test
     public void mr() {
         String article=" * To change this license header, choose License Headers in Project Properties.\n" +
" * To change this template file, choose Tools | Templates\n" +
" * and open the template in the editor.";
         List<String> words=Arrays.asList(article.split(" "));
         
         int count= words.stream()
                 .map(d->1)
                 .reduce(0, (c0,r0)->{return c0+r0;});
         
         Map<String,Long> wordcount = MR.count(words);
         
         
         
         for(Iterator<String> it= wordcount.keySet().iterator();it.hasNext();){
             String key=it.next();
             Long val=wordcount.get(key);
             System.err.println(MessageFormat.format("{0}:{1}", key,val));
         }
         
         System.out.println(count);
     }
     }

