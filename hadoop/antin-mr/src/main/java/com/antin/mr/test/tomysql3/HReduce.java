package com.antin.mr.test.tomysql3;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/10.
 */

public class HReduce extends Reducer<Text, SehrXmanEhrModel, Text, NullWritable> {
    Text text = new Text();

    @Override
    protected void reduce(Text key, Iterable<SehrXmanEhrModel> values, Context context) throws IOException, InterruptedException {
        for (SehrXmanEhrModel value : values) {
            if ("0101".equals(value.getCatalogCode())) {
                String xml = value.getXml();
                if (xml != null && !"".equals(xml)) {
                    //Op0101Model result = ParseXml.parseOp0101Model(value.getXml());
                    //text.set(result.toString());
                    text.set(xml);
                    context.write(text, null);
                }
            }
        }

    }

}
