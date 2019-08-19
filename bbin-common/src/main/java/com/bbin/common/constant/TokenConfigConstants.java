package com.bbin.common.constant;

public class TokenConfigConstants {
    public final static String GLOBE_NAME_SPACE = "globeConfig:"; //  全局命名空间

    public final static String TOKEN_NAME_SPACE = GLOBE_NAME_SPACE+"token"; //  token命名空间
    public final static String PHONE_SPACE_SPACE = GLOBE_NAME_SPACE+"phone:"; //  phone命名空间

    public final static String TOKEN_CONFIG = TOKEN_NAME_SPACE + "config:"; //  数据库config表的配置
    public final static String PHONE_CONFIG = PHONE_SPACE_SPACE + "config:"; //  数据库config表的配置

    public final static String IP = "ip:"; //  ip地址统计
    public final static String USER_NAME = "commonUserName:"; //  用户名地址统计

    public final static String SESSION_ID = "sessionId:"; //  sessionId统计
    public final static String USER_CONFIG = "userConfig:"; //   某个用户的限制
    public final static String IP_CONFIG = "ipConfig:"; //  某个ip的限制
    public final static String COMMON_IP_CONFIG = "commonIp"; //  通用ip配置
    public final static String COMMON_SESSION_ID_CONFIG = "commonSessionId:"; //通用sessionId配置

    public final static String USER_TOKEN_VALUE = "tokenValue:"; //  token的值信息
    public final static String USER_PHONE_VALUE = "phoneValue:"; //  token的值信息

    public final static String SEPARATOR = ":"; // 分割符号
    public final static String SECOND_TPS = "second"; // 设置5秒钟的tps
    public final static String MINUTE_TPS = "minute";
    public final static String HOUR_TPS = "hour";
    public final static String DAY_TPS = "day";
    public final static String MONTH_TPS = "month";
    public final static String YEAR_TPS = "year";
    public final static String USER_TOKEN = "token";
    public final static String SESSION_ID_SECOND_KEY = "sessionIdSecond";
    public final static String SESSION_ID_MINUTE_KEY = "sessionIdMinute";
    public final static String SESSION_ID_HOUR_KEY = "sessionIdHour";
    public final static String SESSION_ID_DAY_KEY = "sessionIdDay";
    public final static String SESSION_ID_MONTH_KEY = "sessionIdMonth";
    public final static String SSESSION_ID_YEAR_KEY = "sessionIdYear";
    public final static String USER_NAME_SECOND_KEY = "userNameSecond";
    public final static String USER_NAME_MINUTE_KEY = "userNameMinute";
    public final static String USER_NAME_HOUR_KEY = "userNameHour";
    public final static String USER_NAME_DAY_KEY = "userNameDay";
    public final static String USER_NAME_MONTH_KEY = "userNameMonth";
    public final static String SUSER_NAME_YEAR_KEY = "userNameYear";
    public final static String IP_SECOND_KEY = "ipSecond";
    public final static String IP_MINUTE_KEY = "ipMinute";
    public final static String IP_HOUR_KEY = "ipHour";
    public final static String IP_DAY_KEY = "ipDay";
    public final static String IP_MONTH_KEY = "ipMonth";
    public final static String IP_YEAR_KEY = "ipYear";
    public final static Long TOKEN_TIME_OUT_SEC =60*5L;

}
