package org.example.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.alipay.api.AlipayApiException;
import com.aliyuncs.http.HttpResponse;
import org.example.pojo.PayData;
import org.example.service.PayService;
import org.example.utils.QRCodeUtil;
import org.example.utils.WxPayUtils;
import org.example.utils.ZhifubaoUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-12 22:15
 */
@RestController
@RequestMapping("/test/pay")
public class payController {
    @Autowired
    PayService payService;
    @Autowired
    ZhifubaoUtill zhifubaoUtill;
    @Autowired
    WxPayUtils wxPayUtils;
    @GetMapping("/zhiFuBaoPay")
    public void zhiFuBaoPay(PayData payData,HttpServletResponse response) throws AlipayApiException, IOException {
        Map pay = zhifubaoUtill.pay(payData);
        String form = (String) pay.get("form");
        response.setContentType("text/html;charset=utf8");
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }
    @GetMapping("/weiXinPay")
    public void weiXinPay(HttpServletResponse response){
        String orderId = RandomUtil.randomNumbers(10);
        String wxCode = wxPayUtils.wxPay(orderId);
        QRCodeUtil.createQRCode(response,wxCode);
    }
    @GetMapping("/updateOrderStatus")
    public void updateOrderStatus() throws Exception {
        String orderId = RandomUtil.randomNumbers(10);
        Boolean aBoolean = wxPayUtils.refreshWxPay(orderId);
        if(aBoolean){
            System.out.println("支付成功");
        }else{
            System.out.println("支付失败");
        }
    }
}
