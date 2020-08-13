package com.rpy.qw.post.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rpy.qw.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: myhomefun
 * @description: 后台轮播图字节
 * @author: 任鹏宇
 * @create: 2020-08-03 22:51
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TreeBannerTypeNode {

    public TreeBannerTypeNode(StateEnum stateEnum) {
        this.value=stateEnum.getCode()+"";
        this.label=stateEnum.getMsg();
    }

    public TreeBannerTypeNode(String label,String value,String cover) {
        this.value = value;
        this.cover=cover;
        this.label = label;
    }

    public TreeBannerTypeNode(String label,String value) {
        this.value = value;
        this.label = label;
    }

    private String value;
    private String label;

    private String cover;


    private List<TreeBannerTypeNode> children=new ArrayList<>();
}
