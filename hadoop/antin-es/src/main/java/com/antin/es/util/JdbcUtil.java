package com.antin.es.util;

/**
 * Created by Administrator on 2017/4/17.
 */

import com.alibaba.fastjson.JSONObject;
import com.antin.es.test.xml2json.XmlConverUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.InputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcUtil {

    // 表示定义数据库的用户名
    private static String USERNAME;

    // 定义数据库的密码
    private static String PASSWORD;

    // 定义数据库的驱动信息
    private static String DRIVER;

    // 定义访问数据库的地址
    private static String URL;

    // 定义数据库的链接
    private Connection connection;

    // 定义sql语句的执行对象
    private PreparedStatement pstmt;

    // 定义查询返回的结果集合
    private ResultSet resultSet;

    static {
        //加载数据库配置信息，并给相关的属性赋值
        loadConfig();
    }

    /**
     * 加载数据库配置信息，并给相关的属性赋值
     */
    public static void loadConfig() {
        try {
            InputStream inStream = JdbcUtil.class
                    .getResourceAsStream("/jdbc.properties");
            Properties prop = new Properties();
            prop.load(inStream);
            USERNAME = prop.getProperty("jdbc.username");
            PASSWORD = prop.getProperty("jdbc.password");
            DRIVER = prop.getProperty("jdbc.driver");
            URL = prop.getProperty("jdbc.url");
        } catch (Exception e) {
            throw new RuntimeException("读取数据库配置文件异常！", e);
        }
    }

    public JdbcUtil() {
        getConnection();
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    public Connection getConnection() {
        try {
            Class.forName(DRIVER); // 注册驱动
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 获取连接
        } catch (Exception e) {
            throw new RuntimeException("get connection error!", e);
        }
        return connection;
    }

    /**
     * 执行更新操作
     *
     * @param sql    sql语句
     * @param params 执行参数
     * @return 执行结果
     * @throws SQLException
     */
    public boolean updateByPreparedStatement(String sql, List<?> params)
            throws SQLException {
        boolean flag = false;
        int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数
        pstmt = connection.prepareStatement(sql);
        int index = 1;
        // 填充sql语句中的占位符
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;
        return flag;
    }

    /**
     * 执行查询操作
     *
     * @param sql    sql语句
     * @param params 执行参数
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findResult(String sql, List<?> params)
            throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
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
                Object colsValue = "";
                if ("xml".equalsIgnoreCase(colsName)) {
                    String s = resultSet.getString(colsName);
                    if (StringUtils.isEmpty(s))
                        colsValue = json;
                    else
                        colsValue = XmlConverUtil.xmltoJsonObject(s);
                } else
                    colsValue = resultSet.getObject(colsName);
                if (colsValue == null) {
                    colsValue = json;
                }
                map.put(colsName, colsValue);
            }
            list.add(map);
//            if (list.size() >= 1000) {
//                try {
//                    put(list);
//                    list.clear();
//                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + list.size());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


        }
        return list;
    }

//    public static void put(List<Map<String, Object>> maps) throws Exception {
//        Settings.Builder settings = Settings.builder().put("cluster.name", "zoe-es");
//        TransportClient client = TransportClient.builder().settings(settings).build()
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.88"), Integer.parseInt("9300")));
//        int count = 0;
//        for (int i = 0; i < maps.size(); i++) {
//            IndexResponse response = client.prepareIndex("sehr_xman_ehr_a", "article").setSource(maps.get(i)).get();
//            if (response.isCreated()) {
//                System.out.println("================"+count++);
//                //System.out.println("创建成功!");
//            }
//        }
//        client.close();
//    }

    /**
     * 释放资源
     */
    public void releaseConn() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JdbcUtil jdbcUtil = new JdbcUtil();
        jdbcUtil.getConnection();
        try {
            List<Map<String, Object>> result = jdbcUtil.findResult(
                    "select * from t_student", null);
            for (Map<String, Object> m : result) {
                System.out.println(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.releaseConn();
        }
    }
}

