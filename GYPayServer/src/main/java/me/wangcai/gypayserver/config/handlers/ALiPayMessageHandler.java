package me.wangcai.gypayserver.config.handlers;

import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import me.wangcai.gypayserver.enums.PayType;
import me.wangcai.gypayserver.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ALiPayMessageHandler implements PayMessageHandler<AliPayMessage, AliPayService> {

    @Autowired
    private IOrderService orderService;

    @Override
    public PayOutMessage handle(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {
        //com.egzosn.pay.demo.entity.PayType.getPayService()#48
        Object payId = payService.getPayConfigStorage().getAttach();

        Map<String, Object> message = payMessage.getPayMessage();
        //交易状态
        String trade_status = (String) message.get("trade_status");

        //上下文对象中获取账单
//        AmtApply amtApply = (AmtApply)context.get("amtApply");
        //日志存储
//        amtPaylogService.createAmtPaylogByCallBack(amtApply,  message.toString());
        //交易完成
        if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
            orderService.orderFinish(message.get("out_trade_no").toString(), PayType.ALIPAY);
            return payService.getPayOutMessage("success", "支付成功!");

        }/* else if ("WAIT_BUYER_PAY".equals(trade_status) || "TRADE_CLOSED".equals(trade_status)) {
        }*/

        return payService.getPayOutMessage("fail", "失败");
    }
}
