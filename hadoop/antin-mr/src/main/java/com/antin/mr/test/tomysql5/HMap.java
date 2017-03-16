package com.antin.mr.test.tomysql5;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2017/3/10.
 */
public class HMap extends TableMapper<Text, Text> {
    private static Logger log = Logger.getLogger(HMap.class);
    public static String family = "info";
    byte[] xmlByte = Bytes.toBytes("xml");
    byte[] catalogCodeByte = Bytes.toBytes("catalog_code");

    Text text1 = new Text();
    Text text2 = new Text();
    int row = 0;
    Connection connection = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        connection = JdbcTestXml.getConnection();
    }

    @Override
    protected void map(ImmutableBytesWritable key, Result value,
                       Context context) throws IOException, InterruptedException {
        NavigableMap<byte[], byte[]> navigableMap = value.getFamilyMap(family.getBytes());
        String catalogCode = Bytes.toString(navigableMap.get(catalogCodeByte));
        String rowkey = Bytes.toString(value.getRow());
        byte[] xmls = navigableMap.get(xmlByte);
        Integer rows = null;
        if (xmls.length > 0) {
            if ("0101".equals(catalogCode)) {
                rows = insertOp0101Model(xmls);

            } else if ("0201".equals(catalogCode)) {
                String xml0201 = Bytes.toString(xmls);
                rows = insertIn0210Model(xml0201);
            } else {
                return;
            }
            text1.set(rowkey + "====> ");
            text2.set(rows == null ? "invalid".getBytes() : Bytes.toBytes(rows));
            log.info("====================================: " + Bytes.toString(value.getRow()) + " ==> " + (rows == null ? "invalid" : rows));
            context.write(text1, text2);
        }

    }

    public Integer insertOp0101Model(byte[] xml) {
        String sql = "insert into op_0101 (sex,birth,marriage,onset_time,treat_Time,diagnosis_date,reg,type,sec_type,sec_no,dept_name,dept_code,doctor_id,doctor,tech_title,symptom_name,symptom_code,diagnosis_name,diagnosis_icd,diagnosis_prop,result) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            XmlOp0101Model xmlOp0101Model = ParseXml.parseXmlOp0101Model(new String(xml));
            if (xmlOp0101Model != null) {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, xmlOp0101Model.getSex());
                pstmt.setString(2, xmlOp0101Model.getBirth());
                pstmt.setString(3, xmlOp0101Model.getMarriage());
                pstmt.setString(4, xmlOp0101Model.getOnsetTime());
                pstmt.setString(5, xmlOp0101Model.getTreatTime());
                pstmt.setString(6, xmlOp0101Model.getDiagnosisDate());
                pstmt.setString(7, xmlOp0101Model.getReg());
                pstmt.setString(8, xmlOp0101Model.getType());
                pstmt.setString(9, xmlOp0101Model.getSecType());
                pstmt.setString(10, xmlOp0101Model.getSecNo());
                pstmt.setString(11, xmlOp0101Model.getDeptName());
                pstmt.setString(12, xmlOp0101Model.getDeptCode());
                pstmt.setString(13, xmlOp0101Model.getDoctorId());
                pstmt.setString(14, xmlOp0101Model.getDoctor());
                pstmt.setString(15, xmlOp0101Model.getTechTitle());
                pstmt.setString(16, xmlOp0101Model.getSymptomName());
                pstmt.setString(17, xmlOp0101Model.getSymptomCode());
                pstmt.setString(18, xmlOp0101Model.getDiagnosisName());
                pstmt.setString(19, xmlOp0101Model.getDiagnosisIcd());
                pstmt.setString(20, xmlOp0101Model.getDiagnosisProp());
                pstmt.setString(21, xmlOp0101Model.getResult());
                row = pstmt.executeUpdate();
                pstmt.close();
                connection.setAutoCommit(false);
                connection.commit();
                return row;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer insertIn0210Model(String xml) {
        String sql = "insert into in_0201  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            LinkedHashMap<String, String> map = ParseXml.parseXmlIn0201Model(xml);
            if (map != null) {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                int i = 1;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    //System.out.println(entry.getKey() + "->" + entry.getValue());
                    pstmt.setString(i, entry.getValue());
                    i++;
                }
                row = pstmt.executeUpdate();
                pstmt.close();
                connection.setAutoCommit(false);
                connection.commit();
                return row;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
