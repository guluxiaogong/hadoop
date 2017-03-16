package com.antin.mr.test.tohbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/6.
 */
public class DBToHBase {
    private static String tableName = "tb1";

    public static class DBInputMapper extends Mapper<LongWritable, SehrXmanEhrModel, Text, Text> {

        @Override
        protected void map(LongWritable key, SehrXmanEhrModel sxem, Context context) throws IOException, InterruptedException {
            // context.write(new Text(sxem.getXmanId()), new Text(sxem.toString()));
            try {
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "catalog_code", sxem.getCatalogCode());
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "serial", sxem.getSerial() == null ? "" : sxem.getSerial() + "");
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "content", sxem.getContent());
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "xml", sxem.getXml());
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "compression", sxem.getCompression() == null ? "" : sxem.getCompression() + "");
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "encryption", sxem.getEncryption() == null ? "" : sxem.getEncryption() + "");
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "status", sxem.getStatus() == null ? "" : sxem.getStatus() + "");
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "version", sxem.getVersion());
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "title", sxem.getTitle());
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "commit_time", sxem.getCommitTime());
                HBaseCurd.addRow(tableName, sxem.getXmanId() + sxem.getEvent(), "info", "istemp", sxem.getIstemp() == null ? "" : sxem.getIstemp() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 新api
     */
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        //conf.setInt("mapreduce.job.maps", 8);

        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");
        Job job = Job.getInstance(conf);

        //export HADOOP_CLASSPATH="${HADOOP_CLASSPATH}:/hadoop-lib/ojdbc6.jar"//hadoop jar ...驱动找不到时执行
        job.addCacheFile(new Path("hdfs://cluster1/hadoop-lib/ojdbc6.jar").toUri());

        job.setJarByClass(DBToHBase.class);

        DBInputFormat.setInput(job, SehrXmanEhrModel.class, "select xman_id,event,catalog_code,serial,content,t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from sehr_xman_ehr_0 t", "select count(*) from sehr_xman_ehr_0");
        job.setInputFormatClass(DBInputFormat.class);
        job.setMapperClass(DBInputMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setNumReduceTasks(0);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(NullWritable.class);

        FileOutputFormat.setOutputPath(job, new Path("hdfs://cluster1/oracle"));

        HBaseCurd.createTable(tableName, new String[]{"info"});
        job.waitForCompletion(true);

    }
}
//注:
// 1、异常：Oracle ORA-3137[12333] 关闭的连接 java.sql.SQLRecoverableException: 无法从套接字读取更多的数据 _optim_peek_user_binds
//    解决：alter system set "_optim_peek_user_binds"=false
// 2、oracle 的BLOB类型通过Utl_Raw.Cast_To_Varchar2(content)转成java的String
