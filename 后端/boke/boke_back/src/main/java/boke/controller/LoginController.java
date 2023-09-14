package boke.controller;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.LoginUser;
import com.Myboke.domain.entity.Menu;
import com.Myboke.domain.entity.User;
import com.Myboke.domain.vo.AdminUserInfoVo;
import com.Myboke.domain.vo.RoutersVo;
import com.Myboke.domain.vo.UserInfoVo;
import com.Myboke.enums.AppHttpCodeEnum;
import com.Myboke.exception.SystemException;
import com.Myboke.service.LoginService;
import com.Myboke.service.MenuService;
import com.Myboke.service.RoleService;
import com.Myboke.utils.BeanCopyUtils;
import com.Myboke.utils.RedisCache;
import com.Myboke.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectRolekeyByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String>roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);

    }
    @GetMapping("getRouters")
    private  ResponseResult<RoutersVo>getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu>menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回

        return ResponseResult.okResult(new RoutersVo(menus));
    }
    @PostMapping("/user/logout")
    private ResponseResult logout(){
        return loginService.logout();
    }

}