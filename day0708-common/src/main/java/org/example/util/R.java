package org.example.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: day0708_week3
 * @author: 段帅虎
 * @description:
 * @create: 2024-07-08 09:21
 */
@Data
public class R implements Serializable {
    private Integer code;
    private String message;
    private Object Data;
    public static R success(){
        R r = new R();
        r.setCode(200);
        r.setMessage("操作成功");
        return r;
    }
    public static R success(Object data){
        R r = new R();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setData(data);
        return r;
    }
    public static R error(Integer code,String message){
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }
}
