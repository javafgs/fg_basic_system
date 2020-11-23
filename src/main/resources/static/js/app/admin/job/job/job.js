var ctp=$FG.getContextPath();
$(function () {
    var settings = {
        url: ctp + "/admin/job/list",
        pageSize: 10,
        toolbar:'#toolbar',
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1
            };
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'jobId',
                title: '任务ID'
            }, {
                field: 'beanName',
                title: 'Bean名称'
            }, {
                field: 'methodName',
                title: '方法名称'
            }, {
                field: 'params',
                title: '参数'
            }, {
                field: 'cronExpression',
                title: 'cron表达式'
            }, {
                field: 'remark',
                title: '备注'
            }, {
                field: 'status',
                title: '状态',
                formatter: function (value, row, index) {
					var value="";
					if (row.status == '1') {
						value = '<span class="badge badge-danger">暂停</span>';
					} else if(row.status == '0') {
						value = '<span class="badge badge-success">正常</span>';
					}else {
						value = row.pType ;
					}
					return value;
                }
            }
        ]
    };

    $FG.initTable('jobTable', settings);
    //initSysCronClassList();

});
function search() {
    $FG.refreshTable('jobTable');
}

function refresh() {
    search();

}

function deleteJob() {
    var selected = $("#jobTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $FG.n_warning('请勾选需要删除的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].jobId;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $FG.confirm({
        text: "确定删除选中的任务？",
        confirmButtonText: "确定删除"
    }, function () {
        $FG.sendRequestAsync(ctp + '/admin/job/deleteBatch',"post",{"ids":ids})
    });
}

function runJob() {
    var selected = $("#jobTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $FG.n_warning('请勾选需要立即执行的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].jobId;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $FG.confirm({
        text: "确定执行选中的任务？",
        confirmButtonText: "确定执行"
    }, function () {
        $FG.sendRequestAsync(ctp + '/admin/job/run',"post",{"jobIds":ids})
    });
}

function pauseJob() {
    var selected = $("#jobTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $FG.n_warning('请勾选需要暂停的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].jobId;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $FG.confirm({
        text: "确定暂停选中的任务？",
        confirmButtonText: "确定暂停"
    }, function () {
        $FG.sendRequestAsync(ctp + '/admin/job/pause',"post",{"jobIds":ids})
    });
}

function resumeJob() {
    var selected = $("#jobTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $FG.n_warning('请勾选需要恢复的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].jobId;
        if (i !== (selected_length - 1)) {
            ids += ",";
        }
    }

    $FG.confirm({
        text: "确定恢复选中的任务？",
        confirmButtonText: "确定恢复"
    }, function () {
        $FG.sendRequestAsync(ctp + '/admin/job/resume',"post",{"jobIds":ids})
    });
}

function exportJobExcel() {
    $.post(ctp + "/admin/job/csv", $(".job-table-form").serialize(), function (r) {
        if (r.code === 200) {
            window.location.href = "file/download?fileName=" + r.data + "&delete=" + true;
        } else {
            $FG.n_warning(r.data);
        }
    });
}

function exportJobCsv() {
    $.post(ctp + "/admin/job/csv", $(".job-table-form").serialize(), function (r) {
        if (r.code === 200) {
            window.location.href = "file/download?fileName=" + r.data + "&delete=" + true;
        } else {
            $FG.n_warning(r.data);
        }
    });
}

/*function initSysCronClassList() {
    $.post(ctp + "/admin/job/getSysCronClass", function (r) {
        r = r.code === 200 ? r.data : [];
        $('#sys-cron-clazz-list-bean').autocomplete({
            hints: r,
            keyname: 'beanName',
            width: 70,
            // height: 32,
            valuename: 'beanName',
            showButton: false,
            onSubmit: function (text) {
                $('#sys-cron-clazz-list-bean').siblings("input[name='beanName']").val(text);

            }
        });
        $('#sys-cron-clazz-list-method').autocomplete({
            hints: r,
            keyname: 'beanName',
            width: 70,
            // height: 31,
            valuename: 'methodName',
            showButton: false,
            onSubmit: function (text) {
                $('#sys-cron-clazz-list-method').siblings("input[name='methodName']").val(text);
            }
        });
    });




}*/
