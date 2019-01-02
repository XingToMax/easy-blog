layui.use('table', function () {
    let table = layui.table;

    let classificationTable = table.render({
        elem: '#classification-list'
        ,height: 500
        ,width: 1240
        ,url: HOST + '/admin/classification/'
        ,page: true
        ,cols: [[
            {type:'checkbox',fixed:'left',width:40}
            ,{field: 'id', title: 'ID', width:100, sort: true}
            ,{field: 'name', title: '分类名', width:150, edit:'text'}
            ,{field: 'fatherName', title: '所属类别', width:100}
            ,{field: 'brief', title: '描述', width:500, edit:'text'}
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
    table.on('tool(classification-list)', (obj) => {
        let data = obj.data;
        let layEvent = obj.event;
        let tr = obj.tr;
        data.time = data.time.replace('T', ' ')
        data.time = data.time.substr(0, data.time.indexOf('+'))
        if(layEvent === 'update'){
            layer.confirm('确认更新分类信息', function (index) {
                $.ajax({
                    url : HOST + '/admin/classification',
                    data : data,
                    type : 'PUT',
                    success : (result) => {
                        // 刷新当前
                        classificationTable.reload({
                            page:{
                                curr : classificationTable.config.page.curr
                            }
                        })
                        if (result.code === 0) {
                            layer.msg('更新成功')
                        } else if (result.code === 501){
                            layer.msg('分类名已存在')
                        } else {
                            layer.msg('更新失败')
                        }
                    }
                })
            });
        } else if(layEvent === 'delete') { //删除
            layer.confirm('确认删除分类', (index) => {
                layer.alert('风险太大，拒绝删除')
            });
        } else if (layEvent === 'add') {
            layer.open({
                type: 2,
                skin: 'layui-layer-rim', //加上边框
                area: ['600px', '340px'], //宽高
                content: 'model/create_class?id=' + data.id,
                title: '新建分类',
                cancel : function(index, layero) {
                    // 刷新当前
                    classificationTable.reload({
                        page:{
                            curr : classificationTable.config.page.curr
                        }
                    })
                }
            });
        }
    });

    $('#create-class').click(function () {
        layer.open({
            type: 2,
            skin: 'layui-layer-rim', //加上边框
            area: ['600px', '340px'], //宽高
            content: 'model/create_class?id=0',
            title: '新建分类',
            cancel : function(index, layero) {
                // 刷新当前
                classificationTable.reload({
                    page:{
                        curr : classificationTable.config.page.curr
                    }
                })
            }
        });
    })
});
