package com.kuang.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//等价于 <bean id="user" class="com.kuang.pojo.User"/>
//@Component 组件

@Component
public class User {

    //相当于 <property name="name" value="狂神"/>
    @Value("狂神")
    public String name;

    public void setName(String name) {
        this.name = name;
    }
}

