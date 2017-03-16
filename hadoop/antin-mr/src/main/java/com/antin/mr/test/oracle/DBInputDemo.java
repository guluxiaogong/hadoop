package com.antin.mr.test.oracle;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/2.
 */
public class DBInputDemo {
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
    public static class DBInputMapper extends Mapper<LongWritable, StudentinfoRecord, LongWritable, Text> {

        @Override
        protected void map(LongWritable key, StudentinfoRecord studentinfoRecord, Context context) throws IOException, InterruptedException {
            context.write(new LongWritable(studentinfoRecord.id), new Text(studentinfoRecord.toString()));
        }

    }

    /**
     * 新api
     */
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        conf.setInt("mapreduce.job.maps", 3);

        //注：要在Job获取之前
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://qdm154746529.my3w.com:3306/qdm154746529_db", "qdm154746529", "552620jg");
        String[] fields = {"id", "name"};

        Job job = Job.getInstance(conf);

        //注：新api中用下面那个DistributedCache已经不可以了
        //export HADOOP_CLASSPATH="${HADOOP_CLASSPATH}:/hadoop-lib/mysql-connector-java-5.1.7-bin.jar"//hadoop jar ...驱动找不到时执行
        job.addCacheFile(new Path("hdfs://cluster1/hadoop-lib/mysql-connector-java-5.1.7-bin.jar").toUri());
        //DistributedCache.addFileToClassPath(new Path("hdfs://cluster1/hadoop-lib/mysql-connector-java-5.1.7-bin.jar"), conf);

        //注意：要将main所在的类设置一下
        job.setJarByClass(DBInputDemo.class);

        DBInputFormat.setInput(job, StudentinfoRecord.class, "studentinfo", null, "id", fields);
        job.setInputFormatClass(DBInputFormat.class);
        //设置Mapper相关的属性
        job.setMapperClass(DBInputMapper.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setNumReduceTasks(0);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        FileOutputFormat.setOutputPath(job, new Path("hdfs://cluster1/dbOutputDemo"));

        //提交任务
        job.waitForCompletion(true);

    }
}