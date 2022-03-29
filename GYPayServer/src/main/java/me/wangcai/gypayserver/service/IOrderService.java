package me.wangcai.gypayserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.wangcai.gypayserver.enums.PayType;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Order;
import me.wangcai.gypayserver.model.param.CreateOrderParam;

import java.util.List;

public interface IOrderService extends IService<Order> {
    ResponseInfo createOrder(CreateOrderParam createOrderParam,String account);

    Order queryOrder(String orderId);

    boolean isPayed(String orderId);

    void orderFinish(String orderId, PayType type);

    List<Order> getUnShipOrder(String account);

    ResponseInfo shipOrder(String orderId,String account);
}
