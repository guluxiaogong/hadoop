package com.antin.mr.test.tohbase2;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DataDrivenDBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 将数据从oracle导入hdfs
 */
public class DBImport {

    public static class DBMapper extends
            Mapper<LongWritable, SehrXmanEhrModel, Text, Text> {
        public void map(LongWritable key, SehrXmanEhrModel sxem, Context context)
                throws IOException, InterruptedException {

            context.write(new Text(sxem.getXmanId()), new Text(sxem.toString()));
        }
    }

    public static class DBReducer extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {

            for (Iterator<Text> it = values.iterator(); it.hasNext(); ) {

                context.write(key, it.next());

            }
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();

//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.set("hbase.zookeeper.quorum", "192.168.176.100");
//        conf.set("hbase.master", "192.168.176.100:600000");

//        conf.set("fs.default.name", "hdfs://192.168.0.34:8020");

        ////DistributedCache.addFileToClassPath(new Path("hdfs://cluster1/hadoop-lib/ojdbc6.jar"), conf);
        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");
//
//          DistributedCache.addFileToClassPath(new Path("hdfs://hadoop:9000/test3/mysql-connector-java-5.1.7-bin.jar"),conf); 
//          DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver","jdbc:mysql://192.168.176.11:3306/test", "root", "root");

        ////Job job = new Job(conf, "db import");
        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("hdfs://cluster1/hadoop-lib/ojdbc6.jar").toUri());

        job.setJarByClass(DBImport.class);

        job.setMapperClass(DBMapper.class);
        job.setReducerClass(DBReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(DBInputFormat.class);

        Path output = new Path("/db/");

        FileOutputFormat.setOutputPath(job, output);

        String[] fields = {"xman_id", "event", "catalog_code", "serial", "content", "xml", "compression", "encryption", "status", "version", "title", "commit_time", "istemp"};

//        DBInputFormat.setInput(job, DBRecord.class, "test1", null,null, fields);
//        DBInputFormat.setInput(job, DBRecord.class, "select id,name from test1","select count(1) from test1");
        DataDrivenDBInputFormat.setInput(job, SehrXmanEhrModel.class, "sehr_xman_ehr_0", null, null, fields);
//        DataDrivenDBInputFormat.setInput(job, DBRecord.class, "select id,name from test1","select count(1) from test1");

        System.exit(job.waitForCompletion(true) ? 0 : 1);


    }

}
