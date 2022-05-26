package me.wangcai.gypayserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Account;
import me.wangcai.gypayserver.model.param.AccountSettings;
import me.wangcai.gypayserver.model.param.AccountVerifyParam;
import me.wangcai.gypayserver.service.IAccountService;
import me.wangcai.gypayserver.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping("/verify")
    public ResponseInfo verify(@RequestBody AccountVerifyParam accountVerifyParam){
        if(!accountService.checkKey(accountVerifyParam.getUsername(),accountVerifyParam.getPassword())){
            return ResponseInfo.error("校验失败!");
        }
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("name", accountVerifyParam.getUsername()));
        if(account.isAdmin()){
            return ResponseInfo.success("绑定成功,您是尊贵的管理员!",account);
        }
        return ResponseInfo.success("绑定成功!",account);
    }

    @RequestMapping("/settings")
    public ResponseInfo accountSettings(@RequestBody AccountSettings accountSettings,HttpServletRequest request){
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("name", request.getHeader("name")));
        if(accountSettings.getPassword() == null || accountSettings.getPassword().length() < 6){
            return ResponseInfo.error("密码不符合规范!");
        }
        account.setPassword(MD5Utils.md5(accountSettings.getPassword()));
        accountService.updateById(account);
        return ResponseInfo.success("修改成功!",account);
    }
}
