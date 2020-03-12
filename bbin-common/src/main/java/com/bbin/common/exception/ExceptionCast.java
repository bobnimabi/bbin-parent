package com.bbin.common.exception;


import com.bbin.common.response.CommonCode;
import com.bbin.common.response.ResultCode;
import com.bbin.common.response.ResultInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-14 17:31
 **/
@Slf4j
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }

    public static void castFail(){
        throw new CustomException(CommonCode.FAIL);
    }

    public static void castFailMes(String mes){
        log.info("错误信息：{}", mes);
        ResultInfo resultInfo = new ResultInfo(false,11111,mes);
        throw new CustomException(resultInfo);
    }

    public static void castInvalid(String mes){
        log.info("错误信息：{}", mes);
        throw new InvalidParamException(new ResultInfo(false,10003,mes));
    }
}
