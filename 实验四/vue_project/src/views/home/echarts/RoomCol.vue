<template>  
  <div ref="mapChart" style="height: 340px"></div>  
</template>  

<script>  
import * as echarts from 'echarts';  

export default {  
  name: 'AgeCol',  
  mounted() {  
      this.initChart();  
      window.addEventListener('resize', this.resizeChart); // 添加窗口大小变化事件监听  
  },  
  beforeDestroy() {  
      window.removeEventListener('resize', this.resizeChart); // 组件销毁时移除事件监听  
  },  
  methods: {  
      initChart() {  
          const chartDom = this.$refs.mapChart; // 使用 ref 获取 DOM  
          const myChart = echarts.init(chartDom); // 初始化图表  

          const option = {  
              title: {  
                  text: '科室人数分布柱状图',  
                  left: 'center',  
                  fontSize: 20  
              },  
              tooltip: {  
                  trigger: 'axis',  
                  axisPointer: {  
                      type: 'shadow'  
                  }  
              },  
              legend: {  // 添加图例  
                  data: ['总人数', '在岗人数'],  
                  top: '10%'  
              },  
              grid: {  
                  left: '3%',  
                  right: '4%',  
                  bottom: '3%',  
                  containLabel: true  
              },  
              xAxis: [  
                  {  
                      type: 'category', // x 轴类型  
                      data: ['内科', '外科', '妇科', '儿科', '骨科'],  
                      axisTick: {  
                          alignWithLabel: true  
                      }  
                  }  
              ],  
              yAxis: [  
                  {  
                      type: 'value'  
                  }  
              ],  
              series: [  
                  {  
                      name: '总人数', // 系列名  
                      type: 'bar',  
                      barWidth: '40%', // 设置柱的宽度  
                      data: [52, 200, 334, 390, 330] //总人数数据  
                  },  
                  {  
                      name: '在岗人数', // 另一个系列名  
                      type: 'bar',  
                      barWidth: '35%', // 设置柱的宽度  
                      data: [30, 150, 200, 280, 250], //在岗人数数据  
                      itemStyle: {  
                          // 为在岗人数设置不同的颜色  
                          color: '#a1cde3'  
                      }  
                  }  
              ]  
          };  

          myChart.setOption(option); // 设置图表选项  
          this.myChart = myChart; // 存储图表实例  
      },  
      resizeChart() {  
          if (this.myChart) {  
              this.myChart.resize(); // 调整图表的大小  
          }  
      }  
  }  
};  
</script>  

<style scoped>  
/* 你可以添加组件的样式 */  
</style>