$(function (){
    $("#searchBtn").click(function (ev) {
        console.info("reload");
        var conditionSelect = $("#conditionSelect").val();
        var conditionInput = $("#conditionInput").val();
        var table=layui.table
        table.reload('reloadTb', {
            url: 'http://47.92.23.6:7002/ConditionSelect'
            //url:'http://47.92.23.6:7002/ConditionSelect'
            , where: {
                conditionSelect: conditionSelect,
                conditionInput: conditionInput
            } //设定异步数据接口的额外参数

        });
    })

    $("#spiderBtn").click(function (ev){
        console.info("spider");
        var spiderSelection=$("#spiderSelection").val();
        var spiderCount=$("#spiderCount").val();
        var index=layer.load(2,{shade:[0.5,'#fff']})
        var table=layui.table
        console.log(spiderSelection)
        $.ajax({
            type:'GET',
            url: 'http://47.92.23.6:7002/spider',
            //url: 'http://47.92.23.6:7002/spider',
            async: true,
            cache: false,
            data: {'spiderContent':spiderSelection,'spiderCount':spiderCount},
            beforeSend:function (){
                return index;
            },
            success:function(res){
                if(res==='finish'){
                    layer.close(index)
                    layer.msg('完成！')
                    setTimeout(table.reload('reloadTb', {
                        url: 'http://47.92.23.6:7002/formGet'
                        //url:'http://47.92.23.6:7002/formGet'

                    }),1000);

                }else{
                    layer.close(index)
                    layer.alert(res)
                }
            }
        })
    })


})