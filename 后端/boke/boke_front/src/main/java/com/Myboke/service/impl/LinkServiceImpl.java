package com.Myboke.service.impl;

import com.Myboke.domain.dto.AdLinkDto;
import com.Myboke.domain.entity.Menu;
import com.Myboke.domain.vo.MenuVo;
import com.Myboke.domain.vo.PageVo;
import com.Myboke.enums.AppHttpCodeEnum;
import com.Myboke.exception.SystemException;
import com.Myboke.utils.SecurityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Myboke.constants.SystemConstants;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Link;
import com.Myboke.domain.vo.LinkVo;
import com.Myboke.mapper.LinkMapper;
import com.Myboke.service.LinkService;
import com.Myboke.utils.BeanCopyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author Haluki
 * @since 2023-07-06 22:45:57
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private LinkMapper linkMapper;
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

    @Override
    public ResponseResult ContentgetAllLink(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        //查询所有未被删除的
        queryWrapper.eq(Link::getDelFlag, SystemConstants.NORMAL);
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        List<Link>links = page.getRecords();
        //转换为Vo
        List<LinkVo> vs = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        PageVo pageVo = new PageVo(vs,page.getTotal());
        //封装返回
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getLinkListByDetail(String linkName, String status) {
        if (linkName!=null){
            LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
            linkLambdaQueryWrapper.eq(Link::getName,linkName);
            linkLambdaQueryWrapper.eq(Link::getDelFlag,SystemConstants.NORMAL);

            List<Link> links = list(linkLambdaQueryWrapper);
            List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
            return ResponseResult.okResult(linkVos);
        }
        if (StringUtils.hasText(status)){
            LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
            linkLambdaQueryWrapper.eq(Link::getStatus,status);
            linkLambdaQueryWrapper.eq(Link::getDelFlag,SystemConstants.NORMAL);

            List<Link> links = list(linkLambdaQueryWrapper);
            List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
            return ResponseResult.okResult(linkVos);
        }
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(Link::getStatus,status);
        linkLambdaQueryWrapper.eq(Link::getName,linkName);
        linkLambdaQueryWrapper.eq(Link::getDelFlag,SystemConstants.NORMAL);
        List<Link> links = list(linkLambdaQueryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult add(AdLinkDto adLinkDto) {


        Link link = BeanCopyUtils.copyBean(adLinkDto,Link.class);
        try{
            //创建SimpleDateFormat对象，指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取当前时间
            Date now = new Date();
            //将当前时间格式转化为指定格式的字符串
            String strNow = sdf.format(now);
            //将字符串转换为Date类型
            Date date = sdf.parse(strNow);
            link.setCreateTime(date);
            link.setUpdateTime(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //save(link);
        linkMapper.insert(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteByLinkId(Long id) {
        if (id == null){
            LOG.error("delete Link id is null");
            return null;
        }
        linkMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getById_for_Modify(Long id) {
        if (id == null){
            LOG.error("modify needs id is null");
            return null;
        }
        /*能够修改菜单，但是修改的时候不能把父菜单设置为当前菜单，如果设置了需要给出相应的提示。并且修改失败。*/

        LambdaQueryWrapper<Link> linkLambdaQueryWrappe = new LambdaQueryWrapper<>();
        linkLambdaQueryWrappe.eq(Link::getId,id);
        List<Link> links = list(linkLambdaQueryWrappe);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links,LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult changeLinkStatus(String id, String status) {
        if (id == null){
            LOG.error("get link id is null");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Link link = linkMapper.selectById(id);
        //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
        if (status.equals("0")){
            link.setStatus("0");
        }else if (status.equals("1")){
            link.setStatus("1");
        }
        updateById(link);
        LinkVo linkVo = BeanCopyUtils.copyBean(link,LinkVo.class);

        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult myUpdateById(String json) {
        /*
        Link link = new Link();
        link.setAddress(linkVo.getAddress());
        link.setDescription(linkVo.getDescription());
        link.setId(linkVo.getId());
        link.setLogo(linkVo.getLogo());
        link.setName(linkVo.getName());
        link.setStatus(linkVo.getStatus());
*/

        Object toJSON = JSON.toJSON(json);
        List<Link> links = JSON.parseArray(String.valueOf(toJSON),Link.class);
        updateBatchById(links);

        return ResponseResult.okResult();

    }
}

