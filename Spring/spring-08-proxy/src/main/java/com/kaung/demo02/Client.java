package com.kaung.demo02;

//客户
public class Client {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        //使用代理输出日志，不破坏原代码
        UserServiceProxy proxy = new UserServiceProxy();

        proxy.setUserService(userService);

        proxy.add();
    }
}
