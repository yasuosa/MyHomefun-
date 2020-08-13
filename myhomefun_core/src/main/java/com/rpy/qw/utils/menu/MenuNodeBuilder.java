package com.rpy.qw.utils.menu;


import com.rpy.qw.sys.domain.Menu;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 菜单转化工具
 * @author: 任鹏宇
 * @create: 2020-07-09 15:15
 **/
@Slf4j
public class MenuNodeBuilder {

    /**
     * 得到顶部菜单
     * @param source
     * @return
     */
    public static List<MenuNode> conversionMenuToTopMenu(List<Menu> source){
        List<MenuNode> data=new ArrayList<>();
        for (Menu menu : source) {
            MenuNode menuNode =new MenuNode();
            menuNode.setId(menu.getId());
            menuNode.setIcon(menu.getIcon());
            menuNode.setParentId(menu.getPid());
            menuNode.setLabel(menu.getTitle());
            menuNode.setPath(menu.getHref());
            data.add(MenuUtils.conversionMenuToMenuNode(menu));
        }
        return data;
    }


    /**
     * 得到树形菜单
     * @param menus
     * @param top
     * @return
     */
    public static List<MenuNode> conversionMenuToTreeMenuVo(List<Menu> menus, Integer top) {
        log.info("转化前"+menus.toString());
        List<MenuNode> menuNodeList = conversionMenuListToMenuVoListNoChildren(menus);
        log.info("转化后："+menuNodeList.toString());
        List<MenuNode> data=new ArrayList<>();
        for (MenuNode m1 : menuNodeList) {
            if(m1.getParentId().equals(top)){
                data.add(m1);
            }
            for (MenuNode m2 : menuNodeList) {
                if(m2.getParentId().equals(m1.getId())){
                    m1.getChildren().add(m2);
                }
            }
        }
        return data;
    }

    /**
     * 得到树形菜单 包括自己
     * @param menus
     * @param topMenu
     * @return
     */
    public static List<MenuNode> conversionMenuToTreeMenuVoIncludeMySelf(List<Menu> menus, Menu topMenu) {
        List<MenuNode> menuNodeList = conversionMenuListToMenuVoListNoChildren(menus);

        List<MenuNode> data=new ArrayList<>();
        for (MenuNode m1 : menuNodeList) {
            if(m1.getId().equals(topMenu.getId())){
                data.add(m1);
            }
            for (MenuNode m2 : menuNodeList) {
                if(m2.getParentId().equals(m1.getId())){
                    m1.getChildren().add(m2);
                }
            }
        }
        return data;
    }


    /**
     * 排除带有子节点的数据
     * @param menus
     * @return
     */
    public static List<Menu> expellentChildNodeList(List<Menu> menus){
        return  MenuUtils.find_parent(menus);
    }


    /**
     * 无子组件的转化
     * @param menus
     * @return
     */
    public static List<MenuNode> conversionMenuListToMenuVoListNoChildren(List<Menu> menus){
        List<MenuNode> data=new ArrayList<>();
        for (Menu menu : menus) {
            data.add(MenuUtils.conversionMenuToMenuNode(menu));
        }
        return data;
    }


}
