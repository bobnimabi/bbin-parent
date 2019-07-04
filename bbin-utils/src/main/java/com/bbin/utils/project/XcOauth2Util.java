package com.bbin.utils.project;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by mrt on 2018/5/25.
 * 功能：解析jwt令牌里面携带的参数
 */
@Slf4j
public class XcOauth2Util {

    /**
     * 获取jwt令牌里面的信息
     */
    public static String getJwtInfo(HttpServletRequest request) throws Exception{
        //取出头信息
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || authorization.indexOf("Bearer") < 0) {
            log.error("Authorization头信息里面没有：jwt令牌");
            return null;
        }
        //从Bearer 后边开始取出token
        String token = authorization.substring(7);
        //解析jwt
        Jwt decode = JwtHelper.decode(token);
        //得到 jwt中的用户信息
        String claims = decode.getClaims();
        return claims;
    }
}
