package com.hqf.compus.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hqf.compus.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long ids);
}
