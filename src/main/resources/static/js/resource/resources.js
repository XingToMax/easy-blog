layui.use('upload', function(){
    let upload = layui.upload;

    //执行实例
    let uploadInst = upload.render({
        elem: '#file-upload' //绑定元素
        ,url: HOST + '/admin/resource/file' //上传接口
        ,data: {
            parentId : function () {
                // return pageData.currentFolderId
            }
        }
        ,field: 'file'
        ,accept: 'file'
        ,done: function(res){
            // console.log(pageData.currentFolderId)
            layer.msg('上传成功')
            // getFolderData(pageData.currentFolderId)
            // getImageData(pageData.currentFolderId)
        }
        ,error: function(){
            //请求异常回调
        }
    });
});