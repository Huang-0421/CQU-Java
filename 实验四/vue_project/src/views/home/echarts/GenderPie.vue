<template>  
    <div ref="mapChart" style="height: 340px"></div>  
  </template>  
  
  <script>  
  import * as echarts from 'echarts';  
  
  export default {  
    name: 'GenderPie',  
    mounted() {  
      this.initChart();  
    },  
    methods: {  
      initChart() {  
        const chartDom = this.$refs.mapChart; // 使用 ref 获取 DOM  
        const myChart = echarts.init(chartDom); // 初始化图表  
        const option = {  
          title: {  
            text: '男女比例饼状图',  
            left: 'center',
            fontSize:20
          },  
          tooltip: {  
            trigger: 'item'  
          },  
          legend: {  
            orient: 'vertical',  
            left: 'left'  
          },  
          series: [  
            {  
              name: 'Access From',  
              type: 'pie',  
              radius: '50%',  
              data: [  
                { value: 1048, name: '男' },  
                { value: 735, name: '女' },  
              ],  
              emphasis: {  
                itemStyle: {  
                  shadowBlur: 10,  
                  shadowOffsetX: 0,  
                  shadowColor: 'rgba(0, 0, 0, 0.5)'  
                }  
              }  
            }  
          ]  
        };  
  
        myChart.setOption(option); // 设置图表选项  
      }  
    }  
  };  
  </script>  
  
  <style scoped>  
  /* 你可以添加组件的样式 */  
  .gender-pie-container {  
  margin-top: 20px;  
  padding: 10px;  
  background: #fff; /* 可选：添加背景颜色 */  
  border-radius: 10px; /* 可选：增加圆角 */  
}
  </style>