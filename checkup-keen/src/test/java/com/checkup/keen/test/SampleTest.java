/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.keen.test;

import com.checkup.keen.Project;
import io.keen.client.java.JavaKeenClientBuilder;
import io.keen.client.java.KeenClient;
import io.keen.client.java.KeenProject;
import junit.framework.TestCase;
import org.junit.Test;



/**
 *
 * @author smart
 */

public class SampleTest extends TestCase{

    protected KeenClient keenClient;
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp(); //To change body of generated methods, choose Tools | Templates.
        keenClient=new JavaKeenClientBuilder().build();
        KeenClient.initialize(keenClient);
    }

    
    
    
    @Test
    public void hello(){
        //KeenProject myfirstProject=Project.myFirstProject.getProject();
        keenClient.setDefaultProject(Project.myFirstProject.getProject());
        keenClient.addEvent(null, null);
    }
    
}
