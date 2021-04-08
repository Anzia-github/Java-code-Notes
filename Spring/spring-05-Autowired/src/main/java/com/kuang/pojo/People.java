package com.kuang.pojo;

import org.springframework.beans.factory.annotation.Autowired;

public class People {
    @Autowired
    private Cat cat;
    @Autowired
    private Dog1 dog1;
    private String name;

    public People() {
    }

    public People(Cat cat, Dog1 dog1, String name) {
        this.cat = cat;
        this.dog1 = dog1;
        this.name = name;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Dog1 getDog1() {
        return dog1;
    }

    public void setDog1(Dog1 dog1) {
        this.dog1 = dog1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "People{" +
                "cat=" + cat +
                ", dog1=" + dog1 +
                ", name='" + name + '\'' +
                '}';
    }
}
