package com.rpy.qw.utils.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @program: myfunhome
 * @description: 后台管理系统菜单
 * @author: 任鹏宇
 * @create: 2020-07-09 15:02
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "菜单和权限的模型")
public class MenuNode {

    /**
     * id
     */
    @ApiModelProperty(value = "菜单(权限)Id")
    private Integer id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单(权限)名称")
    private String label;


    /**
     * 图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;


    /**
     * 地址
     */
    @ApiModelProperty(value = "菜单的请求地址")
    private String path;


    /**
     * 国际化
     */
    @ApiModelProperty(value = "国际化配置")
    private Map<String,Object> meta =new HashMap<>(11);


    /**
     * 父类id
     */
    @ApiModelProperty(value = "菜单(权限)父类Id")
    private Integer parentId;

    /**
     * 组件
     */
    @ApiModelProperty(value = "菜单前端组件地址")
    private String component;


    /**
     * 子组件
     */
    @ApiModelProperty(value = "子类")
    private List<MenuNode> children =new ArrayList<>();


    /**
     * 类型[topmenu/leftmenu/permission]
     */
    @ApiModelProperty(value = "类型[topmenu/leftmenu/permission]")
    private String type;

    /**
     * topmenu:system/business
     permission:menu:addMenu
     */
    @ApiModelProperty(value = "topmenu:system/business或permission:menu:addMenu")
    private String typecode;

    /**
     * 是否展开
     */
    @ApiModelProperty(value="是否展开")
    private Integer spread;

    /**
     * 排序码
     */
    @ApiModelProperty(value="排序码")
    private Integer ordernum;

    /**
     * 状态【0不可用1可用】
     */
    @ApiModelProperty(value="状态【0不可用1可用】")
    private Integer available;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;


    @ApiModelProperty(value = "前端value")
    private Integer value;

    public Integer getValue() {
        return this.id;
    }

    /**
     * 父级名称/ 权限对应的菜单
     */
    @ApiModelProperty(value = "父级名称/ 权限对应的菜单")
    private String parentName;

}
