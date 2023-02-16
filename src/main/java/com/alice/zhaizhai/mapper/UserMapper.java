package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月03日 13:13
 */
@Repository
public interface UserMapper {
    User selectByPhone(String phone);

    User selectById(Long id);

    void insert(User user);
}
