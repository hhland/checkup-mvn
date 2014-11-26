/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.pig.test;

import java.io.IOException;
import org.apache.pig.pigunit.PigTest;
import org.apache.pig.tools.parameters.ParseException;
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
public class TutorialTest {
    
    protected PigTest pigTest;
    
    public TutorialTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        //System.getProperties().setProperty("pigunit.exectype.cluster", "true");
       
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     //@Test
     public void hello() throws IOException, ParseException {
          String[] args = {
        "n=2",
        };
         String[] input = {
        "yahoo",
        "yahoo",
        "yahoo",
        "twitter",
        "facebook",
        "facebook",
        "linkedin",
    };
 
    String[] output = {
        "(yahoo,3)",
        "(facebook,2)",
    };
         pigTest=new PigTest(R.Pig.hello.getFile(),args);
        
        pigTest.assertOutput("data", input, "queries_limit", output);
     }
}
