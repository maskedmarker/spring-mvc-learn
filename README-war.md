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
1.一定要有index文件,用来作为welcome-page,因为有些用户只输入htt://www.xxx.com,后面不带具体的path,此时welcome-page就有用处了.
2.需要保护的资源需要放到WEB-INF目录下面;对于静态资源可以放到根目录下面(即WEB-INF目录外面)


## maven插件制作war包
可以通过mvn war:help -Ddetail -Dgoal=war命令获取到关于maven-war-plugin插件对于生成war包的各种信息.
其中:
0.war包的名称以finalName为准
1.war:war自动生成标准所需的META-INF和WEB-INF目录
2.war:war会将webapp目录(即warSourceDirectory)的内容copy到war包的根目录
3.默认情况下,对于webapp目录下的空文件夹,是不会被copy的,只能通过添加无用的展位文件来完成.
4.既是通过配置maven-resources-plugin/maven-war-plugin也不行无法copy空文件夹.
5.如果不嫌麻烦,可以通过maven-assembly-plugin来完成.


## servlet API的核心概念

###

## todo
1.为什么抛异常和主动设置500,没有渲染error.html