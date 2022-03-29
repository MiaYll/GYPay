package me.wangcai.gypayserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Account;
import me.wangcai.gypayserver.model.param.AccountInfoParam;

public interface IAccountService extends IService<Account> {
    boolean checkKey(String name,String key);
    ResponseInfo getAccountInfo(AccountInfoParam accountInfoParam,String account);
}
