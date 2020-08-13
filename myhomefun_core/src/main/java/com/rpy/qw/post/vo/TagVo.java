package com.rpy.qw.post.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rpy.qw.post.domain.Tag;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: myhomefun
 * @description:
 * @author: 任鹏宇
 * @create: 2020-08-03 00:34
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo  extends Tag {

    /**
     * 文章个数
     */
    @ApiModelProperty(value = "文章个数")
    @TableField(exist = false)
    private Integer count;
}
