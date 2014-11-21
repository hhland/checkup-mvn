/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.spark;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lin
 */
public class TextSearchJUnitTest {

    JavaSparkContext spark;

    public TextSearchJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        spark=new JavaSparkContext(Const.MASTER, TextSearchJUnitTest.class.getName());
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void hello() {
       
        JavaRDD<String> file = spark.textFile(Const.HDFS_IPC_BASE+"/input/test.txt");
        JavaRDD<String> errors = file.filter(new Function<String, Boolean>() {
            public Boolean call(String s) {
                return s.contains("ERROR");
            }
        });
// Count all the errors
        errors.count();
// Count errors mentioning MySQL
        errors.filter(new Function<String, Boolean>() {
            public Boolean call(String s) {
                return s.contains("MySQL");
            }
        }).count();
// Fetch the MySQL errors as an array of strings
        errors.filter(new Function<String, Boolean>() {
            public Boolean call(String s) {
                return s.contains("MySQL");
            }
        }).collect();
    }
}
