/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.checkup.storm.dsl

trait ShutdownFunc {

  
   // register a shutdown function
  def shutdown(sf: => Unit) = _shutdownFunctions ::= sf _

  // fire all registered shutdown functions
  protected def _cleanup() = _shutdownFunctions.foreach(_())

  // list of registered shutdown functions
  private var _shutdownFunctions: List[() => Unit] = Nil
  
}
