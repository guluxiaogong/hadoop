package com.antin.es.test.nginx.normal;

/**
 * Created by Administrator on 2017/4/28.
 */
public class BrowserInfoModel {

    private String browserInfo;

    public String getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }

    @Override
    public String toString() {
        return "BrowserInfoModel{" +
                "browserInfo='" + browserInfo + '\'' +
                '}';
    }
}
