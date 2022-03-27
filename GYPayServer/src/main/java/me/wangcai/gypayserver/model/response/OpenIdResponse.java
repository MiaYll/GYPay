/**
 * Copyright 2022 json.cn
 */
package me.wangcai.gypayserver.model.response;


import lombok.Data;

@Data
public class OpenIdResponse {

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}