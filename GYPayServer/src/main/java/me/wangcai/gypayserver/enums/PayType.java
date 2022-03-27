package me.wangcai.gypayserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayType {
    WECHAT(1,"微信"),
    ALIPAY(2,"支付宝");

    private int id;
    private String name;

}
