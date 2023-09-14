package com.Myboke.controller;

import com.Myboke.annotation.SystemLog;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.User;
import com.Myboke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updataUserInfo(@RequestBody User user){

        return userService.updataUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
       return userService.register(user);
    }
}
