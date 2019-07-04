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
public class ResponseResult implements Response, Serializable {
    private static final long serialVersionUID = 1L;
    //操作是否成功
    private boolean success = SUCCESS;

    //操作代码
    private int code = SUCCESS_CODE;

    //提示信息
    private String message;

    //对象
    private Object obj;

    //请求非成功或失败，无返回信息
    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    //请求成功，有返回对应
    public ResponseResult(ResultCode resultCode, Object obj){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.obj = obj;
    }

    //请求成功，无返回信息
    public static ResponseResult SUCCESS(){
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //请求成功，返回对象
    public static ResponseResult SUCCESS(Object obj){
        return new ResponseResult(CommonCode.SUCCESS,obj);
    }

    //请求失败，无返回信息
    public static ResponseResult FAIL(){
        return new ResponseResult(CommonCode.FAIL);
    }

    //请求失败，返回错误原因
    public static ResponseResult FAIL(String message){
        ResultInfo resultInfo = new ResultInfo(false,11111,message);
        log.error("失败原因：" + message);
        return new ResponseResult(resultInfo);
    }

    //请求失败，非法参数
    public static ResponseResult INVALID_PARAM(String errReason){
        ResultInfo resultInfo = new ResultInfo(false,10003,errReason);
        log.error("失败原因：" + errReason);
        return new ResponseResult(resultInfo);
    }
}
