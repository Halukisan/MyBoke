package com.Myboke.service;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AdMenuDto;
import com.Myboke.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-07-20 12:12:53
 */
public interface MenuService extends IService<Menu> {

    List<String> selectRolekeyByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList();

    ResponseResult getMenuListByDetail(String menuName, String status);

    ResponseResult add(AdMenuDto adMenuDto);

    ResponseResult deleteByMenuId(Long id);
}

