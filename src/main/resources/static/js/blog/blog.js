layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    let blogTable = table.render({
        elem: '#blog-list'
        ,height: 600
        ,width: 1500
        ,url: HOST + '/admin/blog/'
        ,page: true
        ,cols: [[
            {type:'checkbox',fixed:'left',width:40}
            ,{field: 'id', title: 'ID', width:100, sort: true}
            ,{field: 'name', title: '文章名', width:150, edit:'text'}
            ,{field: 'brief', title: '简介', width:400, edit:'text'}
            ,{field: 'classificationName', title: '类别名', width:150, edit:'text'}
            ,{field: 'labels', title: '标签', width:200, edit:'text'}
            ,{field: 'watchCount', title: '浏览次数', width: 100, sort: true}
            ,{field: 'recommendCount', title: '推荐次数', width: 100, sort: true}
            ,{field: 'state', title: '发布状态', width : 110, templet:'#switchTpl'}
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
    form.on('checkbox(stateSwitch)', function(obj){
        let id = obj.elem.id.split('-')[1]
        let state = obj.elem.checked ? 1 : 0
        let param = {
            id : id,
            state : state
        }
        putRequest(HOST + '/admin/blog/state', param, result => {
            blogTable.reload({
                page:{
                    curr : blogTable.config.page.curr
                }
            })
        })
    });
    //监听工具条
    table.on('tool(blog-list)', (obj) => {
        let data = obj.data;
        let layEvent = obj.event;
        let tr = obj.tr;
        data.time = data.time.replace('T', ' ')
        data.time = data.time.substr(0, data.time.indexOf('+'))
        data.updateTime = data.updateTime.replace('T', ' ')
        data.updateTime = data.updateTime.substr(0, data.updateTime.indexOf('+'))
        if(layEvent === 'update'){
            layer.confirm('确认更新博文信息', function (index) {
                $.ajax({
                    url : HOST + '/admin/blog/data',
                    data : data,
                    type : 'PUT',
                    success : (result) => {
                        // 刷新当前
                        blogTable.reload({
                            page:{
                                curr : blogTable.config.page.curr
                            }
                        })
                        if (result.code === 0) {
                            layer.msg('更新成功')
                        } else if (result.code === 501){
                            layer.msg('博文名重复')
                        } else {
                            layer.msg('更新失败')
                        }
                    }
                })
            });
        } else if(layEvent === 'delete') { //删除
            layer.confirm('确认删除文章', (index) => {
                $.ajax({
                    url : HOST + '/admin/blog',
                    data : {id : data.id},
                    type : 'DELETE',
                    success : (result) => {
                        // 刷新当前
                        blogTable.reload({
                            page:{
                                curr : blogTable.config.page.curr
                            }
                        })
                        layer.msg('删除成功')
                    }
                })
            });
        } else if (layEvent === 'edit') {
            layer.open({
                type: 2,
                area: ['1600px', '800px'],
                fixed: false, //不固定
                maxmin: true,
                title: '编辑文章',
                content: 'model/blog_edit?id=' + data.id,
                shadeClose: true,
                cancel : function(index, layero) {
                    // 刷新当前
                    blogTable.reload({
                        page:{
                            curr : blogTable.config.page.curr
                        }
                    })
                }
            });
        }
    });

    $('#create-blog').click(function () {
        layer.open({
            type: 2,
            area: ['1600px', '800px'],
            fixed: false, //不固定
            maxmin: true,
            title: '编辑文章',
            content: 'model/blog_edit?id=0',
            shadeClose: true,
            cancel : function(index, layero) {
                // 刷新当前
                blogTable.reload({
                    page:{
                        curr : blogTable.config.page.curr
                    }
                })
            }
        });
    })
});
