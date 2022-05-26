package me.wangcai.gypayserver.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Account;
import me.wangcai.gypayserver.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccountInterceptor implements HandlerInterceptor {

    @Autowired
    private IAccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String name = request.getHeader("name");
        String key = request.getHeader("password");
        response.setHeader("Content-type","application/json;charset=utf-8");
        if(name == null || key == null){
            response.getWriter().write(JSON.toJSONString(ResponseInfo.error("用户名或密码为空!")));
            return false;
        }
        if(!accountService.checkKey(name,key)){
            response.getWriter().write(JSON.toJSONString(ResponseInfo.error("用户名或密码错误!")));
            return false;
        }
        if(request.getRequestURI().toUpperCase().startsWith("/ADMIN/")){
            Account account = accountService.getOne(new QueryWrapper<Account>().eq("name", name));
            if(!account.isAdmin()) {
                response.getWriter().write(JSON.toJSONString(ResponseInfo.error("权限不足!")));
                return false;
            }
        }
        return true;
    }

}
