<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录页面</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 350px;
            position: relative; /* 相对定位以便定位小字 */
            text-align: center;
        }
        .login-container h1 {
            margin-bottom: 30px;
            color: #333;
            font-size: 24px;
        }
        .login-container input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .login-container button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-bottom: 10px; /* 增加按钮之间的间距 */
        }
        .login-container button:hover {
            background-color: #45a049;
        }
        .register-link {
            position: absolute;
            bottom: 20px; /* 距离底部的距离 */
            left: 40px; /* 距离左边的距离 */
            font-size: 12px; /* 小字体 */
            color: #888;
            text-decoration: none;
        }
        .register-link:hover {
            color: #333;
        }
    </style>
    <script>
        // 函数用于显示弹框提示
        function showError(message) {
            if (message) {
                alert(message);
            }
        }
    </script>
</head>
<body>
<div class="login-container">
    <h1>登录</h1>
    <form action="login" method="post">
        <input type="text" name="username" placeholder="用户名" aria-label="用户名" required>
        <input type="password" name="password" placeholder="密码" aria-label="密码" required>
        <!-- 隐藏的 input，用于传递 target 参数 -->
        <input type="hidden" name="target" id="targetInput">
        <button type="submit">登录</button>
    </form>
    <!-- 注册链接 -->
    <a href="http://localhost:8080/sign" class="register-link">点击注册</a>
</div>
<script>
    // 获取 URL 中的 target 参数并设置为隐藏字段的值
    const urlParams = new URLSearchParams(window.location.search);
    const target = urlParams.get('target');
    if (target) {
        document.getElementById('targetInput').value = target;
    }
</script>
<%-- 检查是否有错误信息，并通过 JavaScript 显示弹框提示 --%>
<c:if test="${not empty errorMessage}">
    <script>
        showError("${errorMessage}");
    </script>
</c:if>

</body>
</html>
