# 关于springmvc知识
spring-web模块的相关学习.

spring-boot的autoconfigure对web配置与spring-web有差异,具体参考spring-boot2-learn项目.

## 核心概念

### FrameworkServlet
在springmvc设计中,FrameworkServlet管理一个spring的WebApplicationContext容器,可以用spring容器来完成处理ServletRequest的任务.  

为什么说是管理而非依赖spring容器呢?
在FrameworkServlet的源码中,仅仅体现了FrameworkServlet管理了(创建或初始化/refresh)一个spring容器.处理http请求的核心方法service方法是abstract,并没有代码展示对于spring容器的依赖.

FrameworkServlet作为servlet被servlet容器管理,同时FrameworkServlet又要管理spring容器.
FrameworkServlet如何衔接好servlet/spring容器的?
FrameworkServlet作为servlet被servlet容器初始化时, FrameworkServlet完成spring容器的创建(或查找),(如果未初始化)并初始化容器.
FrameworkServlet.onRefresh方法作为预留的callback,在其所管理的(自建/注入)spring容器首次完成初始化时会被调用.


1. 每个FrameworkServlet仅管理一个WebApplicationContext(也称web-WebApplicationContext).
2. 这个WebApplicationContext可以是自建的,也可以是被注入进来的.
3. 自己创建的WebApplicationContext需要自己触发refresh方法来完成spring容器的初始化;注入的WebApplicationContext需要是已经完成初始化的.
4. 自己创建的WebApplicationContext会查找root-WebApplicationContext,并将其设置为parent容器.
5. root-WebApplicationContext由ContextLoaderListener(ServletContextListener的实现类)在servlet容器初始化完成时创建的.
   - root-WebApplicationContext主要存放基础性的服务(业务逻辑/数据库/中间件等)
   - root-WebApplicationContext通过context-param的contextConfigLocation来配置容器
   - web-WebApplicationContext更多是处理http请求/响应相关的逻辑,作为root-WebApplicationContext的子容器,可以很方便地使用所需的基础性的服务,同时也明确区分不同的职责从而达到高内聚.
6. parent-root-WebApplicationContext
   - 可以为root-WebApplicationContext指定父容器.
   - 通过自定义ContextLoaderListener子类来实现,并且这个容器需要提前完成初始化(即提前调用完refresh).
   - 该父容器主要是为legacy系统准备的,存放一些EAR之类的逻辑.
7. servlet容器中是可以有多个FrameworkServlet
   - 如果web-WebApplicationContext是自建的,每个FrameworkServlet都拥有独立的spring容器.
   - 如果web-WebApplicationContext是注入的,每个FrameworkServlet公用同一个spring容器.
   - root-WebApplicationContext作为web-WebApplicationContext父容器,所有FrameworkServlet公用同一个root-WebApplicationContext容器.
   - 当然公用同一个parent-root-WebApplicationContext.
8. 通过xml配置spring容器时,一个spring容器可以由多个xml文件完成配置工作.
   - spring在解析配置文件时,将contextConfigLocation值的多个xml文件配置解析到同一个spring容器中.
   - 可以利用这原理,将一个大的xml文件拆分为多个功能独立的小的xml文件.
9. 默认请情况下,web/root-WebApplicationContext使用的都是XmlWebApplicationContext.
   - 用户也可以通过context-param/init-param分别单独指定子类,但是子类必须是ConfigurableWebApplicationContext.

#### parent-root-WebApplicationContext
The main reason to load a parent context here is to allow multiple root web application contexts to all be children of a shared EAR context, or alternately to also share the same parent context that is visible to EJBs. 
For pure web applications, there is usually no need to worry about having a parent context to the root web application context.



### DispatcherServlet
抽象类FrameworkServlet只定义了它会管理一个spring容器,再细化的逻辑是一点没有体现.
其子类DispatcherServlet完成了spring-mvc全部的主体逻辑.

#### DispatcherServlet初始化
DispatcherServlet的初始化逻辑是放在了其所管理的spring容器首次初始化完成时发生的,即重写了FrameworkServlet.onRefresh方法.
DispatcherServlet主要的初始化逻辑在方法initStrategies中.
initStrategies都做了哪些工作:
1. 从spring容器中获取MultipartResolver对象,并注入到DispatcherServlet属性中.
2. 从spring容器中获取LocaleResolver对象,并注入到DispatcherServlet属性中.
3. 从spring容器中获取ThemeResolver对象,并注入到DispatcherServlet属性中.
4. 从spring容器中获取HandlerMapping对象,并注入到DispatcherServlet属性中.
5. 从spring容器中获取HandlerAdapter对象,并注入到DispatcherServlet属性中.
6. 从spring容器中获取HandlerExceptionResolver对象,并注入到DispatcherServlet属性中.
7. 从spring容器中获取RequestToViewNameTranslator对象,并注入到DispatcherServlet属性中.
8. 从spring容器中获取ViewResolver对象,并注入到DispatcherServlet属性中.
9. 从spring容器中获取FlashMapManager对象,并注入到DispatcherServlet属性中.

注意:
DispatcherServlet在初始化过程中,从其管理的spring容器中获取所需的全部依赖对象(依赖对象的初始化由spring容器自动管理),主动注入到自己的属性中(并非放在WebApplicationContext容器中).初始化完成后,就可以脱离spring容器.
普通的spring容器的使用方式是等待spring容器为其注入所需的依赖对象,从而完成初始化过程;
DispatcherServlet初始化与普通的使用方式不同,并不是等待spring容器为其注入所需的依赖对象,而是主动从其管理的spring容器中查找合适的依赖对象并将其注入到自己的属性中,而从完成初始化.
一旦DispatcherServlet完成初始化后,DispatcherServlet就与可以脱离spring容器运行了(好的代码设计也应该是在spring容器完成bean的初始化后,bean所需的对象应该都在自己的属性中,不应该再从spring容器查找)



#### HandlerMapping
Return a handler and any interceptors for this request. The choice may be made on request URL, session state, or any factor the implementing class chooses.

#### HandlerAdapter
Interface that must be implemented for each handler type to handle a request. 
This interface is used to allow the DispatcherServlet to be indefinitely extensible. 
The DispatcherServlet accesses all installed handlers through this interface, meaning that it does not contain code specific to any handler type.


##### HandlerExecutionChain
1. DispatcherServlet.doDispatch方法中的变量mappedHandler(类型是HandlerExecutionChain)的名字太误导人了.老老实实用handlerExecutionChain比较好.
2. HandlerExecutionChain包含了本次请求所需的HandlerMethod和HandlerInterceptor.
3. 由DispatcherServlet先调用HandlerExecutionChain.applyPreHandle
4. 然后由HandlerAdapter.handle(HandlerExecutionChain.getHandler())来执行controller方法
5. 最后由DispatcherServlet调用HandlerExecutionChain.applyPostHandle
6. 其中,HandlerAdapter.handle返回值为null时,意味着请求的响应已经处理好.所以HandlerAdapter必要时需要具备将对象序列化为json字符串,这也是为什么RequestMappingHandlerAdapter包含HttpMessageConverter(用于将对象转换为http响应)


##### RequestMappingHandlerMapping
RequestMappingHandlerMapping在初始化阶段会遍历spring容器,基于@RequestMapping注解收集的metadata.在请求到来时,生成合适的HandlerExecutionChain用来处理请求.

RequestMappingHandlerMapping在初始化时,从容器收集metadata数据.见如下源码:
```java
class AbstractHandlerMethodMapping {
   public void afterPropertiesSet() {
      initHandlerMethods();
   }
    // ...
    protected void initHandlerMethods() {
        // 所有类型都是candidate
        String[] beanNames = (this.detectHandlerMethodsInAncestorContexts ?
                BeanFactoryUtils.beanNamesForTypeIncludingAncestors(obtainApplicationContext(), Object.class) :
                obtainApplicationContext().getBeanNamesForType(Object.class));
        // 基于beanName逐个遍历
        for (String beanName : beanNames) {
            if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
                Class<?> beanType = obtainApplicationContext().getType(beanName);
                if (beanType != null && isHandler(beanType)) {
                    detectHandlerMethods(beanName);
                }
            }
        }
        handlerMethodsInitialized(getHandlerMethods());
    }

   protected void detectHandlerMethods(final Object handler) {
      Class<?> handlerType = (handler instanceof String ? obtainApplicationContext().getType((String) handler) : handler.getClass());
      if (handlerType != null) {
         // ...
         methods.forEach((method, mapping) -> {
             // 在注册时,用的时controller的beanName
            registerHandlerMethod(handler, invocableMethod, mapping);
         });
      }
   }
}

class AbstractHandlerMethodMapping {
    // ...
   protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
      String lookupPath = getUrlPathHelper().getLookupPathForRequest(request);
      this.mappingRegistry.acquireReadLock();
      HandlerMethod handlerMethod = lookupHandlerMethod(lookupPath, request);
      // createWithResolvedBean会从spring容器中基于beanName获取对象
      return (handlerMethod != null ? handlerMethod.createWithResolvedBean() : null);
   }
}
```

##### 同时存在多个DispatcherServlet
servlet容器中是可以有多个FrameworkServlet,即在web.xml中配置多个DispatcherServlet.

请求究竟由哪个DispatcherServlet来处理:
1. 当在web.xml中配置多个DispatcherServlet时,需要通过url的prefix来做路由.
2. 此时prefix肯定不是空字符串,此时请求的pathInfo在剔除prefix后的值才是待映射到的RequestMapping.
3. 当单个dispatcher时,urlPattern通常是/*,等价于prefix为空,当多dispatcher时,就需要注意了.
4. 具体参考调用链路:
   org.springframework.web.servlet.DispatcherServlet.getHandler  
   org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandler  
   org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal  
   org.springframework.web.util.UrlPathHelper.getLookupPathForRequest  
   org.springframework.web.util.UrlPathHelper.getPathWithinServletMapping  



## @EnableWebMvc
通过注解@EnableWebMvc启动webMvc的方式.该注解会为mvc提供必要的基础类.
主要的具体工作由WebMvcConfigurationSupport来完成.

具体的工作原理如下面的代码片段
```java
@Import(DelegatingWebMvcConfiguration.class)
public @interface EnableWebMvc{}

@Configuration(proxyBeanMethods = false)
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {}
```

其中WebMvcConfigurationSupport提供了如下类:
1. RequestMappingHandlerMapping(基于请求uri找到对应的HandlerMethod,然后与对应的HandlerInterceptor构建出HandlerExecutionChain)
2. RequestMappingHandlerAdapter(内置以及基于classpath生成必要的HttpMessageConverter)  
    public BeanNameUrlHandlerMapping beanNameHandlerMapping() {}
3. 还有其他必须的类(比如:SimpleControllerHandlerAdapter/SimpleUrlHandlerMapping/BeanNameUrlHandlerMapping/FormattingConversionService/...)







### 辅助类

#### HttpServletBean
Simple extension of HttpServlet which treats its config parameters (init-param entries within the servlet tag in web.xml) as bean properties.




注意:
1. 如果controller被同时添加@Controller和@Async时.
   - RequestMappingHandlerMapping正常收集metadata时,HandlerMethod.beanType是proxy类
   - RequestMappingHandlerMapping在处理请求时,HandlerMethod.bean是proxy实例
   - aop作用发生在spring容器生成bean时,就已经是proxy类实例了.