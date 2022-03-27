package me.wangcai.gypayserver.config;

import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.spring.boot.core.PayServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;

import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.AliPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxV3PaymentPlatform;
import me.wangcai.gypayserver.config.handlers.ALiPayMessageHandler;
import me.wangcai.gypayserver.config.handlers.WxPayMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 支付服务配置
 *
 * @author egan
 *         email egzosn@gmail.com
 *         date 2019/5/26.19:25
 */
@Configuration
public class MerchantPayServiceConfigurer implements PayServiceConfigurer {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AutowireCapableBeanFactory spring;
    @Autowired
    private WxPayMessageHandler wxPayMessageHandler;
    @Autowired
    private ALiPayMessageHandler aLiPayMessageHandler;
    /**
     * 商户配置
     *
     * @param merchants 商户配置
     */
    @Override
    public void configure(MerchantDetailsServiceConfigurer merchants)  {

//        数据库文件存放 /doc/sql目录下
        merchants.jdbc()
                //是否开启缓存，默认不开启,这里开启缓存
                .cache(true)
                .template(jdbcTemplate);
        //微信请求配置，详情参考https://gitee.com/egzosn/pay-java-parent项目中的使用
//        wxHttpConfigStorage.setStorePassword("1623380339");
//        WxPayConfigStorage wxPayConfigStorage = new WxPayConfigStorage();
//        wxPayConfigStorage.setMchId("1623380339");
//        wxPayConfigStorage.setAppId("wx0a9fdc9c12b6bfe4");
//        wxPayConfigStorage.setV3ApiKey("m5uFu31zJrzE4uPX3Twd3xqympe88QM0");
//        wxPayConfigStorage.setNotifyUrl("http://server.guangyu7.cn/payBack");
//        wxPayConfigStorage.setReturnUrl("http://server.guangyu7.cn/payBack");
//        wxPayConfigStorage.setSignType("MD5");
//        wxPayConfigStorage.setCertStoreType(CertStoreType.PATH);
//        wxPayConfigStorage.setApiClientKeyP12("C://apiclient_cert.p12");
//        wxPayConfigStorage.setInputCharset("utf-8");
        //内存Builder方式
        merchants.inMemory()
                .wxV3()
                .detailsId("WECHAT")
                .appId("wx0a9fdc9c12b6bfe4")
                .mchId("1623380339")
                .v3ApiKey("m5uFu31zJrzE4uPX3Twd3xqympe88QM0")
                .certStoreType(CertStoreType.PATH)
                .apiClientKeyP12("C://apiclient_cert.p12")
                .notifyUrl("http://server.guangyu7.cn/pay/payBack/WECHAT.json")
//                .returnUrl("http://server.guangyu7.cn/pay/payBack/WECHAT.json")
//                .notifyUrl("http://115.237.205.121:81/pay/payBack/WECHAT.json")
                .inputCharset("utf-8")
                .signType("MD5")
                .and()
                .ali()
                .detailsId("ALIPAY")
                .pid("2088341992166841")
                .appid("2021003124646903")
                .keyPublic("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApX2vDnsUI5IfgobmTXepOFSn5vWO+KH/GusHNw5CHhyUGqTkGahIfaBVlWZqNALvOp7FspQQ4TzeUJ4ZpaO200qWTdLRDff+DvMjpbiFdZQZcyxlIZsFMSiFrQhJmro2rMVDn0P9AcsD3jsOcDKOqKnz2brCIlDuJvWUmuq01JryG/hIDvdSZejJuw6XuQh1r/L7jncM3LzXCF3s19Mj/y6E5heQrTXubYH9IGzTUBtLFYqNdeY42Fr45tsLEYoRcVJ5V9T92L+CuaRzuVvq3HJkCS7FqiI2+/jxpWDnraZKV5Yn3ytLa494S/oz3GwMyRxvN1UiNbWnMKH1MJYAYwIDAQAB")
                .keyPrivate("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHsO3YCfC6nUXW\n" +
                        "63IPvwMruGEKFNwMB6adlu//lPFMrGHUGs3xaOBYpI2PI6crvU5w/z9JPoav3Pjd\n" +
                        "FdbUOdWWkOLAspeM59q6/Yw6sO4W5SFL94HGyQjpAWeWHMxzrwObQu5bSWXfMdYa\n" +
                        "rnwbqV6lurUAW1oPQzz7rYvcT05leWfBiEzqXZ/XX3H5SVMyQJxO6/5E8ktModnx\n" +
                        "4bjRElCivPDNu8SNoSD0jXYYOy8puPz7UPPPQ2UeiJkpAjTSOArxK12rTHyQ4UM1\n" +
                        "AejyrA1kqmEgp56lMFhpqzIMoPOXoo2425/5xnpLpXPpga5u21lqAv3ctppy3rQ6\n" +
                        "1aasHQIxAgMBAAECggEAZIIfFG/cZHEoZ8ZVwKspk2XYIdTh9IQdTpBb984lravO\n" +
                        "CY38PhfFY5hfT75oM9Gym5k9Z0Y7UGTuNYj6ehMp+ACwwMCjGt7P7vITFK9dBtUI\n" +
                        "702CWmVxQKXeV2XjWg6qyAjmI7hLuA1DcGaGPP4Aj3XsCGPybmAvX5W4IYkhMl0G\n" +
                        "wrbkyqyhKAvnlmIhwzVmbmNfC3S2MC9UmpVL0ILR+Re7jhLbj5HUHKRmk2cgQSPj\n" +
                        "v6VfYttQ/zZeW/2Sy84hllKqLkgkSIiegegjaccf5kpcTrZg7oC+2evOFE0HaumR\n" +
                        "w3KzZxqCGWXM9QuiCJpjwF/GyTQ9S3XpGl16QJ7GqQKBgQDyIvjbf21z3BwFU/xj\n" +
                        "cHP7JYUmz3RZQ3IfVWBGiqxNrbmrajF6Wa+v3NhbPcRiIZzo5tLx9qIDkvpcUcE5\n" +
                        "10UFVkh5Pea9hm8Jzj3jXgxlbcgAv5B69HMXnfX22pN50L8G0GP3wyejKfQorykj\n" +
                        "i8gdcJz9mFmFoAiVHjpmSXsURwKBgQDTH9P5r7gvq6DuIDQtobzoqA+FT764bYHV\n" +
                        "M2SqfwhJnNoCXhLGe2zrbmhrBlC2lc52fdBjeeVSIMFmj3tZ/l9WWHeCTp2G6LHm\n" +
                        "3kcvjU8J6T0YrRBf7GRNlJnF9YJfQC75EANgjQ/MWECSf0X4XCgrm75zTnnO5oYu\n" +
                        "IngbAhhJxwKBgECvnxPkgvUExGeX/EEEdbeOWnf3y/lcz0wXntS5jzZhRszIXdd1\n" +
                        "xj2RHiuh4KjmmeiOYaRKYxXZWhD3tkshe0rltg7Nvqq7E0bnVhk7sswV++xXQN3Y\n" +
                        "GDci9+auld9hKOdFHK+/7wLj3lpQEQlx+lADGa2V2bSdAC32GVviHYNZAoGADZ7M\n" +
                        "ChuwAkNMSpCBTUBV85yEA9G1OCi4a4y5PdacLkOUPPrcszlbOYUR73Wk12KqwzIy\n" +
                        "s3g1tyqtz3QKPTAP7n4el6WCvCVe9Mn6RbMUGkuVyXXAzK9veSHRGdhQ5Nw7K45A\n" +
                        "6YWwMzquT8UuOmuXm9icXMv/R73PgkJvBS8JahkCgYEAyINReoPFiZGFEjo6d8QH\n" +
                        "LbGvARdQnRZXcQs9s8sNNepm8cOkym3YiDJlF4sQGn22d4XBJ4RepmmzsbFtkA2Z\n" +
                        "qZInQubnu7BqE47rMJZI93v+1vLMs+cAqDc7Q1BezxPSf2BSHaAYoxvd13ZTiDA/\n" +
                        "EdrQ9TrcZR/wi1jhm+GpwEI=")
                .notifyUrl("http://server.guangyu7.cn/pay/payBack/ALIPAY.json")
                .signType("RSA2")
                .seller("2088341992166841")
                .inputCharset("utf-8")
                .initService()
        ;


      /*  //------------内存手动方式------------------
        UnionMerchantDetails unionMerchantDetails = new UnionMerchantDetails();
        unionMerchantDetails.detailsId("3");
        //内存方式的时候这个必须设置
        unionMerchantDetails.setCertSign(true);
        unionMerchantDetails.setMerId("700000000000001");
        //公钥，验签证书链格式： 中级证书路径;
        unionMerchantDetails.setAcpMiddleCert("D:/certs/acp_test_middle.cer");
        //公钥，根证书路径
        unionMerchantDetails.setAcpRootCert("D:/certs/acp_test_root.cer");
        //私钥, 私钥证书格式： 私钥证书路径
        unionMerchantDetails.setKeyPrivateCert("D:/certs/acp_test_sign.pfx");
        //私钥证书对应的密码
        unionMerchantDetails.setKeyPrivateCertPwd("000000");
        //证书的存储方式
        unionMerchantDetails.setCertStoreType(CertStoreType.PATH);
        unionMerchantDetails.setNotifyUrl("http://127.0.0.1/payBack4.json");
        // 无需同步回调可不填  app填这个就可以
        unionMerchantDetails.setReturnUrl("http://127.0.0.1/payBack4.json");
        unionMerchantDetails.setInputCharset("UTF-8");
        unionMerchantDetails.setSignType("RSA2");
        unionMerchantDetails.setTest(true);
        //手动加入商户容器中
        merchants.inMemory().addMerchantDetails(unionMerchantDetails);*/
    }
    /**
     * 商户配置
     *
     * @param configurer 支付消息配置
     */
    @Override
    public void configure(PayMessageConfigurer configurer) {
//        PaymentPlatform wxPaymentPlatform = PaymentPlatforms.getPaymentPlatform(WxPaymentPlatform.platformName);
//        configurer.addHandler(wxPaymentPlatform, wxPayMessageHandler);
        PaymentPlatform wxPaymentPlatform = PaymentPlatforms.getPaymentPlatform(WxV3PaymentPlatform.platformName);
        PaymentPlatform aliPaymentPlatform = PaymentPlatforms.getPaymentPlatform(AliPaymentPlatform.platformName);
        configurer.addHandler(wxPaymentPlatform, wxPayMessageHandler);
        configurer.addHandler(aliPaymentPlatform, aLiPayMessageHandler);
    }
}