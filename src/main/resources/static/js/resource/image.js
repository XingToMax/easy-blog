let pageData = {
    preFolderId : 0,
    currentFolderId : 0,
    deleteStatus : false
}

// 图片点击
let imageClick = (imageId) => {
    if (pageData.deleteStatus === false) {
        layer.open({
            type: 2,
            area: ['1200px', '800px'],
            fixed: false, //不固定
            maxmin: true,
            content: 'model/image_view?id=' + imageId,
            shadeClose: true,
            cancel : function(index, layero) {
                console.log('end')
            }
        });
    } else {
        let chosenImage = $('#image-' + imageId)
        if (chosenImage.hasClass('chosen-image')) {
            chosenImage.removeClass('chosen-image')
        } else {
            chosenImage.addClass('chosen-image')
        }
    }
}

// 文件夹点击
let folderClick = (folderId) => {
    pageData.preFolderId = pageData.currentFolderId
    pageData.currentFolderId = folderId
    let folderName = $('#folder-' + folderId + ' .folder-name').html()
    let linkHtml = '<a id="address-'
    linkHtml += folderId + '"'
    linkHtml += ' class="address-link children-link" onclick="linkJump('+ folderId +')">'
    linkHtml += folderName
    linkHtml += '/</a>'
    document.getElementById('head-address').innerHTML += linkHtml
    getFolderData(folderId)
    getImageData(folderId)
}

// 文件夹点击

// 获取folder数据
let getFolderData = (folderId) => {
    $.get(HOST + '/admin/resource/folder/parent', {parentId : folderId, type : IMAGE_FOLDER_TYPE}, (result) => {
        let folderHtml = ''
        for (let i = 0; i < result.array.length; i++) {
            folderHtml += '<div class="folder-item folder" id="folder-'
            folderHtml += result.array[i].id + '"'
            folderHtml += ' onclick="folderClick(' + result.array[i].id + ')"'
            folderHtml += '><img src="'
            folderHtml += HOST + '/images/folder.png" '
            folderHtml += 'class="folder-cover"><p class="folder-name">'
            folderHtml += result.array[i].name
            folderHtml += '</p></div>'
        }
        $('.folder').remove()
        let container = document.getElementById('container')
        container.innerHTML = folderHtml + container.innerHTML
    })
}

// 获取图片数据
let getImageData = (folderId) => {
    $.get(HOST + '/admin/resource/image/folder', {parentId : folderId}, (result) => {
        let folderHtml = ''
        for (let i = 0; i < result.array.length; i++) {
            folderHtml += '<div class="folder-item resource-image" id="image-'
            folderHtml += result.array[i].id + '"'
            folderHtml += ' onclick="imageClick(' + result.array[i].id + ')"'
            folderHtml += '><img src="'
            folderHtml += HOST + result.array[i].url + '" '
            folderHtml += 'class="folder-cover"><p class="folder-name">'
            folderHtml += result.array[i].name
            folderHtml += '</p></div>'
        }
        $('.resource-image').remove()
        let container = document.getElementById('container')
        container.innerHTML += folderHtml
    })
}

// 初始化页面
getFolderData(pageData.currentFolderId)
getImageData(pageData.currentFolderId)

layui.use('upload', function(){
    let upload = layui.upload;

    //执行实例
    let uploadInst = upload.render({
        elem: '#image-upload' //绑定元素
        ,url: HOST + '/admin/resource/image' //上传接口
        ,data: {
            parentId : function () {
                return pageData.currentFolderId
            }
        }
        ,field: 'image'
        ,done: function(res){
            console.log(pageData.currentFolderId)
            layer.msg('上传成功')
            getFolderData(pageData.currentFolderId)
            getImageData(pageData.currentFolderId)
        }
        ,error: function(){
            //请求异常回调
        }
    });
});

function linkJump(id) {
    let linkAddress = $('.address-link')
    let clickId = String(id)
    for (let i = linkAddress.length - 1; i >= 0; i--) {
        let curId = linkAddress[i].id.split('-')[1]
        if (curId === clickId) {
            break
        }
        linkAddress[i].remove()
    }

    pageData.preFolderId = pageData.currentFolderId
    pageData.currentFolderId = id

    getFolderData(id)
    getImageData(id)
}

$('#image-edit').click(function () {
    if (pageData.deleteStatus === false) {
        pageData.deleteStatus = true;
        $('#image-edit').html('<i class="layui-icon">&#xe640;</i>确认删除')
    } else {

        let deleteList = []
        let chosenList = $('.chosen-image')
        for(let i = 0; i < chosenList.length; i++) {
            deleteList.push(parseInt(chosenList[i].id.split('-')[1]))
        }
        let param = {idList : deleteList}
        console.log(param)
        $.ajax({
            url : HOST + '/admin/resource/image/batch',
            data : param,
            type : 'POST',
            success : (result) => {
                layer.msg('删除结果 : ' + result.msg)
                getFolderData(pageData.currentFolderId)
                getImageData(pageData.currentFolderId)
            }
        })

        console.log(deleteList)

        pageData.deleteStatus = false;
        $('#image-edit').html('<i class="layui-icon">&#xe640;</i>批量删除')
    }
})