package com.rpy.qw.post.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: myfunhome
 * @description: 审批回馈
 * @author: 任鹏宇
 * @create: 2020-07-16 00:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "审批操作的模型")
public class FeedBackContentVo implements Serializable {

    /**
     * 审批单id
     */
    @ApiModelProperty(name = "auditingId",value = "审核单的ID",required = true)
    private String auditingId;

    /**
     * 反馈值
     */
    @ApiModelProperty(name = "feedContent",value = "反馈")
    private String feedContent;
}
