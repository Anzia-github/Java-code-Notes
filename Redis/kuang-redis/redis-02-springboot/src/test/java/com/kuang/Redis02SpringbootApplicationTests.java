package com.kuang;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kuang.pojo.User;
import com.kuang.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class Redis02SpringbootApplicationTests {

    @Resource
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {

        //redisTemplate
        //opsForValue
        //opsForSet
        //opsForHash
        //opsForList
        //opsForZSet
        //opsForGeo
        //除了基本的操作，我们常用的方法都可以直接通过redisTeplate操作，比如事务，和基本的CRUD
        //获取redis的连接对象
        //RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //connection.flushDb();
        //connection.flushAll();
        redisTemplate.opsForValue().set("mykey","kuangshen");
        System.out.println(redisTemplate.opsForValue().get("mykey"));

    }

    @Test
    public void test() throws JsonProcessingException {

        //真实的开发一般都使用json来传递对象
        User user = new User("狂神说", 3);
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

    @Test
    public void test1() {
        redisUtil.set("name","kuangshen");
        System.out.println(redisUtil.get("name"));

    }
}
