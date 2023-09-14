package boke.controller;


import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AdUserDto;
import com.Myboke.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @GetMapping("/list")
    public ResponseResult userlist(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam(value = "userName",required = false) String userName,
                                   @RequestParam(value = "phonenumber",required = false) String phonenumber,
                                   @RequestParam(value = "status",required = false) String status){
    if (StringUtils.hasText(userName)){
        return systemUserService.getUserByUserDetail(pageNum,pageSize,userName,phonenumber,status);
    }
        return systemUserService.getUserList(pageNum,pageSize);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteUserById(@PathVariable List<Long> id){
        return systemUserService.deleteUserById(id);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AdUserDto userDto){
        return systemUserService.add(userDto);
    }

}
