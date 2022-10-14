package com.hqf.compus.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqf.compus.Service.SetmealService;
import com.hqf.compus.entity.Setmeal;
import com.hqf.compus.mapper.SetmealMapper;
import org.springframework.stereotype.Service;


@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
