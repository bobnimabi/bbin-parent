package com.bbin.common.wrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bbin.common.constant.CommonConstant;
import com.bbin.common.utils.ThreadLocalUtils;

/**
 * Created by mrt on 2019/6/8 0008 下午 4:05
 */
public class MyUpdateWrapper<T> extends UpdateWrapper<T> {
    {
        this.eq("tenant_id", ThreadLocalUtils.getTenantId());
        this.eq("channel_id", ThreadLocalUtils.getChannelId());
        this.ne("status", CommonConstant.DELETE);
    }
}
