package com.antin.mr.test.tableOutputFormat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */

public class HBaseMapper extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text> {
    @Override
    public void map(LongWritable key, Text values,
                    OutputCollector<LongWritable, Text> output, Reporter reporter)
            throws IOException {
        output.collect(key, values);
    }
}

