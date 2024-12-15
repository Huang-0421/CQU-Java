<template>
  <el-card class="box-card" shadow="hover">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="teacherId" label="编号" width="180">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="180">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="180">
      </el-table-column>
      <el-table-column prop="college" label="学院" width="180">
      </el-table-column>
    </el-table>
  </el-card>

</template>

<script>
import axios from '@/api/axios'

export default {
  data() {
    return {
      tableData: [] // 用来存放后端返回的数据
    };
  },
  methods: {
    // 获取数据的方法
    fetchData() {
      axios.get('/admin/teacherlist')  // 替换成你的后端 API URL
        .then(response => {
          if (response.data.code === 1) {
            // 假设返回的数据格式符合你的要求
            this.tableData = response.data.data.map(item => {
              return {
                teacherId: item.teacherId,
                name: item.name,
                age: item.age,  // 假设后端返回的数据有这个字段
                college: item.college
              };
            });
          }
        })
        .catch(error => {
          console.error("There was an error fetching the data:", error);
        });
    }
  },
  created() {
    this.fetchData();  // 组件创建时自动请求数据
  }
};
</script>
<style  scoped>
.box-card {
  margin-top: 20px;
  padding: 20px;
  background-color: #fff;
}
</style>
