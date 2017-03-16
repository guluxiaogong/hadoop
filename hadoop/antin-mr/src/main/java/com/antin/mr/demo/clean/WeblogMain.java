package com.antin.mr.demo.clean;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 处理原始日志，过滤出真实pv请求
 * 转换时间格式
 * 对缺失字段填充默认值
 * 对记录标记valid和invalid
 *
 * @author
 */

public class WeblogMain {

    static class WeblogPreProcessMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        //用来存储网站url分类数据
        Set<String> pages = new HashSet<String>();
        Text k = new Text();
        NullWritable v = NullWritable.get();

        /**
         * 从外部加载网站url分类数据
         */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            pages.add(".css");
            pages.add(".html");
            pages.add(".js");
            pages.add(".png");
            pages.add(".jpg");
            pages.add(".cvg");
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            WebLogBean webLogBean = WebLogParser.parser(line);
            // 过滤js/图片/css等静态资源
            WebLogParser.filtStaticResource(webLogBean, pages);
            if (!webLogBean.isValid()) return;
            k.set(webLogBean.toString());
            context.write(k, v);
        }

    }

    public static void main(String[] args) throws Exception {
        String resourcePath = WeblogMain.class.getClassLoader().getResource("com/antin/mr/demo/clean/").getPath();

        Configuration conf = new Configuration();
        //conf.set("mapreduce.framework.name", "yarn");
        //conf.set("mapreduce.framework.name", "local");//#
        //conf.set("yarn.resourcemanager.hostname", "zoe01");

        Job job = Job.getInstance(conf);

        //注意：要将main所在的类设置一下
        job.setJarByClass(WeblogMain.class);

        //设置Mapper相关的属性
        job.setMapperClass(WeblogPreProcessMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        FileInputFormat.setInputPaths(job, new Path( resourcePath+ "log-demo.txt"));
        //FileInputFormat.setInputPaths(job, new Path("hdfs://zoe01:9000/input/log-demo.txt"));
        //FileInputFormat.setInputPaths(job, new Path("hdfs://cluster1/input/log-demo.txt"));

        //设置Reducer相关属性
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        FileOutputFormat.setOutputPath(job, new Path(resourcePath+"output"));
        //FileOutputFormat.setOutputPath(job, new Path("hdfs://zoe01:9000/output/aa"));
        //FileOutputFormat.setOutputPath(job, new Path("hdfs:/cluster1/output/aa2"));
        job.setNumReduceTasks(0);

        //提交任务
        job.waitForCompletion(true);

    }

}
