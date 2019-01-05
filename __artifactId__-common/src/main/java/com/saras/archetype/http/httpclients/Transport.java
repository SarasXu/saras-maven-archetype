package com.saras.archetype.http.httpclients;

public interface Transport<R, T> {

    /**
     * 发送请求，接收响应
     *
     * @param request
     * @return
     */
    T exchange(String url, R request);
}
