package com.bbin.commons.sign;

import com.alibaba.fastjson.JSON;
import com.bbin.common.response.ResponseResult;
import com.bbin.common.utils.FastJsonUtils;
import com.bbin.common.utils.SignByRSA;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.TreeMap;

/**
 * Created by mrt on 2019/7/23 0023 下午 10:15
 */
@Slf4j
public abstract class AbstractVerifySign {
    private static final String SIFN_KEY = "sign";

    /**
     * 获取公钥
     * @return
     * @
     */
    protected abstract String getPublicKeyStr() ;

    /**
     * 获取需签名url
     * @return
     * @
     */
    protected abstract String getSignUrl() ;


    protected ResponseResult checkSign(ProceedingJoinPoint point) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        if (this.isSign(point) && !this.verifySign(point)) {
            return ResponseResult.FAIL("验签失败");
        }
        return ResponseResult.SUCCESS();
    }


    // 是否需要签名
    private boolean isSign(ProceedingJoinPoint point) {
        String methodName = getMethod(point);
        boolean flag = false;
        String signUrl = getSignUrl();
        if (StringUtils.isEmpty(signUrl)) return flag;
        String[] split = signUrl.split(",");
        for (String permit:split) {
            if (permit.contains(methodName)) {
                flag = true;
            }
        }
        return flag;
    }

    // 获取签名公钥并验签
    private boolean verifySign(ProceedingJoinPoint point) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String body = getJson(point);
        // key按字典排序
        TreeMap<String,Object> treeMap = (TreeMap<String, Object>) JSON.parseObject(body, TreeMap.class);
        // 获取签名串
        String signJson = getSignJson(treeMap);
        log.info("签名参数：" + signJson);
        boolean isPass = SignByRSA.verify(
                signJson,
                SignByRSA.getPublicKey(getPublicKeyStr()),//签名公钥
                String.valueOf(treeMap.get(SIFN_KEY))
        );
        log.info("验签结果：" + isPass);
        return isPass;
    }

    // 获取签名json
    private String getSignJson(TreeMap<String,Object> treeMap) {
        return FastJsonUtils.jsonToStringExclude(treeMap, SIFN_KEY);
    }

    // 获取签名body的json串
    private String getJson(ProceedingJoinPoint point)  {
        Object[] args = point.getArgs();
        String jsonStr = JSON.toJSONString(args);
        // 去掉最前[ 和 最后]
        return jsonStr.substring(1, jsonStr.length() - 1);
    }

    // 获取方法名称
    private String getMethod(ProceedingJoinPoint ProceedingJoinPoint)  {
            Signature sig = ProceedingJoinPoint.getSignature();
            MethodSignature msig = null;
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
            msig = (MethodSignature) sig;
            Object target = ProceedingJoinPoint.getTarget();
        Method currentMethod = null;
        try {
            currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException", e);
        }
        return currentMethod.getName();
    }
}
