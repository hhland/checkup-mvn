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
public class Record5<R0,R1,R2,R3,R4> extends Record4<R0, R1, R2, R3>{

    protected R4 r4;
    
    public Record5(R0 r0, R1 r1, R2 r2, R3 r3,R4 r4) {
        super(r0, r1, r2, r3);
        this.r4=r4;
    }

    public R4 getR4() {
        return r4;
    }
    
        @Override
    public Vector asVector() {
        Vector vet= super.asVector(); //To change body of generated methods, choose Tools | Templates.
        vet.add(r4);
        return vet;
    }
    
}
