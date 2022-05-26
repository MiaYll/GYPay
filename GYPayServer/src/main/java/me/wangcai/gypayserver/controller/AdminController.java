package me.wangcai.gypayserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Account;
import me.wangcai.gypayserver.model.param.AccountSettings;
import me.wangcai.gypayserver.model.param.AccountVerifyParam;
import me.wangcai.gypayserver.model.response.AccountInfoResponse;
import me.wangcai.gypayserver.service.IAccountService;
import me.wangcai.gypayserver.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/admin")
@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private IAccountService accountService;

    @PostMapping("/accountList")
    public ResponseInfo getAccountList(){
        List<Account> accountList = accountService.list();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        List<AccountInfoResponse[]> list = new ArrayList<>();
        for (Account account : accountList) {
            AccountInfoResponse[] accountInfoResponses = new AccountInfoResponse[2];
            accountInfoResponses[0] = accountService.getAccountInfo(LocalDateTime.now().format(formatter),null,account);
            accountInfoResponses[1] = accountService.getAccountInfo(LocalDateTime.now().plusDays(-1).format(formatter),LocalDateTime.now().format(formatter),account);
            list.add(accountInfoResponses);
        }
        return ResponseInfo.success("获取成功",list);
    }

    @PostMapping("/addAccount")
    public ResponseInfo addAccount(@RequestBody AccountVerifyParam accountVerifyParam){
        Account account = new Account();
        account.setName(accountVerifyParam.getUsername());
        account.setRate(5);
        account.setPassword(MD5Utils.md5(accountVerifyParam.getPassword()));
        if(accountService.getOne(new QueryWrapper<Account>().eq("name",accountVerifyParam.getUsername())) != null){
            return ResponseInfo.error("用户已存在!");
        }
        if(accountService.save(account)){
            return ResponseInfo.success("添加成功");
        }else{
            return ResponseInfo.error("添加失败");
        }
    }

    @RequestMapping("/editRate")
    public ResponseInfo editAccountRate(@RequestBody AccountSettings accountSettings){
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("name", accountSettings.getName()));
        if(accountSettings.getRate() < 0 && accountSettings.getRate() > 6){
            return ResponseInfo.error("费率超出范围!");
        }
        account.setRate(accountSettings.getRate());
        if(accountService.updateById(account)){
            return ResponseInfo.success("修改成功");
        }else{
            return ResponseInfo.error("修改失败");
        }
    }

    @RequestMapping("/deleteAccount")
    public ResponseInfo deleteUser(@RequestBody AccountSettings accountSettings){
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("name", accountSettings.getName()));
        if(accountService.removeById(account.getId())){
            return ResponseInfo.success("删除成功");
        }else{
            return ResponseInfo.error("删除失败");
        }
    }
}
