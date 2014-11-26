/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.storm.test;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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

public class LearningStormTest  implements Serializable{

    public LearningStormTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
// create an instance of TopologyBuilder class
        TopologyBuilder builder = new TopologyBuilder();
        // set the spout class
        builder.setSpout("LearningStormSpout",
                new LearningStormSpout(), 2);
        // set the bolt class
        builder.setBolt("LearningStormBolt",
                new LearningStormBolt(), 4).shuffleGrouping("LearningStormSpout");
        Config conf = new Config();
        conf.setDebug(true);
 // create an instance of LocalCluster class for
        // executing topology in local mode.
        LocalCluster cluster = new LocalCluster();
        // LearningStormTopolgy is the name of submitted topology.
        cluster.submitTopology("LearningStormToplogy", conf,
                builder.createTopology());
        try {
            Thread.sleep(10000);
        } catch (Exception exception) {
            System.out.println("Thread interrupted exception : "
                    + exception);
        }
        // kill the LearningStormTopology
        cluster.killTopology("LearningStormToplogy");
        // shutdown the storm test cluster
        cluster.shutdown();
    }
    static final Map<Integer, String> map = new HashMap<Integer, String>();

    static {
        map.put(0, "google");
        map.put(1, "facebook");
        map.put(2, "twitter");
        map.put(3, "youtube");
        map.put(4, "linkedin");
    }

    public class LearningStormSpout extends BaseRichSpout {

        private static final long serialVersionUID = 1L;
        private SpoutOutputCollector spoutOutputCollector;

        public void open(Map conf, TopologyContext context,
                SpoutOutputCollector spoutOutputCollector) {
            // Open the spout
            this.spoutOutputCollector = spoutOutputCollector;
        }

        public void nextTuple() {
            // Storm cluster repeatedly calls this method to emit  a continuous // stream of tuples.
            final Random rand = new Random();
            // generate the random number from 0 to 4.
            int randomNumber = rand.nextInt(5);
            spoutOutputCollector.emit(new Values(map.get(randomNumber)));
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            // emit the tuple with field "site"
            declarer.declare(new Fields("site"));
        }
    }

    public class LearningStormBolt extends BaseBasicBolt {

        private static final long serialVersionUID = 1L;

        public void execute(Tuple input, BasicOutputCollector collector) {
            // fetched the field "site" from input tuple.
            String test = input.getStringByField("site");
            // print the value of field "site" on console.
            System.out.println("Name of input site is : " + test);
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
        }
    }
}
