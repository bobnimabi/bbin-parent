package com.bbin.common.request.wrapper;

import com.bbin.common.request.wrapper.MyRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 将ServletRequestWrapper替换成我们的MyRequestWrapper
 * 功能：偷换request，保证inputstream可多次获取
 * 配置：只需要在子项目里面将该拦截器添加即可（WebMvcConfigurer的实现类）
 */
public class RequestReplacedFilter implements Filter {
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new MyRequestWrapper((HttpServletRequest) request);
        }
        if(requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}