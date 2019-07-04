package com.bbin.utils.project;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by mrt on 2019/6/26 0026 下午 12:03
 * 功能：响应json、html、text等信息
 */
@Slf4j
public class ResponseUtils {

    public static void writeJson(HttpServletResponse response, Object o,int statusCode)throws Exception {
        response.setStatus(statusCode);
        write(response, o, "application/json;charset=utf-8");
    }

    public static void writeHtml(HttpServletResponse response, Object o,int statusCode)throws Exception {
        response.setStatus(statusCode);
        write(response, o, "text/html;charset=utf-8");
    }

    public static void writeText(HttpServletResponse response, Object o,int statusCode)throws Exception {
        response.setStatus(statusCode);
        write(response, o, "text/plain;charset=UTF-8");
    }

    public static void writeXml(HttpServletResponse response, Object o,int statusCode)throws Exception {
        response.setStatus(statusCode);
        write(response, o, "text/xml;charset=UTF-8");
    }

    public static void write(HttpServletResponse response, Object o,String contentType)throws Exception {
        PrintWriter out = response.getWriter();
        try {
            response.setContentType(contentType);
            out.println(JSON.toJSONString(o));
            out.flush();
        } catch (Exception e) {
            log.info("response异常", e);
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }
}