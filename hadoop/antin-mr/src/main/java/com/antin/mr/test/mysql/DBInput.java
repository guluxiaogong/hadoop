package com.antin.mr.test.mysql;


import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/2.
 */
public class DBInput {
    // DROP TABLE IF EXISTS `hadoop`.`studentinfo`;
    // CREATE TABLE studentinfo (
    // id INTEGER NOT NULL PRIMARY KEY,
    // name VARCHAR(32) NOT NULL);

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

    /*
     *类需要静态，否则报hadoop(mapreduce):java.lang.NoSuchMethodException: ******Mapper.<init>()
     */
    public static class DBInputMapper extends MapReduceBase implements Mapper<LongWritable, StudentinfoRecord, LongWritable, Text> {
        public void map(LongWritable key, StudentinfoRecord value, OutputCollector<LongWritable, Text> collector, Reporter reporter)
                throws IOException {
            collector.collect(new LongWritable(value.id), new Text(value.toString()));
        }
    }

    public static void main(String[] args) throws IOException {
        JobConf conf = new JobConf(DBInput.class);
        //export HADOOP_CLASSPATH="${HADOOP_CLASSPATH}:/hadoop-lib/mysql-connector-java-5.1.7-bin.jar"//hadoop jar ...前这个是可以的
        DistributedCache.addFileToClassPath(new Path("hdfs://cluster1/hadoop-lib/mysql-connector-java-5.1.7-bin.jar"), conf);
        conf.setInt("mapreduce.job.maps", 3);
        //map
        conf.setMapperClass(DBInputMapper.class);
        conf.setInputFormat(DBInputFormat.class);
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://qdm154746529.my3w.com:3306/qdm154746529_db", "qdm154746529", "552620jg");
        String[] fields = {"id", "name"};
        DBInputFormat.setInput(conf, StudentinfoRecord.class, "studentinfo", null, "id", fields);

        conf.setMapOutputKeyClass(LongWritable.class);
        conf.setMapOutputValueClass(Text.class);

        //reduce
        conf.setReducerClass(IdentityReducer.class);
        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputValueClass(Text.class);

        FileOutputFormat.setOutputPath(conf, new Path("hdfs://cluster1/dbOutput"));

        JobClient.runJob(conf);
    }
}