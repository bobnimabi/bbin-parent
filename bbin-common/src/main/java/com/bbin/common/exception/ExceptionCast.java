package com.bbin.common.exception;


import com.bbin.common.response.ResultCode;
import com.bbin.common.response.ResultInfo;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-14 17:31
 **/
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }

    public static void castFail(String mes){
        ResultInfo resultInfo = new ResultInfo(false,11111,mes);
        throw new CustomException(resultInfo);
    }

    public static void castInvalid(String mes){
        throw new InvalidParamException(new ResultInfo(false,10003,mes));
    }
}
