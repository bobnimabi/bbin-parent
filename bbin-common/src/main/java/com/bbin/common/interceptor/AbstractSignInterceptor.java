package com.bbin.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.bbin.common.request.wrapper.MyRequestWrapper;
import com.bbin.common.utils.MyUrlUtils;
import com.bbin.common.utils.SignByRSA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.TreeMap;

/**
 * Created by mrt on 2019/5/18 0018 下午 8:55
 * 功能：为调用的参数进行签名
 * 使用：子项目继承本类，实现获取公钥和获取放行url的方法
 */
@Slf4j
public abstract class AbstractSignInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        MyRequestWrapper requestWrapper = new MyRequestWrapper(request);
        if (isCheck(requestWrapper)) {
            String body = requestWrapper.getBody();
            log.info("请求参数：" + body);
            //获取签名公钥并验签
            return verifySign(body);
        }
        //将我们的包装好的MyRequestWrapper放进去
        return super.preHandle(requestWrapper, response, handler);
    }

    /**
     * 检查是否需要验签
     */
    private boolean isCheck(HttpServletRequest request) throws Exception{
        return MyUrlUtils.matchUrl(getSignVerifyUrl().split(","), request.getRequestURI());
    }

    /**
     * 获取需要签名的url
     * @return
     * @throws Exception
     */
    protected abstract String getSignVerifyUrl() throws Exception;

    /**
     * 获取签名json
     */
    private String getSignJson(TreeMap<String,Object> treeMap) throws Exception{
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().add("sign");
        return JSON.toJSONString(treeMap, filter);
    }

    /**
     * 验签
     */
    private boolean verifySign(String body) throws Exception{
        //key按字典排序
        TreeMap<String,Object> treeMap = (TreeMap<String, Object>) JSON.parseObject(body, TreeMap.class);
        //获取签名串
        String signJson = getSignJson(treeMap);
        log.info("签名参数：" + signJson);
        boolean isPass = SignByRSA.verifySignature(
                getPublicKey(),//签名公钥
                Base64.getDecoder().decode(String.valueOf(treeMap.get("sign"))),
                signJson
        );
        log.info("验签结果：" + isPass);
        return isPass;
    }

    /**
     * 获取公钥
     */
    protected abstract String getPublicKey() throws Exception;
}
