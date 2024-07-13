package org.example.controller;

import org.example.pojo.TbCar;
import org.example.pojo.TbOrder;
import org.example.service.OrderService;
import org.example.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:33
 */
@RestController
@RequestMapping("/server/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/orderList")
    public R orderList(@RequestBody TbOrder tbOrder){
        return orderService.orderList(tbOrder);
    }
    @PostMapping("/orderTypeList")
    public R orderTypeList(){
        return orderService.orderTypeList();
    }
    @PostMapping("/transportTypeList")
    public R transportTypeList(){
        return orderService.transportTypeList();
    }
    @PostMapping("/upload")
    public R upload(@RequestPart("file")MultipartFile file) throws IOException {
        return orderService.upload(file);
    }
    @PostMapping("/saveOrder")
    public R saveOrder(@RequestBody TbOrder tbOrder) {
        return orderService.saveOrder(tbOrder);
    }
    @PostMapping("/carList")
    public R carList(@RequestBody TbCar tbCar) {
        return orderService.carList(tbCar);
    }
    @PostMapping("/setOrder/{orderId}/{carId}")
    public R setOrder(@PathVariable("orderId")Integer orderId,@PathVariable("carId")Integer carId) {
        return orderService.setOrder(orderId,carId);
    }
    @PostMapping("/selectOrder/{orderId}")
    public R selectOrder(@PathVariable("orderId")Integer orderId) {
        return orderService.selectOrder(orderId);
    }
    @GetMapping("/toPay")
    public void toPay() {
        orderService.toPay();
    }
}
