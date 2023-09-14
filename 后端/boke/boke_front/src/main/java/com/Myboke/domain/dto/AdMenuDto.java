package com.Myboke.domain.dto;

import com.Myboke.domain.entity.Menu;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdMenuDto {
    //菜单ID

    private Long id;

    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;

    //是否为外链（0是 1否）
    private Integer isFrame;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;

    //菜单状态（0正常 1停用）
    private String status;

    //菜单图标
    private String icon;
    //菜单状态（0显示 1隐藏）
    private String visible;
    @TableField(exist = false)
    private List<Menu>children;
}


