package com.antin.mr.test.oracle;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 * Created by Administrator on 2017/3/2.
 */
public class DBModel implements Writable, DBWritable {
    //xman_id,event,catalog_code,serial,content,t.xml.getclobval() xml,compression,encryption,status,version,title,commit_time,istemp
    private String xmanId;
    private String catalog;
    private Long serial;
    private String content;
    private String xml;
    private Long compression;
    private Long encryption;
    private Long status;
    private String version;
    private String title;
    private Date commitTime;
    private Long istemp;

    public DBModel() {
    }

    public void readFields(DataInput in) throws IOException {
        this.xmanId = Text.readString(in);
        this.catalog = Text.readString(in);
        this.serial = in.readLong();
        this.content = Text.readString(in);
        this.xml = Text.readString(in);
        this.compression = in.readLong();
        this.encryption = in.readLong();
        this.status = in.readLong();
        this.version = Text.readString(in);
        this.title = Text.readString(in);
        this.commitTime = new Date(in.readLong());
        this.istemp = in.readLong();
    }

    public void write(DataOutput out) throws IOException {
        Text.writeString(out, this.xmanId);
        Text.writeString(out, this.catalog);
        out.writeLong(this.serial);
        Text.writeString(out, this.content);
        Text.writeString(out, this.xml);
        out.writeLong(this.compression);
        out.writeLong(this.encryption);
        out.writeLong(this.status);
        Text.writeString(out, this.version);
        Text.writeString(out, this.title);
        out.writeLong(this.commitTime.getTime());
        out.writeLong(this.istemp);
    }

    public void readFields(ResultSet result) throws SQLException {
        this.xmanId = result.getString(1);
        this.catalog = result.getString(2);
        this.serial = result.getLong(3);
        this.content = result.getString(4);
        this.xml = result.getString(5);
        this.compression = result.getLong(6);
        this.encryption = result.getLong(7);
        this.status = result.getLong(8);
        this.version = result.getString(9);
        this.title = result.getString(10);
        this.commitTime = result.getDate(11);
        this.istemp = result.getLong(12);
    }

    public void write(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, this.xmanId);
        stmt.setString(2, this.catalog);
        stmt.setLong(3, this.serial);
        stmt.setString(4, this.content);
        stmt.setString(5, this.xml);
        stmt.setLong(6, this.compression);
        stmt.setLong(7, this.encryption);
        stmt.setLong(8, this.status);
        stmt.setString(9, this.version);
        stmt.setString(10, this.title);
        stmt.setDate(11, this.commitTime);
        stmt.setString(12, this.catalog);
    }


    public String getXmanId() {
        return xmanId;
    }

    public void setXmanId(String xmanId) {
        this.xmanId = xmanId;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public java.lang.Long getSerial() {
        return serial;
    }

    public void setSerial(java.lang.Long serial) {
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

    public java.lang.Long getCompression() {
        return compression;
    }

    public void setCompression(java.lang.Long compression) {
        this.compression = compression;
    }

    public java.lang.Long getEncryption() {
        return encryption;
    }

    public void setEncryption(java.lang.Long encryption) {
        this.encryption = encryption;
    }

    public java.lang.Long getStatus() {
        return status;
    }

    public void setStatus(java.lang.Long status) {
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

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Long getIstemp() {
        return istemp;
    }

    public void setIstemp(Long istemp) {
        this.istemp = istemp;
    }

    @Override
    public String toString() {
        return "==========================DBModel";
    }
}
