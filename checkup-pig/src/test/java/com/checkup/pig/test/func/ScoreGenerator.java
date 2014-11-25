/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.pig.test.func;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;
import org.apache.pig.data.DefaultBagFactory;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

/**
 * For each n-gram, we have a set of (hour, count) pairs.
 * 
 * This function reads the set and retains those hours with above
 * above mean count, and calculates the score of each retained hour as the 
 * multiplier of the count of the hour over the standard deviation.
 * 
 * A score greater than 1.0 indicates the frequency of this n-gram 
 * in this particular hour is at least one standard deviation away 
 * from the average frequency among all hours
 */

public class ScoreGenerator extends EvalFunc<DataBag> {

    private static double computeMean(List<Long> counts) {
        int numCounts = counts.size();
    
        // compute mean
        double mean = 0.0;
        for (Long count : counts) {
            mean += ((double) count) / ((double) numCounts);
        }
    
        return mean;
    }
  
    private static double computeSD(List<Long> counts, double mean) {
        int numCounts = counts.size();
    
        // compute deviation
        double deviation = 0.0;
        for (Long count : counts) {
            double d = ((double) count) - mean;
            deviation += d * d / ((double) numCounts);
        }
    
        return Math.sqrt(deviation);
    }

    public DataBag exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;
        try{
            DataBag output = DefaultBagFactory.getInstance().newDefaultBag();
            DataBag in = (DataBag)input.get(0);
    
            Map<String, Long> pairs = new HashMap<String, Long>();
            List<Long> counts = new ArrayList<Long> ();

            Iterator<Tuple> it = in.iterator();
            while (it.hasNext()) {
                Tuple t = it.next();
                String hour = (String)t.get(1);
                Long count = (Long)t.get(2);
                pairs.put(hour, count);
                counts.add(count);
            }
    
            double mean = computeMean(counts);
            double standardDeviation = computeSD(counts, mean);

            Iterator<String> it2 = pairs.keySet().iterator();
            while (it2.hasNext()) {
                String hour = it2.next();
                Long count = pairs.get(hour);
                if ( count > mean ) {
                    Tuple t = TupleFactory.getInstance().newTuple(4);
                    t.set(0, hour);
                    t.set(1, ((double) count - mean) / standardDeviation ); // the score
                    t.set(2, count);
                    t.set(3, mean);
                    output.add(t);
                }
            }
            return output;
        }catch (Exception e){
            System.err.println("ScoreGenerator: failed to process input; error - " + e.getMessage());
            return null;
        }
    }

    @Override
    /**
     * This method gives a name to the column.
     * @param input - schema of the input data
     * @return schema of the output data
     */
     public Schema outputSchema(Schema input) {
         Schema bagSchema = new Schema();
         bagSchema.add(new Schema.FieldSchema("hour", DataType.CHARARRAY));
         bagSchema.add(new Schema.FieldSchema("score", DataType.DOUBLE));
         bagSchema.add(new Schema.FieldSchema("count", DataType.LONG));
         bagSchema.add(new Schema.FieldSchema("mean", DataType.DOUBLE));
         //TODO
         //Here the schema of the bag is the schema of the tuple inside the bag
         //We need to change this so that the bag has the tuple and the tuple has the schema
         try{
            return new Schema(new Schema.FieldSchema(getSchemaName(this.getClass().getName().toLowerCase(), input), bagSchema, DataType.BAG));
         }catch (FrontendException e){
             return null;
         }
     }

}