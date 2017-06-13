package com.antin.es.test.xml2json;

import com.alibaba.fastjson.JSONObject;
import com.antin.es.util.JdbcUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.HppcMaps;
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
 * Created by Administrator on 2017/4/24.
 */
public class Oracle2es_json {
    public static void main(String[] args) throws Exception {
        query();

        // put(maps);


    }

    static int count = 0;

    public static void put(List<Map<String, Object>> maps) {
        try {
            Settings.Builder settings = Settings.builder().put("cluster.name", "zoe-es");
            TransportClient client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.88"), Integer.parseInt("9300")));

            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> o = maps.get(i);
                Object xml = o.get("XML");
                try {
                    IndexResponse response = client.prepareIndex("sehr_xman_ehr_0_json", "article").setSource(maps.get(i)).get();
                    if (response.isCreated()) {
                        System.out.println(count++);
                    }
                } catch (Exception e) {
                    System.out.println(xml);
                    e.printStackTrace();
                }

            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void query() throws Exception {
        String sqlStr = "select xman_id, event, catalog_code, serial, content, t.xml.getclobval() xml, compression, encryption, status, version, title, commit_time, istemp from SEHR_XMAN_EHR_0 t";
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
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        JSONObject json = new JSONObject();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String colsName = metaData.getColumnName(i + 1);
                Object colsValue;
                if ("xml".equalsIgnoreCase(colsName)) {
                    String s = resultSet.getString(colsName);
                    if (StringUtils.isEmpty(s))
                        colsValue = json;
                    else
                        colsValue = XmlConverUtil.xmltoJsonObject(s);
                } else
                    colsValue = resultSet.getObject(colsName);
                if (colsValue == null) {
                   // colsValue = json;
                    colsValue = "";
                }
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
}
