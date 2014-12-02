/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.common.data;

import java.util.Vector;

/**
 *
 * @author lin
 */
public class Record2<R0,R1> implements IRecord{
    protected R0 r0;
    protected R1 r1;

    public Record2(R0 r0,R1 r1){
       this.r0=r0;
       this.r1=r1;
    };
    
    public R0 getR0() {
        return r0;
    }
    
    public R1 getR1() {
        return r1;
    }

    @Override
    public Vector asVector() {
        Vector vet=new Vector();
        vet.add(r0);
        vet.add(r1);
        return vet;
    }
    
    
    
}
