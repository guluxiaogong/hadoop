package com.antin.hbase.test.io;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */
public class PutDemo {
    private final static Log log = LogFactory.getLog(PutDemo.class);
    // 声明静态配置
    private static Configuration conf = null;
    private static Connection conn = null;

    PutDemo() throws IOException {

    }

    private static void init() throws IOException {
        conf = HBaseConfiguration.create();
//        conf.set("hbase.master", "192.168.2.88:16010");
//        conf.set("hbase.zookeeper.quorum", "192.168.2.88");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");

        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "zoe01");
        conf.set("hbase.master", "zoe01:16010");

        conn = ConnectionFactory.createConnection(conf);
    }

    public static void main(String[] args) throws Exception{
        init();
       // creatTable("table02", new String[]{"info"});


        String sql = "select * from SEHR_V_XMAN_EHR_TOTAL  where xman_id='098a01b6-957d-409e-897a-70d7911b6843'";

        JSONArray jsonArray = JdbcUtil.queryAsJson(sql, null);

        putInHBase2(jsonArray);

    }
    public static void creatTable(String tableName, String[] family)
            throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor desc = new HTableDescriptor(tableName);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tableName)) {
            System.out.println("table Exists!");
            System.exit(0);
        } else {
            admin.createTable(desc);
            System.out.println("create table Success!");
        }
    }

    public static void putInHBase2(JSONArray jsonArray) {
    /*
     *  create 'table01','xman_id','info'
     */
        log.info("Entering testPut.");
        TableName tableName = TableName.valueOf("table02");
        byte[] familyName = Bytes.toBytes("info");
        byte[][] qualifiers = {Bytes.toBytes("xman_id"), Bytes.toBytes("event"), Bytes.toBytes("catalog_code"),
                Bytes.toBytes("serial"), Bytes.toBytes("content"), Bytes.toBytes("xml"), Bytes.toBytes("compression"), Bytes.toBytes("encryption")
                , Bytes.toBytes("status"), Bytes.toBytes("version"), Bytes.toBytes("title"), Bytes.toBytes("commit_time")};

        Table table = null;
        try {
            table = conn.getTable(tableName);
            List<Put> puts = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                if(i==0){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Put put = new Put(Bytes.toBytes(obj.getString("xman_id")+i));
                    put.addColumn(familyName, qualifiers[1], Bytes.toBytes(obj.getString("event")));
                    put.addColumn(familyName, qualifiers[2], Bytes.toBytes(obj.getString("catalog_code")));
                    put.addColumn(familyName, qualifiers[3], Bytes.toBytes(obj.getString("serial")));
                    put.addColumn(familyName, qualifiers[4], Bytes.toBytes(obj.getString("content")));

                    put.addColumn(familyName, qualifiers[5], Bytes.toBytes(obj.getString("xml")));
                    put.addColumn(familyName, qualifiers[6], Bytes.toBytes(obj.getString("compression")));
                    put.addColumn(familyName, qualifiers[7], Bytes.toBytes(obj.getString("encryption")));
                    put.addColumn(familyName, qualifiers[8], Bytes.toBytes(obj.getString("status")));
                    put.addColumn(familyName, qualifiers[9], Bytes.toBytes(obj.getString("version")));
                    put.addColumn(familyName, qualifiers[10], Bytes.toBytes(obj.getString("title")));
                    put.addColumn(familyName, qualifiers[11], Bytes.toBytes(obj.getString("commit_time")));
                    puts.add(put);
                }

            }


            // Submit a put request.
            table.put(puts);

            log.info("Put successfully.");
        } catch (IOException e) {
            log.error("Put failed ", e);
        } finally {
            if (table != null) {
                try {
                    // Close the HTable object.
                    table.close();
                } catch (IOException e) {
                    log.error("Close table failed ", e);
                }
            }
        }
        log.info("Exiting testPut.");
    }

    public static void putInHBase(JSONArray jsonArray) {
    /*
     *  create 'table01','xman_id','info'
     */
        log.info("Entering testPut.");
        TableName tableName = TableName.valueOf("table02");
        byte[] familyName = Bytes.toBytes("info");
        byte[][] qualifiers = {Bytes.toBytes("name"), Bytes.toBytes("gender"), Bytes.toBytes("age"),
                Bytes.toBytes("address")};

        Table table = null;
        try {
            table = conn.getTable(tableName);
            List<Put> puts = new ArrayList<>();
            Put put = new Put(Bytes.toBytes("012005000201"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Zhang San"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Male"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("19"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Shenzhen, Guangdong"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000202"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Li Wanting"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Female"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("23"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Shijiazhuang, Hebei"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000203"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Wang Ming"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Male"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("26"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Ningbo, Zhejiang"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000204"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Li Gang"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Male"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("18"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Xiangyang, Hubei"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000205"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Zhao Enru"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Female"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("21"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Shangrao, Jiangxi"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000206"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Chen Long"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Male"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("32"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Zhuzhou, Hunan"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000207"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Zhou Wei"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Female"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("29"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Nanyang, Henan"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000208"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Yang Yiwen"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Female"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("30"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Kaixian, Chongqing"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000209"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Xu Bing"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Male"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("26"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Weinan, Shaanxi"));
            puts.add(put);

            put = new Put(Bytes.toBytes("012005000210"));
            put.addColumn(familyName, qualifiers[0], Bytes.toBytes("Xiao Kai"));
            put.addColumn(familyName, qualifiers[1], Bytes.toBytes("Male"));
            put.addColumn(familyName, qualifiers[2], Bytes.toBytes("25"));
            put.addColumn(familyName, qualifiers[3], Bytes.toBytes("Dalian, Liaoning"));
            puts.add(put);

            // Submit a put request.
            table.put(puts);

            log.info("Put successfully.");
        } catch (IOException e) {
            log.error("Put failed ", e);
        } finally {
            if (table != null) {
                try {
                    // Close the HTable object.
                    table.close();
                } catch (IOException e) {
                    log.error("Close table failed ", e);
                }
            }
        }
        log.info("Exiting testPut.");
    }
}
