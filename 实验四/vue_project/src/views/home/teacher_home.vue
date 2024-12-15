<template>
  <div class="container">
    <!-- 单个卡片容器 -->
    <el-card class="card" shadow="always">
      <h2 class="card-title">班级信息</h2>

      <!-- 班级信息表格 -->
      <el-table :data="classes" style="width: 100%">
        <el-table-column label="课程名称" prop="courseName" width="140" align="center"></el-table-column>
        <el-table-column label="学期" prop="term" width="100" align="center"></el-table-column>
        <el-table-column label="班级人数" prop="count" width="100" align="center"></el-table-column>
        <el-table-column label="班级编号" prop="classNum" width="100" align="center"></el-table-column>
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button @click="manageClass(scope.row)" type="primary" size="small">管理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 管理班级弹窗 -->
    <el-dialog :visible.sync="manageClassDialogVisible" title="管理班级" @close="handleClose" width="65%">
      <div>
        <el-row :gutter="20" style="margin-bottom: 20px;">
          <el-col :span="8">
            <el-input v-model="searchQuery" placeholder="请输入关键词" @input="handleSearchInput" clearable
              style="width: 100%;"></el-input>
          </el-col>
          <el-col :span="14" style="text-align: right;">
            <el-button @click="sortByGradeDesc" type="primary" size="small" style="margin-right: 10px;">成绩降序</el-button>
            <el-button @click="sortByGradeAsc" type="danger" size="small" style="margin-right: 10px;">成绩升序</el-button>
            <el-button @click="exportScores" type="success" size="small" style="margin-right: 0px;">导出成绩</el-button>
          </el-col>
        </el-row>

        <el-table :data="filteredStudentScores" style="width: 100%">
          <el-table-column prop="name" label="学生姓名" width="140" align="center"></el-table-column>
          <el-table-column prop="grade1" label="平时成绩" width="120" align="center"></el-table-column>
          <el-table-column prop="grade2" label="期中成绩" width="120" align="center"></el-table-column>
          <el-table-column prop="grade3" label="实验成绩" width="120" align="center"></el-table-column>
          <el-table-column prop="grade4" label="期末成绩" width="120" align="center"></el-table-column>
          <el-table-column prop="gradeAvg" label="综合成绩" width="120" align="center"></el-table-column>
          <el-table-column label="操作" width="120" align="center">
            <template slot-scope="scope">
              <el-button @click="editScore(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination v-if="filteredStudentScores.length > 0" :current-page="currentPage" :page-size="pageSize"
          :total="totalStudentScores" @current-change="handlePageChange" layout="prev, pager, next"></el-pagination>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="editScoreDialogVisible" title="编辑学生成绩" width="50%">
      <el-form :model="editingStudent" label-width="120px">
        <el-form-item label="姓名">
          <el-input v-model="editingStudent.name" type="text" min="0" max="100" disabled></el-input>
        </el-form-item>
        <el-form-item label="平时成绩">
          <el-input v-model="editingStudent.grade1" type="number" min="0" max="100" @input="updateGradeAvg"></el-input>
          </el-form-item>
          <el-form-item label="期中成绩">
            <el-input v-model="editingStudent.grade2" type="number" min="0" max="100"
              @input="updateGradeAvg"></el-input>
          </el-form-item>
          <el-form-item label="实验成绩">
            <el-input v-model="editingStudent.grade3" type="number" min="0" max="100"
              @input="updateGradeAvg"></el-input>
          </el-form-item>
          <el-form-item label="期末成绩">
            <el-input v-model="editingStudent.grade4" type="number" min="0" max="100"
              @input="updateGradeAvg"></el-input>
          </el-form-item>
          <el-form-item label="综合成绩">
            <el-input v-model="editingStudent.gradeAvg" type="number" min="0" max="100" disabled></el-input>
          </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editScoreDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitScores">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
import axios from '@/api/axios'

export default {
  data() {
    return {
      classes: [], // 存储从后端返回的班级信息
      showDialog: false, // 控制弹窗的显示与隐藏
      selectedClass: null, // 当前选择的班级信息
      manageClassDialogVisible: false,
      searchQuery: '',
      currentPage: 1,
      pageSize: 5,
      currentClassNum: '',
      filteredStudentScores: [],
      editingStudent: {},
      editScoreDialogVisible: false, // 控制编辑成绩弹窗的显示与隐藏
      sort : 0,//控制排序
    };
  },
  mounted() {
    this.fetchClassData();
  },
  methods: {
    // 获取班级数据
    async fetchClassData() {
      try {
        const response = await axios.get('/teacher');
        if (response.data.code === 1) {
          this.classes = response.data.data;
        } 
      } catch (error) {
        console.error('请求失败', error);
        this.$message.error('请求失败，请稍后重试');
      }
    },
    fetchStudentScores(classNum, searchQuery, currentPage, pageSize, sort) {
      axios.post('/teacher/student-scores', new URLSearchParams({
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
    submitScores() {
      axios.post('/teacher/update-student-score', new URLSearchParams({
        name: this.editingStudent.name,
        grade1: this.editingStudent.grade1,
        grade2: this.editingStudent.grade2,
        grade3: this.editingStudent.grade3,
        grade4: this.editingStudent.grade4,
        gradeAvg: this.editingStudent.gradeAvg,
        currentClassNum: this.currentClassNum,
      }), {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      })
        .then(response => {
          if (response.data.code === 1) {
            this.$message.success('成绩修改成功');
            this.editScoreDialogVisible = false;
            this.fetchStudentScores(this.currentClassNum, this.searchQuery, this.currentPage, this.pageSize, 0);
          } else {
            this.$message.error('成绩修改失败');
          }
        })
        .catch(error => {
          console.error('请求失败:', error);
          this.$message.error('提交失败');
        });
    },
    exportScores(){
      axios.post('/teacher/export_to_excel', new URLSearchParams({
        currentClassNum: this.currentClassNum,
      }), {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      })
      .then(response => {
          if (response.data.code === 1) {
            this.$message.success('成绩已导出');
          } else {
            this.$message.error('成绩导出失败');
          }
        })
        .catch(error => {
          console.error('请求失败:', error);
          this.$message.error('请求失败');
        });
    },
    handleSearchInput() {
      this.fetchStudentScores(this.currentClassNum, this.searchQuery, 1, 5, 0);
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
      //this.filteredStudentScores.sort((a, b) => a.gradeAvg - b.gradeAvg);
    },
    sortByGradeDesc() {
      this.sort = 2;
      this.fetchStudentScores(this.currentClassNum, this.searchQuery, this.currentPage, this.pageSize, this.sort)
      //this.filteredStudentScores.sort((a, b) => b.gradeAvg - a.gradeAvg);
    },
    editScore(student) {
      this.editingStudent = { ...student }; // 创建学生数据的副本，避免直接修改原数据
      this.editScoreDialogVisible = true;
    },
    updateGradeAvg() {
      // 确保所有成绩都是数字类型
      const { grade1, grade2, grade3, grade4 } = this.editingStudent;

      // 如果有成绩为空或无效，则不进行计算
      if (grade1 && grade2 && grade3 && grade4) {
        const avg = (parseFloat(grade1) + parseFloat(grade2) + parseFloat(grade3) + parseFloat(grade4)) / 4;
        this.editingStudent.gradeAvg = avg.toFixed(2); // 保留两位小数
      }
    }

  },
};
</script>
<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #f4f7fa;
}

.card {
  width: 600px;
  padding: 20px;
}

.card-title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
}

.el-table {
  margin-top: 20px;
}
</style>