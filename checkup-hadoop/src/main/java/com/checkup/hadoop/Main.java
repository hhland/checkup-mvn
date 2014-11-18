/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.hadoop;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author lin
 */
public class Main {
    
    
    
    public static void main(String[] args) {
    
        AbstractApplicationContext ctx=new ClassPathXmlApplicationContext("context.xml");
        ctx.registerShutdownHook();
    }
}
