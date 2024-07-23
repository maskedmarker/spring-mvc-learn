# 与war包相关的maven插件

通过右键Maven-Show Effective Pom来查看当前项目的有效pom配置信息

普通的web项目包含如下插件:
maven-clean-plugin
maven-resources-plugin
maven-war-plugin
maven-compiler-plugin
maven-surefire-plugin(调用单元测试)
maven-install-plugin
maven-deploy-plugin
maven-site-plugin

当我们仅仅打包时,我们重点关注:
maven-resources-plugin
maven-compiler-plugin
maven-war-plugin

maven执行过程中,如上插件依次被调用.
maven-resources-plugin先是将src/main/resources拷贝到target/classes/
maven-compiler-plugin再将src/main/java编译后的class放到target/classes/
maven-war-plugin再将webapp拷贝到target/finalName/,然后基于target/finalName生成war包




maven预设了,根据不同packaging类型,为不同的lifecycle phrase绑定指定的plugin goal.
配置来自于 ${MAVEN_HOME}/lib/maven-core-3.2.3.jar/META-INF/plex/components.xml
```
    <component>
      <role>org.apache.maven.lifecycle.mapping.LifecycleMapping</role>
      <role-hint>war</role-hint>
      <implementation>org.apache.maven.lifecycle.mapping.DefaultLifecycleMapping</implementation>
      <configuration>
        <lifecycles>
          <lifecycle>
            <id>default</id>
            <phases>
              <process-resources>
                org.apache.maven.plugins:maven-resources-plugin:2.6:resources
              </process-resources>
              <compile>
                org.apache.maven.plugins:maven-compiler-plugin:3.1:compile
              </compile>
              <process-test-resources>
                org.apache.maven.plugins:maven-resources-plugin:2.6:testResources
              </process-test-resources>
              <test-compile>
                org.apache.maven.plugins:maven-compiler-plugin:3.1:testCompile
              </test-compile>
              <test>
                org.apache.maven.plugins:maven-surefire-plugin:2.12.4:test
              </test>
              <package>
                org.apache.maven.plugins:maven-war-plugin:2.2:war
              </package>
              <install>
                org.apache.maven.plugins:maven-install-plugin:2.4:install
              </install>
              <deploy>
                org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy
              </deploy>
            </phases>
          </lifecycle>
        </lifecycles>
      </configuration>
    </component>
```

