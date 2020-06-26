var tale = new $.tale();
//提交视频处理函数
function subE_v(status) {

    $("#textarea_v").text("Hello <b>world</b>!");
    console.log($("#E_v_from #status").val(status));
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
//提交娱乐基本信息函数
function subEntertainment(status) {

}