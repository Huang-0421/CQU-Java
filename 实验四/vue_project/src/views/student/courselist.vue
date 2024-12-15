<template>
    <div class="container">
        <el-card class="box-card" shadow="hover">
            <div slot="header" class="clearfix">
                <span class="course-title">所有课程</span>
            </div>

            <!-- 课程成绩详情弹窗 -->
            <el-dialog :visible.sync="dialogVisible" @close="handleClose"
                style="width: 100%; margin-top: 140px !important; margin-left: 100px !important;">
                <div>
                    <h2>课程成绩详情</h2>
                    <!-- 修改间隔，使按钮和表格之间的空间更小 -->
                    <el-row :gutter="10" style="margin-bottom: 10px;">
                        <el-col :span="24" style="text-align: right;">
                            <el-button @click="showStatistics" type="success" size="small">显示成绩统计</el-button>
                        </el-col>
                    </el-row>
                    <el-table :data="selectedCourseGrades">
                        <el-table-column prop="courseName" label="课程名" width="160"></el-table-column>
                        <el-table-column prop="normalGrade" label="平时成绩" width="100"></el-table-column>
                        <el-table-column prop="midtermGrade" label="期中考试" width="100"></el-table-column>
                        <el-table-column prop="experimentGrade" label="实验成绩" width="100"></el-table-column>
                        <el-table-column prop="finalGrade" label="期末考试" width="100"></el-table-column>
                        <el-table-column prop="comprehensiveGrade" label="综合成绩" width="100"></el-table-column>
                    </el-table>
                </div>
            </el-dialog>

            <el-dialog :visible.sync="StatisticsDialogVisible" title="成绩分析" @close="statisticsClose" width="80%">
                <!-- 使用 flex 布局，左右并排展示两个图表 -->
                <div style="display: flex; justify-content: space-between; gap: 20px;">
                    <!-- 左侧雷达图 -->
                    <div v-if="showChart" ref="radarChart" style="width: 48%; height: 400px;"></div>

                    <!-- 右侧柱状图 -->
                    <div v-if="showChart" ref="echartsContainer" style="width: 48%; height: 400px;"></div>
                </div>
            </el-dialog>

            <div class="course-content">
                <!-- 如果没有课程，则显示提示和选课按钮 -->
                <div v-if="!hasCourse" class="no-courses">
                    <h3>暂无课程信息，赶快选课吧！</h3>
                    <el-button class="nocourseButton" @click="openCourseSelection" type="primary">立即选课</el-button>
                </div>
                <!-- 如果有课程，则显示课程列表 -->
                <el-table v-else :data="courses" class="custom-table">
                    <el-table-column label="课程" width="160">
                        <template slot-scope="scope">
                            <span>{{ scope.row.courseName }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="授课教师" width="160">
                        <template slot-scope="scope">
                            <span>{{ scope.row.teacherName }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="开课学期" width="160">
                        <template slot-scope="scope">
                            <span>{{ scope.row.term }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="160">
                        <template slot-scope="scope">
                            <el-button class="operationButton" @click="openCourseDetails(scope.row)" type="primary"
                                size="small">详情</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-card>
    </div>
</template>


<script>
import axios from '@/api/axios';
import * as echarts from 'echarts';

export default {
    data() {
        return {
            courses: [],            // 课程数据
            dialogVisible: false,   // 弹窗显示状态
            selectedCourseGrades: [], // 当前选中课程的成绩数据
            showChart: false,       // 是否显示雷达图
            hasCourse: true,        // 是否有课程
            StatisticsDialogVisible: false, // 控制统计图表的显示
        };
    },
    created() {
        this.fetchCourses();  // 组件创建时，获取课程数据
    },
    methods: {
        // 获取课程数据
        async fetchCourses() {
            try {
                const response = await axios.get('/student/courses'); // 请求课程数据
                if (response.data.code === 1) {
                    this.hasCourse = true;
                    const data = response.data.data;

                    this.courses = [];
                    for (let i = 1; i <= 3; i++) {
                        const courseKey = `course${i}`;
                        if (data[courseKey]) {
                            const course = data[courseKey];
                            this.courses.push({
                                courseName: course.grade.courseName,
                                teacherName: course.teacher_name,
                                term: course.term,
                                grade: course.grade
                            });
                        }
                    }
                } else {
                    this.hasCourse = false;
                }
            } catch (error) {
                console.error('获取课程数据失败:', error);
            }
        },

        // 打开课程详情弹窗
        openCourseDetails(course) {
            this.selectedCourseGrades = [
                {
                    courseName: course.grade.courseName,
                    normalGrade: course.grade.grade1,
                    midtermGrade: course.grade.grade2,
                    experimentGrade: course.grade.grade3,
                    finalGrade: course.grade.grade4,
                    comprehensiveGrade: course.grade.gradeAvg,
                    classNum: course.grade.classNum,
                }
            ];
            this.dialogVisible = true;
        },

        // 关闭课程详情弹窗时的处理
        handleClose() {
            this.selectedCourseGrades = [];  // 清空课程成绩数据
            this.showChart = false;           // 隐藏雷达图
        },

        // 打开选课页面
        openCourseSelection() {
            this.$router.push('/student/course-selection');
        },
        showStatistics() {
            this.StatisticsDialogVisible = true;
            this.showRadarChart();
            this.renderChart();
        },
        statisticsClose() {
            this.StatisticsDialogVisible = false;
        },
        // 显示雷达图
        showRadarChart() {
            this.showChart = true;
            // 延迟初始化 ECharts
            setTimeout(() => {
                this.$nextTick(() => {
                    const chartDom = this.$refs.radarChart;
                    if (chartDom) {
                        const myChart = echarts.init(chartDom);

                        // 设置雷达图的数据
                        const grades = this.selectedCourseGrades[0];
                        const data = [
                            {
                                value: [
                                    grades.normalGrade,
                                    grades.midtermGrade,
                                    grades.experimentGrade,
                                    grades.finalGrade,
                                    grades.comprehensiveGrade,
                                ],
                                name: '成绩'
                            }
                        ];

                        const option = {
                            title: {
                                text: '课程成绩雷达图',
                                left: 'center',
                            },
                            tooltip: {
                                trigger: 'item',
                            },
                            radar: {
                                indicator: [
                                    { name: '平时成绩', max: 100 },
                                    { name: '期中考试', max: 100 },
                                    { name: '实验成绩', max: 100 },
                                    { name: '期末考试', max: 100 },
                                    { name: '综合成绩', max: 100 }
                                ],
                                shape: 'circle',
                            },
                            series: [
                                {
                                    name: '成绩',
                                    type: 'radar',
                                    data: data,
                                    areaStyle: {
                                        color: 'rgba(0, 139, 139, 0.5)'
                                    },
                                    itemStyle: {
                                        color: '#00bcd4'
                                    },
                                }
                            ]
                        };

                        myChart.setOption(option);
                    }
                });
            }, 200);  // 延迟200ms
        },
        renderChart() {
            // 向后台发送 POST 请求获取成绩分段统计
            axios.post('/student/statistics', new URLSearchParams({
                courseName: this.selectedCourseGrades[0].courseName,
                classNum: this.selectedCourseGrades[0].classNum, // 传递当前教学班号
            }), {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            })
                .then(response => {
                    if (response.data.code === 1) {
                        // 获取分段统计数据
                        const scoreRangeData = response.data.data;

                        // 获取分数段（如：["90-100", "0-59", "60-69", "70-79", "80-89"]）
                        let scoreRanges = Object.keys(scoreRangeData);

                        // 获取每个分数段的人数（如：[1, 0, 1, 18, 17]）
                        let counts = Object.values(scoreRangeData);

                        // 对分数段进行排序，排序依据是分数段的起始值
                        scoreRanges = scoreRanges.sort((a, b) => {
                            // 提取分数段的起始值并进行比较
                            const aStart = parseInt(a.split('-')[0]);
                            const bStart = parseInt(b.split('-')[0]);
                            return aStart - bStart;
                        });

                        // 按照排序后的分数段顺序调整 counts 数组的顺序
                        counts = scoreRanges.map(range => scoreRangeData[range]);

                        // 配置 ECharts 图表
                        const option = {
                            title: {
                                text: '学生成绩分布统计',
                                left: 'center',
                                top: '20',
                            },
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: { type: 'shadow' }
                            },
                            xAxis: {
                                type: 'category',
                                data: scoreRanges,  // 使用排序后的成绩分段作为 X 轴数据
                            },
                            yAxis: {
                                type: 'value',
                                name: '人数',
                                axisLabel: {
                                    formatter: '{value}',
                                },
                            },
                            series: [
                                {
                                    data: counts,  // 使用各分段的学生人数作为 Y 轴数据
                                    type: 'bar',
                                    color: '#4CAF50',
                                }
                            ],
                        };

                        // 获取 ECharts 容器并渲染图表
                        const chart = echarts.init(this.$refs.echartsContainer);
                        chart.setOption(option);
                    } else {
                        this.$message.error('获取统计数据失败');
                    }
                })
                .catch(error => {
                    console.error('请求失败:', error);
                    this.$message.error('请求失败');
                });
        }

    },
};
</script>

<style scoped>
.container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 650px;
    background-color: #f5f5f5;
}

.course-card {
    width: 60%;
    max-width: 1200px;
}

.box-card {
    margin-top: 10px;
    padding: 20px;
    background-color: #fff;
}

.course-title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
    text-align: center;
}

.course-content {
    margin-top: 10px;
}

.custom-table {
    width: 100%;
    padding: 10px;
    border-spacing: 0;
    border-collapse: collapse;
}

.custom-table th,
.custom-table td {
    padding: 12px;
    text-align: center;
    border: 1px solid #ddd;
}

.custom-table th {
    background-color: #f0f0f0;
    color: #555;
}

.operationButton {
    margin-right: 10px;
}

.no-courses {
    text-align: center;
}

.no-courses p {
    font-size: 18px;
    color: #555;
}

.nocourseButton {
    margin-top: 20px;
}
</style>
