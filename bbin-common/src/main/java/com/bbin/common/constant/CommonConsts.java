package com.bbin.common.constant;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

/**
 * Created by mrt on 2019/4/6 0006 下午 6:39
 */
public class CommonConsts {
    /**********************************以下常量所有活动通用**************************************/

    // 逻辑常量：是 与 否
    public static final int YES = 1;
    public static final int NO = 0;

    // 有效状态：有效 与 删除
    public static final int OPEN = 1;
    public static final int DELETE = -1;

    // 字符编码
    public static final String ENCODING_UTF_8 = "utf-8";

    // 请求方式
    public static final String POST = "POST";
    public static final String GET = "GET";

    // 签名头key
    public static final String SIGN = "sign";

    // 环境变量:生产、开发、测试
    public static final String PROD = "prod";
    public static final String DEV = "dev";
    public static final String TEST = "test";

    // 状态码
    public static final int R = -1;


    /**
     * 登录
     */
    public static class Login {
        //项目的redis前缀
        public static final String LOGIN_PRE = "Login:";
        //项目的redis前缀
        public static final String LOGIN_TOKEN_PRE = LOGIN_PRE + "user_token:";
        //用户的登录标志（redis的key）
        public static final String LOGIN_FLAG_PRE = LOGIN_PRE + "Login_flag:";
    }
}
