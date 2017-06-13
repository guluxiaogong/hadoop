package com.antin.mr.demo.nginx;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/2.
 */
public class LogReducer extends Reducer<NginxLogModel, NullWritable, Text, NullWritable> {
    NullWritable nullWritable = NullWritable.get();

    @Override
    protected void reduce(NginxLogModel nginxLogModel, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        context.write(new Text(nginxLogModel.toString()), nullWritable);

    }
}
