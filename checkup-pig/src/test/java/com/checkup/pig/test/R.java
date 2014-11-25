/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.pig.test;

/**
 *
 * @author lin
 */
public class R {
    
    public enum Pig{
      
        script1_hadoop,script1_local,script2_hadoop,script2_local,hello;
        
        public String getFile(){
           return ClassLoader.getSystemResource(this.name()+".pig").getFile();
        }
        
    }
    
}
