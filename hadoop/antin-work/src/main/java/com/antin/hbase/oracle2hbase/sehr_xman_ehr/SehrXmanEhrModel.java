package com.antin.hbase.oracle2hbase.sehr_xman_ehr;

import org.apache.hadoop.hbase.procedure2.util.StringUtils;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/3/6.
 */
public class SehrXmanEhrModel implements Writable, DBWritable {
    private Long id;
    private String xmanId;
    private String event;
    private String catalogCode;
    private String xml;
    private String commitTime;

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readLong();
        this.xmanId = dataInput.readUTF();
        this.event = dataInput.readUTF();
        this.catalogCode = dataInput.readUTF();
        this.xml = dataInput.readUTF();
        this.commitTime = dataInput.readUTF();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.id);
        dataOutput.writeUTF(this.xmanId);
        dataOutput.writeUTF(this.event);
        dataOutput.writeUTF(this.catalogCode);
        dataOutput.writeUTF(this.xml);
        dataOutput.writeUTF(this.commitTime);
    }

    @Override
    public void write(PreparedStatement pstm) throws SQLException {
        pstm.setLong(1, this.id);
        pstm.setString(2, this.xmanId);
        pstm.setString(3, this.event);
        pstm.setString(4, this.catalogCode);
        pstm.setString(5, this.xml);
        Timestamp timestamp = null;
        if (!StringUtils.isEmpty(this.commitTime)) {
            try {
                timestamp = (Timestamp) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.commitTime);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        pstm.setTimestamp(6, timestamp);
    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.id = rs.getLong(1);
        this.xmanId = rs.getString(2);
        this.event = rs.getString(3);
        this.catalogCode = rs.getString(4);
        this.xml = rs.getString(5);
        Timestamp timestamp = rs.getTimestamp(6);
        this.commitTime = timestamp == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXmanId() {
        return xmanId;
    }

    public void setXmanId(String xmanId) {
        this.xmanId = xmanId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
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

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    @Override
    public String toString() {
        return "SehrXmanEhrModel{" +
                "id=" + id +
                ", xmanId='" + xmanId + '\'' +
                ", event='" + event + '\'' +
                ", catalogCode='" + catalogCode + '\'' +
                ", xml='" + xml + '\'' +
                ", commitTime='" + commitTime + '\'' +
                '}';
    }
}
