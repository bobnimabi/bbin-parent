package com.bbin.common.wrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bbin.common.constant.CommonConstant;
import com.bbin.common.utils.ThreadLocalUtils;

/**
 * Created by mrt on 2019/6/8 0008 下午 4:05
 */
public class SUpdateWrapper<T> extends UpdateWrapper<T> {
    {
        this.ne("status", CommonConstant.DELETE);
    }
}
