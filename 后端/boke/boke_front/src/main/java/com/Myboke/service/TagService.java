package com.Myboke.service;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.TagListDto;
import com.Myboke.domain.entity.Tag;
import com.Myboke.domain.vo.PageVo;
import com.Myboke.domain.vo.TagVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-07-19 18:30:28
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    List<TagVo> listAllTag();

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long id);

    ResponseResult getLableById(Long id);

    ResponseResult myUpdateById(TagVo tagVo);
}

