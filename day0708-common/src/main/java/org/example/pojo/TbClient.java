package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:23
 */
@Data
@TableName(value = "tb_client")
public class TbClient implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String clientName;
    private String password;
    @TableField(exist = false)
    private String token;
}
