package com.Myboke.mapper;

import com.Myboke.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author Haluki
 * @since 2023-07-20 12:12:49
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<String> selectPermsByUserId(Long id);
}

