package com.bbin.common.base;


import com.bbin.common.constant.HttpStatusEnum;
import com.bbin.common.response.ResponseResult;
import com.bbin.common.utils.DurationUtils;
import com.bbin.common.utils.RequestUtils;
import com.bbin.common.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrt on 2019/8/8 0008 下午 4:49
 * 时间限制功能拦截器
 */
@Slf4j
public abstract class TimeLimitAbstract extends HandlerInterceptorAdapter {

    // 校验sessionKey时间限制
    protected boolean isTimeLimit(String redisKey,HttpServletRequest request, HttpServletResponse response, StringRedisTemplate redis) throws InvalidValue {
        Assert.hasText(redisKey,"redisKey is Empty");
        Assert.isNull(redis, "redisTemplate is null");

        String url = request.getRequestURI();
        log.info("{}:校验开始,访问路径:{}", getPrefix(), url);
        if (RequestUtils.urlMatch(getInterceptUrlSet(),url)) {
            boolean isSave = this.saveSessionKeyTimeLimitToCache(redisKey, redis);
            if (isSave) {
                return true;
            }
            // sessionKey时间校验
            long limitTime = this.getLimitTime(redisKey, redis);
            if (limitTime > 0 || -1 == limitTime) {
                ResponseUtils.writeJson(response, ResponseResult.FAIL(this.createReject(getPrefix(), limitTime)), HttpStatusEnum.FORBIDDEN);
                return false;
            }
            if (-2 == limitTime) {
                throw new InvalidValue("redisKey不存在:" + redisKey);
            }
        }
        return true;
    }

    // 存sessionKey时间限制
    private boolean saveSessionKeyTimeLimitToCache(String redisKey, StringRedisTemplate redis) {
        return redis.opsForValue().setIfAbsent(redisKey, "1", getLimitTime().toMillis(), TimeUnit.MILLISECONDS);
    }

    // 组装拒绝返回结果
    private String createReject(String prefix, long limitTime) {
        if (-1 == limitTime) {
            limitTime = Duration.ofHours(999).plusMinutes(59).plusSeconds(59).plusMillis(999).toMillis();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(":");
        sb.append(DurationUtils.getResidueTime(limitTime));
        return sb.toString();
    }

    /**
     * 获取过期时间
     */
    private long getLimitTime(String key, StringRedisTemplate redis) {
        Long expire = redis.getExpire(key, TimeUnit.MILLISECONDS);
        return expire;
    }

    /**
     * 获取拦截路径
     * @return
     */
    protected abstract Set<String> getInterceptUrlSet();

    /**
     * 获取限制时间
     * @return
     */
    protected abstract Duration getLimitTime();

    /**
     * 获取拦截器的名称
     * @return
     */
    protected abstract String getPrefix();

}
