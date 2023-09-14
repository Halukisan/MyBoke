package com.Myboke.service;

import com.Myboke.domain.dto.AddArticleDto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult deleteArticle(List<Integer> articleId);
}
