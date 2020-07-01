var mditor;
var tale = new $.tale();
var attach_url = $('#attach_url').val();
// 每10分钟自动保存一次草稿
// var refreshIntervalId = setInterval("autoSave()", 10 * 60 * 1000);
Dropzone.autoDiscover = false;
/**
 * 保存文章
 * @param status
 */
function subArticle(status) {
    var title = $('#articleForm input[name=title]').val();
    // var content =  mditor.value;
    var content = $('#ttt').val();
    console.log(content);
    if (title == '') {
        tale.alertWarn('请输入文章标题');
        return;
    }
    if (title .length>25) {
        tale.alertWarn('文章标题不能超过25个字符！');
        return;
    }
    if (content == '') {
        tale.alertWarn('请输入文章内容');
        return;
    }
    $('#content-editor').val(content);
    $("#articleForm #status").val(status);
    $("#articleForm #categories").val($('#multiple-sel').val());
    var params = $("#articleForm").serialize();
    var url = $('#articleForm #id').val() != '' ? '/admin/article/modify' : '/admin/article/publish';
    tale.post({
        url:url,
        data:params,
        success: function (result) {
            if (result && result.success) {
                tale.alertOk({
                    text:'文章保存成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/admin/article';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '保存文章失败！');
            }
        }
    });
}

function allow_comment(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_comment').val(false);
    }
    if (off == 1) {
        $('#allow_comment').val(true);
    }
}
