/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.checkup.lenskit.test

/**
 *
 * @author lin
 */

import org.gmock.GMockTestCase


class LoaderTest extends GMockTestCase {
    
  
  void testLoader(){
    def mockLoader = mock()
    mockLoader.load('key').returns('value')
    play {
      assertEquals "value", mockLoader.load('key')
    }
  }
}  

