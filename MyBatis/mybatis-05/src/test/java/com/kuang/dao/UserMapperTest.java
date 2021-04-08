package com.kuang.dao;

import com.kuang.pojo.User;
import com.kuang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {
    @Test
    public void test() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //利用注解实现
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //List<User> users = mapper.getUsers();
        //for (User user : users) {
        //    System.out.println(user);
        //}

        //User userByID = mapper.getUserByID(1);
        //System.out.println(userByID);

        //mapper.addUser(new User(6,"Hello","123456"));

        //mapper.updateUser(new User(6,"你好","13456688"));

        mapper.deleteUser(6);

        sqlSession.close();
    }
}
