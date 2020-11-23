package com.fg.system.modules.admin.menu.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.fg.system.common.api.FgResult;
import com.fg.system.common.util.FileUtils;
import com.fg.system.common.util.page.QueryRequest;
import com.fg.system.modules.base.BaseController;
import com.fg.system.common.annotation.Log;
import com.fg.system.modules.admin.menu.entity.Menu;
import com.fg.system.modules.admin.menu.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import io.swagger.annotations.Api;

/**
 * <p>
 * t_menu 前端控制器
 * </p>
 * @author wfg
 * @since 2020-11-02
 */
@Controller
@Api(tags = "菜单管理")
@RequestMapping("/admin")
public class MenuController extends BaseController {
        private Logger log=LoggerFactory.getLogger(this.getClass());

        @Autowired
        protected MenuService menuService;

        @Log("获取菜单信息")
        @GetMapping("/menu")
        public String menu(){
            return "admin/menu/menu";
        }

        @ApiOperationSupport(order = 1)
        @ApiOperation(value = "新增",notes = "新增菜单")
        @Log("新增菜单")
        @PostMapping("/menu/add")
        @ResponseBody
        public FgResult addMenu(Menu menu){
            String name="";
            try{
                this.menuService.addMenu(menu);
                if(Menu.MenuType.MENU.getNum()==Integer.parseInt(menu.getType())){
                    name=Menu.MenuType.MENU.getName();
                }
                return FgResult.success("新增"+name+"成功！");
            }catch(Exception e){
                log.error("新增"+name+"失败",e);
                return FgResult.failed("新增"+name+"失败}，请联系网站管理员！");
            }
        }

        @ApiOperationSupport(order = 5)
        @ApiOperation(value = "修改",notes = "修改菜单")
        @Log("修改菜单")
        @PostMapping("/update")
        @ResponseBody
        public FgResult updateJob(Menu menu){
                String name="";
                try{
                    this.menuService.updateMenu(menu);
                    if(Menu.MenuType.MENU.getNum()==Integer.parseInt(menu.getType())){
                        name=Menu.MenuType.MENU.getName();
                    }
                    return FgResult.success("修改"+name+"成功！");
                }catch(Exception e){
                    log.error("修改"+name+"失败",e);
                     return FgResult.failed("修改"+name+"失败，请联系网站管理员！");
                }
        }

        @ApiOperationSupport(order = 10)
        @ApiOperation(value = "批量删除",notes = "批量删除定时任务")
        @ApiImplicitParam(required = true,value = "菜单id",name = "ids")
        @Log("删除菜单")
        @PostMapping("/menu/deleteBatch")
        @ResponseBody
        public FgResult deleteMenu(String ids){
            try{
                this.menuService.deleteMenuBatch(ids);
                return FgResult.success("删除菜单成功！");
            }catch(Exception e){
                log.error("删除菜单失败",e);
                return FgResult.failed("删除菜单失败，请联系网站管理员！");
            }
        }

        @ApiOperationSupport(order = 15)
        @ApiOperation(value = "分页查询",notes = "分页查询菜单数据")
        @GetMapping("/menu/list")
        @ResponseBody
        public Map<String, Object> menuList(QueryRequest request,Menu menu){
                return selectByPageNumSize(request,()->this.menuService.findAllMenu(menu));
        }

        @ApiOperationSupport(order = 20)
        @ApiOperation(value = "获取单条数据",notes = "获取单条菜单")
        @ApiImplicitParam(required = true,value = "菜单id",name = "menuId")
        @GetMapping("/menu/getMenu")
        @ResponseBody
        public FgResult getJob(Long menuId){
            try{
                Menu menu = this.menuService.findMenuById(menuId);
                return FgResult.success(menu);
            }catch(Exception e){
                log.error("获取菜单信息失败",e);
                return FgResult.failed("获取菜单信息失败，请联系网站管理员！");
            }
         }

        /*@Log("获取系统所有URL")
        @GetMapping("/menu/urlList")
        @ResponseBody
        public List<Map<String, String>> getAllUrl() {
            return this.menuService.getAllUrl("1");
        }*/
    }