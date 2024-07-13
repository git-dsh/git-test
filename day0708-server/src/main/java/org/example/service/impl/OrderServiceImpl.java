package org.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.CarMapper;
import org.example.mapper.OrderMapper;
import org.example.mapper.OrderTypeMapper;
import org.example.mapper.TransportTypeMapper;
import org.example.pojo.*;
import org.example.service.IPayService;
import org.example.service.OrderService;
import org.example.util.R;
import org.example.utils.FastFileUtil;
import org.example.vo.MessageVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:36
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderTypeMapper orderTypeMapper;
    @Autowired
    TransportTypeMapper transportTypeMapper;
    @Autowired
    FastFileUtil fastFileUtil;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    CarMapper carMapper;
    @Autowired
    IPayService iPayService;

    @Override
    public R orderList(TbOrder tbOrder) {
        Page<TbOrder> page = new Page<>(tbOrder.getPageNum(), tbOrder.getPageSize());
        QueryWrapper<TbOrder> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(tbOrder.getOrderNumber())) {
            queryWrapper.lambda().eq(TbOrder::getOrderNumber, tbOrder.getOrderNumber());
        }
        if (!ObjectUtils.isEmpty(tbOrder.getClientName())) {
            queryWrapper.lambda().like(TbOrder::getClientName, tbOrder.getClientName());
        }
        Page<TbOrder> tbOrderPage = orderMapper.selectPage(page, queryWrapper);
        return R.success(tbOrderPage);
    }

    @Override
    public R orderTypeList() {
        List<TbOrderType> orderTypeList = orderTypeMapper.selectList(null);
        return R.success(orderTypeList);
    }

    @Override
    public R transportTypeList() {
        List<TbTransportType> transportTypeList = transportTypeMapper.selectList(null);
        return R.success(transportTypeList);
    }

    @Override
    public R upload(MultipartFile file) throws IOException {
        String url = fastFileUtil.uploadFile(file);
        return R.success(url);
    }

    @Override
    @Transactional(rollbackForClassName = "Exception")
    public R saveOrder(TbOrder tbOrder) {
        int insert = orderMapper.insert(tbOrder);
        if (insert > 0) {
            MessageVo messageVo = new MessageVo();
            String msgId = "MSG=" + UUID.randomUUID().toString();
            String msgBody = "订单创建成功，司机请准备车辆";
            messageVo.setMsgId(msgId);
            messageVo.setMsgBody(msgBody);
            String s = JSON.toJSONString(messageVo);
            stringRedisTemplate.opsForValue().set(msgId, msgBody, 5, TimeUnit.MINUTES);
            rabbitTemplate.convertAndSend("directExchange", "order", s);
            return R.success();
        }
        return R.error(500, "添加失败");
    }

    @RabbitListener(queues = "orderQueue")
    public void orderQueue(Message message, Channel channel) throws IOException {
        String s = new String(message.getBody());
        MessageVo messageVo = JSON.parseObject(s, MessageVo.class);
        if (!stringRedisTemplate.hasKey(messageVo.getMsgId())) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        }
        log.debug(messageVo.getMsgBody());
        String key = "carList";
        List<TbCar> carList = carMapper.selectList(null);
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(carList));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    @Override
    public R carList(TbCar tbCar) {
        String key = "carList";
        String s = stringRedisTemplate.opsForValue().get(key);
        List<TbCar> carList = JSON.parseArray(s, TbCar.class);
        List<TbCar> carResult = new ArrayList<>();
        if (!ObjectUtils.isEmpty(tbCar.getCarNumber())) {
            for (TbCar car : carList) {
                if(car.getCarNumber().equals(tbCar.getCarNumber())){
                    carResult.add(car);
                }
            }
            return R.success(carResult);
        }else{
            return R.success(carList);
        }
    }

    @Override
    public R setOrder(Integer orderId, Integer carId) {
        TbOrder tbOrder = new TbOrder();
        tbOrder.setId(orderId);
        tbOrder.setCarId(carId);
        int i = orderMapper.updateById(tbOrder);
        if(i>0){
            return R.success();
        }
        return R.error(500,"派车失败");
    }

    @Override
    public R selectOrder(Integer orderId) {
        TbOrder tbOrder = orderMapper.selectOne(new QueryWrapper<TbOrder>().lambda().eq(TbOrder::getId, orderId));
        return R.success(tbOrder);
    }

    @Override
    public void toPay() {
        PayData payData = new PayData();
        iPayService.zhiFuBaoPay(payData);
    }
}
