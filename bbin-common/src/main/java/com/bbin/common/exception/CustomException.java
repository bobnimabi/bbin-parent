package com.bbin.common.exception;


import com.bbin.common.response.ResultCode;

/**
 * 自定义异常类型
 * @author Administrator
 * @version 1.0
 * @create 2018-09-14 17:28
 **/
public class CustomException extends RuntimeException {

    //错误代码
    ResultCode resultCode;
    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    public CustomException(ResultCode resultCode,Throwable throwable){
        super(resultCode.message(),throwable);
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return resultCode;
    }
}
