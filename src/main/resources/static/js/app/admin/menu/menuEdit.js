var ctp=$FG.getContextPath();
function updateMenu() {
    var selected = $("#menuTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $FG.n_warning('请勾选需要修改的菜单或按钮！');
        return;
    }
    if (selected_length > 1) {
        $FG.n_warning('一次只能修改一个菜单或按钮！');
        return;
    }
    var menuId = selected[0].menuId;
    $.get(ctp + "admin/menu/getMenu", {"menuId": menuId}, function (r) {
        if (r.code === 0) {
            var $form = $('#menu-add');
            var $menuTree = $('#menuTree');
            $form.modal();
            var menu = r.msg;
            $("#menu-add-modal-title").html('修改菜单/按钮');
            $("input:radio[value='" + menu.type + "']").trigger("click");
            $form.find("input[name='menuName']").val(menu.menuName);
            $form.find("input[name='oldMenuName']").val(menu.menuName);
            $form.find("input[name='menuId']").val(menu.menuId);
            $form.find("input[name='icon']").val(menu.icon);
            // 初始化的全局变量在menu.html中
            $(".mdi-access-point").addClass(menu.icon)
            $('#menu-perms-list').find(".autocomplete-input").val(menu.perms == null ? "" : menu.perms);
            $('#menu-url-list').find(".autocomplete-input").val(menu.url == null ? "" : menu.url);
            $menuTree.jstree('select_node', menu.parentId, true);
            $menuTree.jstree('disable_node', menu.menuId);
            $("#menu-add-button").attr("name", "update");
        } else {
            $FG.n_danger(r.msg);
        }
    });
}