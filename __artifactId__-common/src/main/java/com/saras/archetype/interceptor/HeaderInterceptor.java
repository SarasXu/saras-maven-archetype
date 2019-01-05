package com.saras.archetype.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 请求头处理
 *
 * @author: York.Yu
 * @date: 2017/4/26
 */
public class HeaderInterceptor extends AbstractInterceptorHandler {

    protected Map<String, String> headers;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                response.setHeader(header.getKey(), header.getValue());
            }
        }
        return true;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
