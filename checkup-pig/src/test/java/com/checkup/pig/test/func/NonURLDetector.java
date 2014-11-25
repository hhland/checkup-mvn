/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.pig.test.func;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pig.FilterFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

/**
 *
 * @author lin
 */
public class NonURLDetector extends FilterFunc{
    private Pattern _urlPattern = Pattern.compile("^[\"]?(http[:|;])|(https[:|;])|(www\\.)");
  
  public Boolean exec(Tuple arg0) throws IOException {
      if (arg0 == null || arg0.size() == 0)
          return false;

    String query; 
    try{
        query = (String)arg0.get(0);
        if(query == null)
            return false;
        query = query.trim();
    }catch(Exception e){
        System.err.println("NonURLDetector: failed to process input; error - " + e.getMessage());
        return false;
    }

    if (query.equals("")) {
      return false;
    }
    Matcher m = _urlPattern.matcher(query);
    if (m.find()) {
      return false;
    }
    return true;
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
