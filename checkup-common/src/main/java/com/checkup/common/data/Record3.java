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
public class Record3<R0,R1,R2> extends Record2<R0, R1>{

    protected R2 r2;
    
    public Record3(R0 r0, R1 r1,R2 r2) {
        super(r0, r1);
        this.r2=r2;
    }

    public R2 getR2() {
        return r2;
    }

    @Override
    public Vector asVector() {
        Vector vet= super.asVector(); //To change body of generated methods, choose Tools | Templates.
        vet.add(r2);
        return vet;
    }
    
    
    
}
