package com.antin.mr.test.tohbase7;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.lib.db.DateSplitter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/9.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class MyOracleDateSplitter extends DateSplitter {
    public MyOracleDateSplitter() {
    }

    protected String dateToString(Date d) {

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);


        return "to_date(\'" + timestamp + "\', \'yyyy-MM-dd HH24:MI:SS\')";
    }
}
