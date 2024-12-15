<template>
  <div>
    <!-- 顶部 -->
    <div class="content-header">
      <div class="open">
        <span v-if="!isCollapse" class="iconfont icon-right-indent" @click="changeMenu"></span>
        <span v-else class="iconfont icon-left-indent" @click="changeMenu"></span>
      </div>
      <!-- 右侧内容--时间 登录信息等 -->
      <div class="right">
        <span>{{ nowTime }}</span> |
        <span>欢迎 {{ username }} 同学</span> |
        <span class="el-icon-switch-button icon" @click="loginOut"></span>
      </div>
    </div>

    <!-- 内容 -->
    <div class="wrapper" style="height: 650px;">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
// 导入 dayjs
import dayjs from 'dayjs'
import axios from '@/api/axios'
export default {
  props: ['isCollapse'],
  data() {
    return {
      time: '',        // 当前时间
      username: '',    // 学生用户名
      nowTime: '',     // 显示的当前时间
    };
  },
  methods: {
    // 更改菜单显示/隐藏状态
    changeMenu() {
      this.$emit('changeShow')
    },
    // 退出登录
    loginOut() {
      axios.get('/logout')
        .then(response => {
          if (response.data.code === 1) {
            this.$router.push("/login");
          } else {
            this.$message.error('退出失败');
          }
        })
        .catch(error => {
          console.error('请求失败:', error);
          this.$message.error('请求失败');
        });
    },
    // 获取当前登录学生的姓名
    getUserInfo() {
      axios.get('/student/getInfo')
        .then(response => {
          if (response.data.code === 1) {
            this.username = response.data.data.name;
          } else {
            this.$message.error('获取用户信息失败');
          }
        })
        .catch(error => {
          console.error('请求失败:', error);
          this.$message.error('请求失败');
        });
    }
  },
  created() {
    // 获取当前时间并格式化
    this.nowTime = dayjs(new Date()).format('YYYY-MM-DD HH:mm:ss');
    // 每秒更新时间
    setInterval(() => {
      this.nowTime = dayjs(new Date()).format('YYYY-MM-DD HH:mm:ss');
    }, 1000);

    // 获取登录用户的名字
    this.getUserInfo();
  }
}
</script>

<style>
.wrapper {
  padding: 10px;
  background: #eee;
}

.content-header {
  height: 50px;
  background: #112f50;
  color: #fff;
  line-height: 50px;
  display: flex;

  .open {
    .iconfont {
      font-size: 22px;
      cursor: pointer !important;
    }

  }

  .right {
    flex: 1;
    text-align: right;
    width: 380px;
    font-size: 12px;
    padding-right: 20px;

    span {
      padding: 0 4px;
    }

    .icon {
      font-size: 16px;
    }
  }

}
</style>
