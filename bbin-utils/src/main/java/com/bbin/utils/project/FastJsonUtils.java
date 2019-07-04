package com.bbin.utils.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * Created by mrt on 2019/6/25 0025 下午 4:51
 */
public class FastJsonUtils {

    /**
     * 转json串是只显示哪些字段
     * @param obj 转json串的对象
     * @param strings 需要显示的字段
     * @return 转换好的json串
     * @throws Exception
     */
    public static String jsonToStringInclude(Object obj,String... strings) throws Exception{
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(strings);
        return JSON.toJSONString(obj, filter);
    }

    /**
     * 转json串排除哪些字段
     * @param obj 转json串的对象
     * @param strings 需要排除哪些字段
     * @return 转换好的json串
     * @throws Exception
     */
    public static String jsonToStringExclude(Object obj,String... strings) throws Exception{
        PropertyPreFilter propertyPreFilter = new PropertyPreFilter() {
            @Override
            public boolean apply(JSONSerializer jsonSerializer, Object source, String name) {
                for (String str: strings) {
                    if (name.equalsIgnoreCase(str)) {
                        return false;
                    }
                }
                return true;
            }
        };
        return JSON.toJSONString(obj, propertyPreFilter);
    }
}