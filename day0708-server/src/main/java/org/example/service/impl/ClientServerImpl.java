package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.mapper.ClientMapper;
import org.example.pojo.TbClient;
import org.example.service.ClientServer;
import org.example.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:42
 */
@Service
public class ClientServerImpl implements ClientServer {
    @Autowired
    ClientMapper clientMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public R login(TbClient tbClient) {
        if(!ObjectUtils.isEmpty(tbClient.getClientName())){
            TbClient tbClient1 = clientMapper.selectOne(new QueryWrapper<TbClient>().lambda().eq(TbClient::getClientName, tbClient.getClientName()));
            if(!ObjectUtils.isEmpty(tbClient.getPassword())){
                if(!tbClient1.getPassword().equals(tbClient.getPassword())){
                    return R.error(500,"密码错误");
                }
                String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, "bwie")
                        .claim("id", tbClient1.getId())
                        .claim("clientName", tbClient1.getClientName())
                        .compact();
                tbClient1.setToken(token);
                stringRedisTemplate.opsForValue().set("token",token,15, TimeUnit.MINUTES);
                return R.success(tbClient1);
            }
        }
        return R.error(500,"用户不存在");
    }
}
