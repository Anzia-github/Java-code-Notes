package com.kuang.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuang.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class UserController {

    @RequestMapping(value = "/j1"/*, produces = "application/json;charset=utf-8"*/)
    @ResponseBody //它就不会走视图解析器，会直接返回一个字符串
    public String json1() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        //创建一个对象
        User user = new User("秦将1号", 3, "男");

        String str = mapper.writeValueAsString(user);

        return str;
    }

    @RequestMapping("/j2")
    @ResponseBody
    public String json2() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> userList = new ArrayList<>();

        User user1 = new User("1号", 3, "男");
        User user2 = new User("2号", 3, "男");
        User user3 = new User("3号", 3, "男");
        User user4 = new User("4号", 3, "男");

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        String str = mapper.writeValueAsString(userList);

        return str;
    }

    @RequestMapping("/j4")
    @ResponseBody
    public String json4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> userList = new ArrayList<>();

        User user1 = new User("1号", 3, "男");
        User user2 = new User("2号", 3, "男");
        User user3 = new User("3号", 3, "男");
        User user4 = new User("4号", 3, "男");

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        String string = JSON.toJSONString(userList);

        return string;

    }

}
