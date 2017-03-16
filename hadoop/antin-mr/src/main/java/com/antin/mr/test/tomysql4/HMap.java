package com.antin.mr.test.tomysql4;

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
public class HMap extends TableMapper<Text, XmlModel> {
    public static String family = "info";
    Text text = new Text();
    XmlModel xmlModel = new XmlModel();
    byte[] catalogCodeByte = Bytes.toBytes("catalog_code");
    byte[] xmlByte = Bytes.toBytes("xml");

    @Override
    protected void map(ImmutableBytesWritable key, Result value,
                       Context context) throws IOException, InterruptedException {
        NavigableMap<byte[], byte[]> navigableMap = value.getFamilyMap(family.getBytes());
        String catalogCode = Bytes.toString(navigableMap.get(catalogCodeByte));
        if ("0101".equals(catalogCode)) {
            xmlModel.setCatalogCode(catalogCode);
            xmlModel.setXml(Bytes.toString(navigableMap.get(xmlByte)));
            text.set(value.getRow());
            context.write(text, xmlModel);
        }
    }

}
