package com.antin.hbase.oracle2hbase.sehr_ehr_catalog;

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
public class SehrEhrCatalogModel implements Writable, DBWritable {
    private Long id;
    private String catalogCode;
    private String catalogName;
    private String quantity;
    private String xmlFormat;
    private String status;


    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readLong();
        this.catalogCode = dataInput.readUTF();
        this.catalogName = dataInput.readUTF();
        this.quantity = dataInput.readUTF();
        this.xmlFormat = dataInput.readUTF();
        this.status = dataInput.readUTF();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.id);
        dataOutput.writeUTF(this.catalogCode);
        dataOutput.writeUTF(this.catalogName);
        dataOutput.writeUTF(this.quantity);
        dataOutput.writeUTF(this.xmlFormat);
        dataOutput.writeUTF(this.status);
    }

    @Override
    public void write(PreparedStatement pstm) throws SQLException {
        pstm.setLong(1, this.id);
        pstm.setString(2, this.catalogCode);
        pstm.setString(3, this.catalogName);
        pstm.setString(4, quantity);
        pstm.setString(5, this.xmlFormat);
        pstm.setString(6, this.status);
    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.id = rs.getLong(1);
        this.catalogCode = rs.getString(2);
        this.catalogName = rs.getString(3);
        this.quantity = rs.getString(4);
        this.xmlFormat = rs.getString(5);
        this.status = rs.getString(6);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getXmlFormat() {
        return xmlFormat;
    }

    public void setXmlFormat(String xmlFormat) {
        this.xmlFormat = xmlFormat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
