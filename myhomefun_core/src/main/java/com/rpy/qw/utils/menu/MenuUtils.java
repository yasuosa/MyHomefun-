package com.rpy.qw.utils.menu;


import com.rpy.qw.sys.domain.Menu;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 菜单工具类
 * @author: 任鹏宇
 * @create: 2020-07-10 15:44
 **/
public class MenuUtils {


    /**
     * 将menuNode 转化为menu
     * @param menuNode
     * @return
     */
    public static Menu conversionMenuNodeToMenu(MenuNode menuNode){
        Menu menu =new Menu();
        BeanUtils.copyProperties(menuNode, menu);
        menu.setPid(  menuNode.getParentId());
        menu.setTitle(menuNode.getLabel());
        menu.setTarget(menuNode.getComponent());
        menu.setHref( menuNode.getPath());
        return menu;
    }


    /**
     * 将menu转化为menuNode
     * @param menu
     * @return
     */
    public static MenuNode conversionMenuToMenuNode(Menu menu){
        MenuNode menuNode =new MenuNode();
        BeanUtils.copyProperties(menu, menuNode);
        menuNode.setParentId(menu.getPid());
        menuNode.setLabel(menu.getTitle());
        menuNode.setComponent(menu.getTarget());
        menuNode.setPath(menu.getHref());
        return menuNode;
    }



    /**找到父类*/
    private  static  int find(int x ,List<Integer> f) {
        return x == f.get(x) ? x : f.set(x, find(f.get(x),f));
    }

    private  static  void merge(int id, int pid,List<Integer> f) {
        int xx = find(id,f), yy = find(pid,f);
        f.set(xx, yy);
    }

    public  static  List<Menu> find_parent(List<Menu> list) {
        List<Integer> f=new ArrayList<>(1024);
        for (int i = 0; i < 1000; i++) f.add(i);

        list.stream().forEach(e -> merge(e.getId(), e.getPid(),f));
        List<Menu> result = new ArrayList<>();
        for (Menu e : list) {
            if (f.get(e.getId()) == e.getId() || e.getPid() == f.get(e.getPid())) {
                result.add(e);
            }
        }
        //f.forEach(e -> System.out.println(e));
        return result;
    }
}
