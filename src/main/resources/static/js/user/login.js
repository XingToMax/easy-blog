//点击登录按钮效果(登录按钮的id为`loginBtn`)
$("#loginBtn").on('click',function () {
    //按照通过input的id获取填写的值
    var username = $('#username').val()
    var password = $('#password').val()
    var param = {'username':username, 'password':password}
    layer.load(2)
    $.post('/admin/user/login', param, function (result) {
        layer.closeAll('loading')
        if (result.code === 0) {
            window.location.href = '/admin/page/index'
        } else {
            layer.alert(result.desc, {icon: 2});
        }
    })
})

function enter(event) {
    var button = $("#loginBtn")
    if (event.keyCode === 13) {
        button.click();
        event.returnValue = false;
    }
}



