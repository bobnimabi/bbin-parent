package com.bbin.common.utils;

/**
 * Created by mrt on 2019/7/4 0004 下午 4:45
 * 功能：url相关的功能
 */
public class MyUrlUtils {
    /**
     * 是否能上匹配url
     * @param permitUrls 允许的url数组
     * @param requestURI 本次访问的url
     * @return
     * @throws Exception
     */
    public static boolean matchUrl(String[] permitUrls, String requestURI) throws Exception {
        boolean flag = false;
        String lastUrl = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        for (String url : permitUrls) {
            if (lastUrl.contains(url))
                flag = true;
        }
        return flag;
    }
}
