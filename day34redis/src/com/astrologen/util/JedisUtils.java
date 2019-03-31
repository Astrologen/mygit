package com.astrologen.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * Jedis连接工具类
 */
public class JedisUtils {

    private static JedisPool jedisPool = null;

    static {
        //解析配置文件,利用ResourceBundle工具类
        ResourceBundle bundle = ResourceBundle.getBundle("jedis");
        int maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
        int maxWaitMillis = Integer.parseInt(bundle.getString("maxWaitMillis"));
        int port = Integer.parseInt(bundle.getString("port"));
        String host = bundle.getString("host");
        // 获取连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置参数
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWaitMillis);
        // 获取连接池对象
        jedisPool = new JedisPool(config,host,port);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
