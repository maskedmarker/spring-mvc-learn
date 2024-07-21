# 关于war包的知识

## war的常用结构
hello.war
    |_ccs
    |_html
    |_image
    |_js
    |_WEB-INF
        |_jsp(或者view)
        |_web.xml
    |_index.html(或者index.jsp)

注意:
1.一定要有index文件,用来作为welcome-page,因为有些用户只输入htt://www.xxx.com,后面不带具体的path,此时welcom-page就有用处了.
2.需要保护的资源需要放到WEB-INF目录下面;对于静态资源可以放到根目录下面(即WEB-INF目录外面)


## maven插件制作war包
可以通过mvn war:help -Ddetail -Dgoal=war命令获取到关于maven-war-plugin插件对于生成war包的各种信息.
其中:
0.war包的名称以finalName为准
1.war:war自动生成标准所需的META-INF和WEB-INF目录
2.war:war会将webapp目录的内容copy到war包中


## servlet API的核心概念

###

## todo
1.为什么抛异常和主动设置500,没有渲染error.html