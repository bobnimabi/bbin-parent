package com.bbin.core.sign;

import com.alibaba.fastjson.JSON;
import com.bbin.common.constant.CommonConsts;
import com.bbin.core.utils.SignByRSA;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public abstract class AbstractFeignSignInterceptor implements RequestInterceptor {
    /**
     * 获取签名私钥串
     * @return
     */
    protected abstract String getPrivateKey();

    @Override
    public void apply(RequestTemplate template) {
        String json = "";
        try {
            json = StringUtils.equalsIgnoreCase(template.method(), CommonConsts.GET) ? getJsonForGet(template) : getJsonForPost(template);
            log.info("请求方式:{},FeignSignInterceptor=>签名参数：{}", template.method(), json);
            template.header(CommonConsts.SIGN, sign(json));
        } catch (Exception e) {
            log.error("FeignSignInterceptor设置签名异常", e);
        }
    }

    // 获取Get方式待签名串
    private String getJsonForGet(RequestTemplate template) {
        Map<String, Collection<String>> queries = template.queries();
        log.info("FeignSignInterceptor=>get参数：{}", JSON.toJSONString(queries));
        TreeMap<String,Object> treeMap = new TreeMap();
        queries.forEach((k,v)->{
            treeMap.put(k, v.iterator().next());
        });
        removeNullFromMap(treeMap);
        return JSON.toJSONString(treeMap);
    }

    // 获取Post方式签名串
    private String getJsonForPost(RequestTemplate template) {
        String jsonStr = template.requestBody().asString();
        log.info("FeignSignInterceptor=>post参数：{}", jsonStr);
        TreeMap<String,Object> treeMap = JSON.parseObject(jsonStr, TreeMap.class);
        removeNullFromMap(treeMap);
        return JSON.toJSONString(treeMap);
    }

    // 签名
    private String sign(String json) throws Exception{
        return SignByRSA.sign(json, SignByRSA.getPrivateKey(this.getPrivateKey()));
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
