package com.antin.mr.test.tohbase6;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/3/9.
 */
public class JdbcTestBlob {
    private static Long id;
    private static String xmanId;
    private static String event;
    private static String catalogCode;
    private static Long serial;
    private static String content;
    private static String xml;
    private static Integer compression;
    private static Integer Encryption;
    private static Integer status;
    private static String version;
    private static String title;
    private static String commitTime;
    private static Integer istemp;
    static Logger log = Logger.getLogger(JdbcTestBlob.class);

    public static void main(String[] args) {
        Connection connection = null;
        String sql = "select * from sehr_xman_ehr_A_test";
        try {
            connection = getConnection();

            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                id = rs.getLong(1);
                xmanId = rs.getString(2);
                event = rs.getString(3);
                catalogCode = rs.getString(4);
                serial = rs.getLong(5);
                Reader inReader;
                StringBuffer sb = new StringBuffer();
                try {
                    inReader = rs.getCharacterStream(6);
                    BufferedReader br = new BufferedReader(inReader);
                    String s = br.readLine();

                    while (s != null) {
                        sb.append(s);
                        s = br.readLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                content = sb.toString();
                xml = rs.getString(7);
                compression = rs.getInt(8);
                Encryption = rs.getInt(9);
                status = rs.getInt(10);
                version = rs.getString(11);
                title = rs.getString(12);
                Timestamp timestamp = rs.getTimestamp(13);
                commitTime = timestamp == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                istemp = rs.getInt(14);

                if (!"".equals(content)) {
                    System.out.println("SehrXmanEhrModel{" +
                            "id='" + id + '\'' +
                            "xmanId='" + xmanId + '\'' +
                            ", event='" + event + '\'' +
                            ", catalogCode='" + catalogCode + '\'' +
                            ", serial=" + serial +
                            ", content='" + content + '\'' +
                            ", xml='" + xml + '\'' +
                            ", compression=" + compression +
                            ", Encryption=" + Encryption +
                            ", status=" + status +
                            ", version='" + version + '\'' +
                            ", title='" + title + '\'' +
                            ", commitTime='" + commitTime + '\'' +
                            ", istemp=" + istemp +
                            '}');
                }

            }


            //setArgs(pstmt, args);
            //JSONArray array = queryAsJson(pstmt.executeQuery());
            pstmt.close();

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
}
