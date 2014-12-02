/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.hive.test;

import org.apache.hive.testutils.junit.runners.ConcurrentTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

/**
 *
 * @author lin
 */
public class UDFTest {
    
    ConcurrentTestRunner runner;
    
    public UDFTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws InitializationError {
         runner=new ConcurrentTestRunner(UDFTest.class);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
    public void hello() throws InitializationError {
         
         runner.run(new RunNotifier());
    }
}
