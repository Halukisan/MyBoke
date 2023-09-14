package com.Myboke.service.impl;

import com.Myboke.domain.dto.AdCategoryDto;
import com.Myboke.domain.vo.PageVo;
import com.Myboke.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Myboke.constants.SystemConstants;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Category;
import com.Myboke.domain.vo.CategoryVo;
import com.Myboke.mapper.CategoryMapper;
import com.Myboke.service.CategoryService;
import com.Myboke.utils.BeanCopyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-07-06 11:34:47
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryService categoryService;
    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Category>categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(Category::getStatus,SystemConstants.category_Normal);
        categoryLambdaQueryWrapper.eq(Category::getDelFlag,SystemConstants.category_clean_no);

        List<Category> categories = list(categoryLambdaQueryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    /**
     * 获取category列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseResult categoryList(Integer pageNum, Integer pageSize) {
        //查询条件
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //状态时正式发布的
        lambdaQueryWrapper.eq(Category::getStatus,SystemConstants.category_Normal);

        //分页查询
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);



        //封装查询结果
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryVo.class);

        PageVo pageVo = new PageVo(categoryVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(AdCategoryDto adCategoryDto) {
        //添加 博客
        Category category = BeanCopyUtils.copyBean(adCategoryDto, Category.class);
        save(category);

/*
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);

 */
        return ResponseResult.okResult();


    }

    @Override
    public ResponseResult deleteCategory(List<Long> categoryIds) {
        if (categoryIds == null){
            LOG.error("delete category data is null");
            return null;
        }
        categoryMapper.deleteBatchIds(categoryIds);
        return ResponseResult.okResult();
    }
}

