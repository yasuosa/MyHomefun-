package com.rpy.qw.post.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "post_leave_msg")
public class LeaveMessage  implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;


    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "username")
    private String username;

    @TableField(value = "content")
    private String content;

    @TableField(value = "created_time")
    private Date createdTime;

}