package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月02日 15:51
 */
@Repository
public interface EmployeeMapper {
    Employee selectOneByUsername(String username);

    void insert(Employee employee);

    List<Employee> selectListLikeName(String name);

    void update(Employee employee);

    Employee selectById(Long id);
}

