var ctp=$FG.getContextPath();
function updateJob() {
    var selected = $("#jobTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $FG.n_warning('请勾选需要修改的任务！');
        return;
    }
    if (selected_length > 1) {
        $FG.n_warning('一次只能修改一个任务！');
        return;
    }
    var jobId = selected[0].jobId;
    $.get(ctp + "/admin/job/getJob", {"jobId": jobId}, function (r) {
        if (r.code === 200) {
            var $form = $('#job-add');
            $form.modal();
            var job = r.data;
            $("#job-add-modal-title").html('修改任务');
            $form.find("input[name='jobId']").val(job.jobId);
            $form.find("input[name='status']").val(job.status);
            $form.find("input[name='beanName']").val(job.beanName);
            $form.find("input[name='methodName']").val(job.methodName);
            $form.find("input[name='params']").val(job.params);
            $form.find("input[name='cronExpression']").val(job.cronExpression);
            $form.find("input[name='remark']").val(job.remark);
            $("#job-add-button").attr("name", "update");
        } else {
            $FG.n_danger(r.msg);
        }
    });
}