package com.antin.mr.demo.io.hbase2mysql;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.Logger;

/**
 * 将数据据从hbase写入mysql中,插入数据放在了reduce中
 * 执行报：1 java.io.IOException: java.io.IOException: java.lang.IllegalArgumentException: offset (0) + length (8) exceed the capacity of the array: 4
 * Created by Administrator on 2017/3/10.
 */
public class MRHbaseMain {
    private static Logger log = Logger.getLogger(MRHbaseMain.class);
    public static String tableName = "sehr_xman_ehr_0";

    public static void main(String[] args) {
        hbase2hdfs();
        //hbase2mysql();

    }

    public static void hbase2hdfs() {
        Configuration conf = HBaseConfiguration.create();
        try {
            Job job = Job.getInstance(conf);

            job.setJarByClass(MRHbaseMain.class);

            //Get get = new Get(Bytes.toBytes("0cea6227-47c0-4ed8-a6f8-3081fdb0d396da51b951-0601-4d6d-84fc-7b755a1fccb2"));
            Scan scan = new Scan();
            //scan.addColumn(family.getBytes(), col.getBytes());
            TableMapReduceUtil.initTableMapperJob(tableName, scan, HMap.class, Text.class, SehrXmanEhrModel.class, job);

            //output
            job.setOutputFormatClass(TextOutputFormat.class);
            FileOutputFormat.setOutputPath(job, new Path("/test_hbase_mysql"));

            job.setReducerClass(HReduce.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(NullWritable.class);

            job.waitForCompletion(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void hbase2mysql() {
        Configuration conf = HBaseConfiguration.create();
        try {
            DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.1.77:3306/hbase", "root", "root");

            Job job = Job.getInstance(conf);
            //export HADOOP_CLASSPATH="${HADOOP_CLASSPATH}:/hadoop-lib/mysql-connector-java-5.1.7-bin.jar"//hadoop jar ...驱动找不到时执行
            job.addCacheFile(new Path("/hadoop-lib/mysql-connector-java-5.1.7-bin.jar").toUri());

            job.setJarByClass(MRHbaseMain.class);

            //Get get = new Get(Bytes.toBytes("0cea6227-47c0-4ed8-a6f8-3081fdb0d396da51b951-0601-4d6d-84fc-7b755a1fccb2"));
            Scan scan = new Scan();
            //scan.addColumn(family.getBytes(), col.getBytes());
            TableMapReduceUtil.initTableMapperJob(tableName, scan, HMap.class, Text.class, SehrXmanEhrModel.class, job);

            job.setReducerClass(HReduce.class);
            job.setOutputKeyClass(Op0101Model.class);
            job.setOutputValueClass(NullWritable.class);

            //job.setNumReduceTasks(0);
            DBOutputFormat.setOutput(job, "test_parse_xml", "sex", "birth", "marriage", "onset_time", "treat_Time", "diagnosis_date", "reg", "type", "sec_type", "sec_no", "dept_name", "dept_code", "doctor_id", "doctor", "tech_title", "symptom_name", "symptom_code", "diagnosis_name", "diagnosis_icd", "diagnosis_prop", "result");
            job.setOutputFormatClass(DBOutputFormat.class);

            job.waitForCompletion(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
