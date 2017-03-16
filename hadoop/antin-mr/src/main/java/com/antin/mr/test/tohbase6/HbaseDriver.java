package com.antin.mr.test.tohbase6;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DataDrivenDBInputFormat;

/**
 * Created by Administrator on 2017/3/7.
 * 表中有number类型的id，将表中数据导入hbase
 */
public class HbaseDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInt("mapreduce.job.maps", 10);
        conf.set("mapreduce.jdbc.input.orderby", "id");
        conf.set(TableOutputFormat.OUTPUT_TABLE, "sehr_xman_ehr_A_test");

        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");

        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("hdfs://zoe01:9000/hadoop-lib/ojdbc6.jar").toUri());

        job.setJarByClass(HbaseDriver.class);

        DataDrivenDBInputFormat.setInput(job, SehrXmanEhrModel.class, "select id,xman_id,event,catalog_code,serial,content, t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from sehr_xman_ehr_A_test t where $CONDITIONS", "select MIN(t.id), MAX(t.id) from sehr_xman_ehr_A_test t");

        job.setMapperClass(HBaseMapper.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(Put.class);

        job.setNumReduceTasks(0);
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Put.class);

        job.setOutputFormatClass(TableOutputFormat.class);
        // FileOutputFormat.setOutputPath(job, new Path("hdfs://zoe01:9000/sehr_xman_ehr_A"));

        job.waitForCompletion(true);

    }
}