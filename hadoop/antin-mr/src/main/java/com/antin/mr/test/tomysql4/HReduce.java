package com.antin.mr.test.tomysql4;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


import java.io.IOException;

/**
 * Created by Administrator on 2017/3/10.
 */

public class HReduce extends Reducer<Text, XmlModel, Op0101Model, NullWritable> {
    Text text = new Text();
    @Override
    protected void reduce(Text key, Iterable<XmlModel> values, Context context) throws IOException, InterruptedException {
        for (XmlModel value : values) {
            if ("0101".equals(value.getCatalogCode())) {
                String xml = value.getXml();
                if (xml != null && !"".equals(xml)) {
                    Op0101Model result = ParseXml.parseOp0101Model(xml);
                   // text.set(result.toString());
                    //context.write(text, null);
                    context.write(ParseXml.parseOp0101Model(xml), null);
                }
            }
        }

    }

}
