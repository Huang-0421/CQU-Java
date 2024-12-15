<template>
  <div>
    <el-card class="box-card" shadow="hover">
      <el-input 
        v-model="searchQuery" 
        placeholder="请输入关键词" 
        @input="handleSearchInput" 
        clearable
        style="width: 200px;"
      ></el-input>

      <!-- 表格展示数据 -->
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="studentId" label="学号" width="100"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="gender" label="性别" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.gender === 1 ? '男' : '女' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="课程一" width="180">
          <template slot-scope="scope">
            <span>{{ mapCourseName(scope.row.course1) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="课程二" width="180">
          <template slot-scope="scope">
            <span>{{ mapCourseName(scope.row.course2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="课程三" width="180">
          <template slot-scope="scope">
            <span>{{ mapCourseName(scope.row.course3) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleViewClick(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页组件 -->
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize"
        :current-page="currentPage" @current-change="handlePageChange">
      </el-pagination>
    </el-card>

    <!-- 弹窗展示成绩 -->
    <el-dialog :visible.sync="dialogVisible" title="成绩详情">
      <div style="margin-bottom: 10px;">
        <!-- 显示当前学生姓名 -->
        <h4 style="margin: 0; font-size: 16px;">{{ selectedStudentName }}</h4>
      </div>
      <el-table :data="gradeData" style="width: 100%">
        <el-table-column prop="course" label="课程" width="130"></el-table-column>
        <el-table-column prop="normalGrade" label="平时成绩" width="100"></el-table-column>
        <el-table-column prop="midtermGrade" label="期中考试" width="100"></el-table-column>
        <el-table-column prop="experimentGrade" label="实验成绩" width="100"></el-table-column>
        <el-table-column prop="finalGrade" label="期末考试" width="100"></el-table-column>
        <el-table-column prop="comprehensiveGrade" label="综合成绩" width="100"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/api/axios'

export default {
  data() {
    return {
      tableData: [],      // 存储表格数据
      total: 0,           // 总记录数
      pageSize: 10,       // 每页显示的记录数
      currentPage: 1,     // 当前页
      dialogVisible: false,  // 控制成绩弹窗的显示
      gradeData: [],      // 存储成绩数据
      selectedStudentName: "",  // 存储当前选中的学生姓名
      searchQuery: '',    // 存储搜索的关键词
    };
  },
  methods: {
    // 处理分页变化
    handlePageChange(page) {
      this.currentPage = page;
      this.fetchData();  // 刷新数据
    },

    // 实时模糊搜索处理
    handleSearchInput() {
      this.currentPage = 1;  // 重置分页为第一页
      this.fetchData();      // 触发数据加载
    },

    // 获取学生数据
    fetchData() {
      axios.get('/admin/studentlist', {
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          searchQuery: this.searchQuery,  // 传递搜索关键词
        }
      })
        .then(response => {
          if (response.data.code === 1) {
            const { records, total } = response.data.data;
            this.tableData = records;  // 更新表格数据
            this.total = total;        // 更新总记录数
          }
        })
        .catch(error => {
          console.error("Error fetching data:", error);
        });
    },

    // 映射课程名称
    mapCourseName(courseCode) {
      const courseMap = {
        computer: '计算机组成原理',
        database_sys: '数据库系统',
        java: 'Java企业级应用',
        structure: '数据结构'
      };
      return courseMap[courseCode] || courseCode;  // 如果没有匹配到，返回原始的课程代码
    },

    // 处理查看按钮点击事件
    handleViewClick(student) {
      this.selectedStudentName = student.name;  // 保存选中的学生姓名
      this.fetchGrades(student.name);  // 获取该学生的成绩数据
    },

    // 获取学生的成绩
    fetchGrades(studentName) {
      axios.get('/admin/showgrade', {
        params: { studentName }
      })
        .then(response => {
          if (response.data.code === 1) {
            const gradeData = this.transformGradeData(response.data.data);
            this.gradeData = gradeData;
            this.dialogVisible = true;  // 显示成绩弹窗
          }
        })
        .catch(error => {
          console.error("Error fetching grades:", error);
        });
    },

    // 转换后端成绩数据为表格可用的格式
    transformGradeData(data) {
      const transformedData = [];
      for (const key in data) {
        if (data.hasOwnProperty(key)) {
          const course = data[key];
          transformedData.push({
            course: this.mapCourseName(course.courseName),
            normalGrade: course.grade1,
            midtermGrade: course.grade2,
            experimentGrade: course.grade3,
            finalGrade: course.grade4,
            comprehensiveGrade: course.gradeAvg,
          });
        }
      }
      return transformedData;
    },
  },
  created() {
    this.fetchData();  // 初始化加载第一页数据
  }
};
</script>
