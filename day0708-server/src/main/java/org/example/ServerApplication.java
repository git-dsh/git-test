package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:17
 */
@SpringBootApplication
@EnableFeignClients
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class,args);
    }
}
