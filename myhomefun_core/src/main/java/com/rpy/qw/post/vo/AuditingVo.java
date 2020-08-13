package com.rpy.qw.post.vo;

import com.rpy.qw.post.domain.Auditing;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: myfunhome
 * @description: 审核单
 * @author: 任鹏宇
 * @create: 2020-07-14 20:22
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("审批单模型")
public class AuditingVo  extends Auditing {

    @ApiModelProperty(value = "专栏id")
    private Integer categoryId;
    @ApiModelProperty(value = "专栏名称")
    private String categoryName;
    @ApiModelProperty(value = "分类id")
    private Integer typeId;
    @ApiModelProperty(value = "分类id")
    private String typeName;
    @ApiModelProperty(value = "帖子标题")
    private String postTitle;








}
