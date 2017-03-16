package com.antin.mr.test.tomysql4;

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
import org.apache.log4j.Logger;

/**
 * 将数据据从hbase写入mysql中,插入数据放在了reduce中
 * 正常将hbase中的数据写的hdfs或mysql中
 * 对行大xml字段会有java.io.UTFDataFormatException: encoded string too long
 * Created by Administrator on 2017/3/10.
 */
public class MRHbaseMain {
    private static Logger log = Logger.getLogger(MRHbaseMain.class);
    public static String tableName = "sehr_xman_ehr_0";

    public static void main(String[] args) {
        Configuration conf = HBaseConfiguration.create();
        try {

            DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.1.77:3306/hbase", "root", "root");

            Job job = Job.getInstance(conf);
            job.addCacheFile(new Path("/hadoop-lib/mysql-connector-java-5.1.7-bin.jar").toUri());

            job.setJarByClass(MRHbaseMain.class);

            //Get get = new Get(Bytes.toBytes("0cea6227-47c0-4ed8-a6f8-3081fdb0d396da51b951-0601-4d6d-84fc-7b755a1fccb2"));
            Scan scan = new Scan();
            //scan.addColumn(family.getBytes(), col.getBytes());
            TableMapReduceUtil.initTableMapperJob(tableName, scan, HMap.class, Text.class, XmlModel.class, job);

            //output
            //job.setOutputFormatClass(TextOutputFormat.class);
            //FileOutputFormat.setOutputPath(job, new Path("/test_hbase_mysql2222"));

            job.setReducerClass(HReduce.class);
            job.setOutputKeyClass(Op0101Model.class);
            //job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(NullWritable.class);

            //job.setNumReduceTasks(0);
            DBOutputFormat.setOutput(job, "test_parse_xml", "sex", "birth", "marriage", "onset_time", "treat_Time", "diagnosis_date", "reg", "type", "sec_type", "sec_no", "dept_name", "dept_code", "doctor_id", "doctor", "tech_title", "symptom_name", "symptom_code", "diagnosis_name", "diagnosis_icd", "diagnosis_prop", "result");
            job.setOutputFormatClass(DBOutputFormat.class);
            //Op0101Model{sex='性别代码(字典STD_SEX)', birth='性别代码(字典STD_SEX)', marriage='性别代码(字典STD_SEX)', onsetTime='性别代码(字典STD_SEX)', treatTime='性别代码(字典STD_SEX)', diagnosisDate='性别代码(字典STD_SEX)', reg='性别代码(字典STD_SEX)', type='性别代码(字典STD_SEX)', secType='性别代码(字典STD_SEX)', secNo='性别代码(字典STD_SEX)', deptName='null', deptCode='性别代码(字典STD_SEX)', doctorId='性别代码(字典STD_SEX)', doctor='性别代码(字典STD_SEX)', techTitle='性别代码(字典STD_SEX)', symptomName='性别代码(字典STD_SEX)', symptomCode='null', diagnosisName='null', diagnosisIcd='null', diagnosisProp='性别代码(字典STD_SEX)', result='性别代码(字典STD_SEX)'}

            job.waitForCompletion(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
