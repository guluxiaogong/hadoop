package com.antin.mr.demo.io.hbase2mysql;

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
    private String xmanId;
    private String event;
    private String catalogCode;
    private Long serial;
    private String content;
    private String xml;
    private Integer compression;
    private Integer Encryption;
    private Integer status;
    private String version;
    private String title;
    private String commitTime;
    private Integer istemp;


    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.xmanId = dataInput.readUTF();
        this.event = dataInput.readUTF();
        this.catalogCode = dataInput.readUTF();
        this.serial = dataInput.readLong();
        this.content = dataInput.readUTF();
        this.xml = dataInput.readUTF();
        this.compression = dataInput.readInt();
        this.Encryption = dataInput.readInt();
        this.status = dataInput.readInt();
        this.version = dataInput.readUTF();
        this.title = dataInput.readUTF();
        this.commitTime = dataInput.readUTF();
        this.istemp = dataInput.readInt();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.xmanId);
        dataOutput.writeUTF(this.event);
        dataOutput.writeUTF(this.catalogCode);
        dataOutput.writeLong(this.serial);
        dataOutput.writeUTF(this.content);
        dataOutput.writeUTF(this.xml);
        dataOutput.writeInt(this.compression);
        dataOutput.writeInt(this.Encryption);
        dataOutput.writeInt(this.status);
        dataOutput.writeUTF(this.version);
        dataOutput.writeUTF(this.title);
        dataOutput.writeUTF(this.commitTime);
        dataOutput.writeInt(this.istemp);
    }

    @Override
    public void write(PreparedStatement pstm) throws SQLException {
        pstm.setString(1, this.xmanId);
        pstm.setString(2, this.event);
        pstm.setString(3, this.catalogCode);
        pstm.setLong(4, this.serial);
        pstm.setString(5, this.content);
        pstm.setString(6, this.xml);
        pstm.setInt(7, this.compression);
        pstm.setInt(8, this.Encryption);
        pstm.setInt(9, this.status);
        pstm.setString(10, this.version);
        pstm.setString(11, this.title);
        Timestamp timestamp = null;
        if (!StringUtils.isEmpty(this.commitTime)) {
            try {
                timestamp = (Timestamp) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.commitTime);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        pstm.setTimestamp(12, timestamp);
        pstm.setInt(13, this.istemp);

    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.xmanId = rs.getString(1);
        this.event = rs.getString(2);
        this.catalogCode = rs.getString(3);
        this.serial = rs.getLong(4);
        Reader inReader;
        StringBuffer sb = new StringBuffer();
        try {
            inReader = rs.getCharacterStream(5);
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
        this.xml = rs.getString(6);
        this.compression = rs.getInt(7);
        this.Encryption = rs.getInt(8);
        this.status = rs.getInt(9);
        this.version = rs.getString(10);
        this.title = rs.getString(11);
        Timestamp timestamp = rs.getTimestamp(12);
        this.commitTime = timestamp == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        this.istemp = rs.getInt(13);
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

    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public Integer getCompression() {
        return compression;
    }

    public void setCompression(Integer compression) {
        this.compression = compression;
    }

    public Integer getEncryption() {
        return Encryption;
    }

    public void setEncryption(Integer encryption) {
        Encryption = encryption;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public Integer getIstemp() {
        return istemp;
    }

    public void setIstemp(Integer istemp) {
        this.istemp = istemp;
    }

    @Override
    public String toString() {
        return "SehrXmanEhrModel{" +
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
                '}';
    }
}
