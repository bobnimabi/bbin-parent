package com.bbin.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class QueryUtils<T> {
    public static <T>QueryWrapper getQueryWrapper(){
       return new QueryWrapper<T>()
                    .eq("tenant_id", ThreadLocalUtils.getTenantId())
                    .eq("channel_id", ThreadLocalUtils.getChannelId());
    }
}
