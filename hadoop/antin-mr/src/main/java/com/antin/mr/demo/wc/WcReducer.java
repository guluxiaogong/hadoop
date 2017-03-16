package com.antin.mr.demo.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/2.
 */
public class WcReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //定义一个计数器
        int count = 0;
        //遍历这一组kv的所有v，累加到count中
        for (IntWritable value : values) {
            count += value.get();
        }
        context.write(key, new IntWritable(count));

    }
}
