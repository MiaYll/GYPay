package me.wangcai.gypayserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.wangcai.gypayserver.model.entity.Order;
import me.wangcai.gypayserver.model.param.PageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> getUnShipOrder(String account);
    List<Map> getShipOrder(String account, int start, int end);
    Integer getShipOrderTotal(String account);
}
