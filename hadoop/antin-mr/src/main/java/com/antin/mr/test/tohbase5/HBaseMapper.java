package com.antin.mr.test.tohbase5;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */

public class HBaseMapper extends Mapper<LongWritable, SehrXmanEhrModel, Text, Text> {
    public void map(LongWritable key, SehrXmanEhrModel sxem, Context context)
            throws IOException, InterruptedException {

        context.write(new Text(sxem.getXmanId() + sxem.getEvent()), new Text(sxem.toString()));
    }

}

