package com.hqf.compus.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqf.compus.Service.DishService;
import com.hqf.compus.entity.Dish;
import com.hqf.compus.mapper.DishMapper;
import org.springframework.stereotype.Service;


@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
