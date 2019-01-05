package com.saras.archetype.http.httpclients;

import com.saras.archetype.core.exception.BizErrorException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;

import java.net.URLEncoder;

/**
 * description:
 *
 * @author 345991260@qq.com
 * @date 2018-03-07 19:51 创建
 */
public class HttpGetClient {

    public static String xmlGet(Document document, String url) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = buildGetMethod(url + "?XML=" + URLEncoder.encode(document.asXML(), "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            throw BizErrorException.newBizError("构建XML GET Request Error");
        }
    }

    private static HttpGet buildGetMethod(String url) {
        HttpGet method = new HttpGet(url);
        method.addHeader("Connection", "keep-alive");
        method.addHeader("Cache-Control", "max-age=0");
        method.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        method.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
        return method;
    }
}
