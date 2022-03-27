package me.wangcai.gypayserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.wangcai.gypayserver.model.entity.Account;

public interface IAccountService extends IService<Account> {
    boolean checkKey(String name,String key);
}
