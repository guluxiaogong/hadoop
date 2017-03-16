package com.antin.mr.test.tooracle;

/**
 * Created by Administrator on 2017/3/10.
 */
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.GenericOptionsParser;

public class BulkImportData {

    public static class TokenizerMapper extends
            Mapper<Object, Text, Text, Text> {
        public Text _key = new Text();
        public Text _value = new Text();
        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] splits = value.toString().split(",");
            if(splits.length==3){
                InputSplit inputSplit=(InputSplit)context.getInputSplit();
                String filename=((FileSplit)inputSplit).getPath().getName();
                filename = filename.replace("mv_", "");
                filename = filename.replace(".txt", "");
                _key.set(splits[0]+"_"+filename);
                context.write(_key, value);
            }
        }
    }

    public static class IntSumReducer extends
            TableReducer<Text, Text, ImmutableBytesWritable> {
        public void reduce(Text key, Iterable<Text> values,
                           Context context) throws IOException, InterruptedException {
            Iterator<Text> itr = values.iterator();
            while(itr.hasNext()){
                Text t = itr.next();
                String[] strs = t.toString().split(",");
                if(strs.length!=3)continue;
                Put put = new Put(key.getBytes());
                put.add(Bytes.toBytes("content"), Bytes.toBytes("score"), Bytes.toBytes(strs[1]));
                put.add(Bytes.toBytes("content"), Bytes.toBytes("date"), Bytes.toBytes(strs[2]));
                context.write(new ImmutableBytesWritable(key.getBytes()), put);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String tablename = "ntf_data";
        Configuration conf = HBaseConfiguration.create();
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (admin.tableExists(tablename)) {
            admin.disableTable(tablename);
            admin.deleteTable(tablename);
        }
        HTableDescriptor htd = new HTableDescriptor(tablename);
        HColumnDescriptor hcd = new HColumnDescriptor("content");
        htd.addFamily(hcd);
        admin.createTable(htd);
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 1) {
            System.err
                    .println("Usage: wordcount <in> <out>" + otherArgs.length);
            System.exit(2);
        }
        Job job = new Job(conf, "word count");
        job.setMapperClass(TokenizerMapper.class);
        job.setJarByClass(BulkImportData.class);
        job.setNumReduceTasks(5);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        TableMapReduceUtil.initTableReducerJob(tablename, IntSumReducer.class, job);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
