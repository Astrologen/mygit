package com.astrologen.service;

import com.astrologen.dao.ContactDao;
import com.astrologen.entity.Contact;
import com.astrologen.util.JedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * 业务逻辑层
 */
public class ContactService {

    private ContactDao dao = new ContactDao();

    /**
     * 查询所有联系人,返回json对象字符串
     * @return
     */
    public String findAll() throws JsonProcessingException {
        // 首先从Redis数据库中查找
        Jedis jedis = JedisUtils.getJedis();
        String contacts = jedis.get("contacts");
        if(contacts==null){
            // 无数据再从MySQL中查找
            System.out.println("从数据库中查找");
            List<Contact> listContact = dao.findAll();

            // 存入Redis中
            ObjectMapper mapper = new ObjectMapper();
            contacts = mapper.writeValueAsString(listContact);
            jedis.set("contacts",contacts);
        }
        System.out.println("从Redis中查找");
        // 关闭资源
        jedis.close();
        // 有数据直接返回
        return contacts;
    }
}
