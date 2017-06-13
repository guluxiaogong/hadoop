package com.antin.es.test.nginx.normal;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/28.
 */
public class UrlInfoModel {
    private String url;
    private Map params;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "UrlInfoModel{" +
                "url='" + url + '\'' +
                ", params=" + params +
                '}';
    }
}
