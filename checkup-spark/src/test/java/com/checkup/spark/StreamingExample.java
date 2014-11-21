/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.spark;

import java.util.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import scala.Tuple2;

/**
 *
 * @author lin
 */
public class StreamingExample {

    JavaSparkContext context;

    public StreamingExample() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        SparkConf conf = new SparkConf(true);
        conf.setMaster(Const.MASTER);
        context = new JavaSparkContext(conf);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {

        JavaStreamingContext streamingContext = new JavaStreamingContext(context, new Duration(1000));
        JavaReceiverInputDStream<String> lines = streamingContext.socketTextStream("localhost", 9999);

        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

            @Override
            public Iterable<String> call(String t) throws Exception {
                return Arrays.asList(t.split(" "));
            }
        });

        JavaPairDStream<String, Integer> pairs = words.mapToPair(
                new PairFunction<String, String, Integer>() {
                    @Override
                    public Tuple2<String, Integer> call(String s) throws Exception {
                        return new Tuple2<String, Integer>(s, 1);
                    }
       });
        
        JavaPairDStream<String,Integer> wordCounts=pairs.reduceByKey(
               new Function2<Integer, Integer, Integer>() {

            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
               return v1+v2;
            }
        }
        );
        
        wordCounts.print();
      
        streamingContext.start();
    }
}
