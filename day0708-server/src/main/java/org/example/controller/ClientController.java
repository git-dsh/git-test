package org.example.controller;

import org.example.pojo.TbClient;
import org.example.service.ClientServer;
import org.example.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:41
 */
@RestController
@RequestMapping("/server/client")
public class ClientController {
    @Autowired
    ClientServer clientServer;
    @PostMapping("/login")
    public R login(@RequestBody TbClient tbClient){
        return clientServer.login(tbClient);
    }
}
