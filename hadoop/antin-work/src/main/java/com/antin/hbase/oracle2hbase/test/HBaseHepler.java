package com.antin.hbase.oracle2hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/2/28.
 * 对hbase简单的增、删、改、查
 */
public class HBaseHepler {
    private static Logger log = Logger.getLogger(HBaseHepler.class);

    // 声明静态配置
    static Configuration conf = null;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.master", "zoe01:16010");
        conf.set("hbase.zookeeper.quorum", "zoe01");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
    }

    // 主函数
    public static void main(String[] args) throws Exception {
        try {
            /*
             *第一步：创建数据库表："student"
             */
            String tableName = "sehr_ehr_catalog";//表名
            // String[] columnFamilys = {"info", "course"};//列族
            //           createTable(tableName, columnFamilys);

            /*
             *第二步：向数据表的添加数据
             */
            // 添加第一行数据
            if (isExist(tableName)) {
//                addRow(tableName, "rk0001", "info", "age", "20");//addRow(表名,行键,列族,列,值)
//                addRow(tableName, "rk0001", "info", "sex", "boy");
//                addRow(tableName, "rk0001", "course", "china", "97");
//                addRow(tableName, "rk0001", "course", "math", "128");
//                addRow(tableName, "rk0001", "course", "english", "85");
//                // 添加第二行数据
//                addRow(tableName, "rk0002", "info", "age", "19");
//                addRow(tableName, "rk0002", "info", "sex", "boy");
//                addRow(tableName, "rk0002", "course", "china", "90");
//                addRow(tableName, "rk0002", "course", "math", "120");
//                addRow(tableName, "rk0002", "course", "english", "90");
//                // 添加第三行数据
//                addRow(tableName, "rk0003", "info", "age", "18");
//                addRow(tableName, "rk0003", "info", "sex", "girl");
//                addRow(tableName, "rk0003", "course", "china", "100");
//                addRow(tableName, "rk0003", "course", "math", "100");
//                addRow(tableName, "rk0003", "course", "english", "99");

                //添加多列
//                Map<String, String> qualifiers = new HashMap<>();
//                qualifiers.put("age", "25");
//                qualifiers.put("name", "lisi");
//                qualifiers.put("city", "xiamen");
//                addRows(tableName, "rk0001", "info", qualifiers);

                /*
                 *第三步：获取一条数据
                 */
//                getRow(tableName, "rk0001");//getRow(表名,行键)

                /*
                 *第四步：获取所有数据
                 */
                //getAllRows(tableName);
                //getAllRows(tableName);

                /*
                 *第五步：根据rowkey范围遍历查询
                 */
                //getResultScann("student", "rk0001", "rk0003");//getResultScann(表名,开始行键,结束行键) 注:不包含结束行键

                /*
                 *第六步：查询某一列的值(只会查出最新版本)
                 */
                getResultByColumn(tableName, "0301", "name", "");//getResultByColumn(表名,行键,列族,列)

                /*
                 *第六步：查询某列的多版本
                 * 修改表结构，让Hbase表支持存储3个VERSIONS的版本列数据(alter 'student',{NAME=>'info',VERSIONS=>3})
                 */
                //getResultByVersion("student", "rk0001", "info", "age", 3);//getResultByVersion(表名,行键,列族,列,版本数)

                /*
                 *第七步：更新列
                 */
                //updateTable("student", "rk0001", "info", "age", "50");

                /*
                 *第八步：删除一条数据
                 */
                //delRow(tableName, "rk0001");

                /*
                 *第九步：删除多条数据
                 */
                //String rows[] = new String[]{"rk0001", "rk0002"};
                //delMultiRows(tableName, rows);

                /*
                 *删除一列数据
                 */
                //deleteColumn("student", "rk0001", "info", "age");

                /*
                 *删除一个列族数据
                 */
                //deleteFamily("student", "rk0001", "info");
                /*
                 *第十步：删除表
                 */
                //deleteTable(tableName);
            } else {
                log.info(tableName + "此数据库表不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断表是否存在
     *
     * @param tableName 表名
     * @return
     * @throws IOException
     */
    public static boolean isExist(String tableName) throws IOException {
        HBaseAdmin hAdmin = new HBaseAdmin(conf);
        return hAdmin.tableExists(tableName);
    }

    /**
     * 创建数据库表
     *
     * @param tableName     表名
     * @param columnFamilys 列族
     * @throws Exception
     */
    public static void createTable(String tableName, String[] columnFamilys)
            throws Exception {
        // 新建一个数据库管理员
        HBaseAdmin hAdmin = new HBaseAdmin(conf);
        if (hAdmin.tableExists(tableName)) {
            log.info("表 " + tableName + " 已存在！");
            //System.exit(0);
        } else {
            // 新建一个students表的描述
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            // 在描述里添加列族
            for (String columnFamily : columnFamilys) {
                tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            }
            // 根据配置好的描述建表
            hAdmin.createTable(tableDesc);
            log.info("创建表 " + tableName + " 成功!");
        }
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     * @throws Exception
     */
    public static void deleteTable(String tableName) throws Exception {
        // 新建一个数据库管理员
        HBaseAdmin hAdmin = new HBaseAdmin(conf);
        if (hAdmin.tableExists(tableName)) {
            // 关闭一个表
            hAdmin.disableTable(tableName);
            hAdmin.deleteTable(tableName);
            log.info("删除表 " + tableName + " 成功！");
        } else {
            log.info("删除的表 " + tableName + " 不存在！");
            System.exit(0);
        }
    }

    /**
     * 添加一条数据
     *
     * @param tableName    表名
     * @param rowkey       行键
     * @param columnFamily 列族
     * @param column       列
     * @param value        值
     * @throws Exception
     */
    public static void addRow(String tableName, String rowkey,
                              String columnFamily, String column, String value) throws Exception {
        HTable table = new HTable(conf, tableName);
        Put put = new Put(Bytes.toBytes(rowkey));// 指定行
        // 参数分别:列族、列、值
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column),
                Bytes.toBytes(value));
        table.put(put);
    }

    /**
     * 删除一条(行)数据
     *
     * @param tableName 表名
     * @param rowkey    行键
     * @throws Exception
     */
    public static void delRow(String tableName, String rowkey) throws Exception {
        HTable table = new HTable(conf, tableName);
        Delete del = new Delete(Bytes.toBytes(rowkey));
        table.delete(del);
    }

    /**
     * 删除多条数据
     *
     * @param tableName 表名
     * @param rowkeys   行键
     * @throws Exception
     */
    public static void delMultiRows(String tableName, String[] rowkeys)
            throws Exception {
        HTable table = new HTable(conf, tableName);
        List<Delete> delList = new ArrayList<Delete>();
        for (String row : rowkeys) {
            Delete del = new Delete(Bytes.toBytes(row));
            delList.add(del);
        }
        table.delete(delList);
    }

    /**
     * 删除指定的列
     *
     * @param tableName    表名
     * @param rowKey       行键
     * @param columnFamily 列族
     * @param column       列
     * @throws IOException
     */
    public static void deleteColumn(String tableName, String rowKey,
                                    String columnFamily, String column) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.deleteColumns(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        table.delete(deleteColumn);
        System.out.println(columnFamily + ":" + column + "is deleted!");
    }

    public static void deleteFamily(String tableName, String rowKey, String family)
            throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.addFamily(Bytes.toBytes(family));
        table.delete(delete);
        System.out.println("family are deleted!");
    }


    /**
     * 获取一行数据
     *
     * @param tableName 表名
     * @param rowkey    行键
     * @throws Exception
     */
    public static void getRow(String tableName, String rowkey) throws Exception {
        HTable table = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = table.get(get);
        // 输出结果,raw方法返回所有keyvalue数组
        for (KeyValue rowKV : result.raw()) {
            System.out.print("行键:" + new String(rowKV.getRow()) + " ");
            System.out.print("时间戳:" + rowKV.getTimestamp() + " ");
            System.out.print("列族名:" + new String(rowKV.getFamily()) + " ");
            System.out.print("列名:" + new String(rowKV.getQualifier()) + " ");
            System.out.println("值:" + new String(rowKV.getValue()));
        }
    }

    /**
     * 获取所有数据
     *
     * @param tableName 表名
     * @throws Exception
     */
    public static void getAllRows(String tableName) throws Exception {
        HTable table = new HTable(conf, tableName);
        Scan scan = new Scan();
        ResultScanner results = table.getScanner(scan);
        // 输出结果
        for (Result result : results) {
            for (KeyValue rowKV : result.raw()) {
                System.out.print("行键:" + new String(rowKV.getRow()) + " ");
                System.out.print("时间戳:" + rowKV.getTimestamp() + " ");
                System.out.print("列族名:" + new String(rowKV.getFamily()) + " ");
                System.out.print("列名:" + new String(rowKV.getQualifier()) + " ");
                System.out.println("值:" + new String(rowKV.getValue()));
            }
        }
    }

    /**
     * 根据rowkey范围查询
     *
     * @param tableName   表名
     * @param startRowkey 起始行键
     * @param stopRowkey  结束行键
     * @throws IOException
     */
    public static void getResultScann(String tableName, String startRowkey,
                                      String stopRowkey) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(startRowkey));
        scan.setStopRow(Bytes.toBytes(stopRowkey));
        ResultScanner rs = null;
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.list()) {
                    System.out.print("行键:" + Bytes.toString(kv.getRow()) + " ");
                    System.out.print("时间戳:" + kv.getTimestamp() + " ");
                    System.out.print("列族名:" + Bytes.toString(kv.getFamily()) + " ");
                    System.out.print("列名:" + Bytes.toString(kv.getQualifier()) + " ");
                    System.out.println("值:" + Bytes.toString(kv.getValue()) + " ");
                }
            }
        } finally {
            rs.close();
        }
    }

    /**
     * 查询表中的某一列
     *
     * @param tableName  表名
     * @param rowKey     行键
     * @param familyName 列族
     * @param columnName 列
     * @throws IOException
     */
    public static void getResultByColumn(String tableName, String rowKey,
                                         String familyName, String columnName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
        Result result = table.get(get);
        for (KeyValue kv : result.list()) {
            System.out.print("行键:" + Bytes.toString(kv.getRow()) + " ");
            System.out.print("时间戳:" + kv.getTimestamp() + " ");
            System.out.print("列族名:" + Bytes.toString(kv.getFamily()) + " ");
            System.out.print("列名:" + Bytes.toString(kv.getQualifier()) + " ");
            System.out.println("值:" + Bytes.toString(kv.getValue()) + " ");
        }
    }

    /**
     * 查询某列数据的多个版本
     *
     * @param tableName  表名
     * @param rowKey     行键
     * @param familyName 列族
     * @param columnName 列
     * @param num        版本数
     * @throws IOException
     */
    public static void getResultByVersion(String tableName, String rowKey,
                                          String familyName, String columnName, int num) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
        get.setMaxVersions(num);
        Result result = table.get(get);
        for (KeyValue kv : result.list()) {
            System.out.print("行键:" + Bytes.toString(kv.getRow()) + " ");
            System.out.print("时间戳:" + kv.getTimestamp() + " ");
            System.out.print("列族名:" + Bytes.toString(kv.getFamily()) + " ");
            System.out.print("列名:" + Bytes.toString(kv.getQualifier()) + " ");
            System.out.println("值:" + Bytes.toString(kv.getValue()) + " ");
        }
    }

    /**
     * 更新表中的某一列
     *
     * @param tableName  表名
     * @param rowKey     行键
     * @param familyName 列族
     * @param columnName 列
     * @param value      值
     * @throws IOException
     */
    public static void updateTable(String tableName, String rowKey,
                                   String familyName, String columnName, String value)
            throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName), Bytes.toBytes(value));
        table.put(put);
        System.out.println("update table Success!");
    }

    /**
     * 添加多列
     *
     * @param tableName  表名
     * @param rowkey     行键
     * @param familyName 列族
     * @param qualifiers 键值对 {列：值}
     */
    public static void addRows(String tableName, String rowkey, String familyName, Map<String, String> qualifiers) {

        TableName tableN = TableName.valueOf(tableName);
        byte[] familyNameByte = Bytes.toBytes(familyName);

        Table table = null;
        try {
            Connection conn = ConnectionFactory.createConnection(conf);//新api
            table = conn.getTable(tableN);
            List<Put> puts = new ArrayList<>();
            Put put = new Put(Bytes.toBytes(rowkey));
            Set<String> set = qualifiers.keySet();
            for (String key : set)
                put.addColumn(familyNameByte, Bytes.toBytes(key), Bytes.toBytes(qualifiers.get(key)));

            puts.add(put);
            table.put(puts);
            log.info("Put successfully.");
        } catch (IOException e) {
            log.error("Put failed ", e);
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    log.error("Close table failed ", e);
                }
            }
        }
        log.info("Exiting testPut.");
    }
}
