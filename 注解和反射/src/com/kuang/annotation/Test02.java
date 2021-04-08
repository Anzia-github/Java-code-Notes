package com.kuang.annotation;

import java.lang.annotation.*;

//测试源注解
public class Test02 {

    @MyAnnotation
    public void test() {

    }

}
//定义一个注解
//Target 表示我们的注解可以放在哪个地方  用于描述注解的使用范围（即：被描述的注解可以用在什么地方）
@Target(value = {ElementType.METHOD, ElementType.TYPE})
//Retention 表示我们的注解在什么地方还有效  表述需要在什么级别保存该注解信息，用于描述注解的生命周期
// runtime > class > sources
@Retention(value = RetentionPolicy.RUNTIME)

//@Docunmented 表示是否将我们的注解生成在JavaDoc中
@Documented

//Inherited 子类可以继承父类的注解
@Inherited
@interface MyAnnotation {

}
