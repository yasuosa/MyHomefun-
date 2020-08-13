package com.rpy.qw.sys.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 用户的权限类
 * @author: 任鹏宇
 * @create: 2020-06-23 23:24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "用户角色中间类模型")
public class UserAndRoleVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;


    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色ID集合")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "角色Id")
    private Integer roleId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "角色名称")
    private String rolename;

    @ApiModelProperty(value = "用户用户名")
    private String username;
}
