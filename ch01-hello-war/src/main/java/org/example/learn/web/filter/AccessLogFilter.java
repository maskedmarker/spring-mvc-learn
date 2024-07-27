package org.example.learn.web.filter;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 记录网络访问历史
 */
public class AccessLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        String requestURI = httpServletRequest.getRequestURI();

        // 借用已有的日志组件
        request.getServletContext().log("servletPath: [" + servletPath + "]");
        request.getServletContext().log("requestURL: [" + requestURL + "]");
        request.getServletContext().log("requestURI: [" + requestURI + "]");

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
