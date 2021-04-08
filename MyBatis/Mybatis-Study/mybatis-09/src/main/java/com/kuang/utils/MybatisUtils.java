package com.kuang.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

//一句话,读取配置文件,获得工厂

//从 XML 中构建 SqlSessionFactory （建议）
//工厂模式 --> 产品
//sqlSessionFactory --> sqlSession
public class MybatisUtils {

    //因为静态代码块和下面的方法都要用，所以提升该处作用域
    private static SqlSessionFactory sqlSessionFactory;
    //加个静态代码块，初始就加载了
    static{
        //这三句话是死的，工具类
        //使用Mybatis的第一步：获取sqlSessionFactory对象
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //既然有了SqlSessionFactory，顾名思义，我们就可以从中获得SqlSession的实例了
    //SqlSession完全包含了面向数据库执行SQL命令所需的所有方法
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }
}
