package org.example.service;

import org.example.pojo.PayData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-12 22:44
 */
@Service
@FeignClient(value = "test-pay")
public interface IPayService {
    @GetMapping("/test/pay/zhiFuBaoPay")
    void zhiFuBaoPay(PayData payData);
}
