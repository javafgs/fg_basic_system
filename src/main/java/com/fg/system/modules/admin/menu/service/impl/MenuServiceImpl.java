package com.fg.system.modules.admin.menu.service.impl;

import com.fg.system.modules.admin.menu.entity.Menu;
import com.fg.system.modules.admin.menu.dao.MenuDao;
import com.fg.system.modules.admin.menu.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * <p>
 * t_menu 服务实现类
 * </p>
 *
 * @author wfg
 * @since 2020-11-02
 */
@Service("MenuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {
        private Logger log = LoggerFactory.getLogger(this.getClass());
        @Autowired
        private MenuDao menuDao;

        @Override
        public Menu findMenuById(Long menuId) {
                return this.getById(menuId);
        }

        @Override
        public List<Menu> findAllMenu(Menu menu) {
            try {
                QueryWrapper<Menu> queryWrapper=new QueryWrapper();
                return this.list(queryWrapper);
            } catch (Exception e) {
                log.error("获取菜单失败", e);
                return new ArrayList<>();
            }
        }

        @Override
        @Transactional
        public void addMenu(Menu menu) {
            this.save(menu);
        }

        @Override
        @Transactional
        public void updateMenu(Menu menu) {
            this.updateById(menu);
        }

        @Override
        @Transactional
        public void deleteMenuBatch(String menuIds) {
            List<String> list = Arrays.asList(menuIds.split(","));
            this.menuDao.deleteBatchIds(list);
        }

    /*@Override
    public List<Map<String, String>> getAllUrl(String p1) {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取 url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> urlList = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod handlerMethod = map.get(info);
            PreAuthorize permissions = handlerMethod.getMethodAnnotation(PreAuthorize.class);
            String perms = "";
            if (null != permissions) {
                String value = permissions.value();
                value = StringUtils.substringBetween(value, "hasAuthority(", ")");
                perms = StringUtils.join(value, ",");
            }
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                Map<String, String> urlMap = new HashMap<>();
                urlMap.put("url", url.replaceFirst("\\/", ""));
                urlMap.put("perms", perms);
                urlList.add(urlMap);
            }
        }
        return urlList;

    }*/
}
