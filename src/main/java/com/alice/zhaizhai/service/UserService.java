package com.alice.zhaizhai.service;

import com.alice.zhaizhai.pojo.User;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月03日 13:13
 */
public interface UserService {
    User getByPhone(String phone);

    User getById(Long id);

    void save(User user);
}
