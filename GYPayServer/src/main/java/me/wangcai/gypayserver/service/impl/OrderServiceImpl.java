package me.wangcai.gypayserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.wangcai.gypayserver.enums.PayType;
import me.wangcai.gypayserver.mapper.OrderMapper;
import me.wangcai.gypayserver.mapper.RecordMapper;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Order;
import me.wangcai.gypayserver.model.entity.Record;
import me.wangcai.gypayserver.model.param.CreateOrderParam;
import me.wangcai.gypayserver.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public ResponseInfo createOrder(CreateOrderParam createOrderParam) {
        Order order = new Order();
        order.setItemName(createOrderParam.getName());
        order.setPrice(createOrderParam.getPrice());
        order.setDescription(createOrderParam.getDescription());
        order.setUsername(createOrderParam.getUsername());
        order.setType(createOrderParam.getType());
        String orderId = UUID.randomUUID().toString().replace("-", "");
        order.setOrderId(orderId);
        order.setCreateTime(LocalDateTime.now());
        if(this.save(order)){
            return ResponseInfo.success("创建成功",order);
        }else{
            return ResponseInfo.error("创建失败");
        }
    }

    @Override
    public Order queryOrder(String orderId) {
        QueryWrapper<Order> wrapper = Wrappers.query();
        wrapper.eq("order_id",orderId);
        return orderMapper.selectOne(wrapper);
    }

    @Override
    public boolean isPayed(String orderId) {
        QueryWrapper<Record> wrapper = Wrappers.query();
        wrapper.eq("order_id",orderId);
        return recordMapper.selectOne(wrapper) != null;
    }

    public void orderFinish(String orderId, PayType type) {
        recordMapper.insert(new Record(0,orderId,LocalDateTime.now(),type));
    }

    @Override
    public List<Order> getUnPayOrder() {
        return orderMapper.getUnPayOrder();
    }
}
