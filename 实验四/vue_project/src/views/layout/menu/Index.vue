<template>
  <div>
    <el-menu :default-active="active" class="el-menu-vertical-demo"
             background-color="#112f50" text-color="#fff" active-text-color="#ff0000"
             router :collapse="isCollapse">
      
      <el-menu-item index="/admin">
        <i class="el-icon-menu"></i>
        <span slot="title">首页</span>
      </el-menu-item>

      <el-submenu index="/admin/teacherlist">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span>教师管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/admin/teacherlist">
            <i class="el-icon-s-custom"></i>
            <span slot="title">教师列表</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="/admin/studentlist">
        <template slot="title">
          <i class="el-icon-user"></i>
          <span>学生管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/admin/studentlist">
            <i class="el-icon-user"></i>
            <span slot="title">学生列表</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="/admin/courselist">
        <template slot="title">
          <i class="el-icon-document"></i>
          <span>课程管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/admin/courselist">
            <i class="el-icon-document"></i>
            <span slot="title">课程列表</span>
          </el-menu-item>
          <el-menu-item index="/admin/classlist">
            <i class="el-icon-document"></i>
            <span slot="title">班级列表</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      
    </el-menu>
  </div>
</template>
<script>
export default {
  data() {
    return {
      active: this.$route.path // 默认根据当前路由的路径设置激活菜单项
    };
  },
  props: ['isCollapse'],
  created() {
    this.setActiveMenu(this.$route); // 页面初次加载时，设置激活菜单项
  },
  watch: {
    $route(to, from) {
      this.setActiveMenu(to); // 路由变化时，更新激活菜单项
    }
  },
  methods: {
    // 设置激活菜单项的方法
    setActiveMenu(route) {
      if (route.meta && route.meta.activeMenu) {
        this.active = route.meta.activeMenu; // 使用 meta 中定义的 activeMenu（如果有）
      } else {
        this.active = route.path; // 否则使用当前路由的路径作为激活项
      }
    }
  }
}
</script>
<style lang="less" scoped>
.el-menu {
  border-right: 0;
  .is-active {
    background: #8ba6dd !important;
    color: #fff !important;
  }
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
