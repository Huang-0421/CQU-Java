<template>
    <div>
        <div>
            <div id="chart1" style="width:700px;height:340px;"></div>
        </div>
    </div>
</template>

<script>
//import axios from 'axios'
import { reduce } from 'd3';
import * as echarts from 'echarts';// eslint-disable-line no-unused-vars

export default ({
    data() {
        return {
            nationNames: [],
            nationTotals: [],
            regionTotals: []
        }
    },
    methods: {
        //绘制柱状图表
        bar(nationNames, nationTotals) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('chart1'));
            // 绘制图表
            myChart.setOption({
                title: {  
            text: '教师年龄分布图',  
            left: 'center',
            fontSize:20
          }, 
                tooltip: {
                    trigger: 'axis',
                },
                xAxis: {
                    //data:nationNames
                    data:['18-30', '30-40', '40-50', '50-60', '60-70', '70以上']
                },
                yAxis: { name: '人数' },
                series: [
                    {
                        name: '人数',
                        type: 'line',
                        //data: nationTotals,
                        data:  [1, 4, 3, 20, 1, 0],
                        label: {//图形上的文本标签，可用于说明图形的一些数据信息，比如值。名称等
                            show: true
                        },
                        smooth: true,//表示平滑曲线显示
                        itemStyle:{
                            color:'red',
                        },
                    },
                    {
                        name: '人数',
                        type: 'bar',
                        data: [1, 4, 3, 20, 1, 0],
                        itemStyle:{
                            color:'green',
                        },
                    }
                ]
            });
        }
    },
    mounted() {
        console.log('mounted-id', document.getElementById("charts"));
        // this.getNationTotals();
        this.bar();
    },

})

</script>

<style></style>
