package com.astrologen.test;

import com.astrologen.dao.ContactDao;
import com.astrologen.entity.Contact;
import com.astrologen.service.ContactService;
import com.astrologen.util.JedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JedisTest {
    @Test
    public void test(){
        Jedis jedis = new Jedis("localhost", 6379);
        // 存储String类型
        jedis.set("name1","jack");
        jedis.set("name2","rose");
        jedis.set("name3","lily");
        System.out.print(jedis.get("name1"));
        System.out.print(jedis.get("name2"));
        System.out.println(jedis.get("name3"));
        // 存储list类型
        jedis.lpush("users","jack","rose");
        System.out.println(jedis.lrange("users",0,-1));
        // hash
        jedis.hset("myHash","name","jack");
        Map<String,String> map = new HashMap<>();
        map.put("age","12");
        map.put("wife","rose");
        jedis.hmset("myHash",map);
        List<String> myHash = jedis.hmget("myHash","name","age","wife");
        System.out.println(myHash);
        jedis.close();
    }

    @Test // 连接池
    public void test2(){
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(10);
        // 设置最大等待时间
        config.setMaxWaitMillis(30000);
        // 获取连接池对象
        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("name","zarck");
        System.out.println(jedis.get("name"));
    }

    @Test // jedis链接工具类测试
    public void test3(){
        Jedis jedis = JedisUtils.getJedis();
        Long contacts = jedis.del("contacts");
        System.out.println(contacts);
    }

    @Test
    public void test4() throws JsonProcessingException {
        ContactService service = new ContactService();
        String all = service.findAll();
        System.out.println(all);
    }

    @Test
    public void test5(){
        ContactDao dao = new ContactDao();
        List<Contact> all = dao.findAll();
        System.out.println(all);
    }
}
