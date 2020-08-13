package com.rpy.qw.utils.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: myfunhome
 * @description: 条件查询参数
 * @author: 任鹏宇
 * @create: 2020-07-11 01:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单条件查询模型")
public class MenuParams {


    @ApiModelProperty(value = "父级ID")
    private Integer parentId;
    @ApiModelProperty(value = "名称")
    private String title;
    @ApiModelProperty(value = "是否带有子节点")
    private Boolean isHasChildrens;


    // 权限内容
    @ApiModelProperty(value = "权限内容")
    private String typecode;
}
