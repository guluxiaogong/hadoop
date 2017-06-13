package com.antin.mr.demo.nginx;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/2.
 */
public class LogMapper extends Mapper<LongWritable, Text, NginxLogModel, NullWritable> {

    Text k = new Text();
    NullWritable nullWritable = NullWritable.get();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        NginxLogModel nginxLogModel = LogParser.parseLine(line);

        context.write(nginxLogModel, nullWritable);
    }
}
