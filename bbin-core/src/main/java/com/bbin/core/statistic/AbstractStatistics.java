package com.bbin.core.statistic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bbin.common.response.ResponseResult;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by mrt on 2019/6/7 0007 下午 1:31
 * E:统计视图
 * T:被统计对象
 */
public abstract class AbstractStatistics<E, T> implements IStatistics<E, T> {
    /**
     * 逐条统计
     * @param record 单条记录
     * @param statisticsVo 统计视图
     * @throws Exception
     */
    protected abstract void perStatistic(T record, E statisticsVo) throws Exception;

    /**
     * 统计调度
     * @param service 被统计对象service
     * @param queryWrapper 查询条件
     * @return 统计结果
     * @throws Exception
     */
    protected ResponseResult statisticHandle(IService service, QueryWrapper<T> queryWrapper) throws Exception {
        Class<E> eClass = getEClass();
        contailer.set(eClass.newInstance());
        statisticsGo(service, queryWrapper);
        return ResponseResult.SUCCESS(contailer.remove());
    }

    // 翻页统计开始
    private void statisticsGo(IService service, QueryWrapper<T> queryWrapper) throws Exception {
        Page<T> page = new Page<T>(0, 500).setCurrent(0);
        boolean flag = true;
        while (flag) {
            page.setCurrent(page.getCurrent() + 1);
            Page<T> pageVo = getPage(service, page, queryWrapper);
            List<T> records = pageVo.getRecords();
            if (CollectionUtils.isEmpty(records)) break;
            staticticsDetail(records);
            flag = pageVo.hasNext();
        }
    }

    // 统计细节
    private void staticticsDetail(List<T> list) throws Exception {
        E statisticsVo = contailer.get();
        for (T record : list) {
            perStatistic(record,statisticsVo);
        }
    }

    // 获取运行时类型
    private Class<E> getEClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    // 分页查询
    private Page<T> getPage(IService<T> service, Page<T> page, QueryWrapper<T> queryWrapper) throws Exception {
        return (Page<T>) service.page(page, queryWrapper);
    }

    // 统计视图容器
    protected Contailer<E> contailer = new Contailer<>();

    private class Contailer<K> {
        private Contailer(){}
        private ThreadLocal<K> threadLocal = new ThreadLocal<>();

        public void set(K obj) throws Exception {
            threadLocal.set(obj);
        }

        public K get() throws Exception {
            return threadLocal.get();
        }

        public K remove() throws Exception {
            K clone = (K) BeanUtils.cloneBean(get());
            threadLocal.remove();
            return clone;
        }
    }
}
