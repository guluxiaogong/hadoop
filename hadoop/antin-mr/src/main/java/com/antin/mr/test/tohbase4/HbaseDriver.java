package com.antin.mr.test.tohbase4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DataDrivenDBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by Administrator on 2017/3/7.
 */
public class HbaseDriver {
    //create 't1','f1'
    // /home/yinjie/input目录下有一个hbasedata.txt文件,内容为
    /*
     *r1:f1:c1:value1
     *r2:f1:c2:value2
     *r3:f1:c3:value3
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        conf.setInt("mapreduce.job.maps", 1);
        conf.set("mapreduce.jdbc.input.orderby", "rownum");

        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");

        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("hdfs://zoe01:9000/hadoop-lib/ojdbc6.jar").toUri());

        job.setJarByClass(HbaseDriver.class);

        DataDrivenDBInputFormat.setInput(job, com.antin.mr.test.tohbase3.SehrXmanEhrModel.class, "select rownum as id,xman_id,event,catalog_code,serial,content,t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from sehr_xman_ehr_A t where $CONDITIONS", "select MIN(t.id), MAX(t.id) from (select rownum as id from sehr_xman_ehr_A) t");

        job.setMapperClass(HBaseMapper.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(KeyValue.class);

        job.setNumReduceTasks(0);
        //job.setReducerClass(HBaseReducer.class);
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(KeyValue.class);

        job.setOutputFormatClass(TableOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path("hdfs://zoe01:9000/sehr_xman_ehr_A"));

        job.waitForCompletion(true);

    }
}