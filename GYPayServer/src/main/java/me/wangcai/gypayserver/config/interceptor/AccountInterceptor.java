package me.wangcai.gypayserver.config.interceptor;

import com.alibaba.fastjson.JSON;
import me.wangcai.gypayserver.model.ResponseInfo;
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
        String name = request.getHeader("name");
        String key = request.getHeader("key");
        response.setHeader("Content-type","application/json;charset=utf-8");
        if(name == null || key == null){
            response.getWriter().write(JSON.toJSONString(ResponseInfo.error("用户名或密码为空!")));
            return false;
        }
        if(!accountService.checkKey(name,key)){
            response.getWriter().write(JSON.toJSONString(ResponseInfo.error("用户名或密码错误!")));
            return false;
        }
        return true;
    }

}
