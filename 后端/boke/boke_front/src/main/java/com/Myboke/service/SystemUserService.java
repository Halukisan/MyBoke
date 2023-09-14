package com.Myboke.service;


import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AdUserDto;

import java.util.List;


public interface SystemUserService {

    ResponseResult getUserList(Integer pageNum, Integer pageSize);

    ResponseResult deleteUserById(List<Long> id);

    ResponseResult add(AdUserDto userDto);

    ResponseResult getUserByUserDetail(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status);
}
