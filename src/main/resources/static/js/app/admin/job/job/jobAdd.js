var $jobAddForm = $("#job-add-form");
var ctp=$FG.getContextPath();
$(function () {
    $("#job-add-form").validate({
        //debug: true, //调试模式取消submit的默认提交功能
        //errorClass: "label.error", //默认为错误的样式类为：error
        // focusInvalid: false, //当为false时，验证无效时，没有焦点响应
        //onkeyup: false,
        // 为错误信息提醒元素的 class 属性增加 invalid
        errorClass: "msg_invalid",
        validClass: "msg_valid",
        // 使用 <em> 元素进行错误提醒
        errorElement: "em",
        // 使用 <li> 元素包装错误提醒元素
        //wrapper: "li",
        //指定错误信息的位置
        errorPlacement: function(error, element) {
            element.before(error)
            //error.insertAfter(element)
            //error.appendTo(element.parent().after(error));
        },

        rules:{
            beanName:{
                required:true
            },
            methodName:{
                required:true,
            },
            cronExpression:{
                required:true,
                //发送异步请求
                remote: {
                    url: ctp + "/admin/job/checkCron",//发送请求的url地址
                    type: "get", //请求方式
                    dataType: "json",//接收的数据类型
                    data: {
                        cron: function () {
                            return $("#cronExpression").val();
                        }
                    },
                    dataFilter: function (data) { //过滤返回结果
                        if (data == "true")
                            return true;  //true代表格式正常
                        else{
                            return false; //false代表格式错误
                        }
                    }
                }
            }
        },
        messages:{
            beanName:{
                required:"必填项"
            },
            methodName:{
                required:"必填项",
            },
            cronExpression:{
                required:"必填项",
                remote:"cron格式不对,请输入正确格式",
            }
        }
        });
    //自定义验证方法
    jQuery.validator.addMethod("cron_msg",
        function(value) {
            /*var cron=value;
            if(cron.indexOf("* ?")!=-1) {
                return true
            }else{
                return false
            }*/
        }
    )
    $("#job-add .btn-save").click(function () {
        var name = $(this).attr("name");
        var form = $jobAddForm[0];

        var flag = $jobAddForm.valid();//判断验证是否通过
        //flag = form.checkValidity();
        //form.classList.add('was-validated');
        if (flag) {
            if (name === "save") {
                $FG.sendRequestAsync(ctp + '/admin/job/add',"post",$jobAddForm.serialize())
            }
            if (name === "update") {
                $FG.sendRequestAsync(ctp + '/admin/job/update',"post",$jobAddForm.serialize())
            }
        }
    });

    $("#job-add .btn-close").click(function () {
        closeModal();
    });



});

function closeModal() {
    $("#job-add-button").attr("name", "save");
    $FG.closeAndRestModal("job-add");
    $("#job-add-modal-title").html('新增任务');
}