package me.wangcai.gypayserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.wangcai.gypayserver.mapper.AccountMapper;
import me.wangcai.gypayserver.model.entity.Account;
import me.wangcai.gypayserver.service.IAccountService;
import me.wangcai.gypayserver.utils.MD5Utils;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Override
    public boolean checkKey(String name, String key) {
        QueryWrapper<Account> wappers = Wrappers.query();
        wappers.eq("name",name);
        Account account = this.getOne(wappers);
        if(account == null) return false;
        return account.getPassword().equals(MD5Utils.md5(key));
    }
}
