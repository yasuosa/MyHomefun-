package com.rpy.qw.sys.vo;

import com.rpy.qw.sys.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: myfunhome
 * @description: 展示user
 * @author: 任鹏宇
 * @create: 2020-07-26 01:12
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo  extends User {



    @ApiModelProperty(value = "角色ids")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "角色名")
    private List<String> roleNames;
}
