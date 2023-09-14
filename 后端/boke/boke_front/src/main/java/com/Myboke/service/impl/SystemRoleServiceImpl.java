package com.Myboke.service.impl;

import com.Myboke.constants.SystemConstants;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.entity.Role;
import com.Myboke.domain.vo.PageVo;
import com.Myboke.domain.vo.RoleVo;
import com.Myboke.mapper.RoleMapper;
import com.Myboke.service.SystemRoleService;
import com.Myboke.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SystemRoleService")
public class SystemRoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements SystemRoleService {
    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(Role::getDelFlag, SystemConstants.User_still_survive);


        List<Role> roles = list(categoryLambdaQueryWrapper);
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);

        return ResponseResult.okResult(roleVos);

    }

    @Override
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(Role::getDelFlag,SystemConstants.Role_still_survive);

        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,roleLambdaQueryWrapper);

        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(page.getRecords(),RoleVo.class);
        PageVo pageVo = new PageVo(roleVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getRoleListByDetail(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(Role::getRoleName,roleName);
        roleLambdaQueryWrapper.eq(Role::getStatus,status);
        roleLambdaQueryWrapper.eq(Role::getDelFlag,SystemConstants.Role_still_survive);
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,roleLambdaQueryWrapper);
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleVo.class);
        PageVo pageVo = new PageVo(roleVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
