/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.pig.test.func;

import com.checkup.pig.test.TutorialUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.pig.EvalFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;
import org.apache.pig.data.DefaultBagFactory;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

/**
 * This function divides a search query string into wrods and extracts
 * n-grams with up to _ngramSizeLimit length.
 * Example 1: if query = "a real nice query" and _ngramSizeLimit = 2,
 * the query is split into: a, real, nice, query, a real, real nice, nice query
 * Example 2: if record = (u1, h1, pig hadoop) and _ngramSizeLimit = 2,
 * the record is split into: (u1, h1, pig), (u1, h1, hadoop), (u1, h1, pig hadoop)
 */
public class NGramGenerator extends EvalFunc<DataBag> {

    private static final int _ngramSizeLimit = 2;
  
    public DataBag exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0)
            return null;
        try{
            DataBag output = DefaultBagFactory.getInstance().newDefaultBag();
            String query = (String)input.get(0);
            String[] words = TutorialUtil.splitToWords(query);
            Set<String> ngrams = new HashSet<String>();
            TutorialUtil.makeNGram(words, ngrams, _ngramSizeLimit);
            for (String ngram : ngrams) {
                Tuple t = TupleFactory.getInstance().newTuple(1);
                t.set(0, ngram);
                output.add(t);
            }
            return output;
        }catch(Exception e){
            System.err.println("NGramGenerator: failed to process input; error - " + e.getMessage());
            return null;
        }
    }

    @Override
    /**
     * This method gives a name to the column.
     * @param input - schema of the input data
     * @return schema of the input data
     */
    public Schema outputSchema(Schema input) {
         Schema bagSchema = new Schema();
         bagSchema.add(new Schema.FieldSchema("ngram", DataType.CHARARRAY));
         try{
            return new Schema(new Schema.FieldSchema(getSchemaName(this.getClass().getName().toLowerCase(), input), 
                                                    bagSchema, DataType.BAG));
         }catch (FrontendException e){
            return null;
         }
    }

    /* (non-Javadoc)
     * @see org.apache.pig.EvalFunc#getArgToFuncMapping()
     * This is needed to make sure that both bytearrays and chararrays can be passed as arguments
     */
    @Override
    public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
        List<FuncSpec> funcList = new ArrayList<FuncSpec>();
        funcList.add(new FuncSpec(this.getClass().getName(), new Schema(new Schema.FieldSchema(null, DataType.CHARARRAY))));

        return funcList;
    }

}
