package com.antin.es.test.oracle2es;

import com.antin.es.util.JdbcUtil;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
public class Oracle2Es {
    //http://192.168.2.88:9200/sehr_xman_ehr/sehr_xman_ehr_0

    //es索引名称（关系型数据库的中数据库名）dm_all_rpt_fee_day_not_analyzed
    // private static final String index = "dm_all_rpt_fee_day";
    private static final String index = "dm_all_rpt_fee_day_not_analyzed";
    //es类型（关系型数据库的中表名）
    private static final String type = "article";

    //打印条数
    private static Long count = 1L;

    public static void main(String[] args) throws Exception {

        //String sqlStr = "select xman_id, event, catalog_code, serial, content, t.xml.getclobval() xml, compression, encryption, status, version, title, commit_time, istemp from SEHR_XMAN_EHR_0 t";

        String sqlStr = "select * from dm_all_rpt_fee_day t";

        findResult(sqlStr, null);
    }


    /**
     * 执行查询操作
     *
     * @param sql    sql语句
     * @param params 执行参数
     * @return
     * @throws SQLException
     */
    public static void findResult(String sql, List<?> params)
            throws SQLException {

        // 定义sql语句的执行对象
        PreparedStatement pstmt;

        // 定义查询返回的结果集合
        ResultSet resultSet;
        JdbcUtil jdbcUtil = new JdbcUtil();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = jdbcUtil.getConnection().prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++)
                pstmt.setObject(index++, params.get(i));
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String colsName = metaData.getColumnName(i + 1);
                Object colsValue = null;
                if ("xml".equalsIgnoreCase(colsName))
                    colsValue = resultSet.getString(colsName);
                else
                    colsValue = resultSet.getObject(colsName);
                if (colsValue == null)
                    colsValue = "";

                map.put(colsName, colsValue);
            }
            list.add(map);
            if (list.size() >= 1000) {
                put(list);
                list.clear();
            }
        }
        put(list);
    }

    public static void put(List<Map<String, Object>> maps) {
        TransportClient client = null;
        try {
            Settings.Builder settings = Settings.builder().put("cluster.name", "zoe-es");
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.88"), Integer.parseInt("9300")));

            for (int i = 0; i < maps.size(); i++) {
                IndexResponse response = client.prepareIndex(index, type).setSource(maps.get(i)).get();
                if (response.isCreated()) {
                    if (count != null)
                        System.out.println(count++);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null)
                client.close();
        }

    }
}
