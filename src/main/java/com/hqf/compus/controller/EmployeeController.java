package com.hqf.compus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqf.compus.Service.EmployeeService;
import com.hqf.compus.common.R;
import com.hqf.compus.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 登录方法
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request , @RequestBody Employee employee){

        //将页面提交的密码通过md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(lambdaQueryWrapper);

        //如果没有查询到则返回失败登录结果
        if (emp==null){
            return R.error("登录失败");
        }

        //密码对比，如果不一致则返回失败登录结果

        if (!emp.getPassword().equals(password)){

            return R.error("登录失败，密码不一致");

        }

        //查看员工状态，如果已为禁用状态则返回员工已禁用结果

        if (emp.getStatus()==0){

            
            return R.error("登录失败，员工已禁用");
        }

        //登录成功，将员工id存入Session并返回登录结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);

    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request ){
        //清理Session中保存的员工id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }


    /**
     * 员工新增方法
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息为：{}"+employee.toString() );

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
/*        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);*/

        employeeService.save(employee);

        return R.success("新增员工成功");
    }


    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        log.info("page = {} , pageSize = {} , name = {} ",page,pageSize,name);

        //构造分页构造器
        Page pageinfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageinfo,queryWrapper);


        return R.success(pageinfo);
    }


    /**
     * 根据id更新员工状态
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee) {

        //log.info(employee.toString());

  /*      Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setUpdateUser(empId);
        employee.setUpdateTime(LocalDateTime.now());*/

        long id = Thread.currentThread().getId();
        log.info("当前线程id为 {} ",id);

        employeeService.updateById(employee);

        return R.success("员工状态更新成功");
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){

        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return  R.success(employee);

        }
        return  R.error("查询不到该员工信息");

    }
}
