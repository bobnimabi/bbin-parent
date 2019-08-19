package com.bbin.core.limit;


import com.bbin.common.response.ResponseResult;
import com.bbin.common.utils.DurationUtils;
import com.bbin.common.utils.ResponseUtils;
import com.bbin.common.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrt on 2019/8/8 0008 下午 4:49
 * 时间限制功能拦截器
 */
@Slf4j
public abstract class AbstractTimeLimitInterceptor extends HandlerInterceptorAdapter {

    // 校验sessionKey时间限制
    protected boolean isTimeLimit(String sessionId, HttpServletRequest request, HttpServletResponse response, StringRedisTemplate redis) {
        log.info("{}:校验开始...", this.getInterceptorName());

        // 次数检查前，校验所有参数合法性
        checkParameter(sessionId,redis);
        String redisKey=getTimeLimitCacheKey(sessionId);

        // 次数检查开始
        // 第一次访问=>放行
        Boolean isSave = redis.opsForValue().setIfAbsent(redisKey, "1", this.getLimitTime().toMillis(), TimeUnit.MILLISECONDS);
        if (isSave) {
            return true;
        }
        // 次数未达到上限=>放行
        Long value = redis.opsForValue().increment(redisKey);
        if (value <= getLimitsTimes()) {
            return true;
        }
        // 次数超限制，组装响应说明
        excessTimeOutResponse(redisKey, response, redis);
        return false;
    }

    // 超次数响应
    private void excessTimeOutResponse(String redisKey, HttpServletResponse response, StringRedisTemplate redis) {
        long expire = redis.getExpire(redisKey, TimeUnit.MILLISECONDS);
        if (-2 == expire) {
            throw new IllegalStateException("redisKey不存在:" + redisKey);
        }
        String errorResp = this.excessTimeOutError();
        if (StringUtils.isEmpty(errorResp)) {
            // 默认响应：xxx限制：剩余xx时xx分xx秒xxx毫秒
            if (expire > 0 || -1 == expire) {
                ResponseUtils.writeJson(response, ResponseResult.FAIL(this.createDefaultRejectResponse(this.getInterceptorName(), expire)), HttpStatus.FORBIDDEN);
            }
        } else {
            // 自定义响应
            ResponseUtils.writeJson(response, ResponseResult.FAIL(errorResp), HttpStatus.FORBIDDEN);
        }
    }

    // 组装默认超次数说明：剩余xx时xx分xx秒xxx毫秒
    protected String createDefaultRejectResponse(String prefix, long expire) {
        if (-1 == expire) {
            expire = Duration.ofHours(999).plusMinutes(59).plusSeconds(59).plusMillis(999).toMillis();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(":");
        sb.append(DurationUtils.getResidueTime(expire));
        return sb.toString();
    }

    // 拼接redis的时间限制key
    private String getTimeLimitCacheKey(String sessionId) {
        Assert.hasText(sessionId, getInterceptorName() + ":未传入sessionId");
        return new StringBuilder(sessionId.length() + this.getCachePrefix().length() + 10)
                .append(this.getCachePrefix())
                .append(ThreadLocalUtils.getTenantIdOption().get())
                .append(":")
                .append(ThreadLocalUtils.getChannelIdOption().get())
                .append(":")
                .append(sessionId)
                .toString();
    }

    // 参数合法校验
    private void checkParameter(String sessionId,StringRedisTemplate redis) {
        String prefix = this.getInterceptorName();
        Assert.hasText(prefix, "无拦截器名称");
        Assert.notNull(redis, prefix + ":无缓存实例");
        Assert.hasText(sessionId, prefix + ":无缓存键");
        Assert.isTrue(isTimeLegal(), prefix + ":时间不合规");
        Assert.isTrue(isTimesLegal(), prefix + ":限制次数不能低于0");
    }

    // 校验时间
    private boolean isTimeLegal() {
        return this.getLimitTime().toMillis() > 0;
    }

    // 校验时间
    private boolean isTimesLegal() {
        return getLimitsTimes() > 0;
    }

    /**
     * 获取限制缓存Key的前缀
     * @return
     */
    protected abstract String getCachePrefix();

    /**
     * 获取限制时长
     * @return
     */
    protected abstract Duration getLimitTime();

    /**
     * 获取限制次数
     * @return
     */
    protected abstract Long getLimitsTimes();

    /**
     * 获取拦截器的名称
     * @return
     */
    protected abstract String getInterceptorName();

    /**
     * 自定义超过次数响应文本
     * 返回null(默认):xxx限制：剩余xx时xx分xx秒xxx毫秒
     */
    protected abstract String excessTimeOutError();

}
