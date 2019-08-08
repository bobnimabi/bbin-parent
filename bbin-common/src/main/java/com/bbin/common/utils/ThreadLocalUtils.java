package com.bbin.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ThreadLocalUtils {

    private static ThreadLocal<Map<String, Long>> threadLocal = new ThreadLocal<Map<String, Long>>();

    public static String OPERATOR_ID = "operator_id";
    public static String TENANT_ID = "tenant_id";
    public static String CHANNEL_ID = "channel_id";

    public static void setOperatorId(Long operatorId) {
        set(OPERATOR_ID, operatorId);
    }

    public static void setTenantId(Long tenantId) {
        set(TENANT_ID, tenantId);
    }

    public static void setChannelId(Long channelId) {
        set(CHANNEL_ID, channelId);
    }

    public static void set(String key, Long value) {
        Optional.ofNullable(value).orElseThrow(() -> new IllegalArgumentException("参数value 为空"));
        Optional.ofNullable(key).orElseThrow(() -> new IllegalArgumentException("参数value 为空"));
        Map<String, Long> cache = Optional.ofNullable(threadLocal.get()).orElseGet(() -> {
                    Map<String, Long> map = new HashMap<String, Long>();
                    threadLocal.set(map);
                    return map;
                }
        );
        cache.put(key, value);
    }
    public static void set(String key, String value) {
        set(key,Long.valueOf(value.trim()));
    }
    /**
     *
     * @return
     */
    @Deprecated
    public static Long getOperatorId() {
        return get(OPERATOR_ID);
    }
    public  static Optional<Long> getOperatorIdOption() {
        return Optional.ofNullable(get(OPERATOR_ID));
    }

    @Deprecated
    public static Long getTenantId() {
        return get(TENANT_ID);
    }
    public static Optional<Long> getTenantIdOption() {
        return Optional.ofNullable(get(TENANT_ID));
    }
    public static void clean(){
        threadLocal.set(null);
    }

    /**
     * @link com.bbin.common.constant.ThreadLocalUtils#getChannelIdOption
     * @return
     */
    @Deprecated
    public static Long getChannelId() {
        return get(CHANNEL_ID);
    }

    public static Optional<Long> getChannelIdOption() {
        return Optional.ofNullable(get(CHANNEL_ID));
    }

    public boolean hasSet(){
        return getTenantIdOption().isPresent()&&getChannelIdOption().isPresent();
    }
    private static  Optional<Long> getOption(String name) {
        return   Optional.ofNullable(threadLocal.get()).map(cache->{
            return Optional.of(threadLocal.get().get(name));
        }).orElse(Optional.empty());
    }

    private static Long get(String name) {
        Map<String, Long> cache = threadLocal.get();
        if (cache != null) {
            return (Long) cache.get(name);
        }
        return null;
    }


    public static void main(String[] args) {
//        setOperatorId(11L);
//        setChannelId(12L);
//        setTenantId(13L);
//        System.out.println(getOperatorId());
//        System.out.println(getChannelId());
//        System.out.println(getTenantId());
        System.out.println(Optional.of(Optional.empty()).orElseThrow(()->new IllegalArgumentException("")));
    }
}
