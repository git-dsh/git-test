package org.example.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 10:03
 */
@Data
public class MessageVo implements Serializable {
    private String msgId;
    private String msgBody;
}
