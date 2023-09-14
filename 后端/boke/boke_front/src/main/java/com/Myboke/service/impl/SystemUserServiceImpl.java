package com.Myboke.service.impl;

import com.Myboke.constants.SystemConstants;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AdUserDto;
import com.Myboke.domain.entity.User;
import com.Myboke.domain.vo.PageVo;
import com.Myboke.domain.vo.UserVo;
import com.Myboke.mapper.UserMapper;
import com.Myboke.service.SystemUserService;

import com.Myboke.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;




@Service("SystemUserService")
public class SystemUserServiceImpl extends ServiceImpl<UserMapper, User> implements SystemUserService {
    private static final Logger LOG = LoggerFactory.getLogger(SystemUserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getDelFlag, SystemConstants.User_still_survive);

        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,userLambdaQueryWrapper);

        List<UserVo> userVos = BeanCopyUtils.copyBeanList(page.getRecords(),UserVo.class);
        PageVo pageVo = new PageVo(userVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
    @Override
    public ResponseResult getUserByUserDetail(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,userName);
        userLambdaQueryWrapper.eq(User::getStatus,SystemConstants.User_still_survive);
        userLambdaQueryWrapper.eq(User::getPhonenumber,phonenumber);
        userLambdaQueryWrapper.eq(User::getStatus,status);

        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,userLambdaQueryWrapper);

        List<UserVo> userVos = BeanCopyUtils.copyBeanList(page.getRecords(),UserVo.class);
        PageVo pageVo = new PageVo(userVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }
    @Override
    public ResponseResult deleteUserById(List<Long> id) {
        if (id == null){
            LOG.error("delete user id is null");
            return null;
        }
        userMapper.deleteBatchIds(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult add(AdUserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto,User.class);
        save(user);
        return ResponseResult.okResult();
    }


}
