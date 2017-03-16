package com.antin.mr.test.tohbase2;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat;
import org.apache.hadoop.hbase.mapreduce.KeyValueSortReducer;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DataDrivenDBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DBImportHbase {
    //public static final String TABLE_NAME = "student";

    public static class DBMapper extends
            Mapper<LongWritable, SehrXmanEhrModel, ImmutableBytesWritable, KeyValue> {
        public void map(LongWritable key, SehrXmanEhrModel sxem, Context context)
                throws IOException, InterruptedException {

            byte[] rowkeyByte = Bytes.toBytes(sxem.getXmanId() + sxem.getEvent());
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(rowkeyByte);

            String[] qualifiers = {"catalog_code", "serial", "content", "xml", "compression", "encryption", "status", "version", "title", "commit_time", "istemp"};
//            KeyValue kv = new KeyValue(values.name.getBytes());
            for (int i = 0; i < qualifiers.length; i++) {
                String value = null;
                switch (i) {
                    case 0:
                        value = toString(sxem.getCatalogCode());
                        break;
                    case 1:
                        value = toString(sxem.getSerial());
                        break;
                    case 2:
                        value = toString(sxem.getContent());
                        break;
                    case 3:
                        value = toString(sxem.getXml());
                        break;
                    case 4:
                        value = toString(sxem.getCompression());
                        break;
                    case 5:
                        value = toString(sxem.getEncryption());
                        break;
                    case 6:
                        value = toString(sxem.getStatus());
                        break;
                    case 7:
                        value = toString(sxem.getVersion());
                        break;
                    case 8:
                        value = toString(sxem.getTitle());
                        break;
                    case 9:
                        value = toString(sxem.getCommitTime());
                        break;
                    case 10:
                        value = toString(sxem.getIstemp());
                        break;
                }

                KeyValue kv = new KeyValue(rowkeyByte, Bytes.toBytes("info"), Bytes.toBytes(qualifiers[i]), System.currentTimeMillis(), Bytes.toBytes(value));

                context.write(rowkey, kv);
            }

//            context.write(new Text(values.id), new Text(values.name));
        }


        private static String toString(Object obj) {
            if (obj != null)
                return obj.toString();
            else
                return "";
        }
    }

//    public static class DBReducer extends Reducer<ImmutableBytesWritable, KeyValue, Text, Text> {
//        public void reduce(Text key, Iterable<Text> values, Context context)
//                throws IOException, InterruptedException {
//            
//            for (Iterator<Text> it = values.iterator(); it.hasNext();) {
//                
//                context.write(key, it.next());
//                
//            }
//        }
//    }

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();

        /// DistributedCache.addFileToClassPath(new Path("hdfs://datanode4:8020/test3/ojdbc14.jar"), conf);
        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");
        //
        //DistributedCache.addFileToClassPath(new Path("hdfs://hadoop:9000/test3/mysql-connector-java-5.1.7-bin.jar"),conf);
        //DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver","jdbc:mysql://192.168.176.11:3306/test", "root", "root");

        //// Job job = new Job(conf, "db import");
        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("hdfs://zoe01:9000/hadoop-lib/ojdbc6.jar").toUri());
        job.setJarByClass(DBImportHbase.class);
        job.setMapperClass(DBMapper.class);

        job.setReducerClass(KeyValueSortReducer.class);
//        job.setReducerClass(DBReducer.class);
//        job.setOutputKeyClass(Text.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(KeyValue.class);
//        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(DBInputFormat.class);

        Path output = new Path("hdfs://zoe01:9000/db3");//hdfs://zoe01:9000要写，否则报错

        FileOutputFormat.setOutputPath(job, output);
        String[] fields = {"xman_id", "event", "catalog_code", "serial", "content", "xml", "compression", "encryption", "status", "version", "title", "commit_time", "istemp"};
//        DBInputFormat.setInput(job, DBRecord.class, "test1", null,null, fields);
//        DBInputFormat.setInput(job, DBRecord.class, "select id,name from test1","select count(1) from test1");
//        DataDrivenDBInputFormat.setInput(job, SehrXmanEhrModel.class, "sehr_xman_ehr_0", null, null, fields);//success
//          DataDrivenDBInputFormat.setInput(job, SehrXmanEhrModel.class, "select xman_id,event,catalog_code,serial,content,t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from sehr_xman_ehr_0 t", "select MIN(tt.id), MAX(tt.id) from (select rownum as id from sehr_xman_ehr_0) tt");//success
        DataDrivenDBInputFormat.setInput(job, SehrXmanEhrModel.class, "select xman_id,event,catalog_code,serial,content,t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from SEHR_XMAN_EHR_D t", "select MIN(tt.id), MAX(tt.id) from (select rownum as id from SEHR_XMAN_EHR_D) tt");

//        System.exit(job.waitForCompletion(true) ? 0 : 1);

        // 自动设置partitioner和reduce
        HTable hTable = new HTable(conf, "tb2");
        HFileOutputFormat.configureIncrementalLoad(job, hTable);

        job.waitForCompletion(true);

        // 上面JOB运行完后，就把数据批量load到HBASE中
        LoadIncrementalHFiles loader = new LoadIncrementalHFiles(conf);
        loader.doBulkLoad(output, hTable);

        System.exit(0);

    }

}
