package com.saras.archetype.http.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 基于Spring服务的Servlet基础类
 * <p>
 * <li>1、整合Spring容器到Servlet中，方便子类直接获取Spring容器内的服务 <li>2、提Servlet处理基础模板框架 <li>
 * 3、提供对Servlet配置参数的获取封装
 */
public abstract class AbstractSpringServlet extends HttpServlet {

    /**
     * UID
     */
    private static final long serialVersionUID = 902161018414718234L;

    private static final Logger logger = LoggerFactory.getLogger(AbstractSpringServlet.class);

    /**
     * WebApplicationContext for this servlet
     */
    private WebApplicationContext webApplicationContext;

    @Override
    public void init() throws ServletException {
        logger.info("Initializing Spring-based Servlet '" + getServletName() + "'");
        this.webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        doInit();
    }

    protected void doInit() {

    }

    @Override
    public void destroy() {
        webApplicationContext = null;
    }

}
