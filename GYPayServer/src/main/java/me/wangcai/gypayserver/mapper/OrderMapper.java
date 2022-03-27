package me.wangcai.gypayserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.wangcai.gypayserver.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> getUnPayOrder();
}
