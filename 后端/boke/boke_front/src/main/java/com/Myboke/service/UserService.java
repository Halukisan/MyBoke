package com.Myboke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-07-07 18:31:08
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updataUserInfo(User user);

    ResponseResult register(User user);
}

