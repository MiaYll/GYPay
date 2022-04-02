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
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public ResponseInfo createOrder(CreateOrderParam createOrderParam,String account) {
        Order order = new Order();
        order.setItemName(createOrderParam.getName());
        order.setPrice(createOrderParam.getPrice());
        order.setDescription(createOrderParam.getDescription());
        order.setUsername(createOrderParam.getUsername());
        order.setType(createOrderParam.getType());
        String orderId = UUID.randomUUID().toString().replace("-", "");
        order.setOrderId(orderId);
        order.setAccountName(account);
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
        recordMapper.insert(new Record(0,orderId,LocalDateTime.now(),type,false));
    }

    @Override
    public List<Order> getUnShipOrder(String account) {
        return orderMapper.getUnShipOrder(account);
    }

    @Override
    public ResponseInfo shipOrder(String orderId,String account) {
        Record record = recordMapper.selectOne(new QueryWrapper<Record>().eq("order_id", orderId));
        if(record == null) return  ResponseInfo.error("无此订单");
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_id", orderId));
        if(!order.getAccountName().equals(account)){
            return  ResponseInfo.error("不是此账户下的订单");
        }
        record.setShip(true);
        recordMapper.updateById(record);
        return ResponseInfo.success("发货成功!",order);
    }

    @Override
    public ResponseInfo getShipOrder(String account, int page, int size) {
        List<Map> orderList = orderMapper.getShipOrder(account, (page - 1) * size + 1, size);
        Map<String,Object> map = new HashMap<>();
        map.put("list",orderList);
        map.put("total",orderMapper.getShipOrderTotal(account));
        return ResponseInfo.success("获取成功",map);
    }
}
