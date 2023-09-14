package com.Myboke.controller;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Category;
import com.Myboke.domain.vo.CategoryVo;
import com.Myboke.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceimpl;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryServiceimpl.getCategoryList();
    }
}
