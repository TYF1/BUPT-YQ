$(function() {
    mapChart('mapChart')
})

let mapStack = []
let parentId = null
let parentName = null

let chinaId = 100000
let chinaName = 'china'
let chinaJson = null

let myChart = null

function initMapData(mapJson) {
    let mapData = [];
    for (let i = 0; i < mapJson.features.length; i++) {
        mapData.push({
            name: mapJson.features[i].properties.name
        })
    }
    return mapData;
}

function back(){
    if (mapStack.length !== 0){
        let map = mapStack.pop()
        $.get('../json/map/' + map.mapId + '.json', function(mapJson){
            registerAndSetOption(myChart, map.mapId, map.mapName, mapJson, false);
            parentId = map.mapId;
            parentName = map.mapName;
        })
    }
}

function registerAndSetOption(myChart, id, name, mapJson, flag){
    echarts.registerMap(name, mapJson);
    myChart.setOption({
        series: [{
            type: 'map',
            map: name,
            data: initMapData(mapJson)
        }]
    });

    if(flag){
        mapStack.push({
            mapId: parentId,
            mapName: parentName
        })
        parentId = id;
        parentName = name;
    }
}

function mapChart(div){
    $.get('../json/map/' + chinaId + '.json', function(mapJson){
        chinaJson = mapJson;
        myChart = echarts.init(document.getElementById(div));
        registerAndSetOption(myChart, chinaId, chinaName, mapJson, false)

        parentId = chinaId;
        parentName = chinaName;

        myChart.on('click', function(param){
            let cityId = cityMap[param.name]
            if(cityId){
                $.get('../json/map/' + cityId + '.json', function(mapJson){
                    registerAndSetOption(myChart, cityId, param.name, mapJson, true);
                })
            } else {
                registerAndSetOption(myChart, chinaId, chinaName, chinaJson, false);
                mapStack = [];
                parentId = chinaId;
                parentName = chinaName;
            }
        })
    })
}