package com.antin.mr.test.tohbase7;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;

/**
 * Created by Administrator on 2017/3/7.
 * 将表中数据导入hbase,测试用时间做分隔
 * 自定义MyOracleInput后可以正常执行，但是执行过程中会有很多java.lang.RuntimeException: java.sql.SQLRecoverableException: 无法从套接字读取更多的数据 异常抛出
 * 说明：按时间切分map会存在数据倾斜问题，最好还是按tobase3中的按rownum分，且用Hfile load进hbase效率高
 * 完成表 sehr_xman_ehr_a 抽取
 */
public class HbaseDriver {
    //export HADOOP_CLASSPATH="${HADOOP_CLASSPATH}:/hadoop-lib/ojdbc6.jar"
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        for (int i = 0; i < args.length; i++)
            System.out.println("参数args[" + i + "] = " + args[i]);
        if (args.length < 2) {
            System.out.println("请输入表名和map个数！");
            return;
        }

        String tableName = args[0];
        int maps = Integer.parseInt(args[1]);
        System.out.println("表名：" + tableName + "；map个数：" + maps);
        conf.setInt("mapreduce.job.maps", maps);

        conf.set("mapreduce.jdbc.input.orderby", "commit_time");
        conf.set(TableOutputFormat.OUTPUT_TABLE, tableName);

        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");

        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("hdfs://zoe01:9000/hadoop-lib/ojdbc6.jar").toUri());

        job.setJarByClass(HbaseDriver.class);

        MyOracleInputFormat.setInput(job, SehrXmanEhrModel2.class, "select xman_id,event,catalog_code,serial,content, t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from " + tableName + " t where $CONDITIONS", "select MIN(t.commit_time), MAX(t.commit_time) from " + tableName + " t");

        job.setMapperClass(HBaseMapper.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(Put.class);

        job.setNumReduceTasks(0);
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Put.class);

        job.setOutputFormatClass(TableOutputFormat.class);

        job.waitForCompletion(true);

    }
}