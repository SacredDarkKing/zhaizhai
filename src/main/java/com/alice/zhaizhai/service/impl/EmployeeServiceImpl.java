package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.mapper.EmployeeMapper;
import com.alice.zhaizhai.pojo.Employee;
import com.alice.zhaizhai.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月14日 19:14
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee get(String username) {
        return employeeMapper.selectOneByUsername(username);
    }

    @Override
    public void save(Employee employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public MyPage getPage(int page, int pageSize, String name) {
        Page pageInfo = PageHelper.startPage(page, pageSize);
        employeeMapper.selectListLikeName(name);
        return new MyPage(pageInfo);
    }

    @Override
    public void update(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectById(id);
    }
}
