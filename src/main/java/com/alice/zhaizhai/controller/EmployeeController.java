package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.common.R;
import com.alice.zhaizhai.pojo.Employee;
import com.alice.zhaizhai.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月14日 19:18
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //1. 将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2. 让根据页面提交的用户名username查询数据库
        Employee emp = employeeService.get(employee.getUsername());

        //3. 如果没有查询则返回登陆失败结果
        if (emp == null) {
            return R.error("用户不存在");
        }
        //4. 密码比对，如果不一致则返回登陆失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        //5. 查看员工转台，如果为封号状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("员工已禁用");
        }

        //6. 登陆成功，将员工id放入session并返回登陆成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }


    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        log.info("用户{}登出", request.getSession().getAttribute("employee"));
        request.getSession().removeAttribute("employee");
        return R.success("登出成功");
    }


    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request) {
        //设置密码，并进行md5加密
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        employee.setPassword(password);

        //获取当前用户id
        Long empId = (Long) request.getSession().getAttribute("employee");

        //设置用户的创建、修改时间
        employee.createOrUpdate(empId);

        log.info("新增员工：{}", employee);
        employeeService.save(employee);
        log.info("新增员工的id为：{}", employee.getId());
        return R.success("添加成功");
    }


    @GetMapping("/page")
    public R<MyPage> page(Integer page, Integer pageSize, String name) {
        log.info("分页信息：页码 = {}，页面大小 = {}，查询姓名 = {}", page, pageSize, name);
        MyPage employeePage = employeeService.getPage(page, pageSize, name);
        log.info(employeePage.toString());
        return R.success(employeePage);
    }

    @PutMapping
    public R<String> update(@RequestBody Employee employee, HttpServletRequest request) {
        //设置密码，并进行md5加密
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        employee.setPassword(password);
        
        employee.createOrUpdate((Long) request.getSession().getAttribute("employee"));
        log.info("请求修改员工：{}", employee.toString());
        employeeService.update(employee);
        log.info("员工{}已禁用", employee.getId());
        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable("id") Long id) {
        log.info("根据id：{}查询员工", id);
        Employee employee = employeeService.get(id);
        if (employee == null)
            return R.error("用户不存在");
        return R.success(employee);
    }

    //todo:删除用户
}
