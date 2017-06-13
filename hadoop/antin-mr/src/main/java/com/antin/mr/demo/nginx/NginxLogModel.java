package com.antin.mr.demo.nginx;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/28.
 */
public class NginxLogModel implements Writable {
    private String ipAddr;//访问ip
    private String accessTime;//访问时间
    private String requestMethod;//请求类型
    private String url;//url信息(请求地址和get请求参数)
    private String protocol;//请求协议
    private String status;//请求状态
    private String size;//请求文件的大小
    private String reference;//请求来源url
    private String browserInfo;//浏览器信息
    private String proxyIp;//代理ip
    private boolean valid = true;// 判断数据是否合法

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ipAddr);
        sb.append("     ").append(this.accessTime);
        sb.append("     ").append(this.requestMethod);
        sb.append("     ").append(this.url);
        sb.append("     ").append(this.protocol);
        sb.append("     ").append(this.status);
        sb.append("     ").append(this.size);
        sb.append("     ").append(this.reference);
        sb.append("     ").append(this.browserInfo);
        sb.append("     ").append(this.proxyIp);
        sb.append("     ").append(this.valid);
        return sb.toString();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.ipAddr);
        out.writeUTF(this.accessTime);
        out.writeUTF(this.requestMethod);
        out.writeUTF(this.url);
        out.writeUTF(this.protocol);
        out.writeUTF(this.status);
        out.writeUTF(this.size);
        out.writeUTF(this.reference);
        out.writeUTF(this.browserInfo);
        out.writeUTF(this.proxyIp);
        out.writeBoolean(this.valid);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.ipAddr = in.readUTF();
        this.accessTime = in.readUTF();
        this.requestMethod = in.readUTF();
        this.url = in.readUTF();
        this.protocol = in.readUTF();
        this.status = in.readUTF();
        this.size = in.readUTF();
        this.reference = in.readUTF();
        this.browserInfo = in.readUTF();
        this.proxyIp = in.readUTF();
        this.valid = in.readBoolean();
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
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

}
