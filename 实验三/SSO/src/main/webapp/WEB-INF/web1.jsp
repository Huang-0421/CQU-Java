<!-- web1.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Web1 Page</title>
    <style>
        /* 通用样式 */
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 400px;
            text-align: center;
        }

        h1 {
            color: #4CAF50;
            margin-bottom: 20px;
        }

        a {
            text-decoration: none;
            color: #ffffff;
            background-color: #4CAF50;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        a:hover {
            background-color: #45a049;
        }

        .logout-button {
            margin-top: 20px;
            background-color: #ff4d4d;
            color: #ffffff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        .logout-button:hover {
            background-color: #e04343;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Web1</h1>
    <br>
    <!-- 跳转到 Web2 的链接 -->
    <a href="http://localhost:8080/web2" target="_blank">跳转 Web2</a>
    <!-- 注销按钮 -->
    <form action="logout" method="post" style="margin-top: 20px;">
        <button type="submit" class="logout-button">注销</button>
    </form>
</div>
</body>
</html>
