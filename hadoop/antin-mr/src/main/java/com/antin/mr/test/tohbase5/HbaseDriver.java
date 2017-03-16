package com.antin.mr.test.tohbase5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DataDrivenDBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by Administrator on 2017/3/7.
 * 用rownum不行，最终表中添加id列为NUMBER类型，多map运行成功
 * 将oracle数据导到hdfs
 */
public class HbaseDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInt("mapreduce.job.maps", 5);
        conf.set("mapreduce.jdbc.input.orderby", "id");

        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");

        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("hdfs://cluster1/hadoop-lib/ojdbc6.jar").toUri());

        job.setJarByClass(HbaseDriver.class);

        DataDrivenDBInputFormat.setInput(job, SehrXmanEhrModel.class, "select id,xman_id,event,catalog_code,serial,content, t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from sehr_xman_ehr_test t where $CONDITIONS", "select MIN(t.id), MAX(t.id) from sehr_xman_ehr_test t");

        job.setMapperClass(HBaseMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(HBaseReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileOutputFormat.setOutputPath(job, new Path("hdfs://cluster1/sehr_xman_ehr_test"));

        job.waitForCompletion(true);

    }
}