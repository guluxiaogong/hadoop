package com.antin.es.test.nginx.normal;

/**
 * Created by Administrator on 2017/4/28.
 */
public class NginxLogModel {
    private String ipAddr;//访问ip
    private String accessTime;//访问时间
    private String requestMethod;//请求类型
    private UrlInfoModel urlInfoModel;//url信息(请求地址和get请求参数)
    private String protocol;//请求协议
    private String status;//请求状态
    private String size;//请求文件的大小
    private String reference;//请求来源url
    private BrowserInfoModel browserInfoModel;//浏览器信息
    private String proxyIp;//代理ip
    private boolean valid = true;// 判断数据是否合法

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public UrlInfoModel getUrlInfoModel() {
        return urlInfoModel;
    }

    public void setUrlInfoModel(UrlInfoModel urlInfoModel) {
        this.urlInfoModel = urlInfoModel;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BrowserInfoModel getBrowserInfoModel() {
        return browserInfoModel;
    }

    public void setBrowserInfoModel(BrowserInfoModel browserInfoModel) {
        this.browserInfoModel = browserInfoModel;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "NginxLogModel{" +
                "ipAddr='" + ipAddr + '\'' +
                ", accessTime='" + accessTime + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", urlInfoModel=" + urlInfoModel +
                ", protocol='" + protocol + '\'' +
                ", status='" + status + '\'' +
                ", size='" + size + '\'' +
                ", reference='" + reference + '\'' +
                ", browserInfoModel=" + browserInfoModel +
                ", proxyIp='" + proxyIp + '\'' +
                ", valid=" + valid +
                '}';
    }
}
