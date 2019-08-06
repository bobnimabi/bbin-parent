package com.bbin.utils.project;

import com.alibaba.fastjson.JSON;
import com.bbin.common.constant.CommonConstant;
import com.bbin.common.exception.ExceptionCast;
import com.bbin.common.pojo.AuthToken;
import com.bbin.common.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrt on 2019/4/7 0007 下午 3:45
 */
public class XcTokenUtil {

    /**
     * 存储到令牌到redis
     * @param uid 用户身份令牌
     * @param content      内容就是AuthToken对象的内容
     * @param ttl          过期时间
     * @return
     */
    public static boolean saveToken(String uid, String content, long ttl, StringRedisTemplate stringRedisTemplate) {
        String key = CommonConstant.Login.LOGIN_PRE +"user_token:" + uid;
        stringRedisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire > 0;
    }

    //删除token
    public static boolean delToken(String uid, StringRedisTemplate stringRedisTemplate) {
        String key = CommonConstant.Login.LOGIN_PRE +"user_token:" + uid;
        return stringRedisTemplate.delete(key);
    }

    //从redis查询令牌
    public static AuthToken getUserToken(String uid, StringRedisTemplate stringRedisTemplate) {
        String key = CommonConstant.Login.LOGIN_PRE +"user_token:" + uid;
        //从redis中取到令牌信息
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value))  ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        //转成对象
        try {
            AuthToken authToken = JSON.parseObject(value, AuthToken.class);
            return authToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //拿出用户信息
    public static AuthToken getUserInfo(HttpServletRequest request, String environment, StringRedisTemplate redis)  {
        // 正式环境
        if (CommonConstant.PROD.equals(environment)) {
            String uid = XcCookieUtil.getTokenFormCookie(request);
            AuthToken userToken = XcTokenUtil.getUserToken(uid, redis);
            return userToken;
        } else {
            // 测试环境,保证测试环境不会因为未登陆报错
            AuthToken authToken = new AuthToken();
            authToken.setJwt_token("dev->jwt长令牌");
            authToken.setAccess_token("dev->jwt短令牌");
            authToken.setRefresh_token("dev->jwt刷新令牌");
            authToken.setUserId(10000L);
            authToken.setName("dev->测试账号");
            authToken.setUsername("dev->test");
            authToken.setUtype(1);
            return authToken;
        }
    }
}
