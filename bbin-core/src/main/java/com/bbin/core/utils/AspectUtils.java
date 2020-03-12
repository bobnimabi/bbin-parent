package com.bbin.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;

/**
 * Created by mrt on 2019/7/10 0010 上午 10:52
 * 功能：切面日志打印
 */
@Slf4j
public class AspectUtils {
    /**
     * 请求参数打印
     */
    public static void saveReqLog(ProceedingJoinPoint joinPoint, String className, String methodName) {
        // 请求的参数
        try {
            Object[] args = joinPoint.getArgs();
            log.info("class:{} method:{} reqParams:{}", className, methodName, getParams(args));
        } catch (Exception e) {
            log.error("切面：请求参数打印异常", e);
        }
    }

    /**
     * 响应参数打印
     */
    public static void saveRespLog(Object proceed, String className, String methodName) {
        //请求的参数
        try {
            log.info("class:{} method:{} respParams:{}", className, methodName, JSON.toJSONString(proceed));
        } catch (Exception e) {
            log.error("切面：响应参数打印异常", e);
        }
    }

    // 获取方法名
    public static String getMethod(ProceedingJoinPoint joinPoint) {
        try {
            Signature sig = joinPoint.getSignature();
            MethodSignature msig = null;
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
            msig = (MethodSignature) sig;
            Object target = joinPoint.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            return currentMethod.getName();
        } catch (Exception e) {
            log.error("切面：异常，获取不到方法名称", e);
        }
        return null;
    }

    // 获取类名
    public static String getClassName(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.getTarget().getClass().getName();
        } catch (Exception e) {
            log.error("切面：异常，获取不到类名称", e);
        }
        return null;
    }

    /**
     * 是否通行
     * @param methodName 方法
     * @param permitMethod 通行的方法，中间以逗号分隔
     * @return
     */
    public static boolean isPass(String methodName,String permitMethod) {
        if (StringUtils.isEmpty(methodName) || StringUtils.isEmpty(permitMethod)) {
            return false;
        }
        return StringUtils.contains(permitMethod, methodName);
    }

    /**
     * 是否拦截
     * @param methodName 方法
     * @param interceptMethod 拦截的方法，中间以逗号分隔
     * @return
     */
    public static boolean isIntercept(String methodName,String interceptMethod) {
        if (StringUtils.isEmpty(methodName)) {
            log.error("方法名为空=>拒绝");
            return true;
        }
        if (StringUtils.isEmpty(interceptMethod)) {
            log.error("无拦截方法=>放行");
            return false;
        }
        return StringUtils.contains(interceptMethod, methodName);
    }

    private static String getParams(Object[] args) {
        Object[] arguments  = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
            //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        String paramter = "";
        if (arguments != null) {
            try {
                paramter = JSONObject.toJSONString(arguments);
            } catch (Exception e) {
                paramter = arguments.toString();
            }
        }
        return paramter;
    }
}
