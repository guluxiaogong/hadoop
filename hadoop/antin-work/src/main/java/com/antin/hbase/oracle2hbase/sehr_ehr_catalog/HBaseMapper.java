package com.antin.hbase.oracle2hbase.sehr_ehr_catalog;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */

public class HBaseMapper extends Mapper<LongWritable, SehrEhrCatalogModel, ImmutableBytesWritable, Put> {
    @Override
    protected void map(LongWritable key, SehrEhrCatalogModel sxem, Context context) throws IOException, InterruptedException {
        String catalogCode = sxem.getCatalogCode();
        byte[] rowkeyByte = Bytes.toBytes(catalogCode);
        ImmutableBytesWritable rowkey = new ImmutableBytesWritable(rowkeyByte);
        byte[][] qualifiers = {Bytes.toBytes("xml_format"), Bytes.toBytes("status"), Bytes.toBytes("quantity")};
        for (int i = 0; i < qualifiers.length; i++) {
            String value = "";
            switch (i) {
                case 0:
                    value = toString(sxem.getXmlFormat());
                    break;
                case 1:
                    value = toString(sxem.getStatus());
                    break;
                case 2:
                    value = toString(sxem.getQuantity());
                    break;
            }
            Put put = new Put(rowkeyByte);
            put.addColumn(Bytes.toBytes("name"), null, System.currentTimeMillis(), Bytes.toBytes(toString(sxem.getCatalogName())));
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

