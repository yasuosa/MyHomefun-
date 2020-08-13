package com.rpy.qw.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_music")
@ApiModel(value = "音乐模型")
public class Music implements Serializable {
    /**
     * 音乐id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "音乐id")
    private Integer id;

    /**
     * 是否删除 0否 1是
     */
    @TableId(value = "deleted", type = IdType.INPUT)
    @ApiModelProperty(value = "是否删除 0否 1是")
    private Integer deleted;

    /**
     * 歌曲名
     */
    @ApiModelProperty(value = "歌曲名")
    @TableField(value = "name")
    private String name;

    /**
     * 歌手
     */
    @ApiModelProperty(value = "歌手")
    @TableField(value = "artist")
    private String artist;

    /**
     * 歌曲链接
     */
    @ApiModelProperty(value = "歌曲链接")
    @TableField(value = "url")
    private String url;

    /**
     * 封面链接
     */
    @ApiModelProperty(value = "封面链接")
    @TableField(value = "cover")
    private String cover;

    /**
     * 歌词
     */
    @ApiModelProperty(value = "歌词")
    @TableField(value = "lrc")
    private String lrc;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 是否启用 0否 1是
     */
    @ApiModelProperty(value = "是否启用 0否 1是")
    @TableField(value = "enable")
    private Integer enable;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DELETED = "deleted";

    public static final String COL_NAME = "name";

    public static final String COL_ARTIST = "artist";

    public static final String COL_URL = "url";

    public static final String COL_COVER = "cover";

    public static final String COL_LRC = "lrc";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_ENABLE = "enable";
}