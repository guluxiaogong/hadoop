package com.antin.mr.test.tableOutputFormat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapred.TableOutputFormat;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

/**
 * Created by Administrator on 2017/3/7.
 */
public class HbaseDriver {
    //create 't1','f1'
    // /home/yinjie/input目录下有一个hbasedata.txt文件,内容为
    /*r1:f1:c1:value1
    r2:f1:c2:value2
    r3:f1:c3:value3     */
    public static void main(String[] args) {
        JobConf conf = new JobConf(HbaseDriver.class);
        conf.setMapperClass(HBaseMapper.class);
        conf.setReducerClass(HBaseReducer.class);

        conf.setMapOutputKeyClass(LongWritable.class);
        conf.setMapOutputValueClass(Text.class);

        conf.setOutputKeyClass(ImmutableBytesWritable.class);
        conf.setOutputValueClass(Put.class);

        conf.setOutputFormat(TableOutputFormat.class);

        FileInputFormat.setInputPaths(conf, "/home/yinjie/input");
        FileOutputFormat.setOutputPath(conf, new Path("/home/yinjie/output"));

        conf.set(TableOutputFormat.OUTPUT_TABLE, "t1");
        conf.set("hbase.zookeeper.quorum", "localhost");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            JobClient.runJob(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}