package com.bbin.core.sign;

import com.alibaba.fastjson.JSON;
import com.bbin.common.constant.CommonConsts;
import com.bbin.common.response.ResponseResult;
import com.bbin.common.utils.FastJsonUtils;
import com.bbin.core.utils.AspectUtils;
import com.bbin.core.utils.SignByRSA;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by mrt on 2019/7/23 0023 下午 10:15
 */
@Slf4j
public abstract class AbstractVerifySign {

    protected ResponseResult checkSign(ProceedingJoinPoint point) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        boolean isIntercept = AspectUtils.isIntercept(AspectUtils.getMethod(point), getSignMethod());
        return isIntercept && !this.verifySign(point) ? ResponseResult.FAIL("验签失败") : ResponseResult.SUCCESS();
    }

    /**
     * 获取公钥
     * @return
     */
    protected abstract String getPublicKey() ;

    /**
     * 获取需签名url，中间以逗号分隔
     * @return
     */
    protected abstract String getSignMethod() ;

    // 获取签名公钥并验签
    private boolean verifySign(ProceedingJoinPoint point) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String body = getBody(point);
        log.info("请求参数：" + JSON.toJSONString(body));
        // 从头获取sign
        String sign = getSignFromHead();
        Assert.hasText(sign,"未获取到签名");
        // key按字典排序,去除null
        TreeMap<String, Object> treeMap = parseJsonToTreeMap(body);
        // 获取签名串,去除sign字段（如果包含）
        String signJson = getSignJson(treeMap);
        log.info("签名参数：" + signJson);
        boolean isPass = SignByRSA.verify(signJson, SignByRSA.getPublicKey(getPublicKey()), sign);
        log.info("验签结果：" + isPass);
        return isPass;
    }

    // 获取签名json
    private String getSignJson(TreeMap<String,Object> treeMap) {
        return FastJsonUtils.jsonToStringExclude(treeMap, CommonConsts.SIGN);
    }

    // 获取签名body的json串
    private String getBody(ProceedingJoinPoint point)  {
        Object[] args = point.getArgs();
        return JSON.toJSONString(args[0]);
    }

    // 从头中获取签名
    private String getSignFromHead() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sign = request.getHeader(CommonConsts.SIGN);
        return sign;
    }

    // 将请求json转treemap
    private TreeMap<String,Object> parseJsonToTreeMap(String body) {
        TreeMap<String,Object> treeMap = (TreeMap<String, Object>) JSON.parseObject(body, TreeMap.class);
        removeNullFromMap(treeMap);
        return treeMap;
    }

    // 剔除map里面的null值
    private void removeNullFromMap(TreeMap<String,Object> treeMap) {
        Iterator<String> keyIterator = treeMap.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (null == treeMap.get(key)) {
                keyIterator.remove();
            }
        }
    }

}
