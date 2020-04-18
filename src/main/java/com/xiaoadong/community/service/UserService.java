package com.xiaoadong.community.service;

import com.xiaoadong.community.mapper.UserMapper;
import com.xiaoadong.community.model.User;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void createOrUpdate(User user) {

        User dbuser = userMapper.findByAccountId(user.getAccountId());

        if (dbuser == null) {
            // 插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            // 更新
            dbuser.setGmtModified(System.currentTimeMillis());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            userMapper.update(dbuser);
        }

    }
}
