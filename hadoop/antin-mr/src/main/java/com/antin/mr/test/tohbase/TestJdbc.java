package com.antin.mr.test.tohbase;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/3/6.
 */
public class TestJdbc {
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

    public static void main(String[] args) {//测试用
        String driver = "oracle.jdbc.driver.OracleDriver";
        String passwrod = "sehr";
        String userName = "sehr";
        String url = "jdbc:oracle:thin:@192.168.0.91:1521:xmhealth";
        String sql = "select xman_id,event,catalog_code,serial,content,t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp from sehr_xman_ehr_0 t where catalog_code='0201'";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, userName,
                    passwrod);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("==============================================================================================================================================================");
                xmanId = rs.getString(1);
                event = rs.getString(2);
                catalogCode = rs.getString(3);
                serial = rs.getLong(4);
                Reader inReader;
                try {
                    inReader = rs.getCharacterStream(5);
                    BufferedReader br = new BufferedReader(inReader);
                    String s = br.readLine();
                    StringBuffer sb = new StringBuffer();
                    while (s != null) {
                        sb.append(s);
                        s = br.readLine();
                    }
                    content = sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                xml = rs.getString(6);
                compression = rs.getInt(7);
                Encryption = rs.getInt(8);
                status = rs.getInt(9);
                version = rs.getString(10);
                title = rs.getString(11);
                Timestamp timestamp = rs.getTimestamp(12);
                commitTime = timestamp == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                istemp = rs.getInt(13);

                System.out.println("SehrXmanEhrModel{" +
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
