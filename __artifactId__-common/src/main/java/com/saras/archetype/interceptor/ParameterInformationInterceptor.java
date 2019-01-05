package com.saras.archetype.interceptor;

import com.saras.archetype.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * description:日志拦截器
 * saras_xu@163.com 2017-04-18 11:32 创建
 */
@WebInterceptorUrlConfiguration(priority = 1)
public class ParameterInformationInterceptor extends AbstractInterceptorHandler {
    private final static Logger logger = LoggerFactory.getLogger(ParameterInformationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //此行代码不允许删除,否则其他地方取不到用户
//        SessionHelper.setSession(httpServletRequest.getSession());
        Map<String, String> paramMap = AppUtils.getRequestMap(httpServletRequest);
        logger.info("收到{}#{}请求-->请求参数：{}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod(), paramMap);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
