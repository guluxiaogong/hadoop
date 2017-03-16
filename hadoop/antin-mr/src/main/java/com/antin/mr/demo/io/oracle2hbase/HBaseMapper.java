package com.antin.mr.demo.io.oracle2hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */

public class HBaseMapper extends Mapper<LongWritable, SehrXmanEhrModel2, ImmutableBytesWritable, Put> {
    @Override
    protected void map(LongWritable key, SehrXmanEhrModel2 sxem, Context context) throws IOException, InterruptedException {
        byte[] rowkeyByte = Bytes.toBytes(sxem.getXmanId() + sxem.getEvent());
        ImmutableBytesWritable rowkey = new ImmutableBytesWritable(rowkeyByte);
        byte[][] qualifiers = {Bytes.toBytes("catalog_code"), Bytes.toBytes("serial"), Bytes.toBytes("content"), Bytes.toBytes("xml"), Bytes.toBytes("compression"), Bytes.toBytes("encryption"), Bytes.toBytes("status"), Bytes.toBytes("version"), Bytes.toBytes("title"), Bytes.toBytes("commit_time"), Bytes.toBytes("istemp")};
        for (int i = 0; i < qualifiers.length; i++) {
            String value = null;
            switch (i) {
                case 0:
                    value = toString(sxem.getCatalogCode());
                    break;
                case 1:
                    value = toString(sxem.getSerial());
                    break;
                case 2:
                    value = toString(sxem.getContent());
                    break;
                case 3:
                    value = toString(sxem.getXml());
                    break;
                case 4:
                    value = toString(sxem.getCompression());
                    break;
                case 5:
                    value = toString(sxem.getEncryption());
                    break;
                case 6:
                    value = toString(sxem.getStatus());
                    break;
                case 7:
                    value = toString(sxem.getVersion());
                    break;
                case 8:
                    value = toString(sxem.getTitle());
                    break;
                case 9:
                    value = toString(sxem.getCommitTime());
                    break;
                case 10:
                    value = toString(sxem.getIstemp());
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

