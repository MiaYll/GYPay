package me.wangcai.gypaybukkit.service.impl;

import me.wangcai.gypaybukkit.service.IPayService;
import me.wangcai.gypaybukkit.utils.HttpUtils;

public class PayServiceImpl implements IPayService {

    HttpUtils httpUtils = new HttpUtils();

    @Override
    public boolean ship(String player) {
        return false;
    }
}
