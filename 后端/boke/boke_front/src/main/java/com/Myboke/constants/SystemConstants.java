package com.Myboke.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    public static  final  String STATUS_NORMAL="0";

    /**
     * 友链状态为通过
     */
    public static  final  String LINK_STATUS_NORMAL="0";
    /**
     *评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     *评论类型为：友链评论
     */
    public static final String LINK_COMMENT = "0";
    public static final String MENU = "C";
    public static final String BUTTON = "F";
    /** 正常状态 */
    public static final String NORMAL = "0";


    public static final String ADMAIN = "1";

    /**
     * 以下为category的状态表示
     * 状态0表示正常
     * 状态1表示禁用
     */
    public static final String category_Normal = "0";
    public static final String category_Forbidden = "0";
    /**
     * 以下为category的删除情况
     * 状态1表示已删除
     * 状态0表示未删除
     */
    public static final String category_clean_no = "0";
    public static final String category_clean_yes = "1";

    /**
     * 以下表示后端平台查询用户是否被删除
     * 0表示未被删除
     * 1表示已被删除
     */
    public static final String User_already_delete = "1";
    public static final String User_still_survive = "0";
    /**
     * 以下表示后端管理用户的权限状态
     * 0表示未删除
     * 1表示已经删除
     */
    public static final String Role_already_delete = "1";
    public static final String Role_still_survive = "0";
    /**
     * 以下表示菜单Menu的状态
     * 0表示未删除
     * 1表示删除
     */
    public static final String Menu_already_delete = "1";
    public static final String Menu_still_survive = "0";
}