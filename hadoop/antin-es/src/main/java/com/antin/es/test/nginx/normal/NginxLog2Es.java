package com.antin.es.test.nginx.normal;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/28.
 */
public class NginxLog2Es {

    public static void main(String[] args) {

        try {
            FileReader fileReader = new FileReader("F:\\CommonDevelop\\nginx_log\\access.log");
            BufferedReader br = new BufferedReader(fileReader);
            String line = null;
            List<JSONObject> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                NginxLogModel nginxLogModel = ParseNginxLog.parseLine(line);
                JSONObject obj = (JSONObject) JSONObject.toJSON(nginxLogModel);
                list.add(obj);
                if (list.size() > 1000) {//1000条提交一次
                    put(list);
                    list.clear();
                }
            }
            put(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void put(List<JSONObject> maps) throws Exception {
        try {
            Settings.Builder settings = Settings.builder().put("cluster.name", "zoe-es");
            TransportClient client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.88"), Integer.parseInt("9300")));
            int count = 0;
            for (int i = 0; i < maps.size(); i++) {
                try {
                    IndexResponse response = client.prepareIndex("nginx", "logs").setSource(maps.get(i)).get();
                    if (response.isCreated()) {
                        System.out.println(count++);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
