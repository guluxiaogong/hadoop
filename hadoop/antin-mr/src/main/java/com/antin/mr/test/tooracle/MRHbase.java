package com.antin.mr.test.tooracle;

import java.io.IOException;
import java.util.NavigableMap;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.Logger;

/**
 * 从hbase中取数据到hdfs上
 * Created by Administrator on 2017/3/10.
 */
public class MRHbase {
    private static Logger log = Logger.getLogger(MRHbase.class);
    public static String tableName = "sehr_xman_ehr_0";
    public static String family = "info";
    public static String col = "title";

    public static class HMap extends TableMapper<Text, Text> {
        @Override
        protected void map(ImmutableBytesWritable key, Result value,
                           Context context) throws IOException, InterruptedException {
            //	KeyValue kv = value.getColumnLatest(family.getBytes(), col.getBytes());
//			context.write(new Text(Bytes.toString(kv.getKey())),
//					new Text(Bytes.toString(kv.getValue())));
            NavigableMap<byte[], byte[]> navigableMap = value.getFamilyMap(family.getBytes());
            Set<byte[]> set = navigableMap.keySet();
            StringBuffer sb = new StringBuffer();
            for (byte[] bytes : set) {
                byte[] qualifierValue = navigableMap.get(bytes);
                sb.append(Bytes.toString(qualifierValue) + System.lineSeparator());
            }
          /*  byte[] v = value.getValue(family.getBytes(), col.getBytes());*/
            byte[] r = value.getRow();
            context.write(new Text(Bytes.toString(r)), new Text(sb.toString()));
        }
    }

//    public static class HReduce extends Reducer<Text, Text, Text, Text> {
//        @Override
//        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//            for (Text value : values)
//                context.write(key, value);
//        }
//    }

    public static void main(String[] args) {
        Configuration conf = HBaseConfiguration.create();
        try {
            Job job = Job.getInstance(conf);
            job.setJarByClass(MRHbase.class);

            Get get = new Get(Bytes.toBytes("0cea6227-47c0-4ed8-a6f8-3081fdb0d396da51b951-0601-4d6d-84fc-7b755a1fccb2"));
            Scan scan = new Scan(get);
            //scan.addColumn(family.getBytes(), col.getBytes());
            TableMapReduceUtil.initTableMapperJob(tableName, scan, HMap.class, Text.class, Text.class, job);

            job.setOutputFormatClass(TextOutputFormat.class);
            FileOutputFormat.setOutputPath(job, new Path("/sehr_xman_ehr_0"));
            job.waitForCompletion(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
