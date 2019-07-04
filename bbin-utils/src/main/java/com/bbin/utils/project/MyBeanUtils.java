package com.bbin.utils.project;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrt on 2019/4/8 0008 下午 4:15
 * 功能：对象间的拷贝
 */
public class MyBeanUtils {

    //对象间的属性的复制
    public static void copyProperties(Object source, Object target) throws Exception{
        BeanUtils.copyProperties(source, target);
    }
    //对象间的属性的复制，忽略一些属性
    public static void copyProperties(Object source, Object target,String... ignoreProperties) throws Exception{
        BeanUtils.copyProperties(source, target,ignoreProperties);
    }

    //对象间的属性的复制
    public static <T>T copyProperties(Object source, Class<T> tClass) throws Exception{
        T t = tClass.newInstance();
        BeanUtils.copyProperties(source, t);
        return t;
    }

    //对象转换成map
    public static Map<String,Object> copyToMap(Object bean) throws Exception{
        return com.baomidou.mybatisplus.core.toolkit.BeanUtils.beanToMap(bean);
    }

    //对象转换成map
    public static <K,V>Map<K,V> copyMapToMap(Map<K,V> map,Class<K> keyClazz,Class<V> valueClazz) throws Exception{
        Map<K, V> initMap = new HashMap<>();
        for (Map.Entry<K,V> entry :map.entrySet()) {
            initMap.put(entry.getKey(), MyBeanUtils.copyProperties(entry.getValue(),valueClazz));
        }
        return initMap;
    }

    //map的value转list
    public static <K,V>List<V> mapValueToList(Map<K,V> map,Class<K> keyClazz,Class<V> valueClazz) throws Exception{
        List<V> list = new ArrayList<>();
        for (Map.Entry<K,V> entry :map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    //map转换成bean
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz ) throws Exception{
        return com.baomidou.mybatisplus.core.toolkit.BeanUtils.mapToBean(map, clazz);
    }
    //maps转换成List<bean>
    public static <T> List<T> mapsToBeans(List<Map<String, Object>> maps, Class<T> clazz) throws Exception{
        return com.baomidou.mybatisplus.core.toolkit.BeanUtils.mapsToBeans(maps, clazz);
    }
    //List<bean>转换成maps
    public static <T> List<Map<String, Object>> beansToMaps(List<T> beans) throws Exception{
        return com.baomidou.mybatisplus.core.toolkit.BeanUtils.beansToMaps(beans);
    }
    //List<bean1>转换成List<bean2>
    public static <V> List<V> copyListToList(List beans,Class<V> clazz) throws Exception {
        List<V> list = new ArrayList<>();
        try {
            for (Object bean : beans) {
                V v = clazz.newInstance();
                copyProperties(bean, v);
                list.add(v);
            }
            return list;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    //复制page
    public static <T> Page<T> copyPageToPage(IPage page, Class<T> clazz) throws Exception{
        Page<T> cPage = new Page();
        cPage.setSize(page.getSize());
        cPage.setTotal(page.getTotal());
        cPage.setCurrent(page.getCurrent());
        cPage.setRecords(copyListToList(page.getRecords(), clazz));
        return cPage;
    }
}
