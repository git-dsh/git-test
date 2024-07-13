package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:25
 */
@Data
@TableName(value = "tb_car")
public class TbCar implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String driver;
    private String commonCarrier;
    private String carNumber;
    private String carType;
}
