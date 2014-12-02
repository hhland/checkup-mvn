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
public class Record4<R0,R1,R2,R3> extends Record3<R0, R1, R2>{

    protected R3 r3;
    
    public Record4(R0 r0, R1 r1, R2 r2,R3 r3) {
        super(r0, r1, r2);
        this.r3=r3;
    }

    public R3 getR3() {
        return r3;
    }

        @Override
    public Vector asVector() {
        Vector vet= super.asVector(); //To change body of generated methods, choose Tools | Templates.
        vet.add(r3);
        return vet;
    }
    
}

