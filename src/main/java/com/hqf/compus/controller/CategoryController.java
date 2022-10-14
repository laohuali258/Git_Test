package com.hqf.compus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqf.compus.Service.CategoryService;
import com.hqf.compus.common.R;
import com.hqf.compus.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    /**
     * 新增分类方法
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){

        log.info("category : {}",category);

        categoryService.save(category);

        return R.success("分类新增成功");
    }


    /**
     * 分页方法
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        log.info("当前页码：{} ， 页面长度：{}",page,pageSize);

        //获取分页构造器
        Page<Category> pageinfo = new Page<>(page,pageSize);

        //条件构造器

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        //执行查询

        categoryService.page(pageinfo,queryWrapper);

        return R.success(pageinfo);
    }

    /**
     * 删除分类方法
      * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){

        log.info("删除当前id值为 {} 的分类",ids);
       
        categoryService.remove(ids);

        return R.success("删除分类成功");
    }

    /**
     * 根据id修改分类
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类为 {} ",category);
        categoryService.updateById(category);
        return R.success("修改分类成功");
    }
}
