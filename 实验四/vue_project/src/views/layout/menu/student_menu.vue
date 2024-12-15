<template>
  <div>
    <el-menu :default-active="active" class="el-menu-vertical-demo" background-color="#112f50" text-color="#fff"
      active-text-color="#ffd04b" router :collapse="isCollapse">

      <el-menu-item index="/student">
        <i class="el-icon-menu"></i>
        <span slot="title">首页</span>
      </el-menu-item>

      <el-menu-item index="/student/courselist">
        <i class="el-icon-s-order"></i>
        <span slot="title">我的课程</span>
      </el-menu-item>

      <el-menu-item index="/student/course-selection">
        <i class="el-icon-s-platform"></i>
        <span slot="title">选课系统</span>
      </el-menu-item>
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