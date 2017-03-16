package com.antin.mr.test.tomysql2;

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
public class HMap extends TableMapper<Text, TestModel> {
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

        byte[] serial = navigableMap.get(Bytes.toBytes("serial"));
        byte[] content = navigableMap.get(Bytes.toBytes("content"));
        byte[] xml = navigableMap.get(Bytes.toBytes("xml"));
        byte[] compression = navigableMap.get(Bytes.toBytes("compression"));
        byte[] encryption = navigableMap.get(Bytes.toBytes("encryption"));
        byte[] status = navigableMap.get(Bytes.toBytes("status"));
        byte[] version = navigableMap.get(Bytes.toBytes("version"));
        byte[] commitTime = navigableMap.get(Bytes.toBytes("commit_time"));
        byte[] istemp = navigableMap.get(Bytes.toBytes("istemp"));

        System.out.println("=============================");
        System.out.println(Bytes.toString(serial));
        System.out.println(Bytes.toString(content));
        System.out.println(Bytes.toString(compression));
        System.out.println(Bytes.toString(encryption));
        System.out.println(Bytes.toString(status));
        System.out.println(Bytes.toString(version));
        System.out.println(Bytes.toString(commitTime));
        System.out.println(Bytes.toString(istemp));

        byte[] r = value.getRow();
        byte[] title = navigableMap.get(Bytes.toBytes("title"));
        byte[] catalogCode = navigableMap.get(Bytes.toBytes("catalog_code"));
        testModel.setRowkey(Bytes.toString(r));
        testModel.setTitle(Bytes.toString(title));
        context.write(new Text(r), testModel);

    }
}
