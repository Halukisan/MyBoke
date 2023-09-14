package com.Myboke.service;

import com.Myboke.domain.ResponseResult;

public interface SystemRoleService {

    ResponseResult listAllRole();

    ResponseResult getRoleList(Integer pageNum, Integer pageSize);

    ResponseResult getRoleListByDetail(Integer pageNum, Integer pageSize, String roleName, String status);
}
