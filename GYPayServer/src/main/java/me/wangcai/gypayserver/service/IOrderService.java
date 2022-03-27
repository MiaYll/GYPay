package me.wangcai.gypayserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.wangcai.gypayserver.enums.PayType;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Order;
import me.wangcai.gypayserver.model.param.CreateOrderParam;

import java.util.List;

public interface IOrderService extends IService<Order> {
    ResponseInfo createOrder(CreateOrderParam createOrderParam);

    Order queryOrder(String orderId);

    boolean isPayed(String orderId);

    void orderFinish(String orderId, PayType type);

    //获取最近一天的未支付订单
    List<Order> getUnPayOrder();
}
