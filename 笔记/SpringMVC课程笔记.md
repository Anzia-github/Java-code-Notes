# SpringMVC课程笔记

## 回顾MVC

### 什么是MVC

- MVC是模型（Model）、视图（View）、控制器（Controller）的简写，是一种软件设计规范
- 是将业务逻辑、数据、显示分离的方法来组织代码
- MVC主要作用是降低了视图与业务逻辑间的双向耦合
- MVC不是一种设计模式。MVC是一种架构模式。

**Model（模型）**：数据模型，提供要展示的数据，因此包含数据和行为，可以认为是领域模型或JavaBean组件（包含数据和行为），不过现在一般都分离开来：Value Object（数据Dao）和服务层（行为Service）。也就是模型提供了模型数据查询和模型数据的状态更新等功能，包括数据和业务。

**View（视图）**：负责进行模型的展示，一般就是我们看到的用户界面，客户想看到的东西

**Controller（控制器）**：接收用户请求，委托给模型进行处理（状态改变），处理完毕后把返回的模型数据返回给视图，有视图负责展示，也就是说控制器做了个调度员的工作

**最经典的MVC就是JSP+Servlet+JavaBean的模式**

![在这里插入图片描述](SpringMVC课程笔记.assets\20200719143108784.png)

### Model1时代

- 在web早期的开发中，通常采用的都是Model1
- 在Model1中，主要分为两层，视图层和模型层
  ![在这里插入图片描述](SpringMVC课程笔记.assets\20200719143127605.png)

Model1有点：架构简单，比较适合小型项目开发

Model1缺点：JSP职责不单一，职责过重，不便于维护

### Model2时代

Model2把一个项目分为三部分，包括视图、控制、模型

![在这里插入图片描述](SpringMVC课程笔记.assets\20200719143141122.png)

1. 用户发请求
2. Servlet接收请求数据，并调用对应的业务逻辑方法
3. 业务处理完毕，返回更新后的数据给servlet
4. servlet转向JSP，由JSP来渲染页面
5. 响应给前端更新后的页面

职责分析：

**Controller：控制器**

1. 获得表单数据
2. 调用业务逻辑
3. 转向指定的页面

**Model：模型**

1. 业务逻辑
2. 保存数据的状态

**View：视图**

1. 显示页面

Model2这样不仅提高了代码的复用率与项目的拓展性，且大大降低了项目的维护成本。Model1模式的实现比较简单，适用于快速开发小规模项目，Model1中JSP页面兼View和Controller两种角色，将控制逻辑和表现逻辑混杂在一起，从而导致代码的重用性非常低，增加了应用的扩展性和维护的难度。Model2消除了Model1的缺点

### 回顾Servlet

1. 新建一个Maven工程当父工程！pom依赖！

   ~~~xml
   <dependencies>
      <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.12</version>
      </dependency>
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-webmvc</artifactId>
          <version>5.1.9.RELEASE</version>
      </dependency>
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>servlet-api</artifactId>
          <version>2.5</version>
      </dependency>
      <dependency>
          <groupId>javax.servlet.jsp</groupId>
          <artifactId>jsp-api</artifactId>
          <version>2.2</version>
      </dependency>
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>jstl</artifactId>
          <version>1.2</version>
      </dependency>
   </dependencies>
   ~~~

2. 建立一个Moudle：springmvc-01-servlet，添加Web app的支持！

3. 导入servlet和jsp的jar依赖

   ~~~xml
   <dependencies>
      <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.12</version>
      </dependency>
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-webmvc</artifactId>
          <version>5.1.9.RELEASE</version>
      </dependency>
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>servlet-api</artifactId>
          <version>2.5</version>
      </dependency>
      <dependency>
          <groupId>javax.servlet.jsp</groupId>
          <artifactId>jsp-api</artifactId>
          <version>2.2</version>
      </dependency>
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>jstl</artifactId>
          <version>1.2</version>
      </dependency>
   </dependencies>
   ~~~

4. 编写一个Servlet类，用来处理用户的请求

   ~~~java
   package nuc.ss.servlet;
   
   //实现Servlet接口
   public class HelloServlet extends HttpServlet {
      @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          //取得参数
          String method = req.getParameter("method");
          if (method.equals("add")){
              req.getSession().setAttribute("msg","执行了add方法");
         }
          if (method.equals("delete")){
              req.getSession().setAttribute("msg","执行了delete方法");
         }
          //业务逻辑
          //视图跳转
          req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req,resp);
     }
   
      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          doGet(req,resp);
     }
   }
   ~~~

5. 编写Hello.jsp，在WEB-INF目录下新建一个jsp的文件夹，新建hello.jsp

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
      <title>Kuangshen</title>
   </head>
   <body>
   ${msg}
   </body>
   </html>
   ~~~

6. 在web.xml中注册Servlet

   ~~~xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
      <servlet>
          <servlet-name>HelloServlet</servlet-name>
          <servlet-class>com.kuang.servlet.HelloServlet</servlet-class>
      </servlet>
      <servlet-mapping>
          <servlet-name>HelloServlet</servlet-name>
          <url-pattern>/user</url-pattern>
      </servlet-mapping>
   
   </web-app>
   ~~~

7. 配置Tomcat，并启动测试

   1. localhost:8080/user?method=add
   2. localhost:8080/user?method=delete

MVC框架要做哪些事情

1. 将url映射到Java类或Java类的方法
2. 封装用户提交的数据
3. 处理请求-调用相关的业务处理-封装响应数据
4. 将响应的数据进行渲染 .jsp/html等表示层数据

## 什么是SpringMVC

### 概述

==SpringMVC是Spring Framework的一部分，是基于Java实现MVC的轻量级web框架==

官方文档：https://docs.spring.io/spring/docs/5.2.0.RELEASE/spring-framework-reference/web.html#spring-web

SpringMVC的特点：

1. 轻量级，简单易学
2. 高效，基于请求响应的MVC框架
3. 与Spring兼容性好，无缝结合
4. 约定优于配置
5. 功能强大：restful、数据校验、格式化、本地化、主题等
6. 简洁灵活

Spring的web框架围绕DispatcherServlet【调度Servlet】设计

DispatcherServlet的作用是将请求分发到不同的处理器。从Spring2.5开始，使用Java 5或者以上版本的用户可以采用基于注解形式开发，十分简洁

### 中心控制器

- Spring的web框架围绕DispatcherServlet设计。DispatcherServlet的作用是将请求分发到不同处理器
  ![在这里插入图片描述](C:\Users\Code\Documents\markdown\SpringMVC课程笔记.assets\20200719143231995.png)

SpringMVC框架像许多其他MVC框架一样，以请求为驱动，围绕一个中心Servlet分派请求及提供其他功能，DispatcherServlet是一个实际的Servlet（它继承自HttpServlet基类）

- SpringMVC的原理如下图所示：
  当发起请求时被前置的控制器拦截到请求，根据请求参数生成代理请求，找到请求对应的实际控制器，控制器处理请求，创建数据模型，访问数据库，将模型响应给中心控制器，控制器使用模型与视图渲染视图结果，将结果返回给中心控制器，再将结果返回给请求者
  ![在这里插入图片描述](SpringMVC课程笔记.assets\20200719143322753.png)

  ![在这里插入图片描述](SpringMVC课程笔记.assets\20200719143342368.png)

### SpringMVC执行原理

![在这里插入图片描述](SpringMVC课程笔记.assets\2020071914335890.png)

执行流程

1. DispatcherServlet表示前置控制器，是整个SpringMVC的控制中心。用户发出请求，DispatcherServlet接收请求并拦截请求。
   - 我们假设请求的url为 : http://localhost:8080/SpringMVC/hello
   - **如上url拆分成三部分：**
   - http://localhost:8080 ------> 服务器域名
   - SpringMVC ------> 部署在服务器上的web站点
   - hello ------> 表示控制器
   - 通过分析，如上url表示为：请求位于服务器localhost:8080上的SpringMVC站点的hello控制器。
2. HandlerMapping为处理器映射。DispatcherServlet调用HandlerMapping,HandlerMapping根据请求url查找Handler。
3. HandlerExecution表示具体的Handler,其主要作用是根据url查找控制器，如上url被查找控制器为：hello。
4. HandlerExecution将解析后的信息传递给DispatcherServlet,如解析控制器映射等。
5. HandlerAdapter表示处理器适配器，其按照特定的规则去执行Handler。
6. Handler让具体的Controller执行。
7. Controller将具体的执行信息返回给HandlerAdapter,如ModelAndView。
8. HandlerAdapter将视图逻辑名或模型传递给DispatcherServlet。
9. DispatcherServlet调用视图解析器(ViewResolver)来解析HandlerAdapter传递的逻辑视图名。
10. 视图解析器将解析的逻辑视图名传给DispatcherServlet。
11. DispatcherServlet根据视图解析器解析的视图结果，调用具体的视图。
12. 最终视图呈现给用户。

## 第一个MVC程序

### 配置版

1. 新建一个Moudle、springmvc-02-hello，添加web的支持！

2. 确定导入了SpringMVC的依赖

3. 配置web.xml，注册DispatcherServlet

   ~~~xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
   
      <!--1.注册DispatcherServlet-->
      <servlet>
          <servlet-name>springmvc</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <!--关联一个springmvc的配置文件:【servlet-name】-servlet.xml-->
          <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:springmvc-servlet.xml</param-value>
          </init-param>
          <!--启动级别-1-->
          <load-on-startup>1</load-on-startup>
      </servlet>
   
      <!--/ 匹配所有的请求；（不包括.jsp）-->
      <!--/* 匹配所有的请求；（包括.jsp）-->
      <servlet-mapping>
          <servlet-name>springmvc</servlet-name>
          <url-pattern>/</url-pattern>
      </servlet-mapping>
   
   </web-app>
   ~~~

4. 编写SpringMVC配置文件！名称：springmvc-servlet.xml

   ~~~xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
   
   </beans>
   ~~~

5. 添加处理映射器（可省略）

   ~~~xml
   <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
   ~~~

6. 添加处理器适配器（可省略）

   ~~~xml
   <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>
   ~~~

7. 添加视图解析器

   ~~~xml
   <!--视图解析器:DispatcherServlet给他的ModelAndView-->
   <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="InternalResourceViewResolver">
      <!--前缀-->
      <property name="prefix" value="/WEB-INF/jsp/"/>
      <!--后缀-->
      <property name="suffix" value=".jsp"/>
   </bean>
   ~~~

8. 编写我们要操作业务Controller，要么实现Controller接口，要么增加注解；需要返回一个ModelAndView，装数据，封视图

   ~~~java
   package nuc.ss.controller;
   
   import org.springframework.web.servlet.ModelAndView;
   import org.springframework.web.servlet.mvc.Controller;
   
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   //注意：这里我们先导入Controller接口
   public class HelloController implements Controller {
   
      public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
          //ModelAndView 模型和视图
          ModelAndView mv = new ModelAndView();
   
          //封装对象，放在ModelAndView中。Model
          mv.addObject("msg","HelloSpringMVC!");
          //封装要跳转的视图，放在ModelAndView中
          mv.setViewName("hello"); //: /WEB-INF/jsp/hello.jsp
          return mv;
     }
   }
   ~~~

9. 将自己的类交给SpringIOC容器，注册bean

   ~~~xml
   <!--Handler-->
   <bean id="/hello" class="nuc.ss.controller.HelloController"/>
   ~~~

10. 写要跳转的jsp页面，显示ModelAndVIew存放的数据，以及我们的正常页面

    ~~~jsp
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
       <title>Kuangshen</title>
    </head>
    <body>
    ${msg}
    </body>
    </html>
    ~~~

11. 配置Tomcat启动测试！

可能遇到的问题：访问出现404，排查步骤：

1. 查看控制台输出，看一下是不是缺少了什么jar包
2. 如果jar包存在，显示无法输出，就在IDEA的项目发布中，添加lib依赖
3. 重启Tomcat即可解决！

### 注解版

1. 新建一个Moudle，springmvc-03-annotation。添加web支持

2. 由于maven可能存在资源过滤的情况，我们将配置完善

   ~~~xml
   <build>
      <resources>
          <resource>
              <directory>src/main/java</directory>
              <includes>
                  <include>**/*.properties</include>
                  <include>**/*.xml</include>
              </includes>
              <filtering>false</filtering>
          </resource>
          <resource>
              <directory>src/main/resources</directory>
              <includes>
                  <include>**/*.properties</include>
                  <include>**/*.xml</include>
              </includes>
              <filtering>false</filtering>
          </resource>
      </resources>
   </build>
   ~~~

3. 在pom.xml文件引入相关的依赖：主要由Spring框架核心库、SpringMVC、servlet、JSTL等

4. 配置web.xml

   ~~~xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
   
      <!--1.注册servlet-->
      <servlet>
          <servlet-name>SpringMVC</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <!--通过初始化参数指定SpringMVC配置文件的位置，进行关联-->
          <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:springmvc-servlet.xml</param-value>
          </init-param>
          <!-- 启动顺序，数字越小，启动越早 -->
          <load-on-startup>1</load-on-startup>
      </servlet>
   
      <!--所有请求都会被springmvc拦截 -->
      <servlet-mapping>
          <servlet-name>SpringMVC</servlet-name>
          <url-pattern>/</url-pattern>
      </servlet-mapping>
   
   </web-app>
   ~~~

5. / 和 /* 的区别

   1. <url-pattern>/</url-pattern>不会匹配到 .jsp，只针对我们编写的请求；即 .jsp不会进入spring的DispatcherServlet类
   2. <url-pattern>/*</url-pattern>会匹配到 .jsp，会出现返回jsp视图时再次进入spring的DispatcherServlet类，导致找不到对应的controller所以报404错

6. 添加SpringMVC配置文件

   在resource目录下添加springmvc-servlet.xml配置文件，配置的形式与Spring容器配置基本类似，为了支持基于注解的IOC，设置了自动扫描包的功能，具体配置信息如下：

   ~~~xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/mvc
          https://www.springframework.org/schema/mvc/spring-mvc.xsd">
   
      <!-- 自动扫描包，让指定包下的注解生效,由IOC容器统一管理 -->
      <context:component-scan base-package="nuc.ss.controller"/>
      <!-- 让Spring MVC不处理静态资源 -->
      <mvc:default-servlet-handler />
      <!--
      支持mvc注解驱动
          在spring中一般采用@RequestMapping注解来完成映射关系
          要想使@RequestMapping注解生效
          必须向上下文中注册DefaultAnnotationHandlerMapping
          和一个AnnotationMethodHandlerAdapter实例
          这两个实例分别在类级别和方法级别处理。
          而annotation-driven配置帮助我们自动完成上述两个实例的注入。
       -->
      <mvc:annotation-driven />
   
      <!-- 视图解析器 -->
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
            id="internalResourceViewResolver">
          <!-- 前缀 -->
          <property name="prefix" value="/WEB-INF/jsp/" />
          <!-- 后缀 -->
          <property name="suffix" value=".jsp" />
      </bean>
   
   </beans>
   ~~~

   在视图解析器中我们把所有的视图都存放在/WEB-INF/目录下，这样可以保证视图安全，因为这个目录下的文件，客户端不能直接访问

   - 让IOC的注解生效
   - 静态资源过滤：HTML、Js、CSS、图片、视频……
     - MVC的注解驱动
     - 配置视图解析器

7. 创建Controller
   编写一个Java控制类：com.kuang.controller.HelloController

   ~~~java
   package nuc.ss.controller;
   
   import org.springframework.stereotype.Controller;
   import org.springframework.ui.Model;
   import org.springframework.web.bind.annotation.RequestMapping;
   
   @Controller
   @RequestMapping("/HelloController")
   public class HelloController {
   
      //真实访问地址 : 项目名/HelloController/hello
      @RequestMapping("/hello")
      public String sayHello(Model model){
          //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
          model.addAttribute("msg","hello,SpringMVC");
          //web-inf/jsp/hello.jsp
          return "hello";
     }
   }
   ~~~

   1. @Controller是为了让SpringIOC容器初始化时自动扫描到
   2. @RequestMapping是为了映射请求路径，这里因为类与方法上都有映射所以访问时应该是/HelloController/hello
   3. 方法中声明Model类型的参数是为了把Action中的数据带到视图中
   4. 方法返回的结果是视图的名称hello，加上配置文件中的前后缀变成WEB-INF/jsp/hello.jsp

8. 创建视图层

   在WEB-INF/jsp目录中传教hello.jsp，视图可以直接取出并展示从Controller带回的信息；可以通过EL表达式取出Model中存放的值，或者对象

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
      <title>SpringMVC</title>
   </head>
   <body>
   ${msg}
   </body>
   </html>
   ~~~

9. 配置Tomcat运行

小结：

1. 新建一个web项目
2. 导入相关jar包
3. 编写web.xml，注册DospatcherServlet
4. 编写springmvc配置文件
5. 接下来就是去创建对应的控制器，controller
6. 最后完善前端视图和controller之间的对应
7. 测试运行调试

使用springmvc必须配置的三大件

处理器映射器、处理器适配器、视图解析器

通常，我们只需要手动配置视图解析器，而处理器映射器和处理器适配器只需要开启注解驱动即可，而省去了大段的xml配置

## RestFul和控制器

### 控制器Controller

- 控制器复杂提供访问应用程序的行为，通常通过接口定义或注解定义两种方法实现
- 控制器负责解析用户的请求并将其转换为一个模型
- 在springmvc中一个控制器类可以包含多个方法
- 在springmvc中，对于Controller的配置方法有很多种

### 实现Controller接口

Controller是一个接口，在org.springframework.web.servlet.mvc包下，接口中只有一个方法

~~~java
//实现该接口的类获得控制器功能
public interface Controller {
   //处理请求且返回一个模型与视图对象
   ModelAndView handleRequest(HttpServletRequest var1, HttpServletResponse var2) throws Exception;
}
~~~

测试

1. 新建一个Moudel，springmvc-04-controller

2. mvc的配置文件只留下视图解析器

3. 编写一个Controller类，ControllerTest1

   ~~~java
   //定义控制器
   //注意点：不要导错包，实现Controller接口，重写方法；
   public class ControllerTest1 implements Controller {
   
      public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
          //返回一个模型视图对象
          ModelAndView mv = new ModelAndView();
          mv.addObject("msg","Test1Controller");
          mv.setViewName("test");
          return mv;
     }
   }
   ~~~

4. 编写完毕后，去Spring配置文件中注册请求的bean；name对应请求路径，class对应处理请求的类

   ~~~xml
   <bean name="/t1" class="nuc.ss.controller.ControllerTest1"/>
   ~~~

5. 编写前端test.jsp，注意在WEB-INF/jsp目录下编写，对应我们的视图解析器

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
      <title>Kuangshen</title>
   </head>
   <body>
   ${msg}
   </body>
   </html>
   ~~~

6. 配置Tomcat运行环境

说明：

- 实现接口Controller定义控制器是较老的方法
- 缺点是：一个控制器中只有一个方法，如果多个方法则需要定义多个Controller；定义的方式比较麻烦

**使用注解@Controller**

- @Controller注解类型用于声明Spring类的实例时一个控制器

- Spring可以使用扫描机制来找到应用程序中所有基于注解的控制器类，为了保证Spring能找到你的控制器，需要在配置文件中声明组件扫描

  ~~~xml
  <!-- 自动扫描指定的包，下面所有注解类交给IOC容器管理 -->
  <context:component-scan base-package="nuc.ss.controller"/>
  ~~~

- 增加一个ControllerTest2类，使用注解实现

  ~~~java
  //@Controller注解的类会自动添加到Spring上下文中
  @Controller
  public class ControllerTest2{
  
     //映射访问路径
     @RequestMapping("/t2")
     public String index(Model model){
         //Spring MVC会自动实例化一个Model对象用于向视图中传值
         model.addAttribute("msg", "ControllerTest2");
         //返回视图位置
         return "test";
    }
  }
  ~~~

- 运行tomcat测试

可以发现，我们的两个请求都可以指向一个视图（test），但是页面显示的结果是不一样的，从这里可以看出视图是被复用的，而控制器与视图之间是弱耦合关系

注解方式是平时使用的最多的方式

**RequestMapping**

@RequestMapping

- @RequestMapping注解用于映射url到控制器类或一个特定的处理程序方法。可用于类或方法上。用于方法上，表示类中的所有响应请求的方法都是以该地址作为父路径

- 为了测试结论更加准确，我们可以加上一个项目名测试myweb

- 只注解在方法上面

  ~~~java
  @Controller
  public class TestController {
     @RequestMapping("/h1")
     public String test(){
         return "test";
    }
  }
  ~~~

  访问路径：http://localhost:8080/项目名/h1

- 同时注解类与方法

  ~~~java
  @Controller
  @RequestMapping("/admin")
  public class TestController {
     @RequestMapping("/h1")
     public String test(){
         return "test";
    }
  }
  ~~~

  访问路径：http://localhost:8080/项目名/admin/h1，需要先指定类的路径再指定方法的路径

**RestFul风格**

概念

Restful就是一个资源定位及资源操作的风格。不是标准也不是协议，只是一个风格。基于这个风格设计的软件可以更简洁，更有层次，更易于实现缓存等机制

功能

资源：互联网所有的事物都可以被抽象为资源

资源操作：使用POST/DELETE/PUT/GET，使用不同方法对资源进行操作

分别对应 添加、删除、修改、查询

传统方式操作资源：通过不同的参数来实现不同的效果，方法单一

使用restful操作资源：可以通过不同的请求方式来实现不同的效果。请求地址一样，但是功能可以不同

测试：

1. 在新建一个类RestFulController

   ~~~java
   @Controller
   public class RestFulController {}
   ~~~

2. 在SpringMVC中可以使用@PathVariable注解，让方法参数的值对应绑定到一个URI模板变量上

   ~~~java
   @Controller
   public class RestFulController {
   
      //映射访问路径
      @RequestMapping("/add/{p1}/{p2}")
      public String index(@PathVariable int p1, @PathVariable int p2, Model model){
          
          int result = p1+p2;
          //Spring MVC会自动实例化一个Model对象用于向视图中传值
          model.addAttribute("msg", "结果："+result);
          //返回视图位置
          return "test";
          
     }
      
   }
   ~~~

3. 测试
   ![在这里插入图片描述](SpringMVC课程笔记.assets\20200719144041947.png)

4. 思考：使用路径变量的好处？

   1. 使路径边的更加简洁
   2. 获得参数更加方便，框架会自动进行类型转换
   3. 通过路径变量的类型可以约束访问参数，如果类型不一样，则访问不到对应的请求方法，如这里访问的路径是/add/1/a，则路径与方法不匹配，而不会是参数转换失败
      ![在这里插入图片描述](SpringMVC课程笔记.assets\20200810172356116.png)

5. 修改下对应参数类型，再次测试

   ~~~java
   //映射访问路径
   @RequestMapping("/add/{p1}/{p2}")
   public String index(@PathVariable int p1, @PathVariable String p2, Model model){
   
      String result = p1+p2;
      //Spring MVC会自动实例化一个Model对象用于向视图中传值
      model.addAttribute("msg", "结果："+result);
      //返回视图位置
      return "test";
   
   }
   ~~~

   ![在这里插入图片描述](SpringMVC课程笔记.assets\20200719144133163.png)

**使用method属性指定请求类型**

用于约束请求的类型，可以收窄请求范围。指定请求谓词的类型如GET\POST\HEAD\OPTIONS\PUT\PATCH\DELETE\TRACE等

测试：

- 增加一个方法

  ~~~java
  //映射访问路径,必须是POST请求
  @RequestMapping(value = "/hello",method = {RequestMethod.POST})
  public String index2(Model model){
     model.addAttribute("msg", "hello!");
     return "test";
  }
  ~~~

- 我们使用浏览器地址栏进行访问默认是Get请求，会报错405
  ![在这里插入图片描述](SpringMVC课程笔记.assets\20200719144202631.png)

- 如果将POST修改为GET则正常了

  ~~~java
//映射访问路径，必须时Get请求
  @RequestMapping(value = "/hello", method = {RequestMethod.GET})
  public String index2(Model model) {
  	model.addAttribute("msg","hello!");
      return "test";
  }
  ~~~

小结：

SpringMVC的@RequestMapping注解能够处理HTTP请求的方法，比如GET, PUT, POST, DELETE 以及 PATCH

所有的地址栏请求默认都会是 HTTP GET 类型的

方法级别的注解变体有如下几个：组合注解

方法级别的注解变体有如下几个：组合注解

~~~java
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@DeleteMapping
@PatchMapping
~~~

@GetMapping 是一个组合注解，平时使用的会比较多！

它所扮演的是 @RequetMapping(method=RequestMethod.Get)的一个快捷方式

## 结果跳转方式

### ModelAndView

设置ModelAndView对象，根据view的名称，和视图解析器跳到指定的页面

页面：（视图解析器前缀）+ viewName +（视图解析器后缀）

~~~xml
<!--视图解析器-->
<bean class="org.springframework.web.servler.view.InternalResouceViewResolvr">
	<!--前缀-->
    <property name="prefix" value="/WEB-INF/jsp"/>
	<!--后缀-->
    <property name="suffix" value=".jsp"
</bean>
      
~~~

对应的controller类

~~~java
public class ControllerTest1 implements Controller {
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //返回一个模型视图对象
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","ControllerTest1");
        mv.setViewName("test");
        return mv;
    }
}
~~~

### ServletAPI

通过设置ServletAPI，不需要视图解析器

1. 通过HttpServletResponse进行输出
2. 通过HttpServletResponse实现重定向
3. 通过HttpServletResponse实现转发

~~~java
@Controller
public class ResultGo {
    @RequestMapping("/result/t1")
    public void test1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println("Hello, Spring by servlet API");
    }
    public void test1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/index.jsp");
    }
    public void test1(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("msg","/result/t3");
        req.getRequestDospatcher("/WEB-INF/jsp/test.jsp").forward(req,resp);
    }
}
~~~

### SpringMVC

**通过SpringMVC来实现转发和重定向 - 无需视图解析器**

测试前，需要将视图解析器注释掉

- 默认为forward转发（也可以加上）
- redirect转发需特别加

~~~java
@Controller
public class ResultSpringMVC {
	@RequestMapping("/rsm/t1")
    public String test1() {
		return "/index.jsp";
    }
    @RequestMapping("/rsm/t2")
    public String test2() {
        return "forward:/index.jsp";
    }
    @RequestMapping("/rsm/t3")
    public String test3() {
        return "redirect:/index.jsp";
    }
}
~~~

**通过SpringMVC来实现转发和重定向 - 有视图解析器**

重定向，不需要视图解析器，本质就是重新请求一个新地址嘛，所以注意路径问题

可以重定向到另外一个请求实现

- 默认为forward转发（不可以加上）
- redirect转发需特别加

~~~java
@Controller
public class ResultSpringMVC2 {
    @RequetMapping("/rsm2/t1")
    public String test1() {
        return "test";
    }
    @RequestMapping("/rsm2/t2")
    public String test2() {
        return "redirect:/index.jsp";
    }
}
~~~

## 数据处理

### 处理提交数据

1. **提交的域名和处理方法的参数名一致**
   提交数据：http://localhost:8080/hello?name=kuangshen
   处理方法：

   ~~~java
   @RequestMapping("/hello")
   public String hello(String name) {
       System.out.println(name);
       return "hello";
   }
   ~~~

   后台输出：kuanshen

2. **提交的域名和处理方法的参数名不一致**
   提交数据：http://localhost:8080/hello?username=kuangshen
   处理方法：

   ~~~java
   @RequestMapping("/hello")
   public String hello(@RequestParam("username") String name) {
       System.out.println(name);
       return "hello";
   }
   ~~~

   后台输出：kuangshen

3. **提交的是一个对象**
   要求提交的表单域和对象的属性名一致，参数使用对象即可

   1. 实体类

      ~~~java
      public class User {
          private int id;
          private String name;
          private int age;
      }
      ~~~

   2. 提交数据：http://localhost:8080/mvc04/user?name=kuangshen&id=1&age=15

   3. 处理方法：

      ~~~java
      @RequestMapping("/user")
      public String user(User user) {
          System.out.println(user);
          return "hello";
      }
      ~~~

      后台输出：User {id=1,name='kuangshen',age=15}

      说明：如果使用对象的话，前端传递的参数名和对象名必须一致，否则就是null

### 数据显示到前端

1. **通过ModelAndView**

   ~~~java
   public class COntrollerTest1 implements Controller {
       public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
           ModelAndView mv = new ModelAndView();
           mv.addObject("msg","ControllerTest1");
           mv.setViewName("test");
           return mv;
       }
   }
   ~~~

2. **通过ModelMap**
   ModelMap

   ~~~java
   @RequestMapping("/hello")
   public Sting hello(@RequestParam("username") String name, ModelMap modelMap) {
   	//封装要显示到视图中的数据
   	//相当于req.setAttrubute("name", name);
       modelMap,addAttribute("name",name);
       System.out.println(name);
    return "hello";
   }
   ~~~
   
3. **通过Model**
   Model

   ~~~java
   @RequestMapping("/ct2/hello")
   public String hello(@RequestParam("username") String name, Model model) {
    	//封装要显示到视图中的数据
       //相当于req.setAttribute("name", name);
       model.addAtttribute("msg",name);
       System.out.println(name);
       return "test";
   }
   ~~~

### 对比

>Model 只有寥寥几个方法，只适合用于存储数据，简化了新手对于Model对象的操作和理解
>
>ModelMap 继承了LnkedMap，除了实现了自身的一些方法，同样的继承LinkedMap的方法和特性
>
>ModelAndView 可以在存储数据的同时，可以进行设置返回的逻辑视图，进行控制展示层的跳转

## 乱码问题

测试步骤：

1. 我们在首页编写一个提交的表单

   ~~~html
   <form action="/e/t" method="post">
       <input type="text" name="name">
       <input type="submit">
   </form>
   ~~~

2. 后台编写

   ~~~java
   @Controller
   public class Encoding {
       @RequestMapping("/e/t")
       public String test(Model model, String name) {
           model.addAttribute("msg", name);
           return "test";
       }
   }
   ~~~

3. 输入中文测试，发现乱码
   ![在这里插入图片描述](SpringMVC课程笔记.assets\20200719144344818.png)

以前乱码问题通过过滤器解决，而SpringMVC给我们提供了一个过滤器，可以在web.xml中配置

修改了xml文件需要重启服务器

~~~xml
<filter>
	<filter-name>encoding</filter-name>
    <filter-class>org.springframework.web.ilter.CharacterEncodingFilter</filter-class>
    <init-param>
    	<param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
	<filter-name>encoding</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
~~~

==注意：这里写 /* ，写 / 的话过滤不了jsp页面，不能解决乱码==

有些极端情况下，这个过滤器对get的支持不好

处理方法：

1. 修改tomcat配置文件

   ~~~xml
   <Connector URIEncoding="utf-8" port="8080" protocol="HTTP/1.1"
             connectionTimeout="20000"
             redirectPort="8443" />
   ~~~

2. 自定义过滤器（万能解决）

   ~~~java
   package com.kuang.filter;
   
   import javax.servlet.*;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletRequestWrapper;
   import javax.servlet.http.HttpServletResponse;
   import java.io.IOException;
   import java.io.UnsupportedEncodingException;
   import java.util.Map;
   
   /**
   * 解决get和post请求 全部乱码的过滤器
   */
   public class GenericEncodingFilter implements Filter {
   
      @Override
      public void destroy() {
     }
   
      @Override
      public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
          //处理response的字符编码
          HttpServletResponse myResponse=(HttpServletResponse) response;
          myResponse.setContentType("text/html;charset=UTF-8");
   
          // 转型为与协议相关对象
          HttpServletRequest httpServletRequest = (HttpServletRequest) request;
          // 对request包装增强
          HttpServletRequest myrequest = new MyRequest(httpServletRequest);
          chain.doFilter(myrequest, response);
     }
   
      @Override
      public void init(FilterConfig filterConfig) throws ServletException {
     }
   
   }
   
   //自定义request对象，HttpServletRequest的包装类
   class MyRequest extends HttpServletRequestWrapper {
   
      private HttpServletRequest request;
      //是否编码的标记
      private boolean hasEncode;
      //定义一个可以传入HttpServletRequest对象的构造函数，以便对其进行装饰
      public MyRequest(HttpServletRequest request) {
          super(request);// super必须写
          this.request = request;
     }
   
      // 对需要增强方法 进行覆盖
      @Override
      public Map getParameterMap() {
          // 先获得请求方式
          String method = request.getMethod();
          if (method.equalsIgnoreCase("post")) {
              // post请求
              try {
                  // 处理post乱码
                  request.setCharacterEncoding("utf-8");
                  return request.getParameterMap();
             } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
             }
         } else if (method.equalsIgnoreCase("get")) {
              // get请求
              Map<String, String[]> parameterMap = request.getParameterMap();
              if (!hasEncode) { // 确保get手动编码逻辑只运行一次
                  for (String parameterName : parameterMap.keySet()) {
                      String[] values = parameterMap.get(parameterName);
                      if (values != null) {
                          for (int i = 0; i < values.length; i++) {
                              try {
                                  // 处理get乱码
                                  values[i] = new String(values[i]
                                         .getBytes("ISO-8859-1"), "utf-8");
                             } catch (UnsupportedEncodingException e) {
                                  e.printStackTrace();
                             }
                         }
                     }
                 }
                  hasEncode = true;
             }
              return parameterMap;
         }
          return super.getParameterMap();
     }
   
      //取一个值
      @Override
      public String getParameter(String name) {
          Map<String, String[]> parameterMap = getParameterMap();
          String[] values = parameterMap.get(name);
          if (values == null) {
              return null;
         }
          return values[0]; // 取回参数的第一个值
     }
   
      //取所有值
      @Override
      public String[] getParameterValues(String name) {
          Map<String, String[]> parameterMap = getParameterMap();
          String[] values = parameterMap.get(name);
          return values;
     }
   }
   ~~~

   一般情况下，SpringMVC默认的乱码处理就已经能够很多的解决了

   然后再web.xml中配置这个过滤器即可

   乱码问题，需要平时多注意，在尽可能能设置编码的地方，都设置为统一编码UTF-8

## Json交互处理

### 说明是Json？

- Json（JavaScript Object Notation，JS对象标记）是一种轻量级的数据交换格式
- 采用完全独立于编程语言的文本格式来存储和表示数据
- 简洁和清晰的层次结构使得JSON成为理想的数据交换语言
- 易于人阅读和编写，同时也易于机器解析和生成，并有效地提升网络传输效率

在JavaScript语言中，一切都是对象。因此，任何JavaScript支持的类型都可以通过JSON来表示，例如字符串、数字、对象、数组等

- 对象表示为键值对，数组由逗号分隔
- 花括号保存对象
- 方括号保存数组

JSON键值对是用来保存JavaScript对象的一种方式，和JavaScript对象的写法也大同小异，键值对组合中的键名西在前面并用双引号“”包裹，使用 : 分割，然后紧接着值：

~~~json
{"name":"QinJiang"}
{"age":"3"}
{"sex":"男"}
~~~

JSON是对JavaScript对象的字符串表示法，它使用文本表示一个JS对象的信息，本质是一个字符串

~~~JavaScript
var obj = {a:"Hello", b:"World"}; //这是一个对象，键名也是可以使用引号包裹的
var json = '{"a":"Hello","b":"World"}'; //这是一个JSON字符串，本质是一个字符串
~~~

### JSON和JavaScript对象互转

要实现从JSON字符串转换为JavaScript对象，使用JSON.parse()方法

~~~java
var obj = JSON.parse('{"a":"Hello","b":"World"}');
//结果是{a:'Hello',b:'World'}
~~~

代码测试

1. 新建一个module，springmvc-05-json，添加web的支持

2. 在web目录下新建一个json-1.html，编写测试内容

   ~~~html
   !DOCTYPE html>
   <html lang="en">
   <head>
      <meta charset="UTF-8">
      <title>JSON_秦疆</title>
   </head>
   <body>
   
   <script type="text/javascript">
      //编写一个js的对象
      var user = {
          name:"秦疆",
          age:3,
          sex:"男"
     };
      //将js对象转换成json字符串
      var str = JSON.stringify(user);
      console.log(str);
      
      //将json字符串转换为js对象
      var user2 = JSON.parse(str);
      console.log(user2.age,user2.name,user2.sex);
   
   </script>
   
   </body>
   </html>
   ~~~

3. 在IDEA中使用浏览器打开，查看控制台输出

### Controller返回JSON数据

- Jackson应该是目前比较好的json解析工具了
- 阿里巴巴的fastjson
- 这里使用Jackson，使用它需要导入jar包
~~~xml
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
	<version>2.9.8</version>
</dependency>
~~~
- 配置SpringMVC需要的配置
	
	- （web.xml） 
	
	  ~~~xml
	  <?xml version="1.0" encoding="UTF-8"?>
	  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	          xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
	          version="4.0">
	  
	     <!--1.注册servlet-->
	     <servlet>
	         <servlet-name>SpringMVC</servlet-name>
	         <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	         <!--通过初始化参数指定SpringMVC配置文件的位置，进行关联-->
	         <init-param>
	             <param-name>contextConfigLocation</param-name>
	             <param-value>classpath:springmvc-servlet.xml</param-value>
	         </init-param>
	         <!-- 启动顺序，数字越小，启动越早 -->
	         <load-on-startup>1</load-on-startup>
	     </servlet>
	  
	     <!--所有请求都会被springmvc拦截 -->
	     <servlet-mapping>
	         <servlet-name>SpringMVC</servlet-name>
	         <url-pattern>/</url-pattern>
	     </servlet-mapping>
	  
	     <filter>
	         <filter-name>encoding</filter-name>
	         <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
	         <init-param>
	             <param-name>encoding</param-name>
	             <param-value>utf-8</param-value>
	         </init-param>
	     </filter>
	     <filter-mapping>
	         <filter-name>encoding</filter-name>
	         <url-pattern>/</url-pattern>
	     </filter-mapping>
	  
	  </web-app>
	  ~~~
	
	- springmvc-servlet.xml
	
	  ~~~xml
	  <?xml version="1.0" encoding="UTF-8"?>
	  <beans xmlns="http://www.springframework.org/schema/beans"
	        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	        xmlns:context="http://www.springframework.org/schema/context"
	        xmlns:mvc="http://www.springframework.org/schema/mvc"
	        xsi:schemaLocation="http://www.springframework.org/schema/beans
	         http://www.springframework.org/schema/beans/spring-beans.xsd
	         http://www.springframework.org/schema/context
	         https://www.springframework.org/schema/context/spring-context.xsd
	         http://www.springframework.org/schema/mvc
	         https://www.springframework.org/schema/mvc/spring-mvc.xsd">
	  
	     <!-- 自动扫描指定的包，下面所有注解类交给IOC容器管理 -->
	     <context:component-scan base-package="com.kuang.controller"/>
	  
	     <!-- 视图解析器 -->
	     <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
	           id="internalResourceViewResolver">
	         <!-- 前缀 -->
	         <property name="prefix" value="/WEB-INF/jsp/" />
	         <!-- 后缀 -->
	         <property name="suffix" value=".jsp" />
	     </bean>
	  
	  </beans>
	  ~~~
	
	- 我们随便编写一个User的实体类，然后我们去编写我们的测试Controller
	
	  ~~~java
	  @Data
	  @AllArgsConstructor
	  @NoArgsConstructor
	  public class User {
	      private int id;
	      private String name;
	    private int age;
	  }
	  ~~~
	
	- 这里我们需要两个新东西，一个是@ResponseBody，一个是ObjectMapper对象，我们看下具体的用法
	
	  编写一个Controller：
	
	  ~~~java
	  @Controller
	  public class UserController {
	  @RequestMapping("/j1")
	      @ResponseBody//他就不会走视图解析器，会直接返回一个 字符串
	      public String json1() throws JsonProcessingException {
	          //jackson,ObjectMapper
	          ObjectMapper mapper = new ObjectMapper();
	  
	          //创建一个对象
	          User user = new User(1, "秦疆一号", 12);
	          //System.out.println(user);
	  
	          String str = mapper.writeValueAsString(user);
	          return str;
	      }
	  ~~~
	
	- 此时输入中文会产生乱码
	
	  ~~~java
	  //produces:指定响应体返回类型和编码
	  @RequestMapping(value = "/j1",produces = "application/json;charset=utf-8")
	  ~~~
	
	- 配置Tomcat，启动测试一下

==注意：使用json记得处理乱码问题==

### 乱码优化

**乱码统一解决**

上一种方法比较麻烦，如果项目中有许多请求则每一个都有添加，可以通过Spring统一指定，这样就不用每次都去处理了

我们可以在springmvc的配置文件上添加一段消息StringHttpMessageConverter转换配置

~~~xml
<mvc:annotation-driven>
   <mvc:message-converters register-defaults="true">
       <bean class="org.springframework.http.converter.StringHttpMessageConverter">
           <constructor-arg value="UTF-8"/>
       </bean>
       <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
           <property name="objectMapper">
               <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                   <property name="failOnEmptyBeans" value="false"/>
               </bean>
           </property>
       </bean>
   </mvc:message-converters>
</mvc:annotation-driven>
~~~

**返回json字符串统一解决**

- @ResponseBody解决（每个方法都得加，不建议使用）

  ~~~java
  @Controller
  public class UserController {
     //produces:指定响应体返回类型和编码
     @RequestMapping(value = "/json1")
     @ResponseBody
     public String json1() throws JsonProcessingException {
         //创建一个jackson的对象映射器，用来解析数据
         ObjectMapper mapper = new ObjectMapper();
         //创建一个对象
         User user = new User(1, "秦疆一号", 12);
         //将我们的对象解析成为json格式
         String str = mapper.writeValueAsString(user);
         //由于@ResponseBody注解，这里会将str转成json格式返回；十分方便
         return str;
    }
  }
  ~~~

- @RestControler（直接加到类上即可）

  ~~~java
  @RestController
  public class UserController {
     @RequestMapping(value = "/j1")
     public String json1() throws JsonProcessingException {
         //创建一个jackson的对象映射器，用来解析数据
         ObjectMapper mapper = new ObjectMapper();
         //创建一个对象
         User user = new User(1, "秦疆一号", 12);
         //将我们的对象解析成为json格式
         String str = mapper.writeValueAsString(user);
         return str;
    }
  
  }
  ~~~

### 测试集合输出

增加一个新的方法

~~~java
@RequestMapping("/j2")
public String json2() throws JsonProcessingException {

    //创建一个jackson的对象映射器，用来解析数据
    ObjectMapper mapper = new ObjectMapper();
    //创建一个对象
   	User user1 = new User(1, "秦疆1号", 12);
    User user2 = new User(2, "秦疆2号", 12);
    User user3 = new User(3, "秦疆3号", 12);
    User user4 = new User(4, "秦疆4号", 12);
    User user5 = new User(5, "秦疆5号", 12);
    List<User> list = new ArrayList<User>();
    list.add(user1);
    list.add(user2);
    list.add(user3);
    list.add(user4);
    list.add(user5);
    //将我们的对象解析成为json格式
    String str = mapper.writeValueAsString(list);
    return str;
}
~~~

### 输出时间对象

- 增加一个新的方法

  ~~~java
  @RequestMapping("/j3")
  public String json3() throws JsonProcessingException {
  
     ObjectMapper mapper = new ObjectMapper();
  
     //创建时间一个对象，java.util.Date
     Date date = new Date();
     //将我们的对象解析成为json格式
     String str = mapper.writeValueAsString(date);
     return str;
  }
  ~~~

- 默认日期格式会变成一个数字

- jackson默认是会把时间转成timestamps形式
  **解决方案：取消timestamps形式，自定义时间格式**

  ~~~java
  @RequestMapping("/j3")
  public String json4() throws JsonProcessingException {
  
     ObjectMapper mapper = new ObjectMapper();
  
     //不使用时间戳的方式
     mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
     //自定义日期格式对象
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     //指定日期格式
     mapper.setDateFormat(sdf);
  
     Date date = new Date();
     String str = mapper.writeValueAsString(date);
  
     return str;
  }
  ~~~

==抽取为工具类==

如果要经常使用的话，这样是比较麻烦的，我们可以将这些代码封装到一个工具类中

~~~java
public class JsonUtils {
    public static String getJson(Object object) {
        return getJson(object,"yyyy-MM-dd HH:mm:ss");
    }
    public static String getJson(Object object, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();

        //java自定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        //sdf.format(date)

        // 使用ObjectMapper 来格式化输出
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        mapper.setDateFormat(sdf);

        try {
            //ObjectMapper,时间解析后的默认格式为：TImestamp.时间戳
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
~~~

我们使用工具类，代码更加简洁了

~~~java
@RequestMapping("/j3")
    public String json3(){

        Date date = new Date();

        return JsonUtils.getJson(date,"yyyy-MM-dd HH:mm:ss");
    }
}
~~~

### FastJson

- fastjson.jar是阿里开发一款专门用于Java开发的包
- 实现json对象于JavaBean对象转换
- 实现JavaBean对象与json字符串的转换
- 实现json对象与json字符串的转换
- 实现json的转换方法很多，最后实现结果都是一样的

fastjson的pom依赖

~~~xml
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>fastjson</artifactId>
   <version>1.2.60</version>
</dependency>
~~~

fastjson三个主要的类

1. JSONObject代表json对象
   1. JSONObject实现了Map接口，猜想JSONObject底层操作是由Map实现的
   2. JSONObject对应json对象，通过各种形式的get()方法可以获取json对象中的数据，也可利用诸如size()，isEmpty()等方法获取“键 : 值”对的个数和判断是否为空。其本质是通过实现Map接口中的方法完成的
2. JSONArray代表json对象数组
   1. 内部是有List接口中的方法来完成操作的
3. JSON代表JSONObject和JSONArray的转化
   1. JSON类源码分析与使用
   2. 仔细观察这些方法，主要是实现json对象，json对象数组，javabean对象，json字符串之间的相互转化

代码测试，新建一个FastJsonDemo类

~~~java
@RequestMapping("/j4")
    //@ResponseBody//他就不会走视图解析器，会直接返回一个 字符串
    public String json4(){

        User user1 = new User(1, "秦疆1号", 12);
        User user2 = new User(2, "秦疆2号", 12);
        User user3 = new User(3, "秦疆3号", 12);
        User user4 = new User(4, "秦疆4号", 12);
        User user5 = new User(5, "秦疆5号", 12);

        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);

        System.out.println("*******Java对象 转 JSON字符串*******");
        String str1 = JSON.toJSONString(list);
        System.out.println("JSON.toJSONString(list)==>"+str1);
        String str2 = JSON.toJSONString(user1);
        System.out.println("JSON.toJSONString(user1)==>"+str2);

        System.out.println("\n****** JSON字符串 转 Java对象*******");
        User jp_user1=JSON.parseObject(str2,User.class);
        System.out.println("JSON.parseObject(str2,User.class)==>"+jp_user1);

        System.out.println("\n****** Java对象 转 JSON对象 ******");
        JSONObject jsonObject1 = (JSONObject) JSON.toJSON(user2);
        System.out.println("(JSONObject) JSON.toJSON(user2)==>"+jsonObject1.getString("name"));

        System.out.println("\n****** JSON对象 转 Java对象 ******");
        User to_java_user = JSON.toJavaObject(jsonObject1, User.class);
        System.out.println("JSON.toJavaObject(jsonObject1, User.class)==>"+to_java_user);

        return JSON.toJSONString(list);
    }
~~~

## Ajax研究

### 简介

- AJAX = Asynchronous JavaScript and XML（异步的JavaScript和XML）
- AJAX是一种在无需重新加载整个网页的情况下，能够更新部分网页的技术
- Ajax不是一种新的编程语言，而是一种用于创建更好更快以及交互性更强的web应用程序的技术
- 在2005年，Google通过其Google Suggest使AJAX变得流行起来。Google Suggest能够自动帮你搜索单词
- Google Suggest使用AJAX创造出动态性极强的web页面：当你在谷歌的搜索框输入关键字时，JavaScript会把这些字符发送到服务器，然后服务器会返回一个搜索建议的列表
- 传统的网页（即不用ajax技术的网页），想要更新内容或者提交一个表单，都需要重新家族整个网页
- 使用ajax技术的网页，通过在后台服务器进行少量的数据交换，就可以实现异步局部更新
- 使用AJAX，用户可以创建接近本地桌面应用的直接、高可用、更丰富、更动态的web用户界面

### 伪造Ajax

我们可以使用前端的一个标签来伪造一个ajax的样子。iframe标签

1. 新建一个module：springmvc-06-ajax，导入web支持

2. 编写一个ajax-frame.html使用iframe测试，感受下效果

   ~~~html
   <!DOCTYPE html>
   <html>
   <head lang="en">
      <meta charset="UTF-8">
      <title>kuangshen</title>
   </head>
   <body>
   
   <script type="text/javascript">
      window.onload = function(){
          var myDate = new Date();
          document.getElementById('currentTime').innerText = myDate.getTime();
     };
   
      function LoadPage(){
          var targetUrl =  document.getElementById('url').value;
          console.log(targetUrl);
          document.getElementById("iframePosition").src = targetUrl;
     }
   
   </script>
   
   <div>
      <p>请输入要加载的地址：<span id="currentTime"></span></p>
      <p>
          <input id="url" type="text" value="https://www.baidu.com/"/>
          <input type="button" value="提交" onclick="LoadPage()">
      </p>
   </div>
   
   <div>
      <h3>加载页面位置：</h3>
      <iframe id="iframePosition" style="width: 100%;height: 500px;"></iframe>
   </div>
   
   </body>
   </html>
   ~~~

**利用AJAX可以做：**

- 注册时，输入名自动检测用户是否以及存在
- 登录时，提示用户名密码错误
- 删除数据行时，将行ID发送到后台，后台在数据中删除，数据库删除成功后，在页面DOM中将数据行也删除

### jQuery.ajax

- 纯JS原生实现Ajax我们不去讲解这里，直接使用jquery提供的，方便学习和使用，避免重复造轮子，有兴趣的同学可以去了解下js原生XMLHttpRequest
- Ajax的核心是XMLHttpRequest（对象XHR）。XHR为服务器发送请求和解析服务器响应提供了接口。能够以异步方式从服务器获取新数据
- jquery提供多个与AJAX有关的方法
- 通过jquery AJAX方法，你能够使用HTTP Get 和 HTTP Post从远程服务器上请求文本、HTML、XML或JSON - 同时你可以把这些外部数据直接载入网页的被选元素中
- jquery不是生产者，而是大自然搬运工
- jquery Ajax本质就是XMLHttpRequest，对他进行了封装，方便调用

~~~html
jQuery.ajax(...)
      部分参数：
            url：请求地址
            type：请求方式，GET、POST（1.9.0之后用method）
        headers：请求头
            data：要发送的数据
    contentType：即将发送信息至服务器的内容编码类型(默认: "application/x-www-form-urlencoded; charset=UTF-8")
          async：是否异步
        timeout：设置请求超时时间（毫秒）
      beforeSend：发送请求前执行的函数(全局)
        complete：完成之后执行的回调函数(全局)
        success：成功之后执行的回调函数(全局)
          error：失败之后执行的回调函数(全局)
        accepts：通过请求头发送给服务器，告诉服务器当前客户端可接受的数据类型
        dataType：将服务器端返回的数据转换成指定类型
          "xml": 将服务器端返回的内容转换成xml格式
          "text": 将服务器端返回的内容转换成普通文本格式
          "html": 将服务器端返回的内容转换成普通文本格式，在插入DOM中时，如果包含JavaScript标签，则会尝试去执行。
        "script": 尝试将返回值当作JavaScript去执行，然后再将服务器端返回的内容转换成普通文本格式
          "json": 将服务器端返回的内容转换成相应的JavaScript对象
        "jsonp": JSONP 格式使用 JSONP 形式调用函数时，如 "myurl?callback=?" jQuery 将自动替换 ? 为正确的函数名，以执行回调函数
~~~

我们来个简单的测试，使用原始的HttpServletResponse处理，最简单，最通用

1. 配置web.xml和springmvc的配置文件【记得静态资源过滤和注解驱动配置上】

   ~~~xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/mvc
          https://www.springframework.org/schema/mvc/spring-mvc.xsd">
   
      <!-- 自动扫描指定的包，下面所有注解类交给IOC容器管理 -->
      <context:component-scan base-package="com.kuang.controller"/>
       <!--静态资源过滤-->
      <mvc:default-servlet-handler />
       <!--注解驱动配-->
      <mvc:annotation-driven />
   
      <!-- 视图解析器 -->
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
            id="internalResourceViewResolver">
          <!-- 前缀 -->
          <property name="prefix" value="/WEB-INF/jsp/" />
          <!-- 后缀 -->
          <property name="suffix" value=".jsp" />
      </bean>
   
   </beans>
   ~~~

2. 编写一个AjaxController

   ~~~java
   @Controller
   public class AjaxController {
   
      @RequestMapping("/a1")
      public void ajax1(String name , HttpServletResponse response) throws IOException {
          if ("admin".equals(name)){
              response.getWriter().print("true");
         }else{
              response.getWriter().print("false");
         }
     }
   }
   ~~~

3. 导入jquery，可以使用在线的CDN，也可以下载导入

   ~~~html
   <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
   <script src="${pageContext.request.contextPath}/statics/js/jquery-3.1.1.min.js"></script>
   
   ~~~

4. 编写index.jsp测试

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
    <head>
      <title>$Title$</title>
     <%--<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>--%>
      <script src="${pageContext.request.contextPath}/statics/js/jquery-3.1.1.min.js"></script>
      <script>
          function a1(){
              $.post({
                  url:"${pageContext.request.contextPath}/a1",
                  data:{'name':$("#txtName").val()},
                  success:function (data,status) {
                      alert(data);
                      alert(status);
                 }
             });
         }
      </script>
    </head>
    <body>
   
   <%--onblur：失去焦点触发事件--%>
   用户名:<input type="text" id="txtName" onblur="a1()"/>
   
    </body>
   </html>
   ~~~

5. 启动tomcat测试！打开浏览器的控制台，当我们鼠标离开输入框的时候，可以看到发出一个ajax的请求，是后台返回给我们的结果，测试成功

### Springmvc实现

1. 实体类user（使用了lombok插件，可以自己写实现类方法）

   ~~~java
   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   public class User {
   
      private String name;
      private int age;
      private String sex;
   
   }
   ~~~

2. 我们来获取一个集合对象，展示到前端页面

   ~~~java
   @RequestMapping("/a2")
   public List<User> ajax2(){
      List<User> list = new ArrayList<User>();
      list.add(new User("秦疆1号",3,"男"));
      list.add(new User("秦疆2号",3,"男"));
      list.add(new User("秦疆3号",3,"男"));
      return list; //由于@RestController注解，将list转成json格式返回
   }
   ~~~

3. 前端页面

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
       <title>Title</title>
       <script src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.js"></script>
       <script>
           $(function () {
               $("#btn").click(function () {
                   // console.log('点击事件成功')
                   // $.post(url,param,[可以省略],success)
                   $.post("${pageContext.request.contextPath}/a2",function (data) {
                       console.log(data[0].name);
                       let html =  "";
                       for (let i = 0; i < data.length; i++) {
                           html += `<tr>
                                       <td>${"${data[i].name}"}</td>
                                       <td>${"${data[i].age}"}</td>
                                       <td>${"${data[i].sex}"}</td>
                                   </tr>`
                       }
                       $("#content").html(html)
                       console.log(html)
                   })
               })
           })
       </script>
   </head>
   <body>
   <input type="button" value="加载数据" id="btn">
   <table>
       <thead>
           <tr>
               <td>姓名</td>
               <td>年龄</td>
               <td>性别</td>
           </tr>
       </thead>
       <tbody id="content">
           <%--数据：后台--%>
       </tbody>
   </table>
   </body>
   </html>
   ~~~

成功实现了数据回显，可以体会一下Ajax的好处

### 注册提示效果

1. 我们写是个Controller

   ~~~java
   @RequestMapping("/a3")
   public String ajax3(String name,String pwd){
      String msg = "";
      //模拟数据库中存在数据
      if (name!=null){
          if ("admin".equals(name)){
              msg = "OK";
         }else {
              msg = "用户名输入错误";
         }
     }
      if (pwd!=null){
          if ("123456".equals(pwd)){
              msg = "OK";
         }else {
              msg = "密码输入有误";
         }
     }
      return msg; //由于@RestController注解，将msg转成json格式返回
   }
   ~~~

2. 前端页面 login.jsp

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
      <title>ajax</title>
      <script src="${pageContext.request.contextPath}/statics/js/jquery-3.1.1.min.js"></script>
      <script>
   
          function a1(){
              $.post({
                  url:"${pageContext.request.contextPath}/a3",
                  data:{'name':$("#name").val()},
                  success:function (data) {
                      if (data.toString()=='OK'){
                          $("#userInfo").css("color","green");
                     }else {
                          $("#userInfo").css("color","red");
                     }
                      $("#userInfo").html(data);
                 }
             });
         }
          function a2(){
              $.post({
                  url:"${pageContext.request.contextPath}/a3",
                  data:{'pwd':$("#pwd").val()},
                  success:function (data) {
                      if (data.toString()=='OK'){
                          $("#pwdInfo").css("color","green");
                     }else {
                          $("#pwdInfo").css("color","red");
                     }
                      $("#pwdInfo").html(data);
                 }
             });
         }
   
      </script>
   </head>
   <body>
   <p>
     用户名:<input type="text" id="name" onblur="a1()"/>
      <span id="userInfo"></span>
   </p>
   <p>
     密码:<input type="text" id="pwd" onblur="a2()"/>
      <span id="pwdInfo"></span>
   </p>
   </body>
   </html>
   ~~~

3. 记得处理json乱码问题

   ~~~xml
   <!--JSON乱码问题配置-->
       <mvc:annotation-driven>
           <mvc:message-converters register-defaults="true">
               <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                   <constructor-arg value="UTF-8"/>
               </bean>
               <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                   <property name="objectMapper">
                       <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                           <property name="failOnEmptyBeans" value="false"/>
                       </bean>
                   </property>
               </bean>
           </mvc:message-converters>
       </mvc:annotation-driven>
   ~~~

### 获取baidu接口demo

1. JSON.html

   ~~~html
   <!DOCTYPE HTML>
   <html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
      <title>JSONP百度搜索</title>
      <style>
          #q{
              width: 500px;
              height: 30px;
              border:1px solid #ddd;
              line-height: 30px;
              display: block;
              margin: 0 auto;
              padding: 0 10px;
              font-size: 14px;
         }
          #ul{
              width: 520px;
              list-style: none;
              margin: 0 auto;
              padding: 0;
              border:1px solid #ddd;
              margin-top: -1px;
              display: none;
         }
          #ul li{
              line-height: 30px;
              padding: 0 10px;
         }
          #ul li:hover{
              background-color: #f60;
              color: #fff;
         }
      </style>
      <script>
   
          // 2.步骤二
          // 定义demo函数 (分析接口、数据)
          function demo(data){
              var Ul = document.getElementById('ul');
              var html = '';
              // 如果搜索数据存在 把内容添加进去
              if (data.s.length) {
                  // 隐藏掉的ul显示出来
                  Ul.style.display = 'block';
                  // 搜索到的数据循环追加到li里
                  for(var i = 0;i<data.s.length;i++){
                      html += '<li>'+data.s[i]+'</li>';
                 }
                  // 循环的li写入ul
                  Ul.innerHTML = html;
             }
         }
   
          // 1.步骤一
          window.onload = function(){
              // 获取输入框和ul
              var Q = document.getElementById('q');
              var Ul = document.getElementById('ul');
   
              // 事件鼠标抬起时候
              Q.onkeyup = function(){
                  // 如果输入框不等于空
                  if (this.value != '') {
                      // ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆JSONPz重点☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
                      // 创建标签
                      var script = document.createElement('script');
                      //给定要跨域的地址 赋值给src
                      //这里是要请求的跨域的地址 我写的是百度搜索的跨域地址
                      script.src = 'https://sp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su?wd='+this.value+'&cb=demo';
                      // 将组合好的带src的script标签追加到body里
                      document.body.appendChild(script);
                 }
             }
         }
      </script>
   </head>
   
   <body>
   <input type="text" id="q" />
   <ul id="ul">
   
   </ul>
   </body>
   </html>
   ~~~

## 拦截器

### 概述

- SpringMVC处理器拦截器类似于Servlet开发中的过滤器Filter，用于处理器进行预处理和后处理
- 开发者可以自己定义一些拦截器实现特定的功能

**过滤器于拦截器的区别：拦截器是AOP思想的具体应用**

**过滤器**

- servlet规范中的一部分，任何javaweb工程都可以使用
- 在url-pattern中配置 /* 之后，可以对所以要访问的资源进行拦截

**拦截器**

- 拦截器是SpringMVC框架自己的，只有使用了SpringMVC框架的工程才能使用
- 拦截器只会拦截访问的控制器方法，如果访问的是jsp/html/css/image/js是不会进行拦截的

### 自定义拦截器

1. 新建一个Moudule，springmvc-Interceptor，添加web支持

2. 配置web.xml 和 spring-servlet.xml 文件

3. 编写是个拦截器（必须实现 HandlerInterceptor 接口）

   ~~~java
   package nuc.ss.config;
   
   import org.springframework.web.servlet.HandlerInterceptor;
   import org.springframework.web.servlet.ModelAndView;
   
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   public class MyInterceptor implements HandlerInterceptor {
       //return true; 执行下一个拦截器，放行
       //return false; 不执行下一个拦截器，拦截
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           System.out.println("===========处理前===========");
           return true;
       }
       //日志
       public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
           System.out.println("===========处理后===========");
   
       }
       //日志
       public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
           System.out.println("===========清理===========");
   
       }
   }
   ~~~

4. 在springmvc的配置文件中配置拦截器

   ~~~xml
   <!--拦截器配置-->
   <mvc:interceptors>
       <mvc:interceptor>
           <!--包括这个请求下面的所有请求-->
           <mvc:mapping path="/**"/>
           <bean class="nuc.ss.config.MyInterceptor"/>
       </mvc:interceptor>
   
   </mvc:interceptors>
   ~~~

5. 编写一个Controller，接收请求

   ~~~java
   package nuc.ss.controller;
   
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   @RestController
   public class TestController {
       @GetMapping("/t1")
       public String test() {
           System.out.println("TestController.test");
           return "ok";
       }
   }
   ~~~

6. 启动tomcat测试一下

### 验证用户是否登录（认证用户）

**实现思路**

1. 有一个登录页面，需要编写一个controller访问页面
2. 登录页面有一提交表单的动作，需要在controller中处理。判断用户名密码是否正确。如果正确，向session中写入用户信息。返回登录成功
3. 拦截用户请求，判断用户是否登录。如果用户已经登录。放行，如果用户未登录，跳转到登陆页面

**测试**

1. 编写一个登录页面login.jsp

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
       <title>登录</title>
   </head>
   <body>
   <%--在web-inf下面的所有页面或者资源，只能通过controller或者Servlet进行访问--%>
   <h1>登录页面</h1>
   
   <form action="${pageContext.request.contextPath}/user/login" method="post">
       用户名：<input type="text" name="username">
       密码：<input type="text" name="password">
       <input type="submit" value="提交">
   </form>
   </body>
   </html>
   ~~~

2. 编写一个controller处理请求

   ~~~java
   @Controller
   @RequestMapping("/user")
   public class LoginController {
       @RequestMapping("/main")
       public String main() {
           return "main";
       }
   
       @RequestMapping("/goLogin")
       public String login() {
           return "login";
       }
       @RequestMapping("/login")
       public String login(HttpSession session, String username, String password, Model model) {
   
           //把用户的信息存在session中
           session.setAttribute("userLoginInfo",username);
           model.addAttribute("password",password);
           model.addAttribute("username",username);
           return "main";
       }
   
       @RequestMapping("/goOut")
       public String goOut(HttpSession sessionl) {
           sessionl.removeAttribute("userLoginInfo");
           return "login";
       }
   }
   ~~~

3. 编写一个登录成功的页面 main.jsp

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
       <title>首页</title>
   </head>
   <body>
   <h1>首页</h1>
   <span>${password}</span>
   <span>${username}</span>
   
   <p>
       <a href="${pageContext.request.contextPath}/user/goOut">注销</a>
   </p>
   
   </body>
   </html>
   ~~~

4. 在index页面上测试跳转！启动tomcat测试，未登录也可以进入主页

   ~~~jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
     <title>index</title>
   </head>
   <body>
   <h1><a href="${pageContext.request.contextPath}/user/goLogin">登录页面</a></h1>
   <h1><a href="${pageContext.request.contextPath}/user/main">首页</a></h1>
   
   </body>
   </html>
   ~~~

5. 编写用户登录拦截器

   ~~~java
   package nuc.ss.config;
   
   import org.springframework.web.servlet.HandlerInterceptor;
   
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import javax.servlet.http.HttpSession;
   
   public class LoginInterceptor implements HandlerInterceptor {
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           HttpSession session = request.getSession();
           //放行:判断什么情况下登录
   
           //登录页面也会放行
           if (request.getRequestURI().contains("goLogin")) {
               return true;
           }
           if (request.getRequestURI().contains("login")) {
               return true;
           }
           if (session.getAttribute("userLoginInfo") != null) {
               return true;
           }
           //判断什么情况下没有登录
           request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
           return false;
       }
   }
   ~~~

6. 在SpringMVC配置文件中注册拦截器

   ~~~xml
   <!--关于拦截器的配置-->
   <mvc:interceptors>
      <mvc:interceptor>
          <mvc:mapping path="/**"/>
          <bean id="loginInterceptor" class="com.kuang.interceptor.LoginInterceptor"/>
      </mvc:interceptor>
   </mvc:interceptors>
   ~~~

7. 再次启动tomcat测试

## 文件上传和下载

### 准备工作

- 文件上传是项目开发中最常见的功能之一，springmvc可以很好的支持文件上传
- SpringMVC上下文中默认没有装配MultipartResolver，因此默认情况下其不能处理文件上传工作。如果想要使用Spring的文件上传功能，则需要在上下文中配置MultipartResolver
- 前端表单要求：为了能上传文件，必须将表单的method设置为POST，并将enctype设置为POST，并将enctype设置为multipart/form-data。只有在这样的情况下，浏览器才会把用户选择的二进制数据发送给服务器

**对表单中的enctype属性做个详细的说明：**

- application/x-www=form-urleencoded：默认方式，只处理表单域中的value属性值，采用这种编码方式的表单会将表单域中的值处理成URL编码方式
- multipart/form-data：这种编码方式会以二进制的方式来处理表单数据，这种编码方式会把文件域指定文件的内容也封装到请求参数中，不会对字符编码
- text/plain：除了把空格转化为“+”号外，其他字符都不做编码处理，这种方式适用直接通过表单发送邮件

~~~html
<form action="" enctype="multipart/form-data" method="post">
   <input type="file" name="file"/>
   <input type="submit">
</form>
~~~

一旦设置了enctype为multipart/form-data，浏览器即会采用二进制流的方式来处理表单数据，而对于文件上传的处理则涉及在服务器端原始的HTTP响应。在2003年，Apache Software Foundation发布了开源的Commons FileUpload组件，器很快成为Servlet/JSP程序员上传文件的最佳选择

- Servlet3.0规范已经提供方法来处理文件上传，但这种上传需要在Servlet中完成
- 而Spring MVC则提供了更简单的封装
- SpringMVC为文件上传提供了直接的支持，这种支持是即插即用的ultipartResolver实现的
- SpringMVC使用Apache Commons FileUpload技术实现了一个MultipartResolver实现类
- CommonsMultipartResolver。因此，SpringMVC的文件上传还需要依赖Apache Commons FileUpload的组件

### 文件上传

1. 导入文件上传的jar包，commons-fileupload，Maven会自动帮我们导入他的依赖包commons-io包

   ~~~xml
   !--文件上传-->
   <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.3.3</version>
   </dependency>
   <!--servlet-api导入高版本的-->
   <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.1</version>
   </dependency>
   ~~~

2. 配置bean：multipartResolver

   【注意：这里的bean的id必须为：multipartResolver，否则上传文件会报400错误】

   ~~~xml
   !--文件上传配置-->
   <bean id="multipartResolver"  class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
      <!-- 请求的编码格式，必须和jSP的pageEncoding属性一致，以便正确读取表单的内容，默认为ISO-8859-1 -->
      <property name="defaultEncoding" value="utf-8"/>
      <!-- 上传文件大小上限，单位为字节（10485760=10M） -->
      <property name="maxUploadSize" value="10485760"/>
      <property name="maxInMemorySize" value="40960"/>
   </bean>
   ~~~

   CommonsMultipartFile的常用方法：

   1. String getOriginaFilename()：获取上传文件的原名
   2. InputStream getInputStream()：获取文件流
   3. void transferTo(File dest)：将上传文件保存到一个目录文件中

3. 编写前端页面

   ~~~html
   <form action="/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file"/>
    <input type="submit" value="upload">
   </form>
   ~~~

4. Controller

   ~~~java
   package nuc.ss.controller;
   
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RequestParam;
   import org.springframework.web.bind.annotation.RestController;
   import org.springframework.web.multipart.commons.CommonsMultipartFile;
   
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import java.io.*;
   import java.net.URLEncoder;
   
   @RestController
   public class FileController {
   
       //@RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
       //批量上传CommonsMultipartFile则为数组即可
       @RequestMapping("/upload")
       public String fileUpload(@RequestParam("file") CommonsMultipartFile file , HttpServletRequest request) throws IOException {
   
           //获取文件名 : file.getOriginalFilename();
           String uploadFileName = file.getOriginalFilename();
   
           //如果文件名为空，直接回到首页！
           if ("".equals(uploadFileName)){
               return "redirect:/index.jsp";
           }
           System.out.println("上传文件名 : "+uploadFileName);
   
           //上传路径保存设置
           String path = request.getServletContext().getRealPath("/upload");
           //如果路径不存在，创建一个
           File realPath = new File(path);
           if (!realPath.exists()){
               realPath.mkdir();
           }
           System.out.println("上传文件保存地址："+realPath);
   
           InputStream is = file.getInputStream(); //文件输入流
           OutputStream os = new FileOutputStream(new File(realPath,uploadFileName)); //文件输出流
   
           //读取写出
           int len=0;
           byte[] buffer = new byte[1024];
           while ((len=is.read(buffer))!=-1){
               os.write(buffer,0,len);
               os.flush();
           }
           os.close();
           is.close();
           return "redirect:/index.jsp";
       }
   }
   ~~~

5. 测试上传文件

**采用file.Transto来保存上传的文件**

1. 编写Controller

   ~~~java
   /*
   * 采用file.Transto 来保存上传的文件
   */
   @RequestMapping("/upload2")
   public String  fileUpload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
   
      //上传路径保存设置
      String path = request.getServletContext().getRealPath("/upload");
      File realPath = new File(path);
      if (!realPath.exists()){
          realPath.mkdir();
     }
      //上传文件地址
      System.out.println("上传文件保存地址："+realPath);
   
      //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
      file.transferTo(new File(realPath +"/"+ file.getOriginalFilename()));
   
      return "redirect:/index.jsp";
   }
   ~~~

2. 前端表单提交地址修改

3. 访问提交测试

### 文件下载

文件下载步骤：

1. 设置response响应头
2. 读取文件 - InputStream
3. 写出文件 - OutputStream
4. 执行操作
5. 关闭流（先开后关）

**代码实现：**

~~~java
@RequestMapping(value="/download")
public String downloads(HttpServletResponse response ,HttpServletRequest request) throws Exception{
   //要下载的图片地址
   String  path = request.getServletContext().getRealPath("/upload");
   String  fileName = "基础语法.jpg";

   //1、设置response 响应头
   response.reset(); //设置页面不缓存,清空buffer
   response.setCharacterEncoding("UTF-8"); //字符编码
   response.setContentType("multipart/form-data"); //二进制传输数据
   //设置响应头
   response.setHeader("Content-Disposition",
           "attachment;fileName="+URLEncoder.encode(fileName, "UTF-8"));

   File file = new File(path,fileName);
   //2、 读取文件--输入流
   InputStream input=new FileInputStream(file);
   //3、 写出文件--输出流
   OutputStream out = response.getOutputStream();

   byte[] buff =new byte[1024];
   int index=0;
   //4、执行 写出操作
   while((index= input.read(buff))!= -1){
       out.write(buff, 0, index);
       out.flush();
  }
   out.close();
   input.close();
   return null;
}
~~~

前端

~~~html
<a href="/download">点击下载</a>
~~~

测试




















































