package com.antin.mr.test.mysql;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.IdentityMapper;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/2.
 */
public class DBOutput {

    public static class StudentinfoRecord implements Writable, DBWritable {
        int id;
        String name;

        public StudentinfoRecord() {

        }

        public void readFields(DataInput in) throws IOException {
            this.id = in.readInt();
            this.name = Text.readString(in);
        }

        public void write(DataOutput out) throws IOException {
            out.writeInt(this.id);
            Text.writeString(out, this.name);
        }

        public void readFields(ResultSet result) throws SQLException {
            this.id = result.getInt(1);
            this.name = result.getString(2);
        }

        public void write(PreparedStatement stmt) throws SQLException {
            stmt.setInt(1, this.id);
            stmt.setString(2, this.name);
        }

        public String toString() {
            return new String(this.id + " " + this.name);
        }
    }

    public static class MyReducer extends MapReduceBase implements
            Reducer<LongWritable, Text, StudentinfoRecord, Text> {
        public void reduce(LongWritable key, Iterator<Text> values,
                           OutputCollector<StudentinfoRecord, Text> output, Reporter reporter)
                throws IOException {
            String[] splits = values.next().toString().split("/t");
            StudentinfoRecord r = new StudentinfoRecord();
            r.id = Integer.parseInt(splits[0]);
            r.name = splits[1];
            output.collect(r, new Text(r.name));
        }
    }

    public static void main(String[] args) throws IOException {
        JobConf conf = new JobConf(DBOutput.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(DBOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path("/hua/hua.bcp"));
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.3.244:3306/hadoop", "hua", "hadoop");
        DBOutputFormat.setOutput(conf, "studentinfo", "id", "name");

        conf.setMapperClass(IdentityMapper.class);
        conf.setReducerClass(MyReducer.class);

        JobClient.runJob(conf);
    }

}