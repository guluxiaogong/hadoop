package com.antin.mr.test.tohbase7;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.db.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/9.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class MyOracleInputFormat<T extends DBWritable> extends DataDrivenDBInputFormat<T> implements Configurable {
    public MyOracleInputFormat() {
    }

    protected DBSplitter getSplitter(int sqlDataType) {
        switch (sqlDataType) {
            case 91:
            case 92:
            case 93:
                return new MyOracleDateSplitter();
            default:
                return super.getSplitter(sqlDataType);
        }
    }

    protected RecordReader<LongWritable, T> createDBRecordReader(DBInputSplit split, Configuration conf) throws IOException {
        DBConfiguration dbConf = this.getDBConf();
        Class inputClass = dbConf.getInputClass();

        try {
            return new OracleDataDrivenDBRecordReader(split, inputClass, conf, this.getConnection(), dbConf, dbConf.getInputConditions(), dbConf.getInputFieldNames(), dbConf.getInputTableName());
        } catch (SQLException var6) {
            throw new IOException(var6.getMessage());
        }
    }
    public static void setInput(Job job,
                                Class<? extends DBWritable> inputClass,
                                String inputQuery, String inputBoundingQuery) {
        DBInputFormat.setInput(job, inputClass, inputQuery, "");
        job.getConfiguration().set(DBConfiguration.INPUT_BOUNDING_QUERY, inputBoundingQuery);
        job.setInputFormatClass(MyOracleInputFormat.class);
    }
}
