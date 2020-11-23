package com.fg.system.modules.admin.menu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * t_menu 实体类
 * </p>
 *
 * @author wfg
 * @since 2020-11-02
 */
@TableName("t_menu")
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {
    private static final long serialVersionUID=1L;
    public enum MenuType{
        MENU(0,"菜单"),
        BUTTON(1,"按钮");
        private int num;
        private String name;

        MenuType(int num, String name) {
            this.num = num;
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public String getName() {
            return name;
        }
    }
    @ApiModelProperty(value = "菜单/按钮ID")
    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private Long menuId;

    @ApiModelProperty(value = "上级菜单ID")
    @TableField("PARENT_ID")
    private Long parentId;

    @ApiModelProperty(value = "菜单/按钮名称")
    @TableField("MENU_NAME")
    private String menuName;

    @ApiModelProperty(value = "菜单URL")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "权限标识")
    @TableField("PERMS")
    private String perms;

    @ApiModelProperty(value = "图标")
    @TableField("ICON")
    private String icon;

    @ApiModelProperty(value = "类型 0菜单 1按钮")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "排序")
    @TableField("ORDER_NUM")
    private Long orderNum;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFY_TIME")
    private Date modifyTime;


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "Menu{" +
        "menuId=" + menuId +
        ", parentId=" + parentId +
        ", menuName=" + menuName +
        ", url=" + url +
        ", perms=" + perms +
        ", icon=" + icon +
        ", type=" + type +
        ", orderNum=" + orderNum +
        ", createTime=" + createTime +
        ", modifyTime=" + modifyTime +
        "}";
    }
}
