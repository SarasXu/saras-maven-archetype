package com.saras.archetype.http.httpclients;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.dom4j.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:26 创建
 */
public class XmlPostHttpClient extends NewHttps {
    public XmlPostHttpClient(int connectionTimeout, int socketTimeout) {
        super(connectionTimeout, socketTimeout);
    }

    public HttpResult xmlPost(Document document, String rootParamName, String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>(1);
        Base64 base64 = new Base64();
        nvps.add(new BasicNameValuePair(rootParamName, base64.encodeToString(document.asXML().getBytes())));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, ENCODE);
        entity.setContentEncoding(ENCODE.name());
        httpPost.setEntity(entity);
        return execute(httpPost, false);
    }

    public HttpResult xmlPost(Document document, String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(document.asXML(), "UTF-8"));
        httpPost.addHeader("Content-Type", "text/xml; charset=iso-8859-1");
        return execute(httpPost, false);
    }

    public HttpResult xmlPost(Document document, String url, HttpHost httpHost, HttpContext context) throws IOException {
        HttpPost httpPost = new HttpPost(url);
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>(1);
//        Base64 base64 = new Base64();
//        nvps.add(new BasicNameValuePair(rootParamName, base64.encodeToString(document.asXML().getBytes())));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, ENCODE);
//        entity.setContentEncoding(ENCODE.name());
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("Cache-Control", "max-age=0");
        httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
        httpPost.addHeader("Content-Type", "application/soap+xml;charset=utf-8");
        httpPost.setEntity(new StringEntity(document.asXML(), "UTF-8"));
        return execute(httpPost, false, httpHost, context);
    }
}
