
package com.saras.archetype.http.httpclients;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.saras.archetype.http.HttpConstant;
import com.saras.archetype.utils.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.DisposableBean;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NewHttps implements DisposableBean {
    /**
     * 连接不够用的时候等待超时时间，一定要设置，而且不能太大，单位毫秒
     */
    private static final int CONNECTION_REQUES_TTIMEOUT = 2 * 2000;
    /**
     * 连接池大小
     */
    private static final int POOL_SIZE = 400;
    /**
     * 每个路由的最大连接数
     */
    private static final int MAX_PER_ROUTE = 400;
    protected final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    /**
     * 默认编码字符集
     */
    protected final Charset ENCODE = Consts.UTF_8;

    /**
     * 设置连接超时时间，单位毫秒。
     */
    private int connectionTimeout = 10 * 1000;
    /**
     * 请求获取数据的超时时间，单位毫秒
     */
    private int socketTimeout = 60 * 1000;
    /**
     * http post contentTyp,默认为application/x-www-form-urlencoded
     */
    private String contentType = HttpConstant.DEFAULT_FORM_CONTENT_TYPE;

    private CloseableHttpClient httpClient;
    //请求重试处理
    private HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof SSLException) {// ssl握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    };

    public NewHttps(int connectionTimeout, int socketTimeout, String contentType) {
        this.connectionTimeout = connectionTimeout;
        this.socketTimeout = socketTimeout;
        if (!StringUtils.equals(contentType, HttpConstant.CONTENT_TYPE)
                && !StringUtils.equals(contentType, HttpConstant.DEFAULT_FORM_CONTENT_TYPE)) {
            logger.error("httpClient初始化失败,程序退出:仅支持HttpConstant中预定义的contentType!");
            System.exit(0);
        }
        this.contentType = contentType;
        try {
            init();
        } catch (Exception e) {
            logger.error("初始化htp连接池时报错", e);
            System.exit(0);
        }
    }

    public NewHttps(int connectionTimeout, int socketTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.socketTimeout = socketTimeout;
        try {
            init();
        } catch (Exception e) {
            logger.error("初始化htp连接池时报错", e);
            System.exit(0);
        }
    }

    public void init() throws NoSuchAlgorithmException, KeyManagementException {
        //http的链接工厂池
        ConnectionSocketFactory httpFactory = PlainConnectionSocketFactory.getSocketFactory();
        //https的链接工厂池
        SSLContext ctx = SSLContext.getInstance("SSL");
        ctx.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, new SecureRandom());
        //忽略https的SSL认证
        ConnectionSocketFactory httpsFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
        //注册到主工厂
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", httpFactory).register("https", httpsFactory).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(POOL_SIZE);
        cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        //请求cookie和超时的默认配置
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)//CookieSpecs.IGNORE_COOKIES)之前是忽略cookie 现在是标准
                .setConnectionRequestTimeout(CONNECTION_REQUES_TTIMEOUT).setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout).build();

        httpClient = HttpClients.custom().setConnectionManager(cm).setRetryHandler(httpRequestRetryHandler)
                .setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * post发送text请求
     *
     * @param url  请求的url
     * @param text 请求的body内容
     * @return 请求返回的结果
     * @throws IllegalStateException
     * @throws IOException
     */
    public HttpResult textPost(String url, String text) throws IllegalStateException, IOException {
        return strPost(url, text, ContentType.TEXT_PLAIN); // "text/plain"
    }

    /**
     * post发送form请求
     *
     * @param url  请求的url
     * @param form 键值对的参数字符串
     * @return 请求返回的结果
     * @throws IllegalStateException
     * @throws IOException
     */
    public HttpResult formPost(String url, String form) throws IllegalStateException, IOException {
        return strPost(url, form, ContentType.APPLICATION_FORM_URLENCODED); // "application/x-www-form-urlencoded"
    }

    /**
     * post发送基于字符串的参数请求
     *
     * @param url         请求的url
     * @param str         请求字符串
     * @param contentType 请求头类型
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public HttpResult strPost(String url, String str, ContentType contentType) throws IllegalStateException,
            IOException {
        if (str == null) {
            str = "";
        }
        StringEntity entity = new StringEntity(str, ENCODE);
        entity.setContentType(contentType.getMimeType());
        return post(url, entity, false);
    }

    /**
     * http请求
     *
     * @param url            请求的url
     * @param map            参数键值对
     * @param enableRedirect 遇到重定向是否重定向
     * @return 请求返回的结果
     * @throws IllegalStateException
     * @throws IOException
     */
    public HttpResult mapPost(String url, Map<String, String> map, boolean enableRedirect) throws IllegalStateException,
            IOException {
        if (map == null || map.isEmpty()) {
            return this.textPost(url, null);
        }
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formParams = new ArrayList();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
            String keyEncode = URLEncoder.encode(elem.getKey(), ENCODE.name());
            if (StringUtils.isEmpty(elem.getValue())) {
                formParams.add(new BasicNameValuePair(elem.getKey(), null));
            } else {
                formParams.add(new BasicNameValuePair(keyEncode, URLEncoder.encode(elem.getValue(), ENCODE.name())));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, ENCODE);
        entity.setContentEncoding(ENCODE.name());
        entity.getContent();
        httpPost.setEntity(entity);
        return execute(httpPost, enableRedirect);
    }

    private boolean isKjtContentType() {
        return StringUtils.equals(this.contentType, HttpConstant.CONTENT_TYPE);
    }

    /**
     * post请求
     *
     * @param url            地址
     * @param entity         请求实体
     * @param enableRedirect 遇到重定向是否重定向
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    private HttpResult post(String url, StringEntity entity, boolean enableRedirect) throws IllegalStateException,
            IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        return execute(httpPost, enableRedirect);
    }

    /**
     * get 访问url, 获取返回值
     *
     * @param url
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public HttpResult httpGet(String url) throws IllegalStateException, IOException {
        return this.httpGet(url, null);
    }

    /**
     * get 访问url, 获取返回值
     *
     * @param url
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public HttpResult httpGet(String url, Map<String, String> dataMap) throws IllegalStateException, IOException {
        url = this.normalizeUrl(url, dataMap, ENCODE.name(), true, false);
        HttpGet httpGet = new HttpGet(url);
        return execute(httpGet, false);//get请求会自动重定向
    }

    /**
     * 内部执行请求
     *
     * @param request
     * @param enableRedirect 遇到重定向是否重定向
     * @return
     * @throws IOException
     */
    protected HttpResult execute(HttpUriRequest request, boolean enableRedirect) throws IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            String location = request.getURI().toString();
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (enableRedirect && this.isRedirect(statusCode)) {
                Header locationHeader = response.getFirstHeader("Location");
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    if (location != null) {
                        logger.info("redirect request:{}", location);
                        return this.httpGet(location);
                    }
                }
            }
            HttpResult result = new HttpResult();
            result.setStatus(statusCode);
            Map<String, String> headers = readHeaders(response);
            result.setHeaders(headers);
            //跳转服务
            if (statusCode == 302) {
                Header locationHeader = response.getFirstHeader("Location");
                location = URLDecoder.decode(locationHeader.getValue(), "utf-8");
                headers.put("location", locationHeader.getValue());
                if (location.contains("?")) {
                    headers.put("Location", location.substring(0, location.indexOf("?")));
                    String str = location.substring(location.indexOf("?") + 1);
                    String[] strs = str.split("&");
                    for (String aaa : strs) {
                        String[] strString = aaa.split("=");
                        headers.put(strString[0], strString[1]);
                    }
                }
                result.setBody(JSON.toJSONString(headers));
            } else {
                entity = response.getEntity();
                if (entity != null) {
                    result.setBody(EntityUtils.toString(entity, getCharset(headers)));
                }
            }
            return result;
        } finally {
            if (entity != null) {
                EntityUtils.consume(entity);
            } //关闭流 释放资源
            if (response != null) {
                response.close();
            }
            if (request != null) {
                if (request instanceof HttpPost) {
                    ((HttpPost) request).releaseConnection();
                }
                if (request instanceof HttpGet) {
                    ((HttpGet) request).releaseConnection();
                }
            }
        }

    }

    private Charset getCharset(Map<String, String> headers) {
        String contentType = headers.get("Content-Type");
        Charset encode = ENCODE;
        try {
            if (contentType != null) {
                String[] con = contentType.split("charset=");
                if (con.length > 1) {
                    encode = Charset.forName(con[1].split(";")[0]);
                }
                ;
            }
        } catch (Exception e) {
            logger.warn("获得相应编码解析发生错误response.getHeaders(Content-Type)={},引用默认编码{}解析内容", contentType, encode.name(), e);
        }
        return encode;
    }

    /**
     * 用于get请求参数拼接
     *
     * @param url             地址
     * @param dataMap         参数
     * @param charset         参数编码字符
     * @param enableUrlEncode 是否urlEncode
     * @param includeBlank    value为空的情况是否拼接
     * @return
     */
    private String normalizeUrl(String url, Map<String, String> dataMap, String charset, boolean enableUrlEncode,
                                boolean includeBlank) {

        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        if (dataMap != null && !dataMap.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.contains("?")) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            String value = null;
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                if (StringUtils.isBlank(entry.getValue()) && includeBlank) {
                    sb.append(entry.getKey()).append("=");
                } else {
                    value = enableUrlEncode ? Encodes.urlEncode(entry.getValue(), charset) : entry.getValue();
                    sb.append(entry.getKey()).append("=").append(value);
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            url = sb.toString();
        }

        return url;
    }

    private boolean isRedirect(int statusCode) {
        return String.valueOf(statusCode).startsWith("3");
    }

    private Map<String, String> readHeaders(HttpResponse httpResponse) {
        Map<String, String> result = Maps.newHashMap();
        List<String> headerValues = null;
        for (Header header : httpResponse.getAllHeaders()) {
            if (StringUtils.isNotBlank(header.getName())) {
                if (result.containsKey(header.getName())) {
                    headerValues = Lists.newArrayList(StringUtils.split(result.get(header.getName()), ","));
                    headerValues.add(header.getValue());
                    result.put(header.getName(), StringUtils.join(headerValues.iterator(), ","));
                } else {
                    result.put(header.getName(), header.getValue());
                }
            }
        }
        return result;
    }

    @Override
    public void destroy() throws Exception {
        logger.info("关闭HttpClient");
        if (this.httpClient != null) {
            httpClient.close();
        }
    }

    /**
     * 内部执行请求
     *
     * @param request
     * @param enableRedirect 遇到重定向是否重定向
     * @return
     * @throws IOException
     */
    protected HttpResult execute(HttpUriRequest request, boolean enableRedirect, HttpHost target, HttpContext context) throws IOException {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            String location = request.getURI().toString();
            response = httpClient.execute(target, request, context);
            logger.info("远程请求原始响应结果：{}", response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (enableRedirect && this.isRedirect(statusCode)) {
                Header locationHeader = response.getFirstHeader("Location");
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    if (location != null) {
                        logger.info("redirect request:{}", location);
                        return this.httpGet(location);
                    }
                }
            }
            HttpResult result = new HttpResult();
            result.setStatus(statusCode);
            Map<String, String> headers = readHeaders(response);
            result.setHeaders(headers);
            //跳转服务
            if (statusCode == 302) {
                Header locationHeader = response.getFirstHeader("Location");
                location = URLDecoder.decode(locationHeader.getValue(), "utf-8");
                headers.put("location", locationHeader.getValue());
                if (location.contains("?")) {
                    headers.put("Location", location.substring(0, location.indexOf("?")));
                    String str = location.substring(location.indexOf("?") + 1);
                    String[] strs = str.split("&");
                    for (String aaa : strs) {
                        String[] strString = aaa.split("=");
                        headers.put(strString[0], strString[1]);
                    }
                }
                result.setBody(JSON.toJSONString(headers));
            } else {
                entity = response.getEntity();
                if (entity != null) {
                    result.setBody(EntityUtils.toString(entity, getCharset(headers)));
                }
            }
            return result;
        } finally {
            if (entity != null) {
                EntityUtils.consume(entity);
            } //关闭流 释放资源
            if (response != null) {
                response.close();
            }
            if (request != null) {
                if (request instanceof HttpPost) {
                    ((HttpPost) request).releaseConnection();
                }
                if (request instanceof HttpGet) {
                    ((HttpGet) request).releaseConnection();
                }
            }
        }

    }
}
