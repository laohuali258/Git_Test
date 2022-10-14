package com.hqf.compus.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqf.compus.Service.CategoryService;
import com.hqf.compus.Service.DishService;
import com.hqf.compus.Service.SetmealService;
import com.hqf.compus.common.CustomException;
import com.hqf.compus.entity.Category;
import com.hqf.compus.entity.Dish;
import com.hqf.compus.entity.Setmeal;
import com.hqf.compus.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long ids) {
        //查询当前分类是否已经关联了菜品分类，已关联则返回一个业务异常
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId,ids);

        int count1 = dishService.count(dishQueryWrapper);

        if (count1>0){

            throw new CustomException("当前分类已经关联菜品不可删除");

        }


        //查询当前分类是否已经关联了套餐分类，已关联则返回一个业务异常

        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId,ids);

        int count2 = setmealService.count(setmealQueryWrapper);

        if (count2>0){

            throw new CustomException("当前分类已经关联套餐不可删除");
        }


        //正常删除分类

        super.removeById(ids);

    }
}
