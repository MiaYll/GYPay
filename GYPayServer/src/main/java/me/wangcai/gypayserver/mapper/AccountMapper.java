package me.wangcai.gypayserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.wangcai.gypayserver.model.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
