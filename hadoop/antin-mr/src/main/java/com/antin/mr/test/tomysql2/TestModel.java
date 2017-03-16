package com.antin.mr.test.tomysql2;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/10.
 */
public class TestModel implements Writable, DBWritable {
    private String rowkey;
    private String title;

    public TestModel() {
        super();
    }

    public TestModel(String rowkey, String title) {
        this.rowkey = rowkey;
        this.title = title;
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.rowkey = dataInput.readUTF();
        this.title = dataInput.readUTF();

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.rowkey);
        dataOutput.writeUTF(this.title);
    }

    @Override
    public void write(PreparedStatement pstm) throws SQLException {
        pstm.setString(1, this.rowkey);
        pstm.setString(2, this.title);
    }

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        this.rowkey = rs.getString(1);
        this.title = rs.getString(2);
    }

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
