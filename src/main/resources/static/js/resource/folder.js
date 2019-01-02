layui.use('table', function () {
    let table = layui.table;

    let folderTable = table.render({
        elem: '#folder-list'
        ,height: 500
        ,width: 1240
        ,url: HOST + '/admin/resource/folder?type=0'
        ,page: true
        ,cols: [[
            {type:'checkbox',fixed:'left',width:40}
            ,{field: 'id', title: 'ID', width:100, sort: true}
            ,{field: 'name', title: '文件夹名', width:150, edit:'text'}
            ,{field: 'path', title: '路径', width:300}
            ,{field: 'father', title: '父目录id', width:100}
            ,{field: 'type', title: '文件夹类型', templet: '#folder-kind', width: 200}
            ,{field: 'time', title: '创建时间', width: 200, sort: true}
            ,{title: '操作',width:150, align:'center', toolbar: '#edit-bar'}
        ]]
        ,response: {
            dataName : 'array'
        }
        ,loader : true
        ,text: {
            none: '暂无相关数据'
        }
        ,initSort: {
            field: 'id'
            ,type: 'asc'
        }
    });
    //监听工具条
    table.on('tool(folder-list)', (obj) => {
        let data = obj.data;
        let layEvent = obj.event;
        let tr = obj.tr;

        if(layEvent === 'update'){
            let param = {
                folderId:  data.id,
                name : data.name
            }
            layer.confirm('确认更新文件夹名', function (index) {
                $.ajax({
                    url : HOST + '/admin/resource/folder/name',
                    data : param,
                    type : 'PUT',
                    success : (result) => {
                        // 刷新当前
                        folderTable.reload({
                            page:{
                                curr : folderTable.config.page.curr
                            }
                        })

                        layer.msg('更新成功')
                    }
                })
            });
        } else if(layEvent === 'delete') { //删除
            layer.confirm('确认删除文件夹', (index) => {
                layer.alert('风险太大，拒绝删除')
            });
        } else if (layEvent === 'add') {
            layer.prompt({title: '输入文件名，并确认', formType: 3}, (pass, index) =>{
                let param = {name : pass, parentId : data.id, type : data.type}
                $.post(HOST + '/admin/resource/folder', param, (result) => {
                    if (result.code === 0) {
                        layer.msg('文件夹创建成功')
                    } else if (result.code === 502) {
                        layer.msg('服务器错误，文件夹创建失败')
                    } else if (result.code === 501) {
                        layer.msg('文件夹已存在')
                    }

                    // 刷新当前
                    folderTable.reload({
                        page:{
                            curr : folderTable.config.page.curr
                        }
                    })
                })
                layer.close(index);
            });
        }
    });

    $('#create-folder').click(function () {
        layer.open({
            type: 2,
            skin: 'layui-layer-rim', //加上边框
            area: ['500px', '340px'], //宽高
            content: 'model/create_folder',
            title: '新建文件夹',
            cancel : function(index, layero) {
                // 刷新当前
                folderTable.reload({
                    page:{
                        curr : folderTable.config.page.curr
                    }
                })
            }
        });
    })
});


