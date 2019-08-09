package com.bbin.common.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * Created by mrt on 2019/8/8 0008 下午 1:43
 */
public class RequestUtils {
    // spring的url匹配工具
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    /**
     * 获取请求路径后缀
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.substring(requestURI.lastIndexOf("/"));
    }

    /**
     * 将多个path和请求路径匹配
     * @param urlSet 多个path的set集合
     * @param url 请求路径
     * @return
     */
    public static boolean urlMatch(Set<String> urlSet, String url) {
        Assert.notEmpty(urlSet,"urlSet is empty");
        Assert.hasText(url,"url is empty");
        for (String path : urlSet) {
            boolean match = pathMatcher.match(path, url);
            if (match) {
                return true;
            }
        }
        return false;
    }

    public static boolean urlMatch(String path, String url) {
        Assert.hasText(path,"path is empty");
        Assert.hasText(url,"url is empty");
        return pathMatcher.match(path, url);
    }

    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null)
            return null;
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip))
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            }
            catch (UnknownHostException unknownhostexception) {
            }
        //第一个ip是真ip
        String[] ips = ip.split(",");
        return ips[0];
    }


}
