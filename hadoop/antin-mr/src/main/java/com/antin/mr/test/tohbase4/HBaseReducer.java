package com.antin.mr.test.tohbase4;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */
public class HBaseReducer extends Reducer<LongWritable, Text, ImmutableBytesWritable, Put> {
    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String value = "";
        ImmutableBytesWritable immutableBytesWritable = new ImmutableBytesWritable();
        Text text = new Text();
        for(Text v:values){
            value =v.toString();
            if (value != null && !"".equals(value)) {
                Put put = createPut(value.toString());
                if (put != null)
                    context.write(immutableBytesWritable, put);
            }
        }
    }
    // str格式为row:family:qualifier:value  简单模拟下而已
    private Put createPut(String str) {
        String[] strs = str.split(":");
        if (strs.length < 4)
            return null;
        String row = strs[0];
        String family = strs[1];
        String qualifier = strs[2];
        String value = strs[3];
        Put put = new Put(Bytes.toBytes(row));
        put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), 1L, Bytes.toBytes(value));
        return put;
    }
}