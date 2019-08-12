package com.bbin.common.utils;

import java.time.Duration;

/**
 * Created by mrt on 2019/8/8 0008 下午 6:51
 */
public class DurationUtils {

    // 获取剩余的时分秒毫秒
    public static String getResidueTime(Duration duration) {
        StringBuilder sb = new StringBuilder();
        sb.append(duration.toHours());
        sb.append("时");
        sb.append(duration.minusHours(duration.toHours()).toMinutes());
        sb.append("分");
        sb.append(duration.minusMinutes(duration.toMinutes()).getSeconds());
        sb.append("秒");
        sb.append(duration.minusSeconds(duration.getSeconds()).toMillis());
        sb.append("毫秒");
        return sb.toString();
    }


    /**
     * 将剩余时间转时分秒
     * @param mills 剩余时间，单位：毫秒
     * @return 转换后的时间字符串(从第一个不为0的时间单位开始显示)
     */
    // 将long转换成时分秒毫秒
    public static String getResidueTime(long mills) {
        StringBuilder sb = new StringBuilder();
        sb.append("剩余 ");
        long hour = mills / 3600000;
        long min = mills % 3600000 / 60000;
        long sec = mills % 3600000 % 60000 / 1000;
        long millss = mills % 3600000 % 60000 % 1000;
        long time[] = {hour, min, sec, millss};
        String timeStr[] = {"时", "分", "秒", "毫秒"};
        boolean flag = false;
        for (int i = 0; i < time.length; i++) {
            if (time[i] != 0 || flag) {
                flag = true;
                if (i != time.length-1) {
                    sb.append(String.format("%02d",time[i])).append(timeStr[i]);
                    continue;
                }
                sb.append(String.format("%03d",time[i])).append(timeStr[i]);
            }
        }
        return sb.toString();
    }
}
