package com.antin.mr.test.tomysql5;

import com.antin.mr.test.tomysql4.Op0101Model;
import com.antin.mr.test.tomysql4.ParseXml;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by Administrator on 2017/3/9.
 */
public class JdbcTestXml {

    static Logger log = Logger.getLogger(JdbcTestXml.class);

    public static void main(String[] args) {
        Connection connection = null;
        String sql = "select  t.xml.getclobval() xml from SEHR_XMAN_EHR_0 t";
        try {
            connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                String xml = rs.getString(1);
                if (xml != null && !"".equals(xml)) {
                    Op0101Model op0101Model = ParseXml.parseOp0101Model(xml);
                    System.out.println(op0101Model);
                }
            }
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
            String userName = "root";
            String password = "root";
            String url = "jdbc:mysql://192.168.1.77:3306/hbase";
            Class.forName("com.mysql.jdbc.Driver");
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
