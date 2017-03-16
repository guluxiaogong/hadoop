package com.antin.hbase.test.io;

import com.antin.hbase.test.crud.HBaseCurd;
import net.sf.json.JSONArray;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.mapreduce.Job;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/28.
 */
public class PutDemo2 {
    // 声明静态配置
    static Configuration conf = null;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.master", "zoe01:16010");
        conf.set("hbase.zookeeper.quorum", "zoe01");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        String tableName = "table02";
        //put1(tableName);
        //HBaseCurd.delRow(tableName, "098a01b6-957d-409e-897a-70d7911b6843");
        //HBaseCurd.getAllRows(tableName);
        // HBaseCurd.getRow(tableName, "098a01b6-957d-409e-897a-70d7911b6843");
        //testXml();
        Configuration conf = HBaseConfiguration.create();
        Job job = Job.getInstance(conf);
        HTable hTable = new HTable(conf, "tb1");
        HFileOutputFormat.configureIncrementalLoad(job, hTable);
        LoadIncrementalHFiles loader = new LoadIncrementalHFiles(conf);
        loader.doBulkLoad(new Path("hdfs://cluster1/db2/info/7d9081719137418fa97b77693d144bb6"), hTable);
        System.out.println(formatDuring(System.currentTimeMillis() - start));
    }

    public static void put2(String tableName) {
        try {

            // String sql = "select xman_id, event, catalog_code, serial, content, t.xml.getclobval() xml, compression, encryption, status, version, title, commit_time, istemp from SEHR_XMAN_EHR_0 t where xman_id='1f3e91db-eb63-421c-afa2-4aa382ed6cb8'";
            String sql = "select xman_id, event, catalog_code, serial, content, t.xml.getclobval() xml, compression, encryption, status, version, title, commit_time, istemp from SEHR_XMAN_EHR_0 t";

            ResultSet rs = JdbcUtil.getResultSet(sql, null);
            while (rs.next()) {
                // HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id")), "info", "event", toString(rs.getObject("event")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "catalog_code", toString(rs.getObject("catalog_code")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "serial", toString(rs.getObject("serial")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "content", toString(rs.getObject("content")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "xml", toString(rs.getString("xml")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "compression", toString(rs.getObject("compression")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "encryption", toString(rs.getObject("encryption")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "status", toString(rs.getObject("status")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "version", toString(rs.getObject("version")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "title", toString(rs.getObject("title")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "commit_time", toString(rs.getObject("commit_time")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "istemp", toString(rs.getObject("istemp")));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static String toString(Object obj) {
        if (obj != null)
            return obj.toString();
        else
            return "";
    }

    /**
     * SEHR_V_XMAN_EHR_TOTAL
     */
    public static void put1(String tableName) {
        try {
            //String sql = "select * from SEHR_V_XMAN_EHR_TOTAL  where xman_id='098a01b6-957d-409e-897a-70d7911b6843'";
            //String sql = "select xman_id,event,catalog_code,serial,content,t.xml.getclobval() as xml,compression,encryption,status,version,title,commit_time from SEHR_V_XMAN_EHR_TOTAL t  where xman_id='098a01b6-957d-409e-897a-70d7911b6843'";
            String sql = "select xman_id,event,catalog_code,serial,content,t.xml.getclobval() as xml,compression,encryption,status,version,title,commit_time from SEHR_V_XMAN_EHR_TOTAL t";

            // 第一步：创建数据库表：“student”
            String[] columnFamilys = {"info"};
            if (!HBaseCurd.isExist(tableName))
                HBaseCurd.createTable(tableName, columnFamilys);
            ResultSet rs = JdbcUtil.getResultSet(sql, null);
            while (rs.next()) {
                // OracleResultSet ors = (OracleResultSet) rs;
                //OPAQUE op = ors.getOPAQUE("xml");
                // XMLType xml = XMLType.createXML(op);

                //String xmlString = new String(op.getBytesValue());

                //String xmlString = xml.getStringVal();
                //System.out.println(xmlString);

                // 第二步：向数据表的添加数据
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "catalog_code", toString(rs.getObject("catalog_code")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "serial", toString(rs.getObject("serial")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "content", toString(rs.getObject("content")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "xml", toString(rs.getString("xml")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "compression", toString(rs.getObject("compression")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "encryption", toString(rs.getObject("encryption")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "status", toString(rs.getObject("status")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "version", toString(rs.getObject("version")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "title", toString(rs.getObject("title")));
                HBaseCurd.addRow(tableName, toString(rs.getObject("xman_id") + toString(rs.getObject("event"))), "info", "commit_time", toString(rs.getObject("commit_time")));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testXml() throws Exception {
        String sql = "select * from SEHR_V_XMAN_EHR_TOTAL  where xman_id='098a01b6-957d-409e-897a-70d7911b6843'";
        JSONArray jsonArray = JdbcUtil.queryAsJson(sql, null);
    }


    /**
     * 要转换的毫秒数
     *
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " 天 " + hours + " 小时 " + minutes + " 分钟 "
                + seconds + " 秒 ";
    }

    /**
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }
}
