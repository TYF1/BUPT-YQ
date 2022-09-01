$.get('./json/login.json',res=>{
    
    if(res.code == 0){
        window.location.href = './login.html'
        return
    }
    console.log(res+"123")
    $('form [name=userName]').val(res.data[0].name)
    // 这样也可以
    $('input[name=phone]').val(res.data[0].phoneNumber)
    $('input[name=age]').val(res.data[0].age)
    // select只能设为option以内的值
    $('form [name=sex]').val('男')
})



$('form').on('submit',function(e){
    e.preventDefault()
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
})