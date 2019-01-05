package com.saras.archetype.utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * description:签名效验工具类
 * saras_xu@163.com 2017-03-01 11:02 创建
 */
public class SignCheckUtils {
    /**
     * 传入Map<String,String> 生成生成待签名的参数串
     *
     * @param params
     * @return
     */
    public static String buildSignString(Map<String, String> params) throws UnsupportedEncodingException {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if (entry.getValue() != null) {
                sb.append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            if (it.hasNext()) {
                sb.append('&');
            }
        }
        return sb.toString();
    }


    /**
     * 模拟浏览器GET提交
     *
     * @param url
     * @return
     */
    public static HttpGet getGetMethod(String url) {
        HttpGet get = new HttpGet(url);
        // 设置响应头信息
        get.addHeader("Connection", "keep-alive");
        get.addHeader("Cache-Control", "max-age=0");
        get.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
        return get;
    }

    /**
     * 模拟浏览器POST提交
     *
     * @param url
     * @return
     */
    public static HttpPost getPostMethod(String url) {
        HttpPost post = new HttpPost(url);
        // 设置响应头信息
        post.addHeader("Connection", "keep-alive");
        post.addHeader("Cache-Control", "max-age=0");
        post.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
        return post;
    }
}
