package com.antin.mr.test.tomysql;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.log4j.Logger;

/**
 * 将数据据从hbase写入mysql中
 * Created by Administrator on 2017/3/10.
 */
public class MRHbaseMain {
    private static Logger log = Logger.getLogger(MRHbaseMain.class);
    public static String tableName = "sehr_xman_ehr_0";

    public static void main(String[] args) {
        Configuration conf = HBaseConfiguration.create();
        try {

            DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://qdm154746529.my3w.com:3306/qdm154746529_db", "qdm154746529", "552620jg");

            Job job = Job.getInstance(conf);
            job.addCacheFile(new Path("hdfs://cluster1/hadoop-lib/mysql-connector-java-5.1.7-bin.jar").toUri());

            job.setJarByClass(MRHbaseMain.class);

            Get get = new Get(Bytes.toBytes("0cea6227-47c0-4ed8-a6f8-3081fdb0d396da51b951-0601-4d6d-84fc-7b755a1fccb2"));
            Scan scan = new Scan(get);
            //scan.addColumn(family.getBytes(), col.getBytes());
            TableMapReduceUtil.initTableMapperJob(tableName, scan, HMap.class, TestModel.class, Text.class, job);


            //output
            //job.setOutputFormatClass(TextOutputFormat.class);
            //FileOutputFormat.setOutputPath(job, new Path("/test_hbase_mysql"));
            //job.setReducerClass(HReduce.class);

            //DBOutputFormat.setOutput(job, tableName, "xman_id", "event", "catalog_code", "serial", "content", "xml", "compression", "encryption", "status", "version", "title", "commit_time");
            job.setNumReduceTasks(0);
            DBOutputFormat.setOutput(job, "test_hbase_mysql", "rowkey", "title");
            job.setOutputFormatClass(DBOutputFormat.class);

            job.waitForCompletion(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
