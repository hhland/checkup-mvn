/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.checkup.odps.example;

import com.aliyun.odps.counter.Counter;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.TableInfo;
import com.aliyun.odps.mapred.JobClient;
import com.aliyun.odps.mapred.MapperBase;
import com.aliyun.odps.mapred.ReducerBase;
import com.aliyun.odps.mapred.conf.JobConf;
import com.aliyun.odps.mapred.utils.InputUtils;
import com.aliyun.odps.mapred.utils.OutputUtils;
import com.aliyun.odps.mapred.utils.SchemaUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author lin
 */
public class Join {
    
    public static class JoinMapper extends MapperBase {
    private Record mapkey;
    private Record mapvalue;

    @Override
    public void setup(TaskContext context) throws IOException {
      mapkey = context.createMapOutputKeyRecord();
      mapvalue = context.createMapOutputValueRecord();
    }

    @Override
    public void map(long key, Record record, TaskContext context)
        throws IOException {
      /*
       根据value的值判断这个record来自那张表，此处属于用户代码逻辑。
       如果无法通过表中value字段的值来判断Record属于哪张表，可以考虑向输入表中添加一个字段。
       通过value值产生的tag，将在Reduce中执行连接操作时用到。
      */
      long tag = 1;
      String val = record.get(1).toString();
      if (val.startsWith("valb_")) {
        tag = 2;
      }

      mapkey.set(0, Long.parseLong(record.get(0).toString()));
      mapkey.set(1, tag);

      mapvalue.set(0, tag);
      for (int i = 1; i < record.getColumnCount(); i++) {
        mapvalue.set(i, record.get(i));
      }

      context.write(mapkey, mapvalue);
    }

  }

  public static class JoinReducer extends ReducerBase {

    private Record result = null;

    @Override
    public void setup(TaskContext context) throws IOException {
      result = context.createOutputRecord();
    }

    @Override
    public void reduce(Record key, Iterator<Record> values, TaskContext context)
        throws IOException {
      long k = (Long) key.get(0);
      List<Object[]> list1 = new ArrayList<Object[]>();

      Counter cnt = context.getCounter("MyCounters", "reduce_outputs");
      cnt.increment(1);
      while (values.hasNext()) {
        Record value = values.next();
        long tag = (Long) value.get(0);
        if (tag == 1) {
          //如果数据来自第一张表，将数据缓存到list中。
          //由于数据是按照Key, tag来进行排序的，因此tag==1的Value会被排到前面。
          //在实际计算中，建议用户慎重使用这种方式，如果某个key的value过多，
          //将会导致Reduce所需内存增加，超过JobConf::setMemoryForReduceTask的设置时，
          //Reduce有可能会因为超内存而被系统杀掉。
          list1.add(value.toArray().clone());
        } else {
          //如果数据来自第二张表，按照Key, tag来进行排序，
          //此时第一张表的所有value都已经被存放在list1中。
          //对于第一张表的所有value
          for (Object r1: list1) {
            int index = 0;
            //首先设置Key
            result.set(index++, k);
            Object[] s_arr = (Object[])r1;
            result.set(index++, s_arr[1].toString());
            result.set(index++, value.get(1).toString());
            context.write(result);
            }

          }

        }
      }

    }


  public static void main(String[] args) throws Exception {
    if (args.length != 3) {
      System.err.println("Usage: Join <input table1> <input table2> <out>");
      System.exit(2);
    }
    JobConf job = new JobConf();

    job.setMapperClass(JoinMapper.class);
    job.setReducerClass(JoinReducer.class);

    job.setMapOutputKeySchema(SchemaUtils.fromString("key:bigint,tag:bigint"));
    job.setMapOutputValueSchema(SchemaUtils
        .fromString("tagx:bigint,value:string"));

    job.setPartitionColumns(new String[] { "key" });
    //使用key，tag排序。只有key有序后，在Reduce端才能做join
    //tag用来区分当前Record来自那张表。
    job.setOutputKeySortColumns(new String[] { "key", "tag" });
    job.setOutputGroupingColumns(new String[] { "key" });

    //由于Reduce端使用list缓存数据，建议加大Reduce Worker内存
    job.setMemoryForReduceTask(4096);
    job.setInt("table.counter", 0);

    InputUtils.addTable(TableInfo.builder().tableName(args[0]).build(), job);
    InputUtils.addTable(TableInfo.builder().tableName(args[1]).build(), job);
    OutputUtils.addTable(TableInfo.builder().tableName(args[2]).build(), job);

    JobClient.runJob(job);
  }
}
