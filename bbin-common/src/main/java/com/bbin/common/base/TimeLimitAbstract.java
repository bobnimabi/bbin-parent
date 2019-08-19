package com.bbin.common.base;


import com.bbin.common.response.ResponseResult;
import com.bbin.common.utils.DurationUtils;
import com.bbin.common.utils.RequestUtils;
import com.bbin.common.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrt on 2019/8/8 0008 下午 4:49
 * 时间限制功能拦截器
 */
@Slf4j
public abstract class TimeLimitAbstract extends HandlerInterceptorAdapter {
    private static final String CLASS_NAME = "TIME_LIMIT:";

    // 校验sessionKey时间限制
    protected boolean isTimeLimit(String redisKey,HttpServletRequest request, HttpServletResponse response, StringRedisTemplate redis) throws InvalidValue {
        String prefix = getPrefix();
        Assert.hasText(prefix,  "prefix不能为null");

        log.info("{}:校验开始...", prefix);
        if (isPermit(request)) {
            return true;
        }
        Assert.notNull(redis, prefix + ":redisTemplate不能为null");
        Assert.hasText(redisKey,prefix + ":redisKey不能为null");

        // 若能存入，直接放行
        boolean isSave = this.saveSessionKeyTimeLimitToCache(redisKey, redis);
        if (isSave) {
            return true;
        }
        // 不能存入，返回限制时间
        long limitTime = this.getLimitTime(redisKey, redis);
        if (limitTime > 0 || -1 == limitTime) {
            ResponseUtils.writeJson(response, ResponseResult.FAIL(this.createReject(prefix, limitTime)), HttpStatus.FORBIDDEN);
        }
        if (-2 == limitTime) {
            throw new InvalidValue("redisKey不存在:" + redisKey);
        }
        return false;
    }

    // 拦截路径校验
    private boolean isPermit(HttpServletRequest request) {
        if (StringUtils.isEmpty(getInterceptUrl())) {
            log.info("{}:直接放行->拦截路径为空",getPrefix());
            return true;
        }
        if (!RequestUtils.urlMatch(parseUrlStringToSet(getInterceptUrl()), request.getRequestURI())) {
            log.info("{}:路径放行->路径:{}",getPrefix(),request.getRequestURI());
            return true;
        }
        return false;
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

    // 将url转换成set
    private Set<String> parseUrlStringToSet(String urlString) {
        String[] split = urlString.split(",");
        Set<String> set = new HashSet<>();
        for (String url : split) {
            if (!StringUtils.isEmpty(url)) {
                set.add(url);
            }
        }
        return set;
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
    protected abstract String getInterceptUrl();

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
