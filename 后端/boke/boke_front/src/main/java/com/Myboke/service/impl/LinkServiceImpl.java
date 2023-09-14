package com.Myboke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Myboke.constants.SystemConstants;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Link;
import com.Myboke.domain.vo.LinkVo;
import com.Myboke.mapper.LinkMapper;
import com.Myboke.service.LinkService;
import com.Myboke.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author Haluki
 * @since 2023-07-06 22:45:57
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link>links = list(queryWrapper);
        //转换为Vo
        List<LinkVo> vs = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(vs);
    }
}

