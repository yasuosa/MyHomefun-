package com.rpy.qw.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: myfunhome
 * @description: 角色 权限中间
 * @author: 任鹏宇
 * @create: 2020-07-11 18:56
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "角色和菜单(权限)中间类模型")
public class RoleAndMenu {
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色Id")
    private Integer rid;

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单(权限)Id")
    private List<Integer> mids;
}
