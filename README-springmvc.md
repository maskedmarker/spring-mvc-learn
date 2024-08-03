# 关于springmvc知识

## 核心概念

#### FrameworkServlet可以拥有独立的web WebApplicationContext
FrameworkServlet作为框架的基础类,集成了一个WebApplicationContext,该WebApplicationContext为FrameworkServlet提供了spring ApplicationContext各种便利的功能.
该WebApplicationContext可以是用户以构造器入参或以servletContext的属性的形式提供给FrameworkServlet.但WebApplicationContext必须已经初始化完成了;
当用户不主动提供时,FrameworkServlet就通过servlet的init-param-contextConfigLocation信息为自己构造并初始化一个WebApplicationContext;
所以,servlet container中可以有多个FrameworkServlet;这些个FrameworkServlet可以独占(用户不指定)或公用(用户指定)WebApplicationContext.

#### 多个FrameworkServlet拥有共同的提供基础服务的root WebApplicationContext
上面FrameworkServlet拥有的是web层面的WebApplicationContext,主要用于处理web请求/响应;还有一些基础性的服务需要放置在全局层面的容器里,比如:业务逻辑/数据库/中间件等.
root WebApplicationContext需用通过ContextLoaderListener在servletContext初始化的时候通过context-param的contextConfigLocation来获取用户配置信息,创建并初始化一个WebApplicationContext;
该WebApplicationContext是全局唯一的.
FrameworkServlet的web WebApplicationContext的父容器就是该root WebApplicationContext.这样通过层级关系,方便FrameworkServlet无感地使用基础服务.

### contextConfigLocation
web/root WebApplicationContext默认情况下都是XmlWebApplicationContext,即ConfigurableWebApplicationContext子类.
ConfigurableWebApplicationContext支持一个context由多个配置文件来完成配置过程.即多个配置文件生成一个context


注意:
1.当在web.xml中配置多个DispatcherServlet时,需要通过url的prefix来做路由.
2.此时prefix肯定不是空字符串,此时请求的pathInfo在剔除prefix后的值才是待映射到的RequestMapping.
3.当单个dispatcher时,urlPattern通常是/*,等价于prefix为空,当多dispatcher时,就需要注意了.
4.具体参考调用链路:
org.springframework.web.servlet.DispatcherServlet.getHandler
    org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandler
        org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal
            org.springframework.web.util.UrlPathHelper.getLookupPathForRequest
                org.springframework.web.util.UrlPathHelper.getPathWithinServletMapping


## todo
