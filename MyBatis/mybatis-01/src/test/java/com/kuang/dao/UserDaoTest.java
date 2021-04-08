package com.kuang.dao;

import com.kuang.pojo.User;
import com.kuang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoTest {

    @Test
    public void test() {
        //第一步：获得SqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        try {
            //方式一：getMapper
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            List<User> userList = userDao.getUserList();

            //方式二：(不推荐使用)
            //List<User> userList = sqlSession.selectList("com.kuang.dao.UserDao.getUserList");

            for (User user : userList) {
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    //一个方法对应一个sql语句
    public void getUserById() {
        //这句是固定的
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //在中间实现查询
        //在配置文件中得到要查询的信息
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //给id赋值
        User user = mapper.getUserById(1);
        System.out.println(user);

        //这句也是固定的
        sqlSession.close();
    }

    //增删改需要提交事务
    @Test
    public void addUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int addUser = mapper.addUser(new User(4, "哈哈", "123333"));

        if (addUser > 0) {
            System.out.println("插入成功!");
        }

        //提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void updateUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int updateUser = mapper.updateUser(new User(4,"呵呵","13455688"));
        if (updateUser > 0) {
            System.out.println("更新成功!");
        }
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void deleteUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int deleteUser = mapper.deleteUser(4);
        if (deleteUser > 0) {
            System.out.println("删除成功!");
        }
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void insertUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, Object> map = new HashMap<>();

        map.put("userid",5);
        map.put("userName","哈哈");
        map.put("passWord",233333);
        int addUser2 = mapper.addUser2(map);
        if (addUser2 > 0) {
            System.out.println("插入成功!");
            sqlSession.commit();
        }

        sqlSession.close();
    }

    @Test
    public void getUserLike() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userLike = mapper.getUserLike("%李%");

        for (User user : userLike) {
            System.out.println(user);
        }

        sqlSession.close();
    }
}
