package org.example.service;

import org.example.pojo.TbCar;
import org.example.pojo.TbOrder;
import org.example.util.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @program: day0708_week3
 * @author: dsh
 * @description:
 * @create: 2024-07-08 09:35
 */
public interface OrderService {
    R orderList(TbOrder tbOrder);

    R orderTypeList();

    R transportTypeList();

    R upload(MultipartFile file) throws IOException;

    R saveOrder(TbOrder tbOrder);

    R carList(TbCar tbCar);

    R setOrder(Integer orderId, Integer carId);

    R selectOrder(Integer orderId);

    void toPay();

}
