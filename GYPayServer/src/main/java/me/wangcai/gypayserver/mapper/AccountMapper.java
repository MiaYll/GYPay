package me.wangcai.gypayserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.wangcai.gypayserver.model.entity.Account;
import me.wangcai.gypayserver.model.entity.Order;
import me.wangcai.gypayserver.model.response.AccountInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    List<Order> getAccountInfo(LocalDateTime start, LocalDateTime end, String account);
}
