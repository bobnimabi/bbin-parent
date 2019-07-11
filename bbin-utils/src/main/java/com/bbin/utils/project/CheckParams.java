package com.bbin.utils.project;

import com.bbin.common.exception.ExceptionCast;
import com.bbin.common.exception.InvalidParamException;
import com.bbin.common.response.ResponseResult;
import com.bbin.common.response.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by mrt on 2019/7/5 0005 下午 2:27
 * 功能：校验增强
 */
@Slf4j
public class CheckParams {

    /**
     * 通用校验
     * @param result 校验结果
     * @param errorMes 错误信息
     */
    public static void check(boolean result,String errorMes) throws Exception{
        if (!result) {
            ExceptionCast.castInvalid(StringUtils.isEmpty(errorMes) ? "未指定" : errorMes);
        }
    }

    /**
     * 字符串非空校验
     * @param str 被校验字符串
     * @param errorMes 错误信息
     */
    public static void checkStr(String str,String errorMes) throws Exception{
        if (StringUtils.isEmpty(str)) {
            ExceptionCast.castInvalid(StringUtils.isEmpty(errorMes) ? "空字符串" : errorMes);
        }
    }

    /**
     * 集合非空校验
     * @param collection 被校验集合
     * @param errorMes 错误信息
     */
    public static void checkCollection(Collection collection, String errorMes) throws Exception{
        if (CollectionUtils.isEmpty(collection)) {
            ExceptionCast.castInvalid(StringUtils.isEmpty(errorMes) ? "空集合" : errorMes);
        }
    }

    /**
     * 对象非空校验
     * @param object 被校验对象
     * @param errorMes 错误信息
     */
    public static void checkObj(Object object, String errorMes) throws Exception{
        if (null == object) {
            ExceptionCast.castInvalid(StringUtils.isEmpty(errorMes) ? "空对象" : errorMes);
        }
    }

    /**
     * 状态值非法校验
     * @param status 被校验的状态
     * @param statusAll 所有的状态
     * @param errorMes 错误信息
     * @throws Exception
     */
    public static void checkStatus(Integer status, Integer[] statusAll, String errorMes) throws Exception {
        boolean isContain = ArrayUtils.contains(statusAll, status);
        if (!isContain) {
            ExceptionCast.castInvalid(StringUtils.isEmpty(errorMes) ? "状态有误" : errorMes);
        }
    }

    /**
     * 起始时间和结束时间校验
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param errorMes 错误信息
     * @throws Exception
     */
    public static void checkStartAndEndTime(LocalDateTime startTime,LocalDateTime endTime, String errorMes) throws Exception {
        if (null != startTime
                && null != endTime
                && startTime.isAfter(endTime)) {
            ExceptionCast.castInvalid(StringUtils.isEmpty(errorMes) ? "起始时间不能晚于结束时间" : errorMes);
        }
    }
}
