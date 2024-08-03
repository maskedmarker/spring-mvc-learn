package org.example.learn.web.spring.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;

/**
 * 为root webApplicationContext 提供父容器
 */
public class MyContextLoaderListener extends ContextLoaderListener {

    // 写死地址
    public static final String LOCATION = "/WEB-INF/spring/parent-root-context.xml";

    /**
     * The main reason to load a parent context here is to allow multiple root web application contexts to all be children of a shared EAR context,
     * or alternately to also share the same parent context that is visible to EJBs.
     * For pure web applications, there is usually no need to worry about having a parent context to the root web application context.
     */
    @Override
    protected ApplicationContext loadParentContext(ServletContext servletContext) {

        // 与其他容器配置一致,使用xml来配置.
        XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        applicationContext.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX + LOCATION);
        applicationContext.setConfigLocation(LOCATION);
        // XmlWebApplicationContext需要ServletContext
        applicationContext.setServletContext(servletContext);

        // 容器需要自己准备好,即已经初始化完成
        applicationContext.refresh();

        return applicationContext;
    }
}
