/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.pig;

/**
 *
 * @author lin
 */
public class Utils {
    
    public  static int levenshteinDistance(String str0,String str1){
       int size0=str0.length(),size1=str1.length();
        int distance=0;
        int[][] iarr0= new int[size0+1][];
        
        for(int i=0;i<size0+1;i++){
           iarr0[i]=new int[size1+1];
           iarr0[i][0]=i;
        }
        
        for(int i=0;i<size1+1;i++){
           iarr0[0][i]=i;
        }
        
        for(int i=1;i<size0+1;i++){
        
             for(int j=1;j<size1+1;j++){
                int d=str0.charAt(i-1)==str1.charAt(j-1)?0:1
                  ,temp= Math.min(iarr0[i-1][j]+1 , iarr0[i][j-1]+1);
                
                iarr0[i][j]=Math.min(temp, iarr0[i-1][j-1]+d);
             }
            
        }
        
        distance=iarr0[size0][size1];
        
        
        
        return distance;
    }
    
}
