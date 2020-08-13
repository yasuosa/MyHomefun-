package com.rpy.qw.utils;

import com.rpy.qw.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: myfunhome
 * @description: 分页的类
 * @author: 任鹏宇
 * @create: 2020-06-21 17:23
 **/

@Data
@ApiModel(value = "分页查询模型")
public class PageVo<T> implements Serializable {

    /**
     * 当前页数
     */
    @ApiModelProperty(value = "目标页",required = true,example = "1")
    private Integer currentPage;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Long pageNum;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private Long total;

    /**
     * 每页数
     */
    @ApiModelProperty(value = "每页条数",required = true,example = "10")
    private  Integer pageSize;


    /**
     * 排序
     */
    @ApiModelProperty(value = "排序列")
    private String sortColumn;


    /**
     * 排序方式 asc desc
     */
    @ApiModelProperty(value = "排序方式")
    private String sortMethod = "asc";

    /**
     * 条件参数
     */
    @ApiModelProperty(value = "条件查询的参数")
    private Map<String,Object> params=new HashMap<>();


    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回参数")
    private List<T> data;


    public String getSortColumn() {
        // 驼峰转下划线
        if(null == sortColumn || sortColumn ==""){
            return null;
        }
        return StringUtils.upperCharToUnderLine(sortColumn);
    }

    /**
     * 根据element-ui 的排序进行重写
     * @return
     */
    private static final String FRONT_ASC="ascending";
    public String getSortMethod() {
        if(StringUtils.isBlank(sortMethod) || StringUtils.isBlank(sortColumn)){
            return StateEnum.ASC.getMsg();
        }
        if(FRONT_ASC.equals(sortMethod)){
            return StateEnum.ASC.getMsg();
        }else {
            return StateEnum.DESC.getMsg();
        }
    }
}
