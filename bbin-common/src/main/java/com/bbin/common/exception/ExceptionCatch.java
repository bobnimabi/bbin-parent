package com.bbin.common.exception;

import com.bbin.common.response.CommonCode;
import com.bbin.common.response.ResponseResult;
import com.bbin.common.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常捕获类
 * @author Administrator
 * @version 1.0
 * @create 2018-09-14 17:32
 **/
@ControllerAdvice//控制器增强
@Slf4j
public class ExceptionCatch {
    /**
     * 自定义异常：捕获CustomException此类异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        //记录日志
        ResultCode resultCode = customException.getResultCode();
        log.error("错误信息:{}",customException);
        return new ResponseResult(resultCode);
    }

    /**
     * 自定义异常：捕获InvalidParamException此类异常
     * 解释：非法参数异常
     */
    @ExceptionHandler(InvalidParamException.class)
    @ResponseBody
    public ResponseResult customException(InvalidParamException e){
        ResultCode resultCode = e.getResultCode();
        log.error("非法参数信息:{}", resultCode.message());
        return new ResponseResult(resultCode);
    }

    /**
     * springsecurity : 方法权限异常
     */
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseBody
//    public ResponseResult accessDeniedException(AccessDeniedException exception){
//        log.error("错误信息:{}",exception);
//        return new ResponseResult(UNAUTHORISE);
//    }

    /**
     * hibernate Validate：处理所有接口数据参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        //记录日志
        CommonCode invalidParamCode = CommonCode.INVALID_PARAM;
        log.error("错误信息:{}",e);
        return new ResponseResult(invalidParamCode);
    }

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        //记录日志
        log.error("系统错误:{}",exception.getMessage());
        return ResponseResult.FAIL("系统错误:"+exception.getMessage());
    }
}
