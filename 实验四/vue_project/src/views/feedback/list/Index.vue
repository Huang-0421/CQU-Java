<template>
  <div class="container">
    <div class="course-card">
      <el-card class="box-card" shadow="hover">
        <div slot="header" class="clearfix">
          <span class="course-title">所有课程</span>
          <el-button type="success" round style=" margin-left: 350px" size="small"
            @click="showAddCourseDialog">新增课程</el-button>
        </div>

        <div class="course-content">
          <el-table :data="courses" class="custom-table">
            <el-table-column label="编号" width="100">
              <template slot-scope="scope">
                <span>{{ scope.row.id }}</span>
              </template>
            </el-table-column>
            <el-table-column label="课程" width="160">
              <template slot-scope="scope">
                <span>{{ scope.row.courseName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="开课学期" width="160">
              <template slot-scope="scope">
                <span>{{ scope.row.term }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button @click="openCourseDetails(scope.row)" type="primary" size="small">详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>

    <!-- 新增课程表单弹窗 -->
    <el-dialog :visible.sync="addCourseDialogVisible" title="新增课程" @close="handleDialogClose" width="50%">
      <el-form :model="newCourse" ref="courseForm" label-width="120px">
        <el-form-item label="课程名称" prop="courseName" :rules="[{ required: true, message: '请输入课程名称', trigger: 'blur' }]">
          <el-input v-model="newCourse.courseName" placeholder="请输入课程名称"></el-input>
        </el-form-item>
        <el-form-item label="教学班数量" prop="classNum" :rules="[{ required: true, message: '请输入教学班数量', trigger: 'blur' }]">
          <el-input v-model="newCourse.classNum" placeholder="请输入教学班数量" type="number"></el-input>
        </el-form-item>
        <el-form-item label="授课教师" :rules="[{ required: true, message: '请选择教师', trigger: 'blur' }]">
          <el-select v-model="newCourse.teacherIds" placeholder="请选择授课教师" multiple>
            <el-option v-for="teacher in teachers" :key="teacher.teacherId" :label="teacher.name"
              :value="teacher.teacherId"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="开课学期" prop="term" :rules="[{ required: true, message: '请输入开课学期', trigger: 'blur' }]">
          <el-input v-model="newCourse.term" placeholder="请输入开课学期"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addCourse">提交</el-button>
          <el-button @click="addCourseDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 课程详情弹窗 -->
    <el-dialog :visible.sync="dialogVisible" title="课程详情" @close="handleClose" width="50%">
      <el-table :data="courseDetails" style="width: 100%">
        <el-table-column prop="teacherName" label="教师名称" width="180"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" width="180"></el-table-column>
        <el-table-column prop="classNum" label="教学班号" width="100"></el-table-column>
        <el-table-column prop="count" label="班级人数" width="100"></el-table-column>
        <el-table-column prop="term" label="学期" width="100"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import axios from '@/api/axios'

export default {
  data() {
    return {
      courses: [],          // 课程数据
      selectedCourse: {},   // 当前选中的课程
      dialogVisible: false, // 弹窗显示状态
      courseDetails: [],    // 当前选中课程的班级详细信息
      teachers: [],         // 教师数据
      form: {
        region: '',
      },
      addCourseDialogVisible: false, // 控制新增课程弹窗的显示
      newCourse: {
        courseName: '',
        classNum: '',
        term: '',
        teacherIds: [], // 新增课程表单中选择的教师IDs
      },  // 新增课程表单数据
    };
  },
  created() {
    this.fetchCourses();  // 组件创建时，获取课程数据
    this.fetchTeachers(); // 获取教师数据
  },
  methods: {
    // 获取课程数据
    async fetchCourses() {
      try {
        const response = await axios.get('/admin/courses'); // 请求课程数据
        if (response.data.code === 1) {
          this.courses = response.data.data.map(course => ({
            id: course.id,
            courseName: course.courseName,
            term: course.detail,
          }));
        } else {
          console.error('课程数据加载失败:', response.data.msg);
        }
      } catch (error) {
        console.error('获取课程数据失败:', error);
      }
    },

    // 获取教师数据
    async fetchTeachers() {
      try {
        const response = await axios.get('/admin/teacherlist'); // 请求教师数据
        if (response.data.code === 1) {
          this.teachers = response.data.data || [];
        } else {
          console.error('教师数据加载失败:', response.data.msg);
        }
      } catch (error) {
        console.error('获取教师数据失败:', error);
      }
    },

    // 打开课程详情弹窗
    openCourseDetails(course) {
      this.selectedCourse = course;
      this.courseDetails = []; // 清空当前课程的班级信息
      this.fetchCourseDetails(course.courseName); // 获取该课程的班级详细信息
      this.dialogVisible = true;
    },

    // 获取课程的班级详细信息
    async fetchCourseDetails(courseName) {
      try {
        const response = await axios.get(`/admin/showclass?courseName=${courseName}`);
        if (response.data.code === 1) {
          this.courseDetails = response.data.data || [];
        } else {
          console.error('课程详情数据加载失败:', response.data.msg);
        }
      } catch (error) {
        console.error('获取课程详情失败:', error);
      }
    },

    // 关闭课程详情弹窗时的处理
    handleClose() {
      this.courseDetails = [];  // 清空课程详情
    },

    // 显示新增课程表单弹窗
    showAddCourseDialog() {
      this.addCourseDialogVisible = true;
    },

    // 新增课程提交
    async addCourse() {
      if (this.newCourse.teacherIds.length !== parseInt(this.newCourse.classNum)) {
        this.$message.error('选择的授课教师数量必须与教学班数量相同');
        return;
      }

      try {
        const response = await axios.post('/admin/addcourse',
          `courseName=${this.newCourse.courseName}&classNum=${this.newCourse.classNum}&term=${this.newCourse.term}&teacherIds=${this.newCourse.teacherIds.join('&teacherIds=')}`,
          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            }
          }
        );
        // 发送新增课程请求
        if (response.data.code === 1) {
          this.fetchCourses();  // 重新获取课程数据
          this.addCourseDialogVisible = false;  // 关闭新增课程弹窗
          this.resetNewCourse();  // 重置表单数据
        } else {
          console.error('新增课程失败:', response.data.msg);
        }
      } catch (error) {
        console.error('新增课程失败:', error);
      }
    },

    // 重置新增课程表单
    resetNewCourse() {
      this.newCourse = {
        courseName: '',
        classNum: '',
        term: '',
        teacherIds: [],
      };
    },

    // 关闭新增课程表单弹窗
    handleDialogClose() {
      this.resetNewCourse();  // 关闭时重置表单数据
    },
  },
};
</script>
<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;

  height: 650px;
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
  font-size: 20px;
  font-weight: bold;
}

.course-content {
  margin-top: 10px;
}

.custom-table {
  width: 100%;
  padding: 10px;
}

.el-button {
  margin-right: 10px;
}
</style>