package com.antin.mr.demo.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Created by Administrator on 2017/3/2.
 */
public class WcMain {
//    private static String PATH_TO_CORE_SITE_XML = WcRunner.class.getClassLoader().getResource("core-site.xml").getPath();
//    private static String PATH_TO_HDFS_SITE_XML = WcRunner.class.getClassLoader().getResource("hdfs-site.xml").getPath();
//    private static String PATH_TO_MAPRED_SITE_XML = WcRunner.class.getClassLoader().getResource("mapred-site.xml").getPath();
//    private static String PATH_TO_YARN_SITE_XML = WcRunner.class.getClassLoader().getResource("yarn-site.xml").getPath();

    //把业务逻辑相关的信息（哪个是mapper，哪个是reducer，要处理的数据在哪里，输出的结果放哪里……）描述成一个job对象
    //把这个描述好的job提交给集群去运行
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        System.setProperty("HADOOP_USER_NAME", "root");
        /*
         *只要将配置文件放在resources中会自动加载
         */
//        conf.addResource(new Path(PATH_TO_HDFS_SITE_XML));
//        conf.addResource(new Path(PATH_TO_CORE_SITE_XML));
//        conf.addResource(new Path(PATH_TO_MAPRED_SITE_XML));
//        conf.addResource(new Path(PATH_TO_YARN_SITE_XML));

        Job job = Job.getInstance(conf);
        //指定我这个job所在的jar包
//		job.setJar("/home/hadoop/wordcount.jar");
        job.setJarByClass(WcMain.class);

        /*
         *map
         */
        //指定要处理的数据所在的位置
        FileInputFormat.setInputPaths(job, "hdfs://zoe01:9000/hadoop-lib/ojdbc6.jar");
        job.setMapperClass(WcMapper.class);
        job.setInputFormatClass(TextInputFormat.class);//#
        //设置我们的业务逻辑Mapper类的输出key和value的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);


        /*
         *reduce
         */
        job.setReducerClass(WcReducer.class);
        job.setOutputFormatClass(TextOutputFormat.class);//#
        //设置我们的业务逻辑Reducer类的输出key和value的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //指定处理完成之后的结果所保存的位置
        FileOutputFormat.setOutputPath(job, new Path("hdfs://zoe01:9000/output/wc"));

        //向yarn集群提交这个job
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }
}
