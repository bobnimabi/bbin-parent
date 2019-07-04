package com.bbin.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtils {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    private static String OPERATOR_ID = "operator_id";
    private static String TENANT_ID = "tenant_id";
    private static String CHANNEL_ID = "channel_id";

    public static void setOperatorId(Long operatorId) {
        set(operatorId, OPERATOR_ID);
    }

    public static void setTenantId(Long operatorId) {
        set(operatorId, TENANT_ID);
    }

    public static void setChannelId(Long operatorId) {
        set(operatorId, CHANNEL_ID);
    }

    public static void set(Long operatorId, String name) {
//        Optional.ofNullable(operatorId).orElseThrow(()-> new  IllegalArgumentException("Invalid userName."));
        Map<String, Object> cache = threadLocal.get();
        if (cache == null) {
            cache = new HashMap<String, Object>();
            threadLocal.set(cache);
        }
        cache.put(name, operatorId);
    }


    public static Long getOperatorId() {
        return get(OPERATOR_ID);
    }

    public static Long getTenantId() {
        return get(TENANT_ID);
    }

    public static Long getChannelId() {
        return get(CHANNEL_ID);
    }

    private static Long get(String name) {
        Map<String, Object> cache = threadLocal.get();
        if (cache != null) {
            return (Long) cache.get(TENANT_ID);
        }
        return 1L;
    }

    public static void main(String[] args) {
        setOperatorId(11L);
        System.out.println(getOperatorId());
        ;
    }
}
