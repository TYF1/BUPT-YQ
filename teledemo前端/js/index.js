const userName = window.localStorage.getItem('userName')

if (!userName) {
    //表示未登录
    $('.login').removeClass('active')
    $('.logout').addClass('active')
} else {
    //表示登陆
        $('.login').addClass('active').find('span').text(userName)
        $('.logout').removeClass('active')

}


//个人中心
$('.login button.self').on('click',function(){
    window.location.href = './self.html'
})
//退出登录
$('.login button.logout').on('click',function(){
console.log('执行退出')
    $.ajax({
        url:'http://localhost:10003/login?'+'userName=""&password=""',
        method:'post', 
        xhrFields:{withCredentials:true},
        success:res=>{
         console.log(res)
         //退出登录
         if(res.code == -1){
            window.localStorage.clear()
            window.location.href = './login.html'
             return
         }
     }
     });

})