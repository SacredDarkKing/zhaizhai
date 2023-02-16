package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.mapper.UserMapper;
import com.alice.zhaizhai.pojo.User;
import com.alice.zhaizhai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月03日 13:13
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }
}
