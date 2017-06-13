package com.antin.mr.demo.nginx;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Created by Administrator on 2017/3/2.
 */
public class LogRunner {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        System.setProperty("HADOOP_USER_NAME", "root");

        Job job = Job.getInstance(conf);
        //指定我这个job所在的jar包
//		job.setJar("/home/hadoop/LogRunner.jar");
        job.setJarByClass(LogRunner.class);

        /*
         *map
         */
        //指定要处理的数据所在的位置
        FileInputFormat.setInputPaths(job, "hdfs://zoe01:9000/input/access.log");
        job.setMapperClass(LogMapper.class);
        job.setInputFormatClass(TextInputFormat.class);
        //设置我们的业务逻辑Mapper类的输出key和value的数据类型
        job.setMapOutputKeyClass(NginxLogModel.class);
        job.setMapOutputValueClass(NullWritable.class);

        /*
         *reduce
         */
        //job.setReducerClass(LogReducer.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        //设置我们的业务逻辑Reducer类的输出key和value的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setNumReduceTasks(0);

        //指定处理完成之后的结果所保存的位置
        FileOutputFormat.setOutputPath(job, new Path("hdfs://zoe01:9000/output/nginx_log"));

        //向yarn集群提交这个job
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }
}
