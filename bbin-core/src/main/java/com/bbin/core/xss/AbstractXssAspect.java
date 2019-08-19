package com.bbin.core.xss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.bbin.core.utils.AspectUtils;
import com.bbin.core.utils.SignByRSA;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mrt on 2019/8/17 0023 下午 4:15
 */
@Slf4j
public abstract class AbstractXssAspect {
    protected Object filterXss(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            boolean isValid = XssFilterUtil.isValid(JSON.toJSONString(args[i]));
            if (!isValid) {
                throw new IllegalArgumentException("非法字符");
            }
        }
        return point.proceed();
    }

    // POST方式过滤
    private Object filterXssForPost(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            Map<String,Object> paramsMmap = JSON.parseObject(JSON.toJSONString(args[i]), HashMap.class);
            paramsMmap.forEach((k,v)->{
                String newKey = XssFilterUtil.clean(k);
                Assert.isTrue(StringUtils.equals(k,newKey),"参数键XSS过滤前后不一致");
                if (v instanceof String) {
                    paramsMmap.put(newKey, XssFilterUtil.clean((String) v));
                }
            });
            args[i] = JSON.toJSONString(paramsMmap);
        }
        return point.proceed(args);
    }

    // GET方式过滤
    private Object filterXssForGet(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            args[i] = XssFilterUtil.clean(String.valueOf(args[i]));
        }
        return point.proceed(args);
    }
}
