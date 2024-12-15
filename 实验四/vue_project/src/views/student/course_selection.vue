<template>
  <div class="container">
    <div v-if="!isSelected">
      <el-card class="box-card" shadow="hover">
        <div slot="header" class="clearfix">
          <span class="course-title">选课系统</span>
        </div>

        <!-- 设置表格的滚动区域 -->
        <div class="table-container">
          <el-table :data="classes" class="custom-table" style="width: 100%;">
            <el-table-column label="课程名" width="160">
              <template slot-scope="scope">
                <span>{{ scope.row.courseName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="教师" width="150">
              <template slot-scope="scope">
                <span>{{ scope.row.teacherName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="开课学期" width="150">
              <template slot-scope="scope">
                <span>{{ scope.row.term }}</span>
              </template>
            </el-table-column>
            <el-table-column label="已选人数" width="150">
              <template slot-scope="scope">
                <span>{{ scope.row.count }} / 40</span>
              </template>
            </el-table-column>
            <el-table-column label="选择" width="150">
              <template slot-scope="scope">
                <el-checkbox v-model="checked[scope.row.id]" :disabled="scope.row.count >= 40"
                  @change="validateSelection(scope.row)">
                  选择
                </el-checkbox>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 确认选课和取消选择按钮 -->
        <div class="action-buttons">
          <el-button type="primary" @click="confirmSelection">确认选课</el-button>
          <el-button type="danger" @click="cancelSelection" style="margin-left: 10px;">取消选择</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from '@/api/axios'

export default {
  data() {
    return {
      classes: [], // 教学班数据
      isSelected: false, // 判断是否已选课
      checked: {}, // 存储已选的教学班（按教学班 ID）
      selectedCourses: {}, // 存储已选的课程名和对应的班级 ID
    };
  },
  created() {
    this.checkIfSelected(); // 在组件创建时检查是否已选课
  },
  methods: {
    // 检查学生是否已选课
    async checkIfSelected() {
      try {
        const response = await axios.get('/student/courses');
        if (response.data.code === 1 && Object.keys(response.data.data).length === 3) {
          // 如果已经选满课
          this.isSelected = true;
          this.$message.error("您已选课，无法进入选课系统")
          this.$router.push('/student/courselist')
        } else {
          // 如果没有选课，获取教学班信息
          this.fetchClasses();
        }
      } catch (error) {
        console.error('获取选课信息失败:', error);
      }
    },

    // 获取教学班数据
    async fetchClasses() {
      try {
        const response = await axios.get('/student/showclasses');
        if (response.data.code === 1) {
          this.classes = response.data.data;
        } else {
          this.$message.error('获取教学班信息失败');
        }
      } catch (error) {
        console.error('获取教学班信息失败:', error);
      }
    },

    // 校验选课规则
    validateSelection(row) {
      const selectedIds = Object.keys(this.checked).filter(id => this.checked[id]); // 已选择的教学班 ID
      const selectedCourses = {};

      // 检查是否超出选择上限
      if (selectedIds.length > 3) {
        this.$message.error('最多只能选择 3 个教学班');
        this.checked[row.id] = false; // 撤销当前选择
        return;
      }

      // 检查同一课程是否重复选择
      for (const id of selectedIds) {
        const course = this.classes.find(item => item.id === Number(id));
        if (selectedCourses[course.courseName]) {
          this.$message.error('同一课程只能选择一个班级');
          this.checked[row.id] = false; // 撤销当前选择
          return;
        }
        selectedCourses[course.courseName] = id; // 记录课程和班级
      }

      this.selectedCourses = selectedCourses;
    },

    // 确认选课
    async confirmSelection() {
      const selectedIds = Object.keys(this.checked).filter(id => this.checked[id]); // 获取选中的教学班 ID

      if (selectedIds.length === 0) {
        this.$message.error('请至少选择一个教学班');
        return;
      }

      // 将数组转换为 query 参数字符串
      const params = new URLSearchParams();
      selectedIds.forEach(id => {
        params.append('classIds[]', id);  // 将每个班级 ID 添加到 classIds[] 数组
      });

      try {
        const response = await axios.post('/student/selectcourse', params, {
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, // 请求头设置为表单编码
        });

        if (response.data.code === 1) {
          this.$message.success('选课成功');
          this.$router.push('/student/courselist'); // 跳转到课程列表页面
        } else {
          this.$message.error(response.data.msg || '选课失败，请重试');
          this.cancelSelection();
        }
      } catch (error) {
        console.error('选课请求失败:', error);
        this.$message.error('选课失败，请重试');
      }
    },

    // 取消选择
    cancelSelection() {
      // 清除所有选择
      this.checked = {}; // 清空已选择的班级
      this.selectedCourses = {}; // 清空已选课程

      // 强制 Vue 更新，清空复选框
      this.$nextTick(() => {
        this.classes.forEach(course => {
          this.$set(this.checked, course.id, false); // 确保每个教学班的选中状态是 false
        });
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

.box-card {
  width: 95%;
  max-width: 1200px;
  margin-top: 20px;
  padding: 20px;
  background-color: #fff;
}

.course-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  text-align: center;
}

.table-container {
  max-height: 400px;
  /* 最大高度 */
  overflow-y: auto;
  /* 垂直滚动 */
  margin-top: 20px;
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

.action-buttons {
  text-align: right;
  margin-top: 20px;
}
</style>