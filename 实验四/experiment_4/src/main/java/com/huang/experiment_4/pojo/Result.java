package com.huang.experiment_4.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author F1shBone
 * @version 1.0
 * @date 2024/7/17 13:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    //增删改响应成功
    public static Result success(){
        return new Result(1,"success",null);
    }
    //查询响应成功
    public static Result success(Object data){
        return new Result(1,"success",data);
    }
    //响应失败
    public static Result error(){
        return new Result(0,"error",null);
    }
    //响应失败
    public static Result error(String msg){
        return new Result(0,msg,null);
    }
}
