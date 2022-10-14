package com.hqf.compus.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqf.compus.Service.EmployeeService;
import com.hqf.compus.entity.Employee;
import com.hqf.compus.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
