package com.kuang.controller;

import com.kuang.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {

    @RequestMapping("/t1")
    @ResponseBody
    public String test(){
        return "hello";
    }

    @RequestMapping("/a1")
    public void a1(String  name, HttpServletResponse response) throws IOException {
        System.out.println("a1:param-->" + name);
        if ("kuangshen".equals(name)) {
            response.getWriter().print("true");
        } else {
            response.getWriter().print("false");
        }
    }

    @RequestMapping("/a2")
    @ResponseBody
    public List<User> a2() {
        List<User> userList = new ArrayList<>();

        //添加书籍
        userList.add(new User("Java",1,"男"));
        userList.add(new User("前端",1,"女"));
        userList.add(new User("运维",1,"女"));

        return userList;
    }

    @RequestMapping("/a3")
    @ResponseBody
    public String a3(String name, String pwd) {
        String msg = "";
        if (name != null) {
            if ("admin".equals(name)) {
                msg = "ok";
            } else {
                msg = "用户名有误";
            }
        }
        if (pwd != null) {
            if ("1234567".equals(pwd)) {
                msg = "ok";
            } else {
                msg = "密码有误";
            }
        }
        return msg;
    }

}
