package com.bbin.utils.project;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
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
        //请求的参数
        try {
            Object[] args = joinPoint.getArgs();
            log.info("class:{} method:{} reqParams:", className, methodName, JSON.toJSONString(args));
        } catch (Exception e) {
            log.error("切面：请求参数打印异常", e);
        }
    }

    /**
     * 响应参数打印
     */
    public static void saveRespLog(Object result, String className, String methodName) {
        //请求的参数
        try {
            log.info("class:{} method:{} respParams:", className, methodName, JSON.toJSONString(result));
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
     * 是否对某方法不过滤
     * @param methodName 切面方法
     * @return
     */
    public static boolean isPass(String methodName,String aspectPermit) {
        if (StringUtils.isEmpty(methodName) || StringUtils.isEmpty(aspectPermit)) {
            return false;
        }
        try {
            boolean flag = false;
            String[] split = aspectPermit.split(",");
            for (String permit:split) {
                if (methodName.equalsIgnoreCase(permit)) {
                    flag = true;
                }
            }
            return flag;
        } catch (Exception e) {
            log.error("校验是否过滤异常", e);
        }
        return false;
    }
}
