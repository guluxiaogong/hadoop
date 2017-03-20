package com.antin.hbase.oracle2hbase.sehr_ehr_node;

import org.apache.hadoop.hbase.procedure2.util.StringUtils;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/3/6.
 */
public class SehrEhrNodeModel implements Writable, DBWritable {
    private Long id;
    private String catalogCode;
    private String nodePath;
    private String descs;
    private String commitTime;


    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readLong();
        this.catalogCode = dataInput.readUTF();
        this.nodePath = dataInput.readUTF();
        this.descs = dataInput.readUTF();
        this.commitTime = dataInput.readUTF();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.id);
        dataOutput.writeUTF(this.catalogCode);
        dataOutput.writeUTF(this.nodePath);
        dataOutput.writeUTF(this.descs);
        dataOutput.writeUTF(this.commitTime);
    }

    @Override
    public void write(PreparedStatement pstm) throws SQLException {
        pstm.setLong(1, this.id);
        pstm.setString(2, this.catalogCode);
        pstm.setString(3, this.nodePath);
        pstm.setString(4, descs);
        Timestamp timestamp = null;
        if (!StringUtils.isEmpty(this.commitTime)) {
            try {
                timestamp = (Timestamp) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.commitTime);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        pstm.setTimestamp(5, timestamp);
    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.id = rs.getLong(1);
        this.catalogCode = rs.getString(2);
        this.nodePath = rs.getString(3);
        this.descs = rs.getString(4);
        Timestamp timestamp = rs.getTimestamp(5);
        this.commitTime = timestamp == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
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

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    @Override
    public String toString() {
        return "SehrEhrNodeModel{" +
                "id=" + id +
                ", catalogCode='" + catalogCode + '\'' +
                ", nodePath='" + nodePath + '\'' +
                ", descs='" + descs + '\'' +
                ", commitTime='" + commitTime + '\'' +
                '}';
    }
}
