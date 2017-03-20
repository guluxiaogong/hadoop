package com.antin.hbase.oracle2hbase.sehr_xman_ehr;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */

public class HBaseMapper extends Mapper<LongWritable, SehrXmanEhrModel, ImmutableBytesWritable, Put> {
    @Override
    protected void map(LongWritable key, SehrXmanEhrModel sxem, Context context) throws IOException, InterruptedException {
        String xmanId = sxem.getXmanId(), event = sxem.getEvent(), catalogCode = sxem.getCatalogCode();
        //byte[] rowkeyByte = Bytes.toBytes(System.currentTimeMillis()+sxem.getId());
        byte[] rowkeyByte = Bytes.toBytes(xmanId + "&" + event + "&" + catalogCode);
        ImmutableBytesWritable rowkey = new ImmutableBytesWritable(rowkeyByte);
        byte[][] qualifiers = {Bytes.toBytes("xml"), Bytes.toBytes("commit_time")};
        for (int i = 0; i < qualifiers.length; i++) {
            String value = "";
            switch (i) {
                case 0:
                    value = toString(sxem.getXml());
                    break;
                case 1:
                    value = toString(sxem.getCommitTime());
                    break;
            }
            Put put = new Put(rowkeyByte);
            put.addColumn(Bytes.toBytes("info"), qualifiers[i], System.currentTimeMillis(), Bytes.toBytes(value));
            context.write(rowkey, put);
        }
    }

    private static String toString(Object obj) {
        if (obj != null)
            return obj.toString();
        else
            return "";
    }

}

