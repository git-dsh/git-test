package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:26
 */
@Data
@TableName(value = "tb_order")
public class TbOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String needNumber;
    private String orderNumber;
    private Integer orderType;
    private Integer transportType;
    private String clientName;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date buyDate;
    private String startCity;
    private String endCity;
    private String freightType;
    private Integer sumCount;
    private Integer sumWeight;
    private Integer sumVolume;
    private String freightPhoto;
    private Integer carId;
    @TableField(exist = false)
    private Integer pageNum;
    @TableField(exist = false)
    private Integer pageSize;
}
