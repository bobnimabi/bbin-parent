package com.bbin.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 18:33.
 * @Modified By:
 * 接口响应返回
 */
@Data
@ToString
@NoArgsConstructor
@Slf4j
public class InnerResult<T> implements Response, Serializable {
    private static final long serialVersionUID = 1L;
    //操作是否成功
    private boolean success = SUCCESS;

    //操作代码
    private int code = SUCCESS_CODE;

    //提示信息
    private String message;

    //对象
    private T obj;

    //请求非成功或失败，无返回信息
    public InnerResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    //请求成功，有返回对应
    public InnerResult(ResultCode resultCode, T obj){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.obj = obj;
    }

    //请求成功，无返回信息
    public static InnerResult SUCCESS(){
        return new InnerResult(CommonCode.SUCCESS);
    }
    //请求成功，返回对象
    public InnerResult SUCCESS(T obj){
        return new InnerResult(CommonCode.SUCCESS,obj);
    }

    //请求失败，无返回信息
    public static InnerResult FAIL(){
        return new InnerResult(CommonCode.FAIL);
    }

    //请求失败，返回错误原因
    public static InnerResult FAIL(String message){
        ResultInfo resultInfo = new ResultInfo(false,11111,message);
        log.error("失败原因：" + message);
        return new InnerResult(resultInfo);
    }

    //请求失败，非法参数
    public static InnerResult INVALID_PARAM(String errReason){
        ResultInfo resultInfo = new ResultInfo(false,10003,errReason);
        log.error("失败原因：" + errReason);
        return new InnerResult(resultInfo);
    }
}
