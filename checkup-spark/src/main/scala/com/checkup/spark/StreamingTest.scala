/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.checkup.spark
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import com.checkup.spark._;

object StreamingExample {

  val conf=new SparkConf().setMaster(Const.MASTER);
  val ssc=new StreamingContext(conf,Seconds(1));
  
  val lines=ssc.socketTextStream("localhost", 9999);
  
  val words=lines.flatMap(_.split(" "));
  
  val pairs=words.map(word=>(word,1));
  
  val wordCount=pairs.reduceByKey(_+_);
  
  wordCount.print();
  
  ssc.start();
  ssc.awaitTermination();
  
}
