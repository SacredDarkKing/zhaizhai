package com.alice.zhaizhai.service;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.pojo.Employee;

public interface EmployeeService {
    Employee get(String username);

    void save(Employee employee);

    MyPage getPage(int page, int pageSize, String name);

    void update(Employee employee);


    Employee get(Long id);
}
