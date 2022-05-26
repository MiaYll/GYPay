package me.wangcai.gypayserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Account;
import me.wangcai.gypayserver.model.param.AccountInfoParam;
import me.wangcai.gypayserver.model.response.AccountInfoResponse;

public interface IAccountService extends IService<Account> {
    boolean checkKey(String name,String key);
    AccountInfoResponse getAccountInfo(String startTime, String endTime, Account account);
    ResponseInfo getMonthInfo(String time, String account);
}
