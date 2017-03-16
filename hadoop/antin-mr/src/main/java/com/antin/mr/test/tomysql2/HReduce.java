package com.antin.mr.test.tomysql2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/10.
 */

public class HReduce extends Reducer<Text, TestModel, TestModel, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<TestModel> values, Context context) throws IOException, InterruptedException {
        for (TestModel value : values)
            context.write(value, null);
    }
}
