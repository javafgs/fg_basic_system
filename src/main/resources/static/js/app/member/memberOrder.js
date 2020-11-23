var validator;
var $userAddressForm = $("#userAddress-add-form");
$(function () {
	validateRule();
    var settings = {
        url: ctx + "member/getUserAddressList",
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1
            };
        },
        columns: [
        {
            checkbox: true
        }, 
        {
            field: 'id',
            visible: false
        },       	
        {
            field: 'contactName',
            title: '联系人'
        },{
            field: 'contactMobile',
            title: '联系方式',
            formatter: function (value, row, index) {
                return row.contactMobile+" "+row.province+" "+row.city+" "+row.addrDetails;
            }
        }

        ]
    };

    $MB.initTable('addressTable', settings);
    // 绑定新增加保存
    $("#userAddress-add-button").click(function () {
        validator = $userAddressForm.validate();
    	var flag = validator.form();
    	if(flag){
            $.post(ctx + "member/userAddress/add", $userAddressForm.serialize(), function (r) {
                if (r.code === 0) {
                    closeModal();
                    $MB.n_success(r.msg);
                    $MB.refreshTable("addressTable");
                } else $MB.n_danger(r.msg);
            });    		
    	}
    });

    $("#userAddress-close-button").click(function () {
        closeModal();
    });
});
function refresh() {
    $MB.refreshTable('addressTable');
}
function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i>";
    validator = $userAddressForm.validate({
        rules: {
        	contactName: {
        		required: true
            },
            contactMobile: {
                required: true
            },
            province: {
                required: true
            },
            city: {
                required: true
            },
            districts: {
                required: true
            },
            addrDetails: {
                required: true
            },
            zipCode: {
                required: true
            }           
        },
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
        	contactName: icon + "请填写联系方式",
        	contactMobile: icon + "请填写联系电话",
        	province: icon + "请选择省",
        	city: icon + "请选择市",
        	districts: icon + "请选择区县",
        	addrDetails: icon + "请填写详细地址",
        	zipCode: icon + "请填写邮编"
        }
    });
}
function deleteAddresss() {
    var selected = $("#addressTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的地址！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i !== (selected_length - 1)) ids += ",";
    }    
    $MB.confirm({
        text: "确定删除选中地址？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'member/deleteAddress', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}
function closeModal() {
    $MB.closeAndRestModal("address-add");

}