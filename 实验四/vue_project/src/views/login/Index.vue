<template>
  <div class="clearfix" id="login_wrap">
    <div id="login">
      <h2 class="title">登录页</h2>
      <div class="login--account">
        <span>账号：</span>
        <input type="text" placeholder="输入用户名" name="account" v-model.trim="username" />
      </div>
      <div class="login--password">
        <span>密码：</span>
        <input type="password" placeholder="输入密码" name="password" v-model.trim="password" />
      </div>
      <!-- 添加注册超链接 -->
      <el-link class="register" type="primary" @click="goToRegisterPage">没有账号？立即注册</el-link>
      <p class="login--btn">
        <el-button :plain="true" id="loginBtn" type="primary" @click="login">登录</el-button>
      </p>
    </div>
  </div>
</template>

<script>
import axios from '@/api/axios'
export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    login() {
      // 发送 POST 请求到指定的 URL
      axios
        .post(
          "/login",
          new URLSearchParams({
            username: this.username,
            password: this.password,
          })
        )
        .then((response) => {
          if (response.data.code === 1) {
            if (response.data.data.role == "admin")
              this.$router.push("/admin");
            else if (response.data.data.role == "teacher")
              this.$router.push("/teacher");
            else if(response.data.data.role == "student")
            this.$router.push("/student");
            location.reload();
          } else {
            this.$message.error(response.data.msg);
          }
        });
    },
    goToRegisterPage() {
      // 导航到注册页面
      this.$router.push("/register");
    }
  },
};
</script>

<style scoped lang="less">
.title {
  text-align: center;
  font-size: 22px;
}

#login_wrap {
  position: relative;
  background-image: url('./10233.jpg');
  background-size: cover;
  height: 100%;

  >div {
    background: rgba(255, 255, 255, 0.85);
    border-radius: 15px;
    width: 420px;
    height: 270px;
    padding: 30px 40px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);

    >div {
      padding: 10px 0;
      border-bottom: 1px solid #ddd;

      &.login--account {
        margin: 25px 0 10px;
      }

      span {
        color: #666;
        display: inline-block;
        width: 84px;
        font-size: 16px;
      }

      input {
        background: none;
        font-size: 16px;
        border: none;
        height: 30px;
        width: 280px;
        padding-left: 12px;
        box-sizing: border-box;
        color: #666;

        &.error {
          border: 1px solid #f00;
        }

        &::-webkit-input-placeholder {
          color: #aaa;
        }
      }
    }
    .register {
      display: block;
      text-align: left;
      margin-top: 15px; /* 增加与输入框之间的间距 */
      width: 130px;
      font-size: small;
    }
    p {
      text-align: right;

      &.login--btn {
        button {
          width: 100%;
          height: 50px;
          font-size: 18px;
          margin-top: 15px;
          border-radius: 6px;
        }
      }

      a {
        color: #fff;
        display: inline-block;
        padding: 0 15px;
        font-size: 14px;
      }
    }
  }
}

.info {
  color: #999;
  margin-top: 8px;
  text-align: center !important;
}
</style>