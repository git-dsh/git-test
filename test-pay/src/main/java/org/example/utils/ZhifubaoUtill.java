package org.example.utils;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.example.pojo.PayData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ZhifubaoUtill {

    String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCweE+2xjFG/eKyhZlCQZdl+DWq6nls0tkK8foVPJMATpp2tPd3TjgJHstwl5l3lgmEDNfOjreLLos/M4WnJSTElu2Lx4RqhIgSfIxnX5EFeTcdht/ZH13mmeX2cYGLX1/1zCuYxb0x6lRMXTtA0kK2rnByWVfNX+lCFmp/g4znDWi182Sa/Odej7dMrE5exSg/paDzzu9sIAMknphDfAil43IfF1xKxCqJ4j5/WCHTguCVbfJVZYRUsrSFOe1e9lbMC550Cj20sHbRRUSMt7LFV/CQBCUQuEHIi2TwmLkgSiDoXEAdXRDiSSr216s98sH8CR8q9PjtABYw9keq9atPAgMBAAECggEBAKEBhClMdpVA+t7KieQrmAiPB9gjChpB3Yjg8b0CgVScYs2/mrEdin63vAHEL18uYww584QgLlIsaxuE1IKFnyBGXAFWTfgpZ0UGocuHI/eMer7T2dRti+zjqS7KrNkOcaz/VR+T+mRaWgM6ZN9ldzzZtvp9jWByC44iiZRB4ALFcjfxvHEWw2hiJnSlRq63xL5+oLgSZK6EtIGWNcunE/RXxw8AoP/WTkgs5W0CPVQx7ZDjc0nqxBteUXx2i6nKSLm0fgYXzD+Qy8MKVPl5Rxg3QweCRd52B17xpT40thrLawdRwwdrNLVK9veZ9BEl+lhBHQgPC5FqaOwiydt0deECgYEA5UIFVUhpEJtGViC+JVKyC82OPVzGQLQ3FvMU3HAeYquFYpthHhfwTsvZr4vtuY92q4FWmmqF4HFd6PmUM8mL2wHGTXinRHSIZBQCHYpZvS6nNJtT9a+Pma7OgGjtwWZy15ifNo+5ErqPCrrG5lk5jKq1GR0qDlNqZolrpaxYeKkCgYEAxQ33EPIqHw2WXQ7ems/IU5aqXPWc85fWI+JfXAAt7g/CHmJ20ZzPvTsyyp/7WOKh9kRwyfnMVvW432YtPPozFJnGWWwdoZRbpA7hwoJg+JIVA/2jpuLtbc9SyFMv4rlCv9nM79NmcX44efprbsgqpBWN2dBUOt+i8siwaV5CJzcCgYBYNKmWoXCDCirqslhWRdXpAqJqa4p/lJbphocNNToLnpCIWt3n1TAJMRdFGmJRlagD3pRyfu+ZZiTKoVPrBO2BStKANh6tEOT1FFmoAfX6+rssW2LXUT+/0mjzMOvhEEi5OKhxNqTvVsX4S9qA/+1LeNaKnhTzRBbY8qBvMrsrWQKBgBlUOjXLau6i6Hj9uE9d9foIm81nJM2K7tOd/XPt8flvvRv6vBsldO519YlmFwDN4NOu6HCN0146J5FPqPD1mCtFuOE1rHlggIcOAqbl62EIJftlVuvUr4A17gYh6z3RYmsWq+kH21srRkcnkTekMxb0Tk95H9qRdHiQo1xn6UjxAoGBAKsgUJKAy2Qt11NH2FCYIFRYZlCj/EV3sSaJDIFbBECnuN8yJ2KWNP8OFAkKrYwbEQcNdntbJFu2eiljQNQ1OTwkQb88JWI3a79nFd93NKRHueWWMtC5VoRQ7wsmI7OpvhVPAzC6SEdxCYMWSzUkt/rr12Hc+YjxEPmYMe6VvN6x";
    String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuftnKfRDeTr+JexdUcPpJqIXlXj8jO5atELdMXBbSgJgqH5u6V1Eve4ZbXVHUSY1qtZff27tmCm9/3jDuqt0cZKoRKdQUUS5NLEPLutsWWWKn7qMO8jgCf8zFdiVoAFJ+Wapg5hLRyYumFodY3qtcnfhnsu7k26JnlxOg13x+XFdXpUBq5WSG3F0INheDZ+qA1hiA3kvkHCo7Km+9XTiwGusWFT+8KPkFt5ehglQkkdPz8Rz4RRzTcOeRM+CbB/QyaO58E9k2Q2dFHjNRjXIjXdYMKAznAtF0gnXBvVB+tVQsTSZ10sNsx/AXrHg0olTr7wZW+nNkTug+8GNCYTyhQIDAQAB";

    //生成支付订单接口
    public Map pay(PayData payData) throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId("9021000138655772");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo("21312312313123");
        model.setTotalAmount("1000");
        model.setSubject("支付宝");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        System.out.println(response.getBody());
        HashMap<Object, Object> map = new HashMap<>();
        if (response.isSuccess()) {
            System.out.println("调用成功");
            map.put("code",200);
            map.put("form",response.getBody());
        } else {
            map.put("code",400);
        }
        return map;
    }


    //查询接口
    public Boolean queryResult(String tradeNo) throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId("9021000138655772");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(tradeNo);
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        return response.isSuccess();
    }
}
