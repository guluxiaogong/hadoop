package com.antin.mr.test.tohbase3;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/2/21.
 */
public class JdbcUtil {
    private final static Log log = LogFactory.getLog(JdbcUtil.class);
    private static Map<String, String> labels = new ConcurrentHashMap<>();

    public static JSONArray queryAsJson(String sql, Object[] args) throws Exception {

        if (log.isDebugEnabled())
            log.debug("执行SQL[{" + sql + "}:{" + args.toString() + "}]检索操作。");
        Connection connection = null;
        try {
            connection = getConnection();
            Statement pstmt = connection.createStatement();
            ResultSet rs = pstmt.executeQuery(sql);
            JSONArray jsonArray = new JSONArray();

            //rs.next();

            while (rs.next()) {
                int e = rs.getMetaData().getColumnType(1);

                rs.getTimestamp("commit_time").getTime();
                getSplitter(e);
            }


            //setArgs(pstmt, args);
            //JSONArray array = queryAsJson(pstmt.executeQuery());
            pstmt.close();

            return jsonArray;
        } catch (SQLException e) {
            log.warn(e + "执行SQL[{" + sql + "}:{" + args + "}]检索时发生异常！");

            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException var10) {

                }
            }
        }
    }

    private static void getSplitter(int sqlDataType) {
        switch (sqlDataType) {
            case -7:
            case 16:
                log.info("==============16==============");
                break;
            case -6:
            case -5:
            case 4:
            case 5:
                log.info("==============5==============");
                break;
            case -1:
            case 1:
            case 12:
                log.info("==============12==============");
                break;
            case 2:
            case 3:
                log.info("==============3==============");
                break;
            case 6:
            case 7:
            case 8:
                log.info("==============8==============");
                break;
            case 91:
            case 92:
            case 93:
                log.info("==============93==============");
                break;
            default:
                log.info("=============================");
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            String userName = "sehr";
            String password = "sehr";
            String url = "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, userName, password);
            if (connection == null) {
                throw new IllegalStateException("Connection returned by DataSource [" + url + "] was null");
            }
            return connection;
        } catch (SQLException var11) {
            throw new RuntimeException("Could not get database url", var11);
        } catch (ClassNotFoundException cfe) {
            cfe.printStackTrace();
        }
        return null;
    }

    private static JSONArray queryAsJson(ResultSet rs) throws SQLException {
        JSONArray array = new JSONArray();
        List<String> names = new ArrayList<>();
        int column = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= column; i++)
            names.add(rs.getMetaData().getColumnLabel(i));
        for (; rs.next(); ) {
            JSONObject object = new JSONObject();
            for (String name : names)
                object.put(formatColumnLabel(name), toString(rs.getObject(name)));
            array.add(object);
        }
        rs.close();

        return array;
    }

    private static String formatColumnLabel(String label) {
        if (label == null || "".equals(label))
            return label;

        String string = labels.get(label);
        if (string != null)
            return string;

        StringBuffer sb = new StringBuffer();
        boolean line = false;
        for (char ch : label.toLowerCase().toCharArray()) {
            if (ch == '_') {
                line = true;

                continue;
            }

            if (line) {
                line = false;
                sb.append((char) (ch >= 'a' && ch <= 'z' ? (ch - 'a' + 'A') : ch));

                continue;
            }

            sb.append(ch);
        }
        string = sb.toString();
        labels.put(label, string);

        return string;
    }

    /**
     * 设置参数集。
     *
     * @param pstmt PreparedStatement实例。
     * @param args  参数集。
     * @throws SQLException
     */
    private static void setArgs(PreparedStatement pstmt, Object[] args) throws SQLException {
        if (args == null || args.length == 0)
            return;

        for (int i = 0; i < args.length; i++)
            pstmt.setObject(i + 1, args[i]);
    }


    private static String toString(Object object) {//
        if (object == null || "".equals(object))
            return "";

        if (object.getClass().isArray()) {
            StringBuilder sb = new StringBuilder();
            for (int length = java.lang.reflect.Array.getLength(object), i = 0; i < length; i++)
                sb.append(',').append(java.lang.reflect.Array.get(object, i));

            return sb.substring(1);
        }

        if (object instanceof Iterable) {
            StringBuilder sb = new StringBuilder();
            ((Iterable) object).forEach(obj -> sb.append(',').append(toString(obj)));

            return sb.substring(1);
        }

        if (object instanceof Map) {
            StringBuilder sb = new StringBuilder();
            ((Map) object).forEach((key, value) -> sb.append(',').append(toString(key)).append('=').append(toString(value)));

            return sb.substring(1);
        }

        if (object instanceof java.sql.Date)
            return toString((java.sql.Date) object, "yyyy-MM-dd");

        if (object instanceof Timestamp)
            return toString((Timestamp) object, "yyyy-MM-dd HH:mm:ss");

        return object.toString();
    }

    private static String toString(java.util.Date date, String format) {
        return date == null ? "" : getDateFormat(format).format(date);
    }

    private static FastDateFormat getDateFormat(String format) {
        return FastDateFormat.getInstance(format);
    }


    public static ResultSet getResultSet(String sql, Object[] args) {//测试用
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
//            while (rs.next()) {
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        // 关闭记录集
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭声明
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 关闭链接对象
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
