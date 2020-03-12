package com.bbin.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mrt on 2019/6/26 0026 下午 12:03
 * 功能：响应json、html、text等信息
 */
@Slf4j
public class ResponseUtils {

    public static void writeJson(HttpServletResponse response, Object o, HttpStatus statusCode) {
        response.setStatus(statusCode.value());
        write(response, o, "application/json;charset=utf-8");
    }

    public static void writeHtml(HttpServletResponse response, Object o,HttpStatus statusCode) {
        response.setStatus(statusCode.value());
        write(response, o, "text/html;charset=utf-8");
    }

    public static void writeText(HttpServletResponse response, Object o,HttpStatus statusCode) {
        response.setStatus(statusCode.value());
        write(response, o, "text/plain;charset=UTF-8");
    }

    public static void writeXml(HttpServletResponse response, Object o,HttpStatus statusCode) {
        response.setStatus(statusCode.value());
        write(response, o, "text/xml;charset=UTF-8");
    }

    public static void write(HttpServletResponse response, Object o,String contentType) {
        PrintWriter out = null;
        response.setContentType(contentType);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(JSON.toJSONBytes(o));
            out.flush();
        } catch (IOException e) {
            log.error("IOException", e);
        }finally {
            if (null != out) {
                out.close();
            }
        }
    }
}
