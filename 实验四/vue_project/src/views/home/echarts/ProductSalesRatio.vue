<template>
    <div>
        <div style="margin-top: 10px;">
            <div id="chart2" style="height:340px;"></div>
        </div>
    </div>
</template>

<script>
//import axios from 'axios'
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
        //绘制饼图方法
        pie(result) {

            var myChart = echarts.init(document.getElementById('chart2'));
            // 绘制图表
            myChart.setOption({
                title: {
                    text: '各课程人数占比图',
                    left: 'center',
                    fontSize: 20
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
                        name: '各科室人数占比图',
                        type: 'pie',
                        radius: '50%',
                        data: [
                            { value: 88, name: '计算机组成原理' },
                            { value: 53, name: 'Java企业级开发' },
                            { value: 67, name: '数据库系统' },
                            { value: 75, name: '数据结构' },
                            { value: 35, name: '大数据架构' }
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
            });
        }
    },
    mounted() {
        console.log('mounted-id', document.getElementById("charts"));
        // this.getNationTotals();
        // this.getRegionTotals();
        // this.getYearTotal();
        this.pie();
    },

})

</script>

<style></style>
