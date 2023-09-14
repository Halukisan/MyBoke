package com.Myboke.service.impl;

import com.Myboke.domain.entity.Role;
import com.Myboke.mapper.RoleMapper;
import com.Myboke.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Haluki
 * @since 2023-07-20 12:18:12
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是，返回集合中只需要有admin
        if(id == 1L){
            List<String>roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息

        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}

