package me.wangcai.gypaybukkit.utils;

import lombok.SneakyThrows;
import me.wangcai.gypaybukkit.GYPayBukkit;
import me.wangcai.gypaybukkit.config.Config;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private HttpClient httpClient;

    public HttpUtils(){
        Config config = GYPayBukkit.getGyPayBukkit().getPluginConfig();

        httpClient = HttpClientBuilder.create()
                .setDefaultHeaders(Arrays.asList(new BasicHeader("name", config.SETTINGS_NAME),
                        new BasicHeader("password", config.SETTINGS_PASSWORD)
                        ))
                .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(3000).build())
                .build();
    }


    /**
     * get请求，参数拼接在地址上
     * @param url 请求地址加参数
     * @return 响应
     */
    @SneakyThrows
    public String get(String url)
    {
        String result = null;
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpClient.execute(get);
        if(response != null && response.getStatusLine().getStatusCode() == 200)
        {
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        }
        return result;
    }

    /**
     * 发送post请求，参数用map接收
     * @param url 地址
     * @param map 参数
     * @return 返回值
     */
    @SneakyThrows
    public String postMap(String url, Map<String,String> map) {
        String result = null;
        HttpPost post = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<>();
        for(Map.Entry<String,String> entry : map.entrySet())
        {
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
        HttpResponse response = httpClient.execute(post);
        if(response != null && response.getStatusLine().getStatusCode() == 200)
        {
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        }
        return result;
    }

    /**
     * post请求，参数为json字符串
     * @param url 请求地址
     * @param jsonString json字符串
     * @return 响应
     */
    @SneakyThrows
    public String postJson(String url, String jsonString)
    {
        String result = null;
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type","application/json");
        post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
        HttpResponse response = httpClient.execute(post);
        if(response != null && response.getStatusLine().getStatusCode() == 200)
        {
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        }
        return result;
    }

    @SneakyThrows
    public BufferedImage getImage(String url){
        BufferedImage result = null;
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpClient.execute(get);
        if(response != null && response.getStatusLine().getStatusCode() == 200)
        {
            HttpEntity entity = response.getEntity();
            InputStream inputStream =  entity.getContent();
            result = ImageIO.read(inputStream);
        }
        return result;
    }

}
