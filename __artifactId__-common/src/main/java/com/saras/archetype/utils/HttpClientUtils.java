package com.saras.archetype.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * http请求工具类
 *
 * @author wangzhihuan
 * @version 1.0
 * @date 2017-04-29
 */
public class HttpClientUtils {

    protected static void print(String str) {
        System.out.println(str);
    }

    /**
     * get add by liuli
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        String strResult = null;
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == 200) {
                /**读取服务器返回过来的json字符串数据**/
                strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                url = URLDecoder.decode(url, "UTF-8");
                client.close();
            } else {

            }
        } catch (IOException e) {
            return strResult;
        }
        return strResult;
    }

    /**
     * post方式提交 add by liuli
     *
     * @param url
     * @param map
     * @param charset
     * @return
     */
    public static String doPost(String url, Map<String, String> map, String charset) {
        HttpPost httpPost = null;
        String result = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(60000).setConnectTimeout(60000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
                httpClient.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 方法说明： 方法参数：
     *
     * @param url
     * @return 返回类型：String
     */
    public static String post(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        String result = null;
        HttpPost httppost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(900000).setConnectTimeout(900000).build();//设置请求和传输超时时间
        httppost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httppost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                result = "error code :" + code;
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    protected static void closeHttpClient(CloseableHttpClient client,
                                          CloseableHttpResponse response) {
        try {
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

