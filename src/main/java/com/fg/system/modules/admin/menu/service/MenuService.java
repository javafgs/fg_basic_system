package com.fg.system.modules.admin.menu.service;

import com.fg.system.common.api.Tree;
import com.fg.system.modules.admin.menu.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * t_menu 服务类
 * </p>
 *
 * @author wfg
 * @since 2020-11-02
 */
public interface MenuService extends IService<Menu> {

        Menu findMenuById(Long menuId);

        List<Menu> findAllMenu(Menu menu);

        void addMenu(Menu menu);

        void updateMenu(Menu menu);

        void deleteMenuBatch(String menuIds);

        Tree<Menu> getMenuButtonTree();
        Tree<Menu> getMenuTree();

        Tree<Menu> getUserMenu(String userName);

        Menu findByNameAndType(String menuName, String type);

        /*@Cacheable(key = "'url_'+ #p0")
        List<Map<String, String>> getAllUrl(String p1);*/

}
