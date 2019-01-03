layui.use('table', function () {
    let table = layui.table;

    let linkTable = table.render({
        elem: '#link-list'
        ,height: 500
        ,width: 1240
        ,url: HOST + '/admin/link'
        ,page: true
        ,cols: [[
            {type:'checkbox',fixed:'left',width:40}
            ,{field: 'id', title: 'ID', width:100, sort: true}
            ,{field: 'name', title: '链接名', width:150, edit:'text'}
            ,{field: 'address', title: '地址', width:300}
            ,{field: 'description', title: '描述', width:100}
            ,{field: 'type', title: '链接类型', templet: '#link-kind', width: 200}
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
    table.on('tool(link-list)', (obj) => {
        let data = obj.data;
        let layEvent = obj.event;
        let tr = obj.tr;

        if(layEvent === 'update'){
            let param = {
                linkId:  data.id,
                name : data.name
            }
            layer.confirm('确认更新文件夹名', function (index) {
                $.ajax({
                    url : HOST + '/admin/resource/link/name',
                    data : param,
                    type : 'PUT',
                    success : (result) => {
                        // 刷新当前
                        linkTable.reload({
                            page:{
                                curr : linkTable.config.page.curr
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
                $.post(HOST + '/admin/resource/link', param, (result) => {
                    if (result.code === 0) {
                        layer.msg('文件夹创建成功')
                    } else if (result.code === 502) {
                        layer.msg('服务器错误，文件夹创建失败')
                    } else if (result.code === 501) {
                        layer.msg('文件夹已存在')
                    }

                    // 刷新当前
                    linkTable.reload({
                        page:{
                            curr : linkTable.config.page.curr
                        }
                    })
                })
                layer.close(index);
            });
        }
    });

    $('#create-link').click(function () {
        layer.open({
            type: 2,
            skin: 'layui-layer-rim', //加上边框
            area: ['500px', '340px'], //宽高
            content: 'model/create_link',
            title: '新建文件夹',
            cancel : function(index, layero) {
                // 刷新当前
                linkTable.reload({
                    page:{
                        curr : linkTable.config.page.curr
                    }
                })
            }
        });
    })
});


