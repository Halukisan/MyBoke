package com.Myboke.service.impl;

import com.Myboke.constants.SystemConstants;
import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AdMenuDto;
import com.Myboke.domain.entity.Menu;
import com.Myboke.domain.vo.MenuVo;
import com.Myboke.mapper.MenuMapper;
import com.Myboke.service.MenuService;
import com.Myboke.utils.BeanCopyUtils;
import com.Myboke.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.redis.connection.ReactiveStreamCommands.TrimCommand.stream;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author Haluki
 * @since 2023-07-20 12:12:54
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private static final Logger LOG = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<String> selectRolekeyByUserId(Long id) {
        //如果是管理员，返回所有的数据
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所有具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()){
            //如果是 返回所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else {
            //否则 当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        List<Menu>menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getMenuList() {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getDelFlag,SystemConstants.Menu_still_survive);;

        //Menu中包含子目录，所以不能用分页查询

        //Page<Menu> page = new Page<>(1,10);
        //page(page,menuLambdaQueryWrapper);

        //List<Menu> menus = page.getRecords();
        List<Menu> menus = list(menuLambdaQueryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult getMenuListByDetail(String menuName, String status) {
        if (menuName!=null){
            LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            menuLambdaQueryWrapper.eq(Menu::getMenuName,menuName);
            menuLambdaQueryWrapper.eq(Menu::getDelFlag,SystemConstants.Menu_still_survive);

            List<Menu> menus = list(menuLambdaQueryWrapper);
            List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
            return ResponseResult.okResult(menuVos);
        }
        if (StringUtils.hasText(status)){
            LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            menuLambdaQueryWrapper.eq(Menu::getStatus,status);
            menuLambdaQueryWrapper.eq(Menu::getDelFlag,SystemConstants.Menu_still_survive);

            List<Menu> menus = list(menuLambdaQueryWrapper);
            List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
            return ResponseResult.okResult(menuVos);
        }
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getStatus,status);
        menuLambdaQueryWrapper.eq(Menu::getMenuName,menuName);
        menuLambdaQueryWrapper.eq(Menu::getDelFlag,SystemConstants.Menu_still_survive);
        List<Menu> menus = list(menuLambdaQueryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult add(AdMenuDto adMenuDto) {
        Menu parentMenu = menuMapper.selectById(adMenuDto.getParentId());

        // 创建一个 LocalDateTime 对象
        LocalDateTime localDateTime = LocalDateTime.now();

        // 将 LocalDateTime 转换为 Date
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        if (parentMenu!=null){
         adMenuDto.setComponent(parentMenu.getComponent()+parentMenu.getPath());
        }else{
            adMenuDto.setComponent(adMenuDto.getPath());
        }
        adMenuDto.setCreateTime(date);
        Menu menu = BeanCopyUtils.copyBean(adMenuDto,Menu.class);

        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteByMenuId(Long id) {
        if (id == null){
            LOG.error("delete Menu id is null");
            return null;
        }
        menuMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getById_for_Modify(Long id) {
        if (id == null){
            LOG.error("modify needs id is null");
            return null;
        }
        /*能够修改菜单，但是修改的时候不能把父菜单设置为当前菜单，如果设置了需要给出相应的提示。并且修改失败。*/

        LambdaQueryWrapper<Menu> menuLambdaQueryWrappe = new LambdaQueryWrapper<>();
        menuLambdaQueryWrappe.eq(Menu::getId,id);
        List<Menu> menus = list(menuLambdaQueryWrappe);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus,MenuVo.class);

        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult updateMenu(List<MenuVo> menuVos) {
        MenuVo menuVo = menuVos.get(0);
        Menu parentMenu = menuMapper.selectById(menuVo.getParentId());
        if (menuVo.getMenuName().equals(parentMenu.getMenuName())){
            //当前菜单的名字和父菜单的名字一样，表示把当前在修改的菜单设置为父菜单了。
            ResponseResult result = new ResponseResult();
            result.setMsg("修改菜单"+menuVo.getMenuName()+"失败，上级菜单不能选择自己");
            result.setCode(500);
            return result;
        }
        Menu menu = new Menu();
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            String strNow = sdf.format(now);
            Date date = sdf.parse(strNow);
            menu.setUpdateTime(date);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }
        menu.setComponent(menuVo.getComponent());
        menu.setCreateTime(menuVo.getCreateTime());
        menu.setIcon(menuVo.getIcon());
        menu.setId(menuVo.getId());
        menu.setIsFrame(menuVo.getIsFrame());
        menu.setMenuName(menuVo.getMenuName());
        menu.setMenuType(menuVo.getMenuType());
        menu.setOrderNum(menuVo.getOrderNum());
        menu.setParentId(menuVo.getParentId());
        menu.setPath(menuVo.getPath());
        menu.setStatus(menuVo.getStatus());
        menu.setVisible(menuVo.getVisible());
        menuMapper.updateById(menu);

        return ResponseResult.okResult();
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu>menuTree = menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }
    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}

