package com.antin.mr.test.tomysql;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.NavigableMap;

/**
 * Created by Administrator on 2017/3/10.
 */
public class HMap extends TableMapper<TestModel, Text> {
    public static String family = "info";
    TestModel testModel = new TestModel();

    @Override
    protected void map(ImmutableBytesWritable key, Result value,
                       Context context) throws IOException, InterruptedException {
        NavigableMap<byte[], byte[]> navigableMap = value.getFamilyMap(family.getBytes());
//        Set<byte[]> set = navigableMap.keySet();
//        StringBuffer sb = new StringBuffer();
//        for (byte[] bytes : set) {
//            byte[] qualifierValue = navigableMap.get(bytes);
//            sb.append(Bytes.toString(qualifierValue) + System.lineSeparator());
//        }
//        byte[] r = value.getRow();
//        context.write(new Text(Bytes.toString(r)), new Text(sb.toString()));

        byte[] r = value.getRow();
        byte[] title = navigableMap.get(Bytes.toBytes("title"));
        testModel.setRowkey(Bytes.toString(r));
        testModel.setTitle(Bytes.toString(title));
        context.write(testModel, null);

    }
}
