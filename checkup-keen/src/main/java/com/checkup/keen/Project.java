/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.keen;

import io.keen.client.java.KeenProject;

/**
 *
 * @author smart
 */
public enum Project {
    
    myFirstProject("54ae852396773d4055967a6a"
            ,"e213e4d189fc7edcd46841b9fb1a83e77aa284b63f55afa1f123304e876bc63bc3ad806ea648b06d01bb051ebea7d8d6edd05ab9931a96dc225d791e4f4a3851038379ffd4b61c17132ec3dc5d34a264c31c317eec66b53924a57a5d8c158f9d1b69b65a21c56a22e17428429649035c"
            ,"fcb5e556789749196a281d9ea5051eb1ee29da5eaec675757678a3cd875c15dee24e6bdd5b031cc2e2954eebc4a20f7462e31df9ad8ac301844fef0cab86cd8cadb05e13ab2a0aa10022f4ff6dd39b0bf9f46ebdb134b61e31b35d8c633b915bf7d4be4a9112ef48a7e8e4eb3fa60c00");
    
    private String id,read_key,write_key;
    
    private KeenProject project;
    
    Project(String id,String read_key,String write_key){
          this.id=id;
          this.read_key=read_key;
          this.write_key=write_key;
          this.project=new KeenProject(id,read_key,write_key);
    }

    public String getId() {
        return id;
    }

    public String getRead_key() {
        return read_key;
    }

    public String getWrite_key() {
        return write_key;
    }

    public KeenProject getProject() {
        return project;
    }
    
}
