<template>
  <div ref="chartContainer" style="height: 340px; width: 100%;"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'BookCall',
  mounted() {
    this.initChart();
  },
  methods: {
    initChart() {
      const chartDom = this.$refs.chartContainer; // 获取 DOM 元素  
      const myChart = echarts.init(chartDom); // 初始化图表  

      const option = {
        title: {
          text: '访问时间',
          left: 'center',
          fontSize: 20,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        toolbox: {
          show: true,
          // feature: {  
          //   saveAsImage: {}  
          // }  
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: [
            '05:30', '05:45', '06:00', '06:15', '06:30', '06:45',
            '07:00', '07:15', '07:30', '07:45', '08:00', '08:15',
            '08:30', '08:45', '09:00', '09:15', '09:30', '09:45',
            '10:00', '10:15', '10:30', '10:45', '11:00', '11:15',
            '11:30', '11:45', '12:00', '12:15', '12:30', '12:45',
            '13:00', '13:15', '13:30', '13:45', '14:00', '14:15',
            '14:30', '14:45', '15:00', '15:15', '15:30', '15:45',
            '16:00', '16:15', '16:30', '16:45', '17:00', '17:15',
            '17:30', '17:45', '18:00', '18:15', '18:30', '18:45',
            '19:00', '19:15', '19:30', '19:45', '20:00', '20:15',
            '20:30', '20:45', '21:00', '21:15', '21:30', '21:45',
            '22:00', '22:15', '22:30'
          ]
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value}'
          },
          axisPointer: {
            snap: true
          }
        },
        visualMap: {
          show: false,
          dimension: 0,
          pieces: [
            { lte: 6, color: 'green' },
            { gt: 6, lte: 8, color: 'green' },
            { gt: 8, lte: 14, color: 'green' },
            { gt: 14, lte: 17, color: 'green' },
            { gt: 17, color: 'green' }
          ]
        },
        series: [
          {
            name: '人数',
            type: 'line',
            smooth: true,
            data: [4, 2, 0, 3, 1, 5, 2, 1, 0, 5, 3, 4, 1, 2, 4, 0, 5, 3, 1, 4, 2, 5,
             1, 0, 3, 2, 4, 1, 5, 3, 0, 2, 1, 4, 0, 5, 2, 1, 3, 4, 1, 5, 0, 4, 2, 3, 
             0, 1, 5, 3, 2, 4, 1, 0, 4, 5, 1, 3, 2, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0,
              1, 1, 0, 1, 1],
            itemStyle: {
              color: 'yellowgreen',
            },
            markArea: {
              itemStyle: {
                color: 'rgba(255, 173, 177, 0.4)'
              },
              data: [
                [
                  { xAxis: '05:30' },
                  { xAxis: '06:30' }
                ],
                [
                  { xAxis: '06:30' },
                  { xAxis: '07:30' }
                ],
                [
                  { xAxis: '20:30' },
                  { xAxis: '21:30' }
                ],
                [
                  { xAxis: '21:30' },
                  { xAxis: '22:30' }
                ]
              ]
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
/* 可选择添加样式 */
</style>