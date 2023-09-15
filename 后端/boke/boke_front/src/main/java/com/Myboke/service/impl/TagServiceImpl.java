package com.Myboke.service.impl;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.TagListDto;
import com.Myboke.domain.entity.LoginUser;
import com.Myboke.domain.entity.Tag;
import com.Myboke.domain.vo.PageVo;
import com.Myboke.domain.vo.TagVo;
import com.Myboke.mapper.TagMapper;
import com.Myboke.service.TagService;
import com.Myboke.utils.BeanCopyUtils;
import com.Myboke.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author Haluki
 * @since 2023-07-19 18:30:30
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag>page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //封装数据返回

        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        //新建标签
        //吧新增数据转为tag类型
        Tag tag = new Tag();
        //获得[创建时间按，创建人]
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //创建人的id好，在sql中可以通过id号查询sg_user中的user_name
        tag.setCreateBy(loginUser.getUser().getId());
        try{
            //创建SimpleDateFormat对象，指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取当前时间
            Date now = new Date();
            //将当前时间格式转化为指定格式的字符串
            String strNow = sdf.format(now);
            //将字符串转换为Date类型

            Date date = sdf.parse(strNow);
            tag.setCreateTime(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        //添加到数据库
        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        //通过数据id查找数据
        Tag tag = tagMapper.selectById(id);
        //把值传入数据库进行更新
        if (tag != null){
            //把del_flag = 1 为逻辑删除
            int flag = 1;
            tagMapper.myUpdateById(id,flag);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLableById(Long id) {
        //通过id查询到tag类型数据包
        Tag tag = tagMapper.selectById(id);
        //封装成vo响应给前端
        TagVo tagVoData = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVoData);
    }

    @Override
    public ResponseResult myUpdateById(TagVo tagVo) {
        //把修改数据转化为tag类型
        Tag tag = new Tag();
        //获得[更新时间，更新人]
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //更新人的id号，在sql中可以通过id号查询sg_user中的user_name
        tag.setUpdateBy(loginUser.getUser().getId());
        //创建、更新时间
        try{
            //创建SimpleDateFormat对象，指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取当前时间
            Date now = new Date();
            //将当前时间格式转化为指定格式的字符串
            String strNow = sdf.format(now);
            //将字符串转换为Date类型
            Date date = sdf.parse(strNow);
            //时间修改
            tag.setUpdateTime(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        tag.setId(tagVo.getId());
        tag.setName(tagVo.getName());
        tag.setRemark(tagVo.getRemark());
        //更新到数据库
        tagMapper.updateById(tag);
        return ResponseResult.okResult();
    }
}

