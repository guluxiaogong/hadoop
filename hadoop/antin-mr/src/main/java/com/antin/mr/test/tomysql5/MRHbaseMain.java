package com.antin.mr.test.tomysql5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.net.URI;

/**
 * 将数据据从hbase写入mysql中,插入数据放在了reduce中
 * 正常将hbase中的数据写的hdfs或mysql中
 * 对行大xml字段会有java.io.UTFDataFormatException: encoded string too long
 * Created by Administrator on 2017/3/10.
 */
public class MRHbaseMain {
    public static String tableName = "sehr_xman_ehr_0";
    public static String root = "hdfs://zoe01:9000/";
    public static Path filePath = new Path("hdfs://zoe01:9000/test_hbase_mysql_info");

    public static void main(String[] args) {
        Configuration conf = HBaseConfiguration.create();
        if (args.length > 0 && "local".equals(args[0])) {
            System.setProperty("HADOOP_USER_NAME", "root");
            conf.set("mapreduce.framework.name", "local");
            conf.set("hbase.master", "zoe01:16010");
            conf.set("hbase.zookeeper.quorum", "zoe01");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
        }

        try {
            FileSystem fileSystem = FileSystem.get(new URI(root), conf, "root");
            if (fileSystem.exists(filePath))
                fileSystem.delete(filePath, true);

            //DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.1.77:3306/hbase", "root", "root");

            Job job = Job.getInstance(conf);
            job.addCacheFile(new Path("hdfs://zoe01:9000/hadoop-lib/mysql-connector-java-5.1.7-bin.jar").toUri());

            job.setJarByClass(MRHbaseMain.class);

            Scan scan = new Scan();
            TableMapReduceUtil.initTableMapperJob(tableName, scan, HMap.class, Text.class, XmlHbaseModel.class, job);

            //output
            job.setOutputFormatClass(TextOutputFormat.class);
            FileOutputFormat.setOutputPath(job, filePath);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            job.setNumReduceTasks(0);

            job.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
