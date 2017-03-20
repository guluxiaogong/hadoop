package com.antin.hbase.oracle2hbase.sehr_ehr_node;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DataDrivenDBInputFormat;

/**
 * Created by Administrator on 2017/3/20.
 */
public class HbaseMain {
    //export HADOOP_CLASSPATH="${HADOOP_CLASSPATH}:/hadoop-lib/ojdbc6.jar"
    public static void main(String[] args) throws Exception {
        String tableName = "sehr_ehr_node", hTableName = "sehr_ehr_node";
        int maps = 1;
        if (null != args && args.length > 0) {
            try {
                maps = Integer.parseInt(args[0]);
            } catch (Exception e) {
                maps = 1;
                e.printStackTrace();
            }
            if (args.length > 1)
                tableName = args[1];
        }

        Configuration conf = new Configuration();

        /************本地运行打开****************/
//        System.setProperty("HADOOP_USER_NAME", "root");
//        conf.set("mapreduce.framework.name", "local");
//        conf.set("hbase.master", "zoe01:16010");
//        conf.set("hbase.zookeeper.quorum", "zoe01");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
        /************#本地运行打开****************/

        conf.setInt("mapreduce.job.maps", maps);

        conf.set("mapreduce.jdbc.input.orderby", "id");
        conf.set(TableOutputFormat.OUTPUT_TABLE, hTableName);

        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");

        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("hdfs://zoe01:9000/hadoop-lib/ojdbc6.jar").toUri());

        job.setJarByClass(HbaseMain.class);

        DataDrivenDBInputFormat.setInput(job, SehrEhrNodeModel.class, "select * from (select rownum id ,code,name,quantity,t.xml_format.getclobval() xml_format,status from " + tableName + " t) b where $CONDITIONS", "select MIN(t.id), MAX(t.id) from (select rownum as id from " + tableName + ") t");

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