package com.saras.archetype.http.httpclients;

import java.util.Map;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:26 创建
 */
public class HttpResult {
    private int status;
    private Map<String, String> headers;
    private String body;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return String.format("HttpResult: {status:%s, headers:%s, body:%s}", status, headers, body);
    }

}
