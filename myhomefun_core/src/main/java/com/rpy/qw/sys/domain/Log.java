package com.rpy.qw.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.rpy.qw.utils.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_log")
public class Log implements Serializable {
    /**
     * 日志id
     */
    @TableId(value = "log_id", type = IdType.INPUT)
    @Excel(name = "编号")
    private Integer logId;

    /**
     * 请求的地址
     */
    @TableField(value = "log_url")
    @Excel(name = "请求地址")
    private String logUrl;

    /**
     * 请求参数
     */
    @TableField(value = "log_params")
    @Excel(name = "请求参数")
    private String logParams;

    /**
     * 访问状态 1正常 0异常
     */
    @TableField(value = "log_status")
    @Excel(name = "访问状态")
    private Integer logStatus;

    /**
     * 异常信息
     */
    @TableField(value = "log_message")
    @Excel(name = "报错信息")
    private String logMessage;

    /**
     * 请求方式，get post等
     */
    @TableField(value = "log_method")
    @Excel(name = "请求方式")
    private String logMethod;

    /**
     * 响应时间
     */
    @TableField(value = "log_time")
    @Excel(name = "请求时间")
    private Long logTime ;

    /**
     * 返回值
     */
    @TableField(value = "log_result")
    private String logResult;

    /**
     * 请求ip
     */
    @TableField(value = "log_ip")
    @Excel(name = "请求ip")
    private String logIp;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @Excel(name = "创建时间")
    private Date createdTime;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private String createdBy;

    private static final long serialVersionUID = 1L;

    public static final String COL_LOG_ID = "log_id";

    public static final String COL_LOG_URL = "log_url";

    public static final String COL_LOG_PARAMS = "log_params";

    public static final String COL_LOG_STATUS = "log_status";

    public static final String COL_LOG_MESSAGE = "log_message";

    public static final String COL_LOG_METHOD = "log_method";

    public static final String COL_LOG_TIME = "log_time";

    public static final String COL_LOG_RESULT = "log_result";

    public static final String COL_LOG_IP = "log_ip";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_CREATED_BY = "created_by";
}