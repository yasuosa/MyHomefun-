package com.rpy.qw.sys.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rpy.qw.post.vo.SimpleCommentVo;
import com.rpy.qw.post.vo.SimplePostVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 首页用户信息
 * @author: 任鹏宇
 * @create: 2020-07-31 14:06
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHomeVo  implements Serializable {

    private Integer userId;

    private String username;

    private String nickname;

    private String address;

    /**
     * 积分
     */
    private Double points;

    private Integer sex;

    private String sign;

    private String headerUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    private List<SimplePostVo> postList;

    private List<SimpleCommentVo> commentList;
}
