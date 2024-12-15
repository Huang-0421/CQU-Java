<template>
    <div class="clearfix" id="register_wrap">
        <div id="register">
            <h2 class="title">注册页</h2>
            <div class="register--account">
                <span>账号：</span>
                <input type="text" placeholder="输入用户名" name="account" v-model.trim="username" />
            </div>
            <div class="register--password">
                <span>密码：</span>
                <input type="password" placeholder="输入密码" name="password" v-model.trim="password"
                    @blur="validatePassword" />
            </div>
            <div class="register--confirm-password">
                <span>确认密码：</span>
                <input type="password" placeholder="确认密码" name="confirmPassword" v-model.trim="confirmPassword"
                    @blur="validatePassword" />
            </div>
            <!-- 注册超链接 -->
            <el-link class="login" type="primary" @click="goToLoginPage">已有账号？立即登录</el-link>
            <p class="register--btn">
                <el-button :plain="true" id="registerBtn" type="primary" @click="register">注册</el-button>
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
            confirmPassword: "",
            passwordError: false, // 用于控制密码一致性错误提示
        };
    },
    methods: {
        // 注册逻辑
        register() {
            if (this.password !== this.confirmPassword) {
                this.$message.error("两次输入的密码不一致")
                return;
            }

            // 发送 POST 请求到指定的 URL
            axios
                .post(
                    "/register",
                    new URLSearchParams({
                        username: this.username,
                        password: this.password,
                    })
                )
                .then((response) => {
                    if (response.data.code === 1) {
                        this.$message.success('注册成功');
                        this.$router.push("/login"); // 注册成功后跳转到登录页
                    } else {
                        this.$message.error("用户已存在");
                    }
                })
                .catch(error => {
                    this.$message.error('注册失败，请重试');
                });
        },
        // 校验密码一致性
        validatePassword() {
            if (this.password !== this.confirmPassword) {
                this.passwordError = true;
            } else {
                this.passwordError = false;
            }
        },
        // 跳转到登录页面
        goToLoginPage() {
            this.$router.push("/login");
        }
    },
};
</script>

<style scoped lang="less">
.title {
    text-align: center;
    font-size: 22px;
}

#register_wrap {
    position: relative;
    background-image: url('../login/10233.jpg');
    background-size: cover;
    height: 100%;

    >div {
        background: rgba(255, 255, 255, 0.85);
        border-radius: 15px;
        width: 420px;
        height: 315px;
        padding: 30px 40px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);

        >div {
            padding: 10px 0;
            border-bottom: 1px solid #ddd;

            &.register--account {
                margin: 25px 0 10px;
            }

            &.register--password {
                margin: 0 0 10px;
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

            .error-message {
                color: #f00;
                font-size: 12px;
                margin-top: 5px;
            }
        }

        .login {
            display: block;
            text-align: left;
            margin-top: 15px;
            /* 增加与输入框之间的间距 */
            width: 130px;
            font-size: small;
        }

        p {
            text-align: right;

            &.register--btn {
                button {
                    width: 100%;
                    height: 50px;
                    font-size: 18px;
                    margin-top: 20px;
                    border-radius: 6px;
                }
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