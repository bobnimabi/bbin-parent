package com.bbin.core.cache;

import com.alibaba.fastjson.JSON;
import com.bbin.common.exception.ExceptionCast;
import com.bbin.common.response.CommonCode;
import com.bbin.common.response.InnerResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mrt on 2019/6/25 0025 下午 3:09
 */
@Slf4j
public class AbstractRedisHelper {
    //一个key会维护一个锁
    private static Map<String, ReentrantLock> lockMap = new HashMap<>();
    //创建锁的锁
    private static ReentrantLock lock = new ReentrantLock();

    private static ReentrantLock getLock(String key) {
        ReentrantLock textLock = lockMap.get(key);
        if (null == textLock) {
            if (lock.tryLock()) {
                try {
                    textLock = new ReentrantLock();
                    lockMap.put(key, textLock);
                } catch (Exception e) {
                    log.info("创建锁异常", e);
                } finally {
                    lock.unlock();
                }
            } else {
                ExceptionCast.cast(CommonCode.SERVER_CONGESTION);
            }
        }
        return textLock;
    }

    public static InnerResult<String> queryText(String key, RedisTemplate<String,String> redis, DataBaseAid dataBaseAid) {
        String text = redis.opsForValue().get(key);
        if (StringUtils.isEmpty(text)) {
            ReentrantLock textLock = getLock(key);
            if (textLock.tryLock()) {
                try {
                    text = redis.opsForValue().get(key);
                    if (StringUtils.isEmpty(text)) {
                        text = dataBaseAid.aid(key);
                        if (StringUtils.isEmpty(text)) return InnerResult.FAIL("DataBaseAid援助失败：数据库未配置");
                        redis.opsForValue().set(key, text);
                    }
                } catch (Exception e) {
                    log.info("从缓存获取信息异常", e);
                } finally {
                    textLock.unlock();
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
                }
                return queryText(key, redis, dataBaseAid);
            }
        }
        return new InnerResult(CommonCode.SUCCESS, text);
    }

    //查询出来后封装对象
    public <T> InnerResult<T> queryObj(String key, RedisTemplate<String,String> redis, DataBaseAid dataBaseAid, Class<T> tClass) {
        InnerResult<String> result = queryText(key, redis, dataBaseAid);
        if (!result.isSuccess()) return InnerResult.FAIL(result.getMessage());
        String jsonStr = result.getObj();
        return new InnerResult(CommonCode.SUCCESS, JSON.parseObject(jsonStr, tClass));
    }

    //删除
    public static boolean del(String key,RedisTemplate<String,String> redis) {
        return redis.delete(key);
    }
}
