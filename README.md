# rain-java

杨桐的通用开发脚手架——后端代码（Java版本）



## 1 项目基础架构

* 版本和依赖控制

  Maven 3.9.6，层级如下

  ```css
  your-project/
  ├── pom.xml
  ├── src/
  │   ├── main/
  │   │   ├── java/
  │   │   │   └── com/
  │   │   │       └── yourcompany/
  │   │   │           └── yourapp/
  │   │   └── resources/
  │   └── test/
  │       ├── java/
  │       │   └── com/
  │       │       └── yourcompany/
  │       │           └── yourapp/
  │       └── resources/
  
  ```



* JDK21

  至少要使用JDK17及以上的版本



* Spring-Boot 3.4.4

  截止2025-04-04，最新的SpringBoot稳定版本

  在父POM中引入以下依赖，子工程就可以直接使用

  ```xml
  <dependencyManagement>
      <dependencies>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-dependencies</artifactId>
              <version>3.4.4</version>
              <type>pom</type>
              <scope>import</scope>
          </dependency>
      </dependencies>
  </dependencyManagement>
  ```



* MyBatis-Plus

  https://baomidou.com/introduce/

  数据库操作库，基于MyBatis

  ```xml
  <!--仅适用于SpringBoot3版本的MyBatis-Plus-->
  <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
      <version>3.5.11</version>
  </dependency>
  ```



* 数据库驱动依赖（MySQL）

  ```xml
  <dependency>
  	<groupId>com.mysql</groupId>
  	<artifactId>mysql-connector-j</artifactId>
  	<version>8.3.0</version>
  </dependency>
  ```

  

* 缓存库依赖

  Redis

  ```xml
  <!--由于使用了SpringBoot的版本控制，所以这里无需指定版本-->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>
  ```

  caffeine，基于JVM内存的缓存库

  ```xml
  <dependency>
      <groupId>com.github.ben-manes.caffeine</groupId>
      <artifactId>caffeine</artifactId>
      <version>3.0.3</version>
  </dependency>
  ```



* 工具库

  Guava

  ```xml
  <dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>33.4.6-jre</version>
  </dependency>
  ```



* 单元测试

  junit

  ```xml
  <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <scope>test</scope>
  </dependency>
  ```

  

* 接口文档

  使用SpringDoc

  ```xml
  <dependency>
      <groupId>com.github.xiaoymin</groupId>
      <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
      <version>4.4.0</version>
  </dependency>
  ```



* OFFICE操作库

  POI，可能跟其他库比如easyexcel，poi-tl等产生版本冲突

  ```xml
  <!--poi相关依赖-->
  <!--处理03版本office-->
  <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi</artifactId>
      <version>5.2.5</version>
  </dependency>
  <!--处理07版本office-->
  <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId>
      <version>5.2.5</version>
      <exclusions>
          <exclusion>
              <groupId>org.apache.commons</groupId>
              <artifactId>commons-compress</artifactId>
          </exclusion>
      </exclusions>
  </dependency>
  <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-compress</artifactId>
      <version>1.26.0</version>
  </dependency>
  <!--处理数据量大的office依赖-->
  <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml-schemas</artifactId>
      <version>4.1.2</version>
  </dependency>
  ```

  

  为了方便版本控制，以上所有依赖版本最好在父POM中的`properties`标签里面统一管理

  父POM中的其他内容如下：

  ```xml
  <!--构建项目的行为-->
  <build>
      <plugins>
          <plugin>
              <!--maven编译插件-->
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-compiler-plugin</artifactId>
              <version>3.13.0</version>
              <configuration>
                  <!--源代码的jdk版本-->
                  <source>${java.version}</source>
                  <!--需要生成的目标class文件的编译版本-->
                  <target>${java.version}</target>
                  <!--使用release代替source和target-->
                  <release>${java.version}</release>
                  <!--字符集编码-->
                  <encoding>${project.build.sourceEncoding}</encoding>
                  <!--跳过测试-->
                  <skip>true</skip>
                  <!--是否运行一个独立的进程来执行编译任务-->
                  <fork>true</fork>
                  <compilerArgs>
                      <arg>-parameters</arg>
                  </compilerArgs>
              </configuration>
          </plugin>
          <!--该插件可以使打包扫描文件时，指定后缀名的文件不会被扫描，也就不会被损坏-->
          <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-resources-plugin</artifactId>
              <version>3.3.1</version>
              <configuration>
                  <encoding>${project.build.sourceEncoding}</encoding>
                  <nonFilteredFileExtensions>
                      <nonFilteredFileExtension>xlsx</nonFilteredFileExtension>
                      <nonFilteredFileExtension>xls</nonFilteredFileExtension>
                      <nonFilteredFileExtension>doc</nonFilteredFileExtension>
                      <nonFilteredFileExtension>docx</nonFilteredFileExtension>
                      <nonFilteredFileExtension>jpg</nonFilteredFileExtension>
                      <nonFilteredFileExtension>png</nonFilteredFileExtension>
                  </nonFilteredFileExtensions>
              </configuration>
          </plugin>
          <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-surefire-plugin</artifactId>
              <version>3.2.5</version>
              <configuration>
                  <skip>true</skip>
              </configuration>
          </plugin>
      </plugins>
      <!--打包时对不同目录下的文件处理方式-->
      <resources>
          <resource>
              <!--指定目录-->
              <directory>src/main/java</directory>
              <!--includes表示打包时除了默认的文件，还应该额外包含的文件-->
              <includes>
                  <include>**/*.xml</include>
              </includes>
              <!--excludes表示打包时应该排除的文件-->
              <!--<excludes></excludes>-->
              <!--filtering表示该目录下的文件，是否可以使用maven的profiles定义的变量-->
              <filtering>false</filtering>
          </resource>
          <resource>
              <directory>src/main/resources</directory>
              <includes>
                  <!--打包时应包含该目录下的所有文件-->
                  <include>**/*.*</include>
              </includes>
              <!--打包时会扫描该目录下的文件，将文件中的占位符@xxx@替换为profiles中配置的环境变量-->
              <!--扫描excel等文件时，可能造成文件损坏，而这些文件也不需要被扫描，所以需要配置额外的插件排除不需要扫描的文件，如上-->
              <filtering>true</filtering>
          </resource>
      </resources>
  </build>
  <!--环境配置, profile标签里面可以填pom文件中的所有内容，方便对不同环境做定制化处理-->
  <profiles>
      <!--开发环境-->
      <profile>
          <id>dev</id>
          <!--激活该环境的条件-->
          <activation>
              <!--默认环境-->
              <activeByDefault>true</activeByDefault>
          </activation>
          <!--这里配置的变量，能够在spring的配置文件中引用-->
          <properties>
              <!--当前环境-->
              <profile.env>dev</profile.env>
          </properties>
      </profile>
      <!--测试环境-->
      <profile>
          <id>test</id>
          <!--这里配置的变量，能够在spring的配置文件中引用-->
          <properties>
              <!--当前环境-->
              <profile.env>test</profile.env>
          </properties>
      </profile>
      <!--生产环境-->
      <profile>
          <id>prod</id>
          <!--这里配置的变量，能够在spring的配置文件中引用-->
          <properties>
              <!--当前环境-->
              <profile.env>prod</profile.env>
          </properties>
      </profile>
  </profiles>
  ```

  

## 2 代码整体结构

项目整体结构

```css
parent-project/
├── common/ 	#基础模块，包含工具栏，基础依赖等不含有web配置的代码，该模块在任何环境下都可单独使用
├── core/ 		#核心模块，包含SpringBoot项目的配置，只能在SpringBoot环境下使用
├── system/ 	#业务模块，使用者可以将自己的业务代码写在该模块
```

以下是几个子模块的介绍：

* common基础模块结构

  ```css
  common/
  ├── anno/ 		#通用注解
  ├── convert/ 	#类型转换器组件
  ├── enums/ 		#通用枚举
  ├── exception/ 	#异常相关
  ├── lock/ 		#锁相关
  ├── model/ 		#通用类
  ├── tree/ 		#树形操作组件
  ├── util/ 		#工具类
  ```

  

* core核心模块结构



* system业务模块结构





## 3 权限系统设计



## 4 数据权限设计



## 5 前后端交互设计