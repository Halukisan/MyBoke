package com.Myboke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Myboke.constants.SystemConstants;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Comment;
import com.Myboke.domain.vo.CommentVo;
import com.Myboke.domain.vo.PageVo;
import com.Myboke.enums.AppHttpCodeEnum;
import com.Myboke.exception.SystemException;
import com.Myboke.mapper.CommentMapper;
import com.Myboke.service.CommentService;
import com.Myboke.service.UserService;
import com.Myboke.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author Haluki
 * @since 2023-07-07 17:03:05
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);

        //评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment>page = new Page<>();
        page(page,queryWrapper);

        List<CommentVo>cOmmentVoList = BeanCopyUtils.copyBeanList(page.getRecords(),CommentVo.class);

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : cOmmentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(cOmmentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论id查询所对应的子评论对应的集合
     * @param id 根评论id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);

        //排序
        queryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment>comments = list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo>toCommentVoList(List<Comment>list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过createBy查询用户的昵称并复制
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            String avatar = userService.getById(commentVo.getCreateBy()).getAvatar();
            commentVo.setUsername(nickName);
            commentVo.setAvatar(avatar);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }


        return commentVos;
    }


}

