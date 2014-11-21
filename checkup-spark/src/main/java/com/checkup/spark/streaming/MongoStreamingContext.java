/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.spark.streaming;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.streaming.Checkpoint;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.StreamingContext;
import scala.collection.Map;
import scala.collection.Seq;

/**
 *
 * @author lin
 */
public class MongoStreamingContext extends StreamingContext{

    public MongoStreamingContext(SparkContext sc, Checkpoint c, Duration drtn) {
        super(sc, c, drtn);
    }

    public MongoStreamingContext(SparkContext sc, Duration drtn) {
        super(sc, drtn);
    }

    public MongoStreamingContext(SparkConf sc, Duration drtn) {
        super(sc, drtn);
    }

    public MongoStreamingContext(String string, String string1, Duration drtn, String string2, Seq<String> seq, Map<String, String> map) {
        super(string, string1, drtn, string2, seq, map);
    }

    public MongoStreamingContext(String string, Configuration c) {
        super(string, c);
    }

    public MongoStreamingContext(String string) {
        super(string);
    }
    
}
