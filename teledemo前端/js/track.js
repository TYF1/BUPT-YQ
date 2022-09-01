$('#searchForm').on('submit', function (e) {
    e.preventDefault()
    let sdata = $('#searchForm').serialize()
    // console.log(data)
    // searchId=123
    // 传给后端获取轨迹
    let data = $('form [name=searchId]').val().trim()

    if (sdata == "searchId=") {
        console.log('获取全部确诊轨迹')
        initAllTrack()
        return
    } else {
        console.log('获取个人轨迹')

        if (isNaN(data)) {
            alert('请输入数字')
            $('form [name=searchId]').val('')
            // 置空
            initAllTrack()
            return
        }

        // console.log(sdata)
        initTrack(data)
    }
})

$('#total').on('click', function () {
    console.log("向后端请求所有轨迹")
    initAllTrack()
})

$('#resone').on('click', function () {
    console.log("向后端请求确诊轨迹")
    initSigleTrack('1')
})
$('#restwo').on('click', function () {
    console.log("向后端请求密接轨迹")
    initSigleTrack('2')
})
$('#resthree').on('click', function () {
    console.log("向后端请求次密接轨迹")
    initSigleTrack('3')
})

var resone
var restwo
var resthree
// 获取所有轨迹
initAllTrack()

function initTrack(searchId) {
    $.ajax({
        url: 'http://localhost:10003/gettrack',
        method: 'GET',
        // async: false,
        data: { 'searchId': searchId },
        xhrFields: { withCredentials: true },
        success: res => {
            console.log(res)
            if (res.code != 0) {
                alert('查询id错误或无该用户轨迹数据')
                $('form [name=searchId]').val('')
                initAllTrack()
                return
            } else {
                console.log("获取个人轨迹成功")
                console.log(res.msg)
                if(res.msg == 1){
                inite( res.data, [], [] )
                }else if(res.msg == 2){
                    inite( [],res.data, [] )
                }else if(res.msg == 3){
                    inite( [],[],res.data )
                }
            }
        }
    }
    );
}

function initAllTrack() {
    resone = []
    restwo = []
    resthree = []
    console.log('问题')
    initThreeTrack('2')
    initThreeTrack('1')
    initThreeTrack('3')
    //观察一下前后关系
    console.log('我还没问题')
    inite(resone, restwo, resthree)
    console.log('之后有问题')
}



function initSigleTrack(status) {
    resone = []
    restwo = []
    resthree = []
    initThreeTrack(status)
    // var list = []
    // list[status]=
    inite(resone, restwo, resthree)
}


function initThreeTrack(status) {
    $.ajax({
        url: 'http://localhost:10003/getalltrack',
        method: 'GET',
        async: false,
        data: { 'status': status },
        xhrFields: { withCredentials: true },
        success: res => {
            console.log(res)
            if (res.code != 0) {
                console.log(res.msg)
            } else {
                console.log("获取1/3轨迹成功")
                //  init(res)
                if (status == '1') {
                    resone = res.data
                    return
                } else if (status == '2') {
                    restwo = res.data
                    return
                } else if (status == '3') {
                    resthree = res.data
                    return
                } else {
                    console.log('你输入的status格式不对哦')
                }
            }
        },
        // error:function(res){
        //     console.log('获取失败')
        //     console.log(res)
        // }

    });
}




function inite(dataone,datatwo,datathree){

    var dom = document.getElementById('container');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};
    // var ROOT_PATH = 'https://echarts.apache.org/examples';
    var option;
    // $.get('./js/lines-bus.json', function
    //     (res) {
            //data是后端返回的数据
        // data = Array.from(data);
        // apply和concat降维
        // if(res.code != 0){
        //     console.log('获取数据失败')
        // }else{
        //     console.log('获取数据成功')
        // }
        // let busLines = res.data
        // let busLines = [].concat.apply(
        //     [],
        //     res.data.map(function (busLine, idx) {
                // map()方法是创建一个新数组，其结果是该数组中的每个元素都调用一个提供的函数后返回的结果
                // let prevPt = [];
                // let points = [];
                // 然后列表奇数位依次相加、偶数位依次相加，两两一组,把数据都除以10000,即为各个公交站点地理坐标，每个列表代表1个线路
                // for (let i = 0; i < busLine.length; i += 2) {
                //     let pt = [busLine[i], busLine[i + 1]];
                //     if (i > 0) {
                //         pt = [prevPt[0] + pt[0], prevPt[1] + pt[1]];
                //     }
                //     prevPt = pt;
                //     //各个公交站点地理坐标
                //     points.push([pt[0] / 1e4, pt[1] / 1e4]);
                // }
                // return {
                //     coords: points
                // };
        //     })
        // );
        myChart.setOption(
            (option = {
                bmap: {
                    center: [116.46, 39.92],
                    zoom: 10,
                    roam: true,
                    mapStyle: {
                        styleJson: [
                            {
                                featureType: 'water',
                                elementType: 'all',
                                stylers: {
                                    color: '#d1d1d1'
                                }
                            },
                            {
                                featureType: 'land',
                                elementType: 'all',
                                stylers: {
                                    color: '#f3f3f3'
                                }
                            },
                            {
                                featureType: 'railway',
                                elementType: 'all',
                                stylers: {
                                    visibility: 'off'
                                }
                            },
                            {
                                featureType: 'highway',
                                elementType: 'all',
                                stylers: {
                                    // color: '#fdfdfd'
                                    visibility: 'off'
                                }
                            },
                            {
                                featureType: 'highway',
                                elementType: 'labels',
                                stylers: {
                                     visibility: 'off'
                                }
                            },
                            {
                                featureType: 'arterial',
                                elementType: 'geometry',
                                stylers: {
                                    color: '#fefefe'
                                }
                            },
                            {
                                featureType: 'arterial',
                                elementType: 'geometry.fill',
                                stylers: {
                                    color: '#fefefe'
                                }
                            },
                            {
                                
                                featureType: 'poi',
                                elementType: 'all',
                                stylers: {
                                     visibility: 'off'
                                }
                            },
                            {
                                featureType: 'green',
                                elementType: 'all',
                                stylers: {
                                    visibility: 'off'
                                }
                            },
                            {
                                featureType: 'subway',
                                elementType: 'all',
                                stylers: {
                                     visibility: 'off'
                                }
                            },
                            {
                                featureType: 'manmade',
                                elementType: 'all',
                                stylers: {
                                    color: '#d1d1d1'
                                }
                            },
                            {
                                featureType: 'local',
                                elementType: 'all',
                                stylers: {
                                    color: '#d1d1d1'
                                }
                            },
                            {
                                // 主干道
                                featureType: 'arterial',
                                elementType: 'labels',
                                stylers: {
                                    visibility: 'off'
                                }
                            },
                            {
                                featureType: 'boundary',
                                elementType: 'all',
                                stylers: {
                                    color: '#fefefe'
                                }
                            },
                            {
                                featureType: 'building',
                                elementType: 'all',
                                stylers: {
                                    color: '#d1d1d1'
                                }
                            },
                            {
                                featureType: 'label',
                                elementType: 'labels.text.fill',
                                stylers: {
                                    color: '#999999'
                                }
                            }
                        ]
                    }
                },
                series: [
                    {
                        type: 'lines',
                        coordinateSystem: 'bmap',
                        polyline: true,
                        data: dataone,
                        silent: true,
                        // 确诊------红色
                        lineStyle: {
                            color: '#fd042d',
                            // opacity: 0.8,
                            width: 2
                        },
                        progressiveThreshold: 500,
                        progressive: 200
                    },
                    {
                        type: 'lines',
                        coordinateSystem: 'bmap',
                        polyline: true,
                        data: datatwo,
                        silent: true,
                        // 密接-----蓝色
                        lineStyle: {
                            color: '#5fa3ca',
                            opacity: 0.8,
                            width: 2
                        },
                        progressiveThreshold: 500,
                        progressive: 200
                    },{
                        type: 'lines',
                        coordinateSystem: 'bmap',
                        polyline: true,
                        data: datathree,
                        silent: true,
                        // 次密接-----绿色
                        lineStyle: {
                            color: 'rgb(9, 241, 9)',
                            opacity: 0.4,
                            width: 2
                        },
                        progressiveThreshold: 500,
                        progressive: 200
                    }
                ]
            })
        );

    
    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }
    
    window.addEventListener('resize', myChart.resize);
    
    }
    

















    