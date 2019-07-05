package com.bbin.utils.project;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Created by mrt on 2019/7/4 0004 下午 3:05
 */
@Slf4j
public class BigDecimalUtils {
    //金额转换需要分->元
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * 元转分（保留到个位,向下取整）
     * 注意：一定要接收，因为运算后的结果会存放到新的对象
     * @param yuan 金额，单位：元
     * @return  新的计算好的Bigdecimal
     * @throws Exception
     */
    public static BigDecimal yuanToFen(BigDecimal yuan) throws Exception {
        if (null == yuan) {
            log.error("未传入元的值");
            return null;
        }
        return yuan.multiply(ONE_HUNDRED).setScale(0, BigDecimal.ROUND_DOWN);
    }

    /**
     * 分转元（保留到小数点后面2位，向下取整）
     * 注意：一定要接收，因为运算后会存放到新的对象
     * @param fen 金额，单位：分
     * @return 新的计算好的Bigdecimal
     * @throws Exception
     */
    public static BigDecimal fenToYuan(BigDecimal fen) throws Exception {
        if (null == fen) {
            log.error("未传入分的值");
            return null;
        }
        return fen.divide(ONE_HUNDRED).setScale(2, BigDecimal.ROUND_DOWN);
    }
}
