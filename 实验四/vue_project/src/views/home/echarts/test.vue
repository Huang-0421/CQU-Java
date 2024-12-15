
<template>  
  <div>  
    <div  
      ref="chinaMap"  
      style="height: 600px; width: 100%; background:rgb(240,240,240)"  
    >  
      地图1  
    </div>  
  </div>  
</template>  

<script lang="ts" setup>  
import * as echarts from 'echarts';  
import chinaJSON from './china.json';  
import { onMounted, ref } from 'vue';  

const chinaMap = ref();  

// 预定义所有省份的经纬度和名称  
const provinceCoordinates = {  
  '北京': [116.405285, 39.904989],  
  '天津': [117.190182, 39.125596],  
  '上海': [121.4737, 31.2304],  
  '重庆': [106.530635, 29.544607],  
  '河北': [115.6612, 38.5139],  
  '山西': [112.4121, 37.5789],  
  '内蒙古': [111.7665, 40.8174],  
  '辽宁': [123.429096, 41.796768],  
  '吉林': [125.838199, 43.963015],  
  '黑龙江': [126.642464, 45.756967],  
  '江苏': [119.421999, 32.14015],  
  '浙江': [120.153576, 30.287459],  
  '安徽': [117.283042, 31.86119],  
  '福建': [119.296982, 26.100358],  
  '江西': [115.892151, 28.676493],  
  '山东': [117.000923, 36.675807],  
  '河南': [113.665412, 34.757975],  
  '湖北': [114.298572, 30.584455],  
  '湖南': [113.0823, 28.2569],  
  '广东': [113.2644, 23.1291],  
  '海南': [110.33119, 20.031971],  
  '四川': [104.065735, 30.659462],  
  '贵州': [106.713478, 26.578343],  
  '云南': [102.710002, 25.045806],  
  '西藏': [91.132212, 29.660361],  
  '陕西': [108.953445, 34.288842],  
  '甘肃': [103.826308, 36.059987],  
  '宁夏': [106.278179, 38.46637],  
  '新疆': [87.613228, 43.810394],   
  '香港': [114.15769, 22.28552],  
  '澳门': [113.54909, 22.19895],  
  '台湾': [121.509062, 25.044332]  
}; 

// 后端返回的数据示例  
let backendData = [  
  { name: '北京', value: 100 },
  { name: '兰州', value: 300 },  
  { name: '新疆', value: 200 }, 
  { name: '长沙', value: 250 },  
  { name: '西安', value: 400 }, 
  { name: '云南', value: 400 },  
  { name: '上海', value: 500 }, 
  { name: '广东', value: 600 }, 
  { name: '重庆', value: 999 },   
  { name: '四川', value: 600 },
  { name: '湖北', value: 400 }, 
];  

// 根据后端返回的数据生成热力图数据  
let heatmapData = Object.keys(provinceCoordinates).map(province => {  
  const coords = provinceCoordinates[province];  
  // 查找后端数据中的对应省份  
  const backendEntry = backendData.find(item => item.name === province);  
  const weight = backendEntry ? backendEntry.value : 0; // 若无返回则权重为0  
  return { name: province, value: [...coords, weight] };  
});  

// 绘制中国热力图  
function drawChina() {  
  const myChart = echarts.init(chinaMap.value);  
  echarts.registerMap('china', chinaJSON); // 注册可用的地图  
  const option = {  
    geo: {  
      map: 'china',  
      roam: true, // 是否允许缩放，拖拽  
      zoom: 1, // 初始化大小  
      scaleLimit: {  
        min: 1,  
        max: 2,  
      },  
      center: [115.97, 29.71],  
      itemStyle: {  
        areaColor: '#ffffff',  
        borderColor: '#232652',  
        borderWidth: 1,  
      },  
      // 高亮状态  
      emphasis: {  
        itemStyle: {  
          areaColor: '#66ccff',  
        },  
      },  
    },  
    visualMap: {  
      min: 0,  
      max: 1000,  
      calculable: true,  
      inRange: {  
        color: ['white', 'red'], // 从蓝色到红色的渐变  
      },  
    },  
    series: [  
      {  
        name: '热力图数据',  
        type: 'heatmap',  
        coordinateSystem: 'geo',  
        data: heatmapData,  
      },  
    ],  
  };  
  myChart.setOption(option);  
}  

onMounted(() => {  
  drawChina();  
});  
</script>

<style scoped>  
/* 可根据需要添加样式 */  
</style>