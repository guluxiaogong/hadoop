package com.antin.mr.demo.io.load2hbase;

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
import org.apache.hadoop.mapreduce.lib.db.DataDrivenDBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 生成HFiles，然后load到hbase中
 * 怎么设置多map工作？
 * 解：体例通过rownum设置多map工作
 */
public class DBImportHbase {

    public static class DBMapper extends
            Mapper<LongWritable, SehrXmanEhrModel, ImmutableBytesWritable, KeyValue> {
        public void map(LongWritable key, SehrXmanEhrModel sxem, Context context)
                throws IOException, InterruptedException {

            byte[] rowkeyByte = Bytes.toBytes(sxem.getXmanId() + sxem.getEvent());
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(rowkeyByte);

            String[] qualifiers = {"catalog_code", "serial", "content", "xml", "compression", "encryption", "status", "version", "title", "commit_time", "istemp"};
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
        }


        private static String toString(Object obj) {
            if (obj != null)
                return obj.toString();
            else
                return "";
        }
    }

    //export HADOOP_CLASSPATH="${HADOOP_CLASSPATH}:/hadoop-lib/ojdbc6.jar"
    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();

//        conf.set("mapreduce.framework.name", "local");
//        conf.set("dfs.nameservices", "cluster1");
//        conf.set("dfs.ha.namenodes.cluster1", "zoe01,zoe02");
//        conf.set("dfs.namenode.rpc-address.cluster1.zoe01", "zoe01:9000");
//        conf.set("dfs.namenode.rpc-address.cluster1.zoe02", "zoe02:9000");
//        conf.set("fs.client.failover.proxy.provider.cluster1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

        conf.setInt("mapreduce.job.maps", 10);
        conf.set("mapreduce.jdbc.input.orderby", "id");

        DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth", "sehr", "sehr");

        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("hdfs://zoe01:9000/hadoop-lib/ojdbc6.jar").toUri());
        job.setJarByClass(DBImportHbase.class);

        //job.setInputFormatClass(DBInputFormat.class);//DataDrivenDBInputFormat已经扩展了

        job.setMapperClass(DBMapper.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(KeyValue.class);

        job.setReducerClass(KeyValueSortReducer.class);

        //job.setNumReduceTasks(2);

        Path output = new Path("hdfs://zoe01:9000/sehr_xman_ehr_d");//hdfs://zoe01:9000要写，否则报错java.net.UnknownHostException: cluster1
        FileOutputFormat.setOutputPath(job, output);

        //job.setOutputFormatClass(HFileOutputFormat.class);//and $CONDITIONS

        DataDrivenDBInputFormat.setInput(job, SehrXmanEhrModel.class, "select * from (select rownum as id,xman_id,event,catalog_code,serial,content,t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from sehr_xman_ehr_d t) where $CONDITIONS", "select MIN(t.id), MAX(t.id) from (select rownum as id from sehr_xman_ehr_d) t");
        //OracleDataDrivenDBInputFormat.setInput(job, SehrXmanEhrModel.class, "select xman_id,event,catalog_code,serial,content,t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from sehr_xman_ehr_0 t where $CONDITIONS", "select MIN(t.commit_time), MAX(t.commit_time) from sehr_xman_ehr_0 t");

        //job.setOutputFormatClass(HFileOutputFormat.class);
        // 自动设置partitioner和reduce
        HTable hTable = new HTable(conf, "sehr_xman_ehr_d");
        HFileOutputFormat.configureIncrementalLoad(job, hTable);
        //HFileOutputFormat2.configureIncrementalLoadMap(job,hTable);

        job.waitForCompletion(true);

        // 上面JOB运行完后，就把数据批量load到HBASE中
        LoadIncrementalHFiles loader = new LoadIncrementalHFiles(conf);
        loader.doBulkLoad(output, hTable);

        System.exit(0);

    }

}
