package com.kuang.dao;

import com.kuang.pojo.User;
import com.kuang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

    @Test
    public void test() {
        //第一步：获得SqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        try {
            //方式一：getMapper
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            User user = userDao.getUserById(1);

            System.out.println(user);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

}
