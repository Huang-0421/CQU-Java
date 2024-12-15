<template>
    <div class="container">
        <div class="class-card">
            <el-card class="box-card" shadow="hover">
                <div slot="header" class="clearfix">
                    <span class="class-title">所有班级</span>
                </div>
                <div class="class-content">
                    <div class="scrollable-table">
                        <el-table :data="classes" class="custom-table">
                            <el-table-column label="教学班号" width="120">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.id }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="课程名称" width="160">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.courseName }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="授课教师" width="160">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.teacherName }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="总人数" width="120">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.count }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" width="120">
                                <template slot-scope="scope">
                                    <el-button @click="manageClass(scope.row)" type="primary"
                                        size="small">管理</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </div>
            </el-card>
        </div>

        <!-- 管理班级弹窗 -->
        <el-dialog :visible.sync="manageClassDialogVisible" title="管理班级" @close="handleClose" width="55%">
            <div>
                <el-row :gutter="20" style="margin-bottom: 20px;">
                    <el-col :span="8">
                        <el-input v-model="searchQuery" placeholder="请输入关键词" @input="handleSearchInput" clearable
                            style="width: 100%;"></el-input>
                    </el-col>
                    <el-col :span="14" style="text-align: right;">
                        <el-button @click="sortByGradeDesc" type="primary" size="small"
                            style="margin-right: 10px;">成绩降序</el-button>
                        <el-button @click="sortByGradeAsc" type="danger" size="small"
                            style="margin-right: 10px;">成绩升序</el-button>
                        <el-button @click="showStatistics" type="success" size="small">统计</el-button>
                    </el-col>
                </el-row>

                <el-table :data="filteredStudentScores" style="width: 100%">
                    <el-table-column prop="name" label="学生姓名" width="180"></el-table-column>
                    <el-table-column prop="grade1" label="平时成绩" width="120"></el-table-column>
                    <el-table-column prop="grade2" label="期中成绩" width="120"></el-table-column>
                    <el-table-column prop="grade3" label="实验成绩" width="120"></el-table-column>
                    <el-table-column prop="grade4" label="期末成绩" width="120"></el-table-column>
                    <el-table-column prop="gradeAvg" label="综合成绩" width="120"></el-table-column>
                </el-table>

                <el-pagination v-if="filteredStudentScores.length > 0" :current-page="currentPage" :page-size="pageSize"
                    :total="totalStudentScores" @current-change="handlePageChange"
                    layout="prev, pager, next"></el-pagination>
            </div>
        </el-dialog>
        <el-dialog :visible.sync="StatisticsDialogVisible" title="班级统计" @close="statisticsClose" width="40%">
            <!-- ECharts柱状图 -->
            <div v-if="showChart" style="height: 400px;" ref="echartsContainer"></div>
        </el-dialog>
    </div>
</template>

<script>

import axios from '@/api/axios'
import * as echarts from 'echarts';

export default {
    data() {
        return {
            classes: [],
            filteredStudentScores: [],
            totalStudentScores: 0,
            searchQuery: '',
            currentPage: 1,
            pageSize: 5,
            manageClassDialogVisible: false,
            currentClassNum: '',
            StatisticsDialogVisible: false, // 控制统计图表的显示
            showChart: true,
            sort: 0,
        };
    },
    mounted() {
        this.fetchClassList();
    },
    methods: {
        fetchClassList() {
            axios.get('/admin/classlist')
                .then(response => {
                    if (response.data.code === 1) {
                        this.classes = response.data.data;
                    } else {
                        this.$message.error('获取班级数据失败');
                    }
                })
                .catch(error => {
                    console.error('请求失败:', error);
                    this.$message.error('请求失败');
                });
        },
        fetchStudentScores(classNum, searchQuery, currentPage, pageSize, sort) {
            axios.post('/admin/student-scores', new URLSearchParams({
                classNum: classNum,
                searchQuery: searchQuery,
                currentPage: currentPage,
                pageSize: pageSize,
                sort: sort,
            }), {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            })
                .then(response => {
                    if (response.data.code === 1) {
                        this.filteredStudentScores = response.data.data.data;
                        this.totalStudentScores = response.data.data.total;
                    } else {
                        this.$message.error('获取学生成绩数据失败');
                    }
                })
                .catch(error => {
                    console.error('请求失败:', error);
                    this.$message.error('请求失败');
                });
        },
        handleSearchInput() {
            this.fetchStudentScores(this.currentClassNum, this.searchQuery, 1, 5, this.sort);
        },
        handlePageChange(page) {
            this.currentPage = page;
            this.fetchStudentScores(this.currentClassNum, this.searchQuery, this.currentPage, this.pageSize, this.sort);
        },
        manageClass(classRow) {
            this.manageClassDialogVisible = true;
            this.currentClassNum = classRow.id;
            this.fetchStudentScores(this.currentClassNum, this.searchQuery, this.currentPage, this.pageSize, 0);
        },
        handleClose() {
            this.searchQuery = '';
            this.currentClassNum = '';
            this.currentPage = 1;
            this.pageSize = 5;
            this.manageClassDialogVisible = false;
        },
        sortByGradeAsc() {
            this.sort = 1;
            this.fetchStudentScores(this.currentClassNum, this.searchQuery, this.currentPage, this.pageSize, this.sort)
        },
        sortByGradeDesc() {
            this.sort = 2;
            this.fetchStudentScores(this.currentClassNum, this.searchQuery, this.currentPage, this.pageSize, this.sort)
        },
        showStatistics() {
            this.StatisticsDialogVisible = true;
            this.renderChart();
        },
        statisticsClose() {
            this.StatisticsDialogVisible = false;
        },
        renderChart() {
            // 向后台发送 POST 请求获取成绩分段统计
            axios.post('/admin/statistics', new URLSearchParams({
                classNum: this.currentClassNum,  // 传递当前教学班号
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
/* 容器样式 */
.container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 670px;
    background-color: #f5f5f5;
}

/* 其他样式 */
.echarts-container {
    height: 400px;
    width: 100%;
    margin-top: 20px;
}

/* 卡片样式 */
.class-card {
    width: 65%;
    max-width: 1200px;
    margin: 20px 0;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 卡片标题 */
.class-title {
    font-size: 20px;
    font-weight: bold;
}

/* 表格样式 */
.custom-table {
    width: 100%;
    padding-left: 10px;
    padding-right: 10px;
    border-spacing: 0;
}

/* 表格列的间距和行高 */
.el-table th,
.el-table td {
    padding: 12px 16px;
    text-align: center;
}

/* 表头样式 */
.el-table th {
    background-color: #f7f7f7;
    color: #666;
    font-weight: 600;
}

/* 表格的行悬停效果 */
.el-table__row:hover {
    background-color: #f1f1f1;
}

/* 弹窗样式 */
.el-dialog {
    max-width: 800px;
}

.el-form-item {
    margin-bottom: 20px;
}

.el-dialog__header {
    background-color: #f7f7f7;
    font-size: 18px;
    font-weight: 600;
    color: #333;
}

.el-dialog__footer {
    padding: 15px;
    text-align: right;
}

/* 输入框样式 */
.el-input {
    width: 100%;
}

.scrollable-table {
    max-height: 500px;
    overflow-y: auto;
}
</style>
