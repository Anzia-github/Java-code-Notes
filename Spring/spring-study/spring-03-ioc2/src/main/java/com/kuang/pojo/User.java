package com.kuang.pojo;

public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }
    //干掉一个无参构造的方法就是创建一个有参构造

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void show() {
        System.out.println("name " + name);
    }
}
