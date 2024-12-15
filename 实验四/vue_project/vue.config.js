const { defineConfig } = require('@vue/cli-service')
module.exports = {
  devServer: {
    port: 8082,
    proxy:'http://localhost:8080'// 代理到后端端口号
  }
}
