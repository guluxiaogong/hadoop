package com.antin.mr.test.tomysql3;

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
public class HMap extends TableMapper<Text, SehrXmanEhrModel> {
    public static String family = "info";
    SehrXmanEhrModel sxem = new SehrXmanEhrModel();

    @Override
    protected void map(ImmutableBytesWritable key, Result value,
                       Context context) throws IOException, InterruptedException {
        NavigableMap<byte[], byte[]> navigableMap = value.getFamilyMap(family.getBytes());
        byte[] catalogCode = navigableMap.get(Bytes.toBytes("catalog_code"));
        String code = Bytes.toString(catalogCode);
        if (code.equals("0101")) {
            byte[] r = value.getRow();

            byte[] serial = navigableMap.get(Bytes.toBytes("serial"));
            byte[] content = navigableMap.get(Bytes.toBytes("content"));
            byte[] xml = navigableMap.get(Bytes.toBytes("xml"));
            byte[] compression = navigableMap.get(Bytes.toBytes("compression"));
            byte[] encryption = navigableMap.get(Bytes.toBytes("encryption"));
            byte[] status = navigableMap.get(Bytes.toBytes("status"));
            byte[] version = navigableMap.get(Bytes.toBytes("version"));
            byte[] title = navigableMap.get(Bytes.toBytes("title"));
            byte[] commitTime = navigableMap.get(Bytes.toBytes("commit_time"));
            byte[] istemp = navigableMap.get(Bytes.toBytes("istemp"));

            sxem.setCatalogCode(code);
            sxem.setSerial(Bytes.toLong(serial));
            sxem.setContent(Bytes.toString(content));
            sxem.setXml(Bytes.toString(xml));
            try {
                sxem.setCompression(Bytes.toInt(compression));
                sxem.setEncryption(Bytes.toInt(encryption));
                sxem.setStatus(Bytes.toInt(status));
            } catch (Exception e) {
                System.out.println("==========================");
                e.printStackTrace();
            }

            sxem.setVersion(Bytes.toString(version));
            sxem.setTitle(Bytes.toString(title));
            sxem.setCommitTime(Bytes.toString(commitTime));
            try {
                sxem.setIstemp(Bytes.toInt(istemp));
            } catch (Exception e) {
                System.out.println("***************************");
                e.printStackTrace();
            }

            context.write(new Text(r), sxem);
        }
    }

}
