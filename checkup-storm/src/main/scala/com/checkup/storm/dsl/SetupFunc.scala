/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.checkup.storm.dsl

trait SetupFunc {

    def _setup()= setupFuncs.foreach(_());
    
    def set(func: => Unit)={ setupFuncs ::= func _ }
    
    private var setupFuncs:List[()=>Unit]=Nil
}
