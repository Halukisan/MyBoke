package com.Myboke.service;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
