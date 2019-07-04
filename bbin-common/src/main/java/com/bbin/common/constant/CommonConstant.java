package com.bbin.common.constant;

import java.math.BigDecimal;

/**
 * Created by mrt on 2019/4/6 0006 下午 6:39
 */
public class CommonConstant {
    /**********************************以下常量所有活动通用**************************************/

    //逻辑常量是 与 否
    public static final int YES = 1;
    public static final int NO = 0;

    //开启
    public static final int OPEN = 1;
    //删除
    public static final int DELETE = -1;


    //字符编码
    public static final String ENCODING = "utf-8";

    //富文本编辑器redisKey
    public static final String DETAIL_HEML = "detailPage:";

    //申请页面可编辑html，字典表的字典key
    public static final String HTML_DICT_KEY = "htmlCode";

    public static final String HTML_CACHE_KEY = "htmlCode:";
    /**
     * 登录
     */
    public static class Login {
        //项目的redis前缀
        public static final String LOGIN_PRE = "Login:";
    }

    //环境变量
    public static final String PROD = "prod";
    public static final String DEV = "dev";
    public static final String TEST = "test";






}
