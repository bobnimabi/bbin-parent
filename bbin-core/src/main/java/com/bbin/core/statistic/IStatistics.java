package com.bbin.core.statistic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbin.common.response.ResponseResult;

/**
 * Created by mrt on 2019/6/7 0007 下午 1:08
 */
public interface IStatistics<E, T> {
    //统计
    public ResponseResult statistic(QueryWrapper<T> queryWrapper) throws Exception;
}
