package me.wangcai.gypaybukkit.utils;

import me.wangcai.gypaybukkit.GYPayBukkit;
import me.wangcai.gypaybukkit.config.Config;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.util.Arrays;

public class HttpUtils {

    private HttpClient httpClient;

    public HttpUtils(){
        Config config = GYPayBukkit.getGyPayBukkit().getPluginConfig();
        httpClient = HttpClientBuilder.create()
                .setDefaultHeaders(Arrays.asList(new BasicHeader("name", config.SETTINGS_NAME),new BasicHeader("name", config.SETTINGS_PASSWORD)))
                .build();
    }


}
