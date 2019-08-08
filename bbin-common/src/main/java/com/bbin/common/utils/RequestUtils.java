package com.bbin.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by mrt on 2019/8/8 0008 下午 1:43
 */
public class RequestUtils {

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
