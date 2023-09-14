package com.Myboke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-07-06 22:45:55
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

