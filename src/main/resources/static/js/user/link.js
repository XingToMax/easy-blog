let layerIndex = 1
layui.use('table', function () {
    let table = layui.table;

    let linkTable = table.render({
        elem: '#link-list'
        ,height: 500
        ,width: 1340
        ,url: HOST + '/admin/link'
        ,page: true
        ,cols: [[
            {type:'checkbox',fixed:'left',width:40}
            ,{field: 'id', title: 'ID', width:100, sort: true}
            ,{field: 'name', title: '链接名', width:150, edit:'text'}
            ,{field: 'address', title: '地址', width:300, edit:'text'}
            ,{field: 'description', title: '描述', width:300, edit:'text'}
            ,{field: 'type', title: '链接类型', templet: '#link-kind', width: 100, edit:'text'}
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
        data.time = data.time.replace('T', ' ')
        data.time = data.time.substr(0, data.time.indexOf('+'))
        if(layEvent === 'update'){
            putRequest(HOST + '/admin/link', data, (result) => {
                if (result.code === 0) {
                    layer.msg('更新成功')
                }
            })
        } else if(layEvent === 'delete') { //删除
            layer.confirm('确认删除链接', (index) => {
                deleteRequest(HOST + '/admin/link', {id : data.id}, (result) => {
                    if (result.code === 0) {
                        layer.msg('删除成功')
                        // 刷新当前
                        linkTable.reload({
                            page:{
                                curr : linkTable.config.page.curr
                            }
                        })
                    }
                })
            });
        }
    });

    $('#create-link').click(function () {
        layerIndex = layer.open({
            type : 1,
            title: '新建链接',
            closeBtn: 1,
            area: ['800px', '600px'],
            content: $('#creat-link-container'),
            cancel : function(index, layero) {
                // 刷新当前
                linkTable.reload({
                    page:{
                        curr : linkTable.config.page.curr
                    }
                })
            },
            end : function (index, layero) {
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


