package com.firstproject.common.util;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/9/29 17:39
 */
@Component("httpClientUtil")
public class HttpClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class) ;
    /**请求链接**/
    protected CloseableHttpClient httpClient;

    /**链接池**/
    private PoolingHttpClientConnectionManager connectionManager;

    /**编码格式**/
    private String CHARSET_DEFAULT = "UTF-8";

    /**
     * 默开启链接数
     */
    private int defaultPerRoute = 10;
    /**
     * 最大链接数
     */
    private int maxTotal = 100;
    /**
     *  死亡等待时间（以毫秒为单位）
     */
    private long idleTime = 5L * 1000;

    /**重试次数**/
    private int retryCount = 0;

    private int validateAfterInactivity = -1;

    /**
     * 确定建立连接之前的超时（以毫秒为单位）。
     */
    private int connectTimeout = 5 * 1000;
    /**
     * 定义套接字超时时间（以毫秒为单位）
     */
    private int socketTimeout = 10 * 1000;

    /**
     * 从请求连接时使用的超时（以毫秒为单位）
     */
    private int connectionRequestTimeout=5000;
    /**
     * 是否阻止URL重定向
     */
    private boolean redirectsEnabled = Boolean.FALSE;

    @PostConstruct
    public void init(){
        Registry<ConnectionSocketFactory> builder = RegistryBuilder.<ConnectionSocketFactory>
                create().register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();

        connectionManager = new PoolingHttpClientConnectionManager(builder , null, null);

        connectionManager.setDefaultMaxPerRoute(defaultPerRoute);
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setValidateAfterInactivity(validateAfterInactivity);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setRedirectsEnabled(redirectsEnabled)
                .build();

        httpClient = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
    }

    @PreDestroy
    public void closed() {
        try {
            if(null !=httpClient ){
                httpClient.close();
            }
        } catch (IOException e) {
            LOGGER.error("HttpClientUtils.close.is.IOException: ", e);
        } catch (Exception e) {
            LOGGER.error("HttpClientUtils.close.is.Exception: : ", e);
        }
    }

    public <T> ResponseEntity<T> post(URI uri,
                                      Map<String, ?> params,
                                      ResponseHandler<ResponseEntity<T>> responseHandler){
        return post(uri,params,responseHandler,null,null,null);
    }
    /**
     * POST 请求
     * @param uri 请求地址
     * @param params 参数
     * @param headers 请求header
     * @param cookies 请求cookie
     * @param responseHandler  返回装换实体
     * @param config 请求配置
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> post(URI uri,
                                      Map<String, ?> params,
                                      ResponseHandler<ResponseEntity<T>> responseHandler,
                                      List<Header> headers,
                                      Collection<ClientCookie> cookies,
                                      RequestConfig config){
        HttpPost httpPost = new HttpPost(uri);

        if (headers != null) {
            for (Header header : headers) {
                httpPost.addHeader(header);
            }
        }
        /**配置Cookie**/
        HttpClientContext context = HttpClientContext.create();
        if (!CollectionUtils.isEmpty(cookies)) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Cookie c : cookies) {
                cookieStore.addCookie(c);
            }
            context.setCookieStore(cookieStore);
        }
        /**配置请求配置**/
        if (config != null) {
            httpPost.setConfig(config);
        }
        /**参数信息封装**/
        boolean emptyParams = CollectionUtils.isEmpty(params);
        List<NameValuePair> nvps = new ArrayList<>(emptyParams ? 0 : params.size());
        if (!emptyParams) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                Object val = entry.getValue();
                nvps.add(new BasicNameValuePair(entry.getKey(), val != null ? val.toString() : ""));
            }
        }
        ResponseEntity<T> result = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, CHARSET_DEFAULT);
            httpPost.setEntity(entity);
            result = httpClient.execute(httpPost, responseHandler, context);
            if (!result.success()) {
                LOGGER.error(String.format("HttpClientUtils.post.is.error:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)));
            }
        } catch (IOException e){
            LOGGER.error(String.format("HttpClientUtils.post.is.IOException:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)),e);
        }catch (Exception e) {
            LOGGER.error(String.format("HttpClientUtils.post.is.Exception:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)),e);
        }
        return result;
    }


    /**
     * POST 请求
     * @param uri 请求地址
     * @param paramsJson 参数
     * @param responseHandler  返回装换实体
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> postJson(URI uri,
                                          String paramsJson,
                                          ResponseHandler<ResponseEntity<T>> responseHandler){
        return postJson(uri,paramsJson,responseHandler,null,null,null);
    }
    /**
     * POST 请求
     * @param uri 请求地址
     * @param paramsJson 参数
     * @param headers 请求header
     * @param cookies 请求cookie
     * @param responseHandler  返回装换实体
     * @param config 请求配置
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> postJson(URI uri,
                                          String paramsJson,
                                          ResponseHandler<ResponseEntity<T>> responseHandler,
                                          List<Header> headers,
                                          Collection<ClientCookie> cookies,
                                          RequestConfig config){
        HttpPost httpPost = new HttpPost(uri);

        if (headers != null) {
            for (Header header : headers) {
                httpPost.addHeader(header);
            }
        }
        /**配置Cookie**/
        HttpClientContext context = HttpClientContext.create();
        if (!CollectionUtils.isEmpty(cookies)) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Cookie c : cookies) {
                cookieStore.addCookie(c);
            }
            context.setCookieStore(cookieStore);
        }
        /**配置请求配置**/
        if (config != null) {
            httpPost.setConfig(config);
        }
        ResponseEntity<T> result = null;
        try {
            /**参数信息封装**/
            StringEntity param = new StringEntity(paramsJson, CHARSET_DEFAULT);
            httpPost.setEntity(param);
            param.setContentType("application/json");

            result = httpClient.execute(httpPost, responseHandler, context);
            if (!result.success()) {
                LOGGER.error(String.format("HttpClientUtils.post.is.error:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)));
            }
        } catch (IOException e){
            LOGGER.error(String.format("HttpClientUtils.post.is.IOException:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)),e);
        }catch (Exception e) {
            LOGGER.error(String.format("HttpClientUtils.post.is.Exception:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)),e);
        }
        return result;
    }

    public <T> ResponseEntity<T> get(URI uri,ResponseHandler<ResponseEntity<T>> responseHandler){
        return get(uri,responseHandler,null,null,null);
    }
    /**
     * 短链接 GET 请求
     * @param uri 请求地址
     * @param headers 请求header
     * @param cookies 请求cookie
     * @param responseHandler  返回装换实体
     * @param config 请求配置
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> get(URI uri,
                                     ResponseHandler<ResponseEntity<T>> responseHandler,
                                     List<Header> headers,
                                     Collection<ClientCookie> cookies,
                                     RequestConfig config) {
        HttpGet httpGet = new HttpGet(uri);

        /**封装Header**/
        httpGet.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType()));
        /**短链接方式请求**/
        httpGet.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        if (headers != null) {
            for (Header header : headers) {
                httpGet.addHeader(header);
            }
        }
        /**配置Cookie**/
        HttpClientContext context = HttpClientContext.create();
        if (!CollectionUtils.isEmpty(cookies)) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Cookie c : cookies) {
                cookieStore.addCookie(c);
            }
            context.setCookieStore(cookieStore);
        }
        /**配置请求配置**/
        if (config != null) {
            httpGet.setConfig(config);
        }
        ResponseEntity<T> result = null;
        try{
            result = httpClient.execute(httpGet, responseHandler, context);
            if (!result.success()) {
                LOGGER.error(String.format("HttpClientUtils.get.is.error:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)));
            }
        }catch (IOException e){
            LOGGER.error(String.format("HttpClientUtils.get.is.IOException:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)),e);
        }catch (Exception e){
            LOGGER.error(String.format("HttpClientUtils.get.is.Exception:%s-%s", uri.getPath(), JsonUtil.toJSONString(result)),e);
        }
        return result;
    }

    public String getHtml(String url) throws Exception{
        WebHtmlResponseEntity htmlResponseEntity = new WebHtmlResponseEntity() ;
        URI uri = new URI(url) ;
        ResponseEntity<String> html = this.get(uri, htmlResponseEntity) ;
        if(html.success()) {
            return html.getData() ;
        }
        return "error" ;
    }

    private static class WebHtmlResponseEntity implements ResponseHandler<ResponseEntity<String>> {

        @Override
        public ResponseEntity<String> handleResponse(HttpResponse response) throws IOException {
            int status = response.getStatusLine().getStatusCode();
            ResponseEntity<String> responseEntity = new ResponseEntity<>();
            responseEntity.setStatus(status);
            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Header[] headers = response.getAllHeaders();
                    responseEntity.setHeaders(headers);
                    String resp = getResponseEntity(response.getEntity());
                    responseEntity.setData(resp);
                }
                return responseEntity;
            }
            return responseEntity;
        }

        public String getResponseEntity(HttpEntity entity) throws IOException {
            String vo = null;
            try{
                vo =  EntityUtils.toString(entity, "UTF-8");
            }catch (Exception e){
                LOGGER.error("ResWebHtmlHandler.is.syste.error:"+EntityUtils.toString(entity, "UTF-8"),e);
            }
            return vo;
        }
    }
}
