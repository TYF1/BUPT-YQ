$('form').on('submit',function(e){
    e.preventDefault()

    if($('form [name=password]').val()=='' || $('form [name=newPassword]').val()=='' || $('form [name=rnewPassword]').val()==''){
        // $('form [name=userName]').focus()
        alert('密码不能为空')

    }else{

    const data = $('form').serialize()
    console.log(data)
    

    $.get('./json/login.json',res=>{
        console.log(res)
        //修改失败
        if(res.code == 0){
           
            return
        }
        window.alert('恭喜你，修改成功')
        window.location.href = './index.html'
    })

}
})