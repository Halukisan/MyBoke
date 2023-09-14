package com.Myboke.service;

import com.Myboke.domain.dto.AdCategoryDto;
import com.Myboke.domain.vo.CategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Category;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-07-06 11:34:45
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult categoryList(Integer pageNum, Integer pageSize);
    ResponseResult addCategory(AdCategoryDto adCategoryDto);
    ResponseResult deleteCategory(List<Long> categoryIds);

}

