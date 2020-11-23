package com.fg.system.modules.admin.menu.dao;

import com.fg.system.modules.admin.menu.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * t_menu mapper接口
 * </p>
 *
 * @author wfg
 * @since 2020-11-02
 */
public interface MenuDao extends BaseMapper<Menu> {
    List<Menu> findUserPermissions(String userName);

    List<Menu> findUserMenus(String userName);

    // 删除父节点，子节点变成顶级节点（根据实际业务调整）
    void changeToTop(List<String> menuIds);

}
