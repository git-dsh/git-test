package org.example.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lenovo
 * @description TODO
 * @date 2023/3/15 09:24
 */
@Component
public class WxPayUtils {

    public String wxPay(String orderId){
        //1.下单
        //2.获取订单号  金额 信息
        //3.组装信息
        try {
            //根据订单信息封装得到请求XML报文
            Map<String, String> params = new HashMap<String, String>();
            params.put("mch_id", Constants.MCH_ID);
            params.put("appid", Constants.APP_ID);
            params.put("notify_url", Constants.NOTIFY_URL);
            params.put("fee_type", Constants.FEE_TYPE);
            params.put("device_info", Constants.DEVICE_INFO);
            params.put("trade_type", Constants.TRADE_TYPE);
            params.put("sign_type", Constants.SIGN_TYPE);
            params.put("nonce_str", CommUtil.getNonce_str());
            params.put("out_trade_no", orderId);
            params.put("spbill_create_ip", "127.0.0.1");
            params.put("body", "订单信息");
            params.put("product_id", orderId);
            params.put("total_fee", "1");
            //MD5(appid=xxxxx&body=bwie微信支付&........&密钥)---->sign
            params.put("sign", CommUtil.generateSignature(params, Constants.API_KEY, "HMACSHA256"));

            //xstream

            String requestXML = XMLUtil.mapToXml(params);
            //将XML报文发送给微信支付服务器得到返回结果(XML)
            String resultXML = HttpUtil.postData(Constants.UFDODER_URL, requestXML);
            //解析返回的XML，从中拿到code_url字符串
            Map<String, String> result = XMLUtil.doXMLParse(resultXML);
            String codeURL = result.get("code_url");
            System.out.println(codeURL);
            return codeURL;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "1";
    }

    public Boolean refreshWxPay(String orderId) throws Exception {
        Boolean flag=false;
        System.out.println(orderId);
        Map<String,String> params=new HashMap<String,String>();
        params.put("mch_id", Constants.MCH_ID);
        params.put("appid", Constants.APP_ID);
        params.put("out_trade_no", orderId);
        params.put("nonce_str", CommUtil.getNonce_str());
        params.put("sign_type", Constants.SIGN_TYPE);
        params.put("sign", CommUtil.generateSignature(params, Constants.API_KEY, "HMACSHA256"));

        String requestXML = XMLUtil.mapToXml(params);
        System.out.println(requestXML);
        String resultXML = HttpUtil.postData(Constants.CHECK_URL, requestXML);

        Map<String, String> result = XMLUtil.doXMLParse(resultXML);
        System.out.println(resultXML);
        String tradeState = result.get("trade_state");
        if(tradeState.equals("SUCCESS")){
            flag=true;
        }
        return flag;
    }
}
