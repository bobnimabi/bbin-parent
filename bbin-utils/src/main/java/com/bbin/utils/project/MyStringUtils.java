package com.bbin.utils.project;

/**
 * Created by mrt on 2019/7/1 0001 下午 3:03
 * 功能：字符串扩展功能的一些方法
 */
public class MyStringUtils {

    /**
     * 首字母大写
     * @param str
     * @return
     * @throws Exception
     */
    public static String uppercaseFirst(String str) throws Exception {
        return (char)(str.charAt(0) - 32) + str.substring(1);
    }
}
