package me.wangcai.gypayserver.config.handlers;

import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.wx.v3.api.WxPayService;
import com.egzosn.pay.wx.v3.bean.response.WxPayMessage;
import me.wangcai.gypayserver.enums.PayType;
import me.wangcai.gypayserver.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WxPayMessageHandler implements PayMessageHandler<WxPayMessage, WxPayService> {

    @Autowired
    private IOrderService orderService;

    @Override
    public PayOutMessage handle(WxPayMessage wxPayMessage, Map<String, Object> map, WxPayService wxPayService) throws PayErrorException {
        if ("SUCCESS".equals(wxPayMessage.getPayMessage().get("trade_state"))){
            orderService.orderFinish(wxPayMessage.getPayMessage().get("out_trade_no").toString(), PayType.WECHAT);
            return  wxPayService.getPayOutMessage("SUCCESS", "支付成功");
        }
        return  wxPayService.getPayOutMessage("FAIL", "失败");
    }
}