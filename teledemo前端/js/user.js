
$('#searchForm').on('submit', function (e) {
    e.preventDefault()
    let sdata = $('#searchForm').serialize()
    // console.log(sdata)
    // searchId=123
    // 传给后端获取用户信息

   let data = $('form [name=searchId]').val().trim()
    if (sdata == "searchId=") {
         data = 0
    }
    if(isNaN(data)){
        alert('请输入数字')
        $('form [name=searchId]').val('')
        data = 0
    }
//    if(data.iscNAN)
    // console.log(data)
    // console.log(data)
    // console.log(data)
    // console.log(data)
    // console.log('('+sdata+'(')
    initTable(data);
})

initTable(0)
// getData('searchId=0')

// function getData(sdata) {

//     $.ajax({
//       url: 'http://localhost:10003/getuserpage',
//       method: "get",
//       data:sdata,
//     //   dataType: "json",
//     //   contentType: 'application/json',
//     //   crossDomain: true,
//       xhrFields: {withCredentials: true},
//     //   where:{
//     //         "searchId":sdata,
//     //     },
//     //   data: JSON.stringify(),
//       success: function (res) {
//           console.log(res)
//         initTable(res.data);
//       },
//       error: function (res) {
//         layer.msg(data.msg);
//       }
//     });
//   }
//表格table
function initTable(searchId) {

    console.log(searchId)
    layui.use('table', function () {
        var table = layui.table;
        // let laypage = layui.laypage;
        $.ajaxSetup({
            xhrFields: {
                withCredentials: true
            }
        });
        //第一个实例
        table.render({
            elem: '#disasterPredictionTb',
            //要修改http
            url: 'http://localhost:10003/getuserpage',
            // type:'get',
            // dataType: "json",
            // contentType: 'application/json',
            // crossDomain: true,
            // xhrFields:{withCredentials:true},
            page: true,
            limit: 10,   // 每页条数
            limits: [10],
            loading: true,
            cellMinWidth: 80, //全局定义常规单元格的最小宽度
            toolbar: '#toolbarDemo',//左侧栏
            defaultToolbar: ['filter', 'exports', 'print'],
            where: {
                "searchId": searchId,
            },
            //右侧栏
            cols: [
                [
                    //{type:'checkbox', fixed: 'left'},
                    {
                        field: 'id',
                        title: 'ID',
                        sort: true,
                        fixed: true,
                        align: 'center'
                    }, {
                        field: 'status',
                        title: '状态',
                        sort: true,
                        fixed: true,
                        align: 'center',
                        templet: function (d) {
                            if (d.status == 0) {
                                return '<span>正常</sapn>'
                            } else {
                                if (d.status == 1) {
                                    return '<span>确诊</sapn>'
                                } else {
                                    if (d.status == 2) {
                                        return '<span>密接</sapn>'
                                    }
                                    return '<span>次密接</sapn>'
                                }
                            }
                        }
                    }, {
                        field: 'phoneNumber',
                        title: '电话号码',
                        align: 'center'
                    }, {
                        title: '操作',
                        align: 'center',
                        toolbar: '#barDemo'
                    }
                ]
            ]
        });

        //触发单元格工具事件
        table.on('tool(disasterPredictionTb)', function (obj) { // 双击 toolDouble
            var data = obj.data;

            if (obj.event === 'del') {
                layer.confirm('真的删除该行么？', function (index) {


                    console.log(index)


                    $.ajaxSetup({
                        xhrFields: {
                            withCredentials: true
                        },
                        async: false
                    });

                    $.post('http://localhost:10003/deleteuser', { 'id': obj.data.id }, res => {
                        if (res.code != 0) {
                            //后端假删除
                            // console.log(res.msg)
                            console.log("删除成功")
                            // initTable();
                        } else {
                            console.log("删除失败")
                        }
                    }
                    )

                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.open({
                    title: '编辑状态',
                    type: 1,
                    area: ['40%', '65%'],
                    content: `
<form class="layui-form frc" action="" lay-filter="example">
  <div class="layui-form-item">
    <label class="layui-form-label">用户ID:</label>
    <div class="layui-input-block editin">
      <input type="text" name="id" lay-verify="title" autocomplete="off" class="layui-input"  disabled value="${obj.data.id}">
    </div>
  </div>

  <div class="layui-form-item">
    <label class="layui-form-label">电话号码:</label>
    <div class="layui-input-block editin">
      <input type="text" name="phoneNumber" lay-verify="title" autocomplete="off" class="layui-input" disabled value="${obj.data.phoneNumber}">
    </div>
  </div>

  <div class="layui-form-item">
    <label class="layui-form-label">选择框:</label>
    <div class="layui-input-block">
      <select name="status" lay-filter="state">
        <option value="0">正常</option>
        <option value="1">确诊</option>
        <option value="2">密接</option>
        <option value="3">次密接</option>
      </select>
    </div>
  </div>
  
  <div class="layui-form-item">
      <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
  </div>
</form>
<script>
        $('select[name=status]').val(${obj.data.status})
</script>
`
                });
            }
        });

        layui.use(['form'], function () {
            var form = layui.form;
            var layer = layui.layer;

            //提交事件
            form.on('submit(demo1)', function (data) {
                // JSON.stringify(data.field)
                //{"id":"1","phoneNumber":"18811027992","status":"0"}

                console.log(JSON.stringify(data.field))

                $.ajax({
                    url: 'http://localhost:10003/changeuserstatus',
                    method: 'POST',
                    data: data.field,
                    async: false,
                    xhrFields: { withCredentials: true },
                    success: res => {
                        console.log('2314235436576')
                        console.log(res)
                        console.log('2314235436576')
                        if (res.code != 0) {
                            console.log(res.msg)
                        } else {
                            console.log("修改成功")
                            initTable();
                            //这里必须加这个
                            return false;
                        }
                    },
                    error : res=>{
                        console.log('hello')
                        console.log(res)
                        console.log('hello')
                    }
                
                }
                );

                // $.post('http://localhost:10003/changeuserstatus', data.field, res => {
                //     console.log('2314235436576')
                //     console.log(res)
                //     console.log('2314235436576')
                //     // if (res.code != 0) {
                //     //     console.log(res.msg)
                //     // } else {
                //     //     console.log("修改成功")
                //     //     initTable();
                //     //     //这里必须加这个
                //     //     return false;
                //     // }
                // }
                // )

                // alert(JSON.stringify(data.field), {
                //     title: '最终的提交信息'
                // })



                // 这个不能加，不然报错
                // return false;
            });



        });
    });
}







