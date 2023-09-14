package boke.controller;

import com.Myboke.domain.ResponseResult;
import com.Myboke.service.SystemRoleService;
import com.Myboke.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/role")
public class SystemRoleController {
    @Autowired
    private SystemRoleService systemRoleService;

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return systemRoleService.listAllRole();
    }
    @GetMapping("/list")
    public ResponseResult rolelist(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam(value = "roleName",required = false) String roleName,
                                   @RequestParam(value = "status",required = false) String status){
        if (StringUtils.hasText(roleName) || StringUtils.hasText(status)){
            return systemRoleService.getRoleListByDetail(pageNum,pageSize,roleName,status);
        }
        return systemRoleService.getRoleList(pageNum,pageSize);
    }
}
