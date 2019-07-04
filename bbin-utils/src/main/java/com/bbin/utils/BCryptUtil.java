package com.bbin.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by mrt on 2018/5/22.
 * 功能：验证密码的的正确性，springSecurity里面的BCryptPasswordEncoder加密方式和校验使用
 */
public class BCryptUtil {

    /**
     * 对密码进行加密
     * @param password
     * @return
     */
    public static String encode(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 校验密码是否正确使用
     * @param password 未加密的密码
     * @param hashPass 加密后的密码
     * @return
     */
    public static boolean matches(String password,String hashPass){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashPass);
    }
}
