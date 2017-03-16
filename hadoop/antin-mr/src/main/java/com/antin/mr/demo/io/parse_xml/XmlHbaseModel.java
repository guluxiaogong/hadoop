package com.antin.mr.demo.io.parse_xml;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/6.
 */
public class XmlHbaseModel implements Writable, DBWritable {
    private String catalogCode;
    private String xml;


    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.catalogCode = dataInput.readUTF();
        this.xml = dataInput.readUTF();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.catalogCode);
        dataOutput.writeUTF(this.xml);
    }

    @Override
    public void write(PreparedStatement pstm) throws SQLException {
        pstm.setString(1, this.catalogCode);
        pstm.setString(2, this.xml);

    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.catalogCode = rs.getString(1);
        this.xml = rs.getString(2);

    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    @Override
    public String toString() {
        return "XmlModel{" +
                "catalogCode='" + catalogCode + '\'' +
                ", xml='" + xml + '\'' +
                '}';
    }
}
