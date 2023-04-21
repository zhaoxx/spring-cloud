package com.mia.gateway.config;


public class ResponseLogMsg {
    private String requestUrl;
    private String method;
    private String clientHostIp;
    private String contentType;
    private Integer responseStatus;
    private long costTime;
//    private Object params;
//    private Object result;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClientHostIp() {
        return clientHostIp;
    }

    public void setClientHostIp(String clientHostIp) {
        this.clientHostIp = clientHostIp;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }
}
