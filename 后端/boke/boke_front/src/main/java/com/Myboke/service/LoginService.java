package com.Myboke.service;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
