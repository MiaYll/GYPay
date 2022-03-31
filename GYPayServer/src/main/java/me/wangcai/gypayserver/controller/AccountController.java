package me.wangcai.gypayserver.controller;

import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.param.AccountVerifyParam;
import me.wangcai.gypayserver.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping("/verify")
    public ResponseInfo verify(@RequestBody AccountVerifyParam accountVerifyParam){
        return ResponseInfo.success("请求成功",accountService.checkKey(accountVerifyParam.getUsername(),accountVerifyParam.getPassword()));
    }
}
