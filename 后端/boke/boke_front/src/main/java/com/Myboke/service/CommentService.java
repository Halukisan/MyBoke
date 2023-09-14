package com.Myboke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-07-07 17:03:03
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

