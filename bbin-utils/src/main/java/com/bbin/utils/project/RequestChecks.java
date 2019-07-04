package com.bbin.utils.project;

import com.bbin.common.constant.CommonConstant;
import com.bbin.common.exception.ExceptionCast;
import com.bbin.common.response.CommonCode;
import com.bbin.common.response.ResponseResult;
import com.bbin.common.response.ResultCode;
import com.bbin.utils.CookieUtil;
import com.bbin.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrt on 2019/6/25 0025 下午 2:38
 * 功能：对jwt令牌等信息进行校验
 */
@Slf4j
public class RequestChecks {

    //拒绝访问
    public static void access_denied(HttpServletResponse response, String loginUrl, ResultCode resultCode) throws Exception{
        //构建响应的信息
        ResponseResult result = new ResponseResult(resultCode);
        //重定向到登录页面
        response.setHeader("Location", loginUrl);
        ResponseUtils.writeJson(response,result,302);
    }

    //从头取出jwt令牌
    public static String getJwtFromHeader(HttpServletRequest request) throws Exception{
        //取出头信息
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)){
            return null;
        }
        if(!authorization.startsWith("Bearer ")){
            return null;
        }
        //取到jwt令牌
        String jwt = authorization.substring(7);
        return jwt;
    }

    //从cookie取出uid
    public static String getTokenFromCookie(HttpServletRequest request) throws Exception{
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
        String access_token = cookieMap.get("uid");
        if(StringUtils.isEmpty(access_token)){
            return null;
        }
        return access_token;
    }

    //查询令牌的有效期
    public static long getExpire(String access_token, RedisTemplate redisTemplate) throws Exception{
        //key
        String key = CommonConstant.Login.LOGIN_PRE + "user_token:"+access_token;
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire;
    }

    //校验ip
    public static boolean checkIp(HttpServletRequest request, HttpServletResponse response, List<String> ips,String loginUrl) throws Exception{
        boolean permit = false;
        String ipAddress = IpUtil.getIpAddress(request);
        if (CollectionUtils.isEmpty(ips)) {
            ExceptionCast.castFail("未放行任何ip");
        }
        for (String ip : ips) {
            if (ip.equals(ipAddress)) {
                permit = true;
            }
        }
        if (!permit) {
            log.info("ip拒绝，ip:"+ipAddress);
            RequestChecks.access_denied(response,loginUrl, CommonCode.IP_REFUSE);
        }
        return permit;
    }

    //是否放行此url
    public static boolean permitUrl(HttpServletRequest request,String permitUrl) throws Exception{
        String requestURI = request.getRequestURI();
        String[] urls = permitUrl.split(",");

        for (String url: urls) {
            if (requestURI.contains(url)) {
                return true;
            }
        }
        return false;
    }

    //校验短令牌
    public static String checkShortToken(HttpServletRequest request,HttpServletResponse response, RedisTemplate<String,String> redis,String loginUrl) throws Exception{
        //1.取cookie中的短令牌
        String jwtShortToken = RequestChecks.getTokenFromCookie(request);
        if(StringUtils.isEmpty(jwtShortToken) || checkTokenExpire(jwtShortToken,response,redis,loginUrl)){
            //拒绝访问
            RequestChecks.access_denied(response,loginUrl, CommonCode.UNAUTHENTICATED);
            return null;
        }
        return jwtShortToken;
    }

    //校验长令牌
    public static boolean checkLongToken(HttpServletRequest request,HttpServletResponse response,String loginUrl) throws Exception{
        //2.从header中取jwt长令牌
        String jwtLongToken = RequestChecks.getJwtFromHeader(request);
        if(StringUtils.isEmpty(jwtLongToken)){
            //拒绝访问
            RequestChecks.access_denied(response,loginUrl, CommonCode.UNAUTHENTICATED);
            return false;
        }
        return true;
    }

    //校验短令牌是否过期
    public static boolean checkTokenExpire(String jwtShortToken, HttpServletResponse response, RedisTemplate<String,String> redis,String loginUrl) throws Exception{
        long expire = RequestChecks.getExpire(jwtShortToken,redis);
        if (expire <= 0) {
            //拒绝访问
            RequestChecks.access_denied(response, loginUrl, CommonCode.UNAUTHENTICATED);
            return true;
        }
        return false;
    }
}
