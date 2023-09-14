package com.Myboke.mapper;

import com.Myboke.domain.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-19 18:30:25
 */
public interface TagMapper extends BaseMapper<Tag> {

    void myUpdateById(Long id, int flag);
}

