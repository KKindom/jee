var tale = new $.tale();
var tale_v=new $.tale();
//提交视频处理函数
function subE_v(status) {

    console.log($("#E_v_from #status").val(status));
    //保存的数据
    var params = $("#E_v_from").serialize();
    console.log(params);
    var url = $('#E_v_from #id').val() != '' ? '/admin/entertainment_v/modify' : '/admin/entertainment_v/publish';
    console.log(url)
    tale.post({
        url:url,
        data:params,
        success: function (result) {
            if (result && result.success) {
                tale.alertOk({
                    text:'娱乐视频保存成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/admin/entertainment';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '娱乐视频保存失败！');
            }
        }
    });
    console.log(params);
}

// //提交图片函数
// $('#submit_btn').click(function () {
//
//     var formData=new FormData();
//     formData.append("file",$('#upload').get(0).files[0]);
//     console.log(formData.get("file"));
//     //formData.append('photo', document.getElementById('upload').files[0]);
//     var url =  '/admin/upload' ;
//     console.log(formData);
//     $.ajax({
//         type:'post',
//         url:url,
//         data:formData,
//         processData:false,//*
//         contentType:false,//*
//         success: function (result) {
//             if (result && result.success) {
//                 tale.alertOk({
//                     text:'图片保存成功',
//                     then: function () {
//                         setTimeout(function () {
//                             window.location.href = '/admin/entertainment';
//                         }, 500);
//                     }
//                 });
//             } else {
//                 tale.alertError(result.msg || '图片保存失败！');
//             }
//         }
//     });
// });
//提交图片函数
function subE_pic() {

    var address=$("#headPic").attr("src");
    console.log(address);
    console.log(flag2);
    if(flag2!=address) {
//装载图片
        var formData = new FormData();
        formData.append("file", $('#upload').get(0).files[0]);
        console.log(formData.get("file"));

        //formData.append('photo', document.getElementById('upload').files[0]);
        var url = '/admin/upload';
        console.log(formData);
        $.ajax({
            type: 'post',
            url: url,
            data: formData,
            processData: false,//*
            contentType: false,//*
            success: function (result) {
                if (result && result.success) {
                    tale.alertOk({
                        text: '图片保存成功',
                        then: function () {
                            setTimeout(function () {
                                window.location.href = '/admin/entertainment';
                            }, 500);
                        }
                    });
                } else {
                    tale.alertError(result.msg || '图片保存失败！');
                }
            }
        });
    }
}
//在ajax请求中加入请求头部即可跳过拦截
$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

});
//提交娱乐基本信息函数
function subEntertainment(status)
{
//拿到form的值
    var params = $("#E_form").serialize();
    console.log(params);
    var url = $('#E_form #id').val() != '' ? '/admin/entertainment/modify' : '/admin/entertainment/publish';
    console.log(url)
    tale.post({
        url:url,
        data:params,
        success: function (result) {
            if (result && result.success) {
                tale.alertOk({
                    text:'娱乐信息保存成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/admin/entertainment';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '娱乐信息保存失败！');
            }
        }
    });
    console.log(params);

subE_pic();
}

