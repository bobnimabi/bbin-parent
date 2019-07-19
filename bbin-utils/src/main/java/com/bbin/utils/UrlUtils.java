package com.bbin.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrt on 2019/7/15 0015 下午 2:17
 */
public class UrlUtils {
    // 注意：java.net的url编码方法在对一些特殊符号编码时有个bug，如：+号decord时，+号会变成空格，需要把+号替换成%2B，str=str.replaceAll("\\+", "%2B");

    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, String> getUrlParams(String url) {
        Map<String, String> map = new HashMap<>();
        url = url.replace("?", ";");
        if (!url.contains(";")) {
            return map;
        }
        if (url.split(";").length > 0) {
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr) {
                String key = String.valueOf(s.split("=")[0]);
                String value = String.valueOf(s.split("=")[1]);
                map.put(key, value);
            }
        }
        return map;
    }
}
