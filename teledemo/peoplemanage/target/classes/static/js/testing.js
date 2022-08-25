$(function (){
    display('main')
})

function display(div){
    let myChart = echarts.init(document.getElementById(div));

    $.get('../json/map/100000.json',(chinaJson)=>{
        console.log(chinaJson);
        echarts.registerMap('chinaMap', chinaJson)
        let option = {
            geo: {
                type: 'map',
                map: 'chinaMap',
                roam: true,
                label: {
                    show: false
                },
                zoom: 1.2,
                center: [87.617733, 43.792818]
            }
        }
        myChart.setOption(option)
    })
}
