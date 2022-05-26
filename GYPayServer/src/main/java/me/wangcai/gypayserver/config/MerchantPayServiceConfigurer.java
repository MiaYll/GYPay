package me.wangcai.gypayserver.config;

import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.spring.boot.core.PayServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;

import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.bean.AliMerchantDetails;
import com.egzosn.pay.spring.boot.core.merchant.bean.WxV3MerchantDetails;
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
        AliMerchantDetails aliMerchantDetails = new AliMerchantDetails();
        aliMerchantDetails.setDetailsId("ALIPAYTransfer");
        aliMerchantDetails.setPid("2088341992166841");
        aliMerchantDetails.setAppId("2021003125649275");
        aliMerchantDetails.setSignType("RSA2");
        aliMerchantDetails.setCertSign(true);
        aliMerchantDetails.setSeller("2088341992166841");
        aliMerchantDetails.setInputCharset("UTF-8");
        aliMerchantDetails.setCertStoreType(CertStoreType.PATH);
        aliMerchantDetails.setAliPayCert("C:/alipayCertPublicKey_RSA2.crt");
        aliMerchantDetails.setAliPayRootCert("C:/alipayRootCert.crt");
        aliMerchantDetails.setMerchantCert("C:/appCertPublicKey_2021003125649275.crt");
        aliMerchantDetails.setKeyPrivate("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC0V0uWv0I1Bb8TYB6kttOG4H81A1TWrMV4H9xbmzyyhHzJCbdJGYro0eiuGOvhbp7zOFTpC6VRR+VHLpTb3eNebL4baIKhaqTuWVi2qXoc97c3W75FSx7TBdKCLqwflG9sDBwmL+DCr7MViTrMwAULalfGEaIgGoch4vEOUTrJZI98r8ol+w/0fWM8QJweislrRB/YyEgLVPJPOMFQSDV9120iiqWMrlf68daRsD414claqA2uhb8uvAdykQw4AWfbrtuMHhSPWRuv62tLbq8K0drwa4+nlAbs3SblVPFvSCYqIsLsyHmKluTvGfdkGtkZ86StP9526jJ19pE1qXTBAgMBAAECggEAbPuYNYhthUdsulPLEPVfjVHb7fiOa5FLYJW4gC+nFabVFlTly6pA9QPtXlC3M6u7KPaw8YjVJfKRz1uKH/jOr8B9fmjBELU26Mdbwj9XCWx172Fk8dE81n/dQEjeQdNdJmmHFVi3tk+hpPEidJvSMJzaYFtFo6sEYOkZjfJu2UcFatqDwauly7AddOagNAzPEo+XolhJ2RXw70hiwlf7oh9rZ8mjxJYkyLGy2C40uN4QBhmIr0kTC6m12i0N9fuBDH0qBcYuFkKuxN8qXpnja03p4o3jv3TIM1JMmAimviRF9oT1F5ptuiPyVrz674ghtL7dgs+ALbuD3oQyprJ98QKBgQDZy3RORKItdBxJ9NNrmqXctwJ1OHryx46CdIf6YhzHNchsrZTLWVOCBLX6lBGA7PgQ1pgAP39nJm9Vyrf7LvQzRkzqhOdswWGbAXHMivBnbn92HE7hz/EDixsXxQE3mZkQCTWHA95s5pFeqIB3rY2+G3RqyTOd0KKYb/Bxpc59TQKBgQDT+eYwkaiTBsnr96s1lBevLdLtSb7UiP9nDJA5lmfKSXX+OBPGuubkRHFr7zzbtafHCFdlKXZsr6Fd54//nx6MM2cIHgyEx9p/z9KI9r++yhwGfg3WDtw9XkOsH8UDMAW2ydizcRqSO0IHgllkiSy3G/6ERa8W5EUSBjr2yezrRQKBgBkZspjGni5A4HXBipAvLYhnaxgZX0oVp53vUjCQGCL5VtT3mFtrOw2T/2kWoYHLTbebE7+LkeJQcqpfh56iRbCXHIw2rvlAvEACBZx+MsCo4nW5Q3oQ3gT7hi7SHJbM0/AeL5YU4xCH7h8LqmqgjOJpBZy8/X7lqaeJ1xsx8b9NAoGAPoMg5kveWdWTsLnuTLOqtGegH4gy9bydDUfANKttWlOFr33cHyw5MTIZ/Mmluel9XA3SsCulF/JHdUj3x1umh5QVG2i5KmNBxZWFMg0KiPpTf3qnG+xv+M0WyejokcMUvE4UUP7W5i8BjvNa53IotKtVyK5AL1EMKX+a3fK4or0CgYAOfv0nqgJzE7TIGIsm980gaMpaTIWTsr1lsW1K4/7jT98MvNlyyeDyJVQAKQbJiJmi88iClWMgUp4pYix3tAFhjyPCJOaLGIgdg1UKs2+JJWg2/rgJt0myFMht5mPkzATgiiS6gstVRljasBGdmGwdesIAnfGPENrQAuIMZ7srRQ==");
        aliMerchantDetails.setKeyPublic("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtFdLlr9CNQW/E2AepLbThuB/NQNU1qzFeB/cW5s8soR8yQm3SRmK6NHorhjr4W6e8zhU6QulUUflRy6U293jXmy+G2iCoWqk7llYtql6HPe3N1u+RUse0wXSgi6sH5RvbAwcJi/gwq+zFYk6zMAFC2pXxhGiIBqHIeLxDlE6yWSPfK/KJfsP9H1jPECcHorJa0Qf2MhIC1TyTzjBUEg1fddtIoqljK5X+vHWkbA+NeHJWqgNroW/LrwHcpEMOAFn267bjB4Uj1kbr+trS26vCtHa8GuPp5QG7N0m5VTxb0gmKiLC7Mh5ipbk7xn3ZBrZGfOkrT/eduoydfaRNal0wQIDAQAB");
        aliMerchantDetails.initService();

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
                .and()
        .addMerchantDetails(aliMerchantDetails);

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