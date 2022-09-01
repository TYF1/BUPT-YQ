$('form').on('submit', function (e) {
    e.preventDefault()

    if($('form [name=userName]').val()=='' || $('form [name=password]').val()==''){
        // $('form [name=userName]').focus()
        alert('用户名或密码不能为空')

    }else{
    const data = $('form').serialize()
    console.log(data)
    //userName=mmmm&password=123传递给后端登录
    //若登录失败，显示提示信息active

    // xhrFields:{withCredentials:true}
    $.ajax({
           url:'http://localhost:10003/login?'+data,
           method:'post', 
           xhrFields:{withCredentials:true},
           success:res=>{
            console.log(res)
            //登录失败
            if(res.code == -1){
                $('form>span').css('display','block')
                return
            }
            console.log($('form [name=username]').val())
            window.localStorage.setItem('userName',$('form [name=username]').val())
            window.location.href = './index.html'
        }
        });

    //  $.post('http://localhost:10003/login?'+data,
    //  })

    //测试环境只能用get，具体用上面的
/*     $.get('./json/login.json', res => {
        console.log(res)
        //登录失败
        if (res.code == 0) {
            $('form>span').css('display', 'block')
            return
        }
        console.log(res.data[0].userId)
        window.localStorage.setItem('id',res.data[0].userId)
        window.location.href = './index.html'
    }) */
}
})