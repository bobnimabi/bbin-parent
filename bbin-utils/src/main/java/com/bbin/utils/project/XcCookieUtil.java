package com.bbin.utils.project;



import com.bbin.common.exception.ExceptionCast;
import com.bbin.common.response.CommonCode;
import com.bbin.utils.CookieUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by mrt on 2019/4/7 0007 下午 3:38
 * 功能：cookie工具类
 */
public class XcCookieUtil {

    //将令牌存储到cookie
    public static void saveCookie(String token,String cookieDomain,int cookieMaxAge) throws Exception{
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
    }

    //从cookie删除token
    public static void clearCookie(String token,String cookieDomain) throws Exception{
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,0,false);
    }

    //取出cookie中的身份令牌
    public static String getTokenFormCookie( HttpServletRequest request) throws Exception{
        Map<String, String> map = CookieUtil.readCookie(request, "uid");

        if(map!=null && map.get("uid")!=null){
            String uid = map.get("uid");
            return uid;
        }
        ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        return null;
    }
}
