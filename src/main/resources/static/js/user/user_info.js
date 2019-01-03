layui.use('laydate', function() {
    let laydate = layui.laydate;
    laydate.render({
        elem: '#from-date-1'
        ,theme: 'molv'
    });
    laydate.render({
        elem: '#to-date-1'
        ,theme: 'molv'
    });
    laydate.render({
        elem: '#from-date-2'
        ,theme: 'molv'
    });
    laydate.render({
        elem: '#to-date-2'
        ,theme: 'molv'
    });
});