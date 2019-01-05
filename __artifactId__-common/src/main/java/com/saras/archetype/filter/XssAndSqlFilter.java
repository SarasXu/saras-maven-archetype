package com.saras.archetype.filter;

import com.saras.archetype.filter.wrapper.XssAndSqlHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * description:
 *
 * @author saras_xu@163.com
 * @date 2018-01-24 15:46 创建
 */
public class XssAndSqlFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(XssAndSqlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        Map<String, String> map = Maps.newHashMap();
//        Enumeration<String> en = request.getParameterNames();
//        while (en.hasMoreElements()) {
//            String parameterName = en.nextElement();
//            map.put(parameterName, request.getParameter(parameterName));
//        }
//        logger.info("过滤原始请求-->请求参数：{}", map);
        XssAndSqlHttpServletRequestWrapper xssRequest = new XssAndSqlHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {

    }
}
