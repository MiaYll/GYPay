package me.wangcai.gypayserver.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.ali.bean.AliTransferOrder;
import com.egzosn.pay.ali.bean.AliTransferType;
import com.egzosn.pay.common.bean.*;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatformMerchantDetails;
import com.egzosn.pay.spring.boot.core.provider.MerchantDetailsManager;
import me.wangcai.gypayserver.enums.PayType;
import me.wangcai.gypayserver.model.entity.Order;
import me.wangcai.gypayserver.model.response.OpenIdResponse;
import me.wangcai.gypayserver.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.spring.boot.core.PayServiceManager;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;
import com.egzosn.pay.spring.boot.core.bean.MerchantQueryOrder;
import com.egzosn.pay.web.support.HttpRequestNoticeParams;
import org.springframework.web.client.RestTemplate;

/**
 * @author egan
 * email egzosn@gmail.com
 * date 2019/5/26.20:10
 */

@RequestMapping("pay")
@Controller
public class PayMerchantController {

    @Autowired
    private PayServiceManager manager;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IOrderService orderService;

    //微信调用JSAPI
    @RequestMapping("/wechat/success")
    public String wechatPaySuccessPage(){
        return "wechat/success";
    }

    //微信调用JSAPI
    @RequestMapping("/wechat")
    public String wechatPayPage(Model model, HttpSession session){
        String orderId = (String) session.getAttribute("orderId");
        if(orderId == null) return null;
        Order order = orderService.queryOrder(orderId);
        model.addAttribute("openId",session.getAttribute("openId"));
        model.addAttribute("order",order);
        return "wechat/pay";
    }

    //微信支付入口
    @RequestMapping(value = "/check/{orderId}")
    public void weChatAuthy(@PathVariable String orderId,HttpServletResponse response, HttpSession session) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if(orderService.isPayed(orderId)){
            response.getWriter().write("该订单已支付!");
            return;
        }
        Order orderInfo = orderService.queryOrder(orderId);
        switch (orderInfo.getType()){
            case WECHAT:
                String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0a9fdc9c12b6bfe4"+
                        "&redirect_uri=http://server.guangyu7.cn/pay/wechat/slient/check"  +
                        "&response_type=code" +
                        "&scope=snsapi_base" +
                        "&state=STATE#wechat_redirect";
                session.setAttribute("orderId",orderId);
                response.sendRedirect(url);
                break;
            case ALIPAY:
                MerchantPayOrder payOrder = new MerchantPayOrder(PayType.ALIPAY.name(),AliTransactionType.WAP.getType(),orderInfo.getItemName(), orderInfo.getDescription(), new BigDecimal(orderInfo.getPrice()) , orderId);
                //获取支付订单信息
                String msg = manager.toPay(payOrder);
                System.out.println(msg);
                response.getWriter().write(msg);
                break;

        }
    }

    /**
     * 获得openId,静默授权
     * @param code
     * @param session
     * @param response
     */
    @RequestMapping(value = "/wechat/slient/check")
    public void callBackBase(@RequestParam(value = "code", required = false) String code, HttpSession session,
                             HttpServletResponse response) throws IOException {

        if (!StringUtils.isEmpty(code)) {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx0a9fdc9c12b6bfe4" +
                    "&secret=359f0e01c7d0fea242eec7a9a5513365" +
                    "&code=" + code +
                    "&grant_type=authorization_code";
            String res = restTemplate.getForEntity(url, String.class).getBody();
            OpenIdResponse openIdResponse = JSON.parseObject(res, OpenIdResponse.class);
            session.setAttribute("openId",openIdResponse.getOpenid());
            //获取openId， 可以存到session中，后面pay会用到
            response.sendRedirect("/pay/wechat");
            //可以重定向到你要拉取支付的页面(例如：输入金额面值大小页面)，省略...
        }
    }


    /**
     * 支付回调地址
     *
     * @param request   请求
     * @return 支付是否成功
     * 拦截器相关增加， 详情查看{@link com.egzosn.pay.common.api.PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     * <p>
     * 业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     * </p>
     * 如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     */
    @RequestMapping(value = "payBack/{type}.json")
    @ResponseBody
    public String payBack( HttpServletRequest request, @PathVariable PayType type) {
        return manager.payBack(type.name(), new HttpRequestNoticeParams(request));
    }

    /**
     * jsapi
     * @return 返回jsapi所需参数
     */
    @RequestMapping(value = "jsapi")
    @ResponseBody
    public Map<String, Object> jsapi(String openid, String orderId) {
        Order order = orderService.queryOrder(orderId);
        //获取对应的支付账户操作工具（可根据账户id）
        MerchantPayOrder payOrder = new MerchantPayOrder(PayType.WECHAT.name(), "JSAPI", "支付" , order.getItemName(),new BigDecimal(order.getPrice()), orderId);
        payOrder.setOpenid(openid);
        return manager.getOrderInfo(payOrder);
    }

//    /**
//     * 转账到支付宝
//     */
//    @RequestMapping(value = "/transferToAliPay")
//    @ResponseBody
//    public Map<String, Object> transferToAliPay() {
//        AliTransferOrder aliTransfer = new AliTransferOrder();
//        aliTransfer.setBizScene("DIRECT_TRANSFER");
//        aliTransfer.setBusinessParams("{\"payer_show_name_use_alias\":\"true\"}");
//        aliTransfer.setTransAmount(new BigDecimal(1));
//        aliTransfer.setPayeeAccount("13385869080");
//        aliTransfer.setIdentityType("ALIPAY_LOGON_ID");
//        aliTransfer.setIdentity("13385869080");
//        aliTransfer.setName("王鸿宇");
//        aliTransfer.setTransferType(AliTransferType.TRANS_ACCOUNT_NO_PWD);
//        aliTransfer.setOutBizNo(UUID.randomUUID().toString().replace("-",""));
//        aliTransfer.setOrderTitle("测试发送");
//        return manager.transfer("ALIPAYTransfer",aliTransfer);
//    }


}