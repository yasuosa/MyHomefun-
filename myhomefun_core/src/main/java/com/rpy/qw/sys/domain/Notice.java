package com.rpy.qw.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_notice")
public class Notice implements Serializable {
    /**
     * id主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公告标题
     */
    @TableField(value = "title")
    private String title;


    /**
     * 公告图片
     */
    @TableField(value = "cover")
    private String cover;
    /**
     * md内容
     */
    @TableField(value = "md_content")
    private String mdContent;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 任务
     */
    @TableField(value = "task")
    private String task;

    /**
     * 0 未删除 1已删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建者id
     */
    @TableField(value = "created_by_id")
    private Integer createdById;

    /**
     * 创建者名称
     */
    @TableField(value = "created_by_name")
    private String createdByName;

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(value = "enable")
    private Integer enable;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_TITLE = "title";

    public static final String COL_CONTENT = "content";

    public static final String COL_TASK = "task";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CREATED_BY_ID = "created_by_id";

    public static final String COL_CREATED_BY_NAME = "created_by_name";

    public static final String COL_CREATED_TIME = "created_time";
}