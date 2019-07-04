package com.bbin.common.wrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bbin.common.utils.ThreadLocalUtils;

/**
 * Created by mrt on 2019/6/8 0008 下午 4:05
 */
public class TUpdateWrapper<T> extends UpdateWrapper<T> {
    {
        this.eq("tenant_id", ThreadLocalUtils.getTenantId());
    }
}
