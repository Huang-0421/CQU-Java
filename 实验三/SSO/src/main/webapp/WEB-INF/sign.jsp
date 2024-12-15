<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>注册页面</title>
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
        .register-container {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 350px;
            text-align: center;
        }
        .register-container h1 {
            margin-bottom: 30px;
            color: #333;
            font-size: 24px;
        }
        .register-container input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .register-container button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .register-container button:hover {
            background-color: #45a049;
        }
        .error-message {
            color: red;
            font-size: 12px;
            display: none; /* 默认隐藏 */
        }
    </style>
    <script>
        // 函数用于校验两次输入的密码是否相同
        function validateForm(event) {
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;
            const errorMessage = document.getElementById("error-message");

            if (password !== confirmPassword) {
                errorMessage.style.display = "block";
                errorMessage.textContent = "两次输入的密码不一致！";
                event.preventDefault(); // 阻止表单提交
            } else {
                errorMessage.style.display = "none";
            }
        }
    </script>
</head>
<body>
<div class="register-container">
    <h1>注册</h1>
    <form action="sign" method="post" accept-charset="UTF-8" onsubmit="validateForm(event)">
        <input type="text" name="username" placeholder="用户名" aria-label="用户名" required>
        <input type="password" id="password" name="password" placeholder="密码" aria-label="密码" required>
        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="确认密码" aria-label="确认密码" required>
        <div class="error-message" id="error-message"></div>
        <button type="submit">注册</button>
    </form>
</div>
</body>
</html>
