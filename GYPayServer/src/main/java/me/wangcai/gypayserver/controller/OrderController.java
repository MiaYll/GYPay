package me.wangcai.gypayserver.controller;

import lombok.SneakyThrows;
import me.wangcai.gypayserver.model.ResponseInfo;
import me.wangcai.gypayserver.model.entity.Order;
import me.wangcai.gypayserver.model.param.AccountInfoParam;
import me.wangcai.gypayserver.model.param.CreateOrderParam;
import me.wangcai.gypayserver.service.IAccountService;
import me.wangcai.gypayserver.service.IOrderService;
import me.wangcai.gypayserver.utils.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IAccountService accountService;

    @PostMapping("/create")
    public ResponseInfo createOrder(@RequestBody CreateOrderParam createOrderParam,HttpServletRequest request){
        return orderService.createOrder(createOrderParam,request.getHeader("name"));
    }

    @GetMapping("/query/{orderId}")
    public ResponseInfo queryOrder(@PathVariable String orderId){
        Order order = orderService.queryOrder(orderId);
        if(order != null){
            return ResponseInfo.success("查询成功",order);
        }
        return ResponseInfo.error("未查找到此订单号");
    }

    @SneakyThrows
    @GetMapping("/qrcode/{orderId}")
    public void Order(@PathVariable String orderId, HttpServletResponse response){
        Order order = orderService.queryOrder(orderId);
        response.setHeader("content-type","image/png");
        QRCodeUtil.encode("http://server.guangyu7.cn/pay/check/" + orderId,
                "",
                true,
                response.getOutputStream()
                );
    }

    //获取一天内的未支付订单

    @SneakyThrows
    @GetMapping("/unShipOrder")
    public ResponseInfo getUnShipOrder(HttpServletRequest request){
        List<Order> orderList = orderService.getUnShipOrder(request.getHeader("name"));
        return ResponseInfo.success("获取成功",orderList);
    }

    @GetMapping("/ship/{orderId}")
    public ResponseInfo shipOrder(@PathVariable String orderId,HttpServletRequest request){
        return orderService.shipOrder(orderId,request.getHeader("name"));
    }



    @PostMapping("/info")
    public ResponseInfo getAccountInfo(@RequestBody AccountInfoParam accountInfoParam, HttpServletRequest request){
        return accountService.getAccountInfo(accountInfoParam,request.getHeader("name"));
    }
}
