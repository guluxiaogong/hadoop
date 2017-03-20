package com.antin.hbase.oracle2hbase.sehr_xman_event;

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
public class SehrXmanEventModel implements Writable, DBWritable {
    private String id;
    private String xmanId;
    private String event;
    private String commitTime;

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        dataInput.readUTF();
        this.id = dataInput.readUTF();
        this.xmanId = dataInput.readUTF();
        this.event = dataInput.readUTF();
        this.commitTime = dataInput.readUTF();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.id);
        dataOutput.writeUTF(this.xmanId);
        dataOutput.writeUTF(this.event);
        dataOutput.writeUTF(this.commitTime);

    }

    @Override
    public void write(PreparedStatement pstm) throws SQLException {
        pstm.setString(1, this.id);
        pstm.setString(2, this.xmanId);
        pstm.setString(3, this.event);
        Timestamp timestamp = null;
        if (!StringUtils.isEmpty(this.commitTime)) {
            try {
                timestamp = (Timestamp) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.commitTime);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        pstm.setTimestamp(4, timestamp);
    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.id = rs.getString(1);
        this.xmanId = rs.getString(2);
        this.event = rs.getString(3);
        Timestamp timestamp = rs.getTimestamp(4);
        this.commitTime = timestamp == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    @Override
    public String toString() {
        return "SehrXmanEventModel{" +
                "id='" + id + '\'' +
                ", xmanId='" + xmanId + '\'' +
                ", event='" + event + '\'' +
                ", commitTime='" + commitTime + '\'' +
                '}';
    }
}
