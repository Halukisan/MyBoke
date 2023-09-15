package com.Myboke.service;

import com.Myboke.domain.dto.AdLinkDto;
import com.Myboke.domain.dto.AdMenuDto;
import com.Myboke.domain.vo.LinkVo;
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


    ResponseResult ContentgetAllLink(Integer pageNum,Integer pageSize);

    ResponseResult getLinkListByDetail(String linkName, String status);

    ResponseResult add(AdLinkDto adLinkDto);

    ResponseResult deleteByLinkId(Long id);

    ResponseResult getById_for_Modify(Long id);

    ResponseResult changeLinkStatus(String id, String status);
    ResponseResult myUpdateById(String json);
}

