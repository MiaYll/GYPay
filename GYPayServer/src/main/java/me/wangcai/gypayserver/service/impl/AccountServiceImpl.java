package me.wangcai.gypayserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.wangcai.gypayserver.mapper.AccountMapper;
import me.wangcai.gypayserver.mapper.RecordMapper;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Account;
import me.wangcai.gypayserver.model.entity.Order;
import me.wangcai.gypayserver.model.entity.Record;
import me.wangcai.gypayserver.model.param.AccountInfoParam;
import me.wangcai.gypayserver.model.response.AccountInfoResponse;
import me.wangcai.gypayserver.service.IAccountService;
import me.wangcai.gypayserver.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public boolean checkKey(String name, String key) {
        QueryWrapper<Account> wappers = Wrappers.query();
        wappers.eq("name",name);
        Account account = this.getOne(wappers);
        if(account == null) return false;
        return account.getPassword().equals(MD5Utils.md5(key));
    }

    @Override
    public ResponseInfo getAccountInfo(AccountInfoParam accountInfoParam, String account) {
        LocalDateTime end = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(accountInfoParam.getEnd() != null) end = LocalDateTime.parse(accountInfoParam.getEnd(),formatter);
        LocalDateTime start = LocalDateTime.parse(accountInfoParam.getStart(),formatter);
        List<Order> orderList = accountMapper.getAccountInfo(start, end, account);
        double total = 0;
        for (Order order : orderList) {
            total += order.getPrice();
        }
        AccountInfoResponse response = new AccountInfoResponse(start,end,total,orderList.size());
        return ResponseInfo.success("获取成功",response);
    }
}
