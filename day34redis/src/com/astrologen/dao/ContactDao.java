package com.astrologen.dao;

import com.astrologen.entity.Contact;
import com.astrologen.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ContactDao {

    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    /**
     * 查询所有联系人
     * @return
     */
    public List<Contact> findAll(){
        String sql = "select * from contact2;";
        return template.query(sql,new BeanPropertyRowMapper<>(Contact.class));
    }
}
