<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.huang.utils.UserManager" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="static com.huang.utils.UserManager.checkCookieValidity" %>
<!DOCTYPE html>
<html>
<head>
    <title>main</title>
    <meta charset="UTF-8">
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
            line-height: 1.6; /* 增加行高，改善可读性 */
        }

        .container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            padding: 30px;
            max-width: 800px;
            width: 100%;
            position: relative;
            overflow: hidden; /* 隐藏可能超出的内容 */
        }

        .user-info strong {
            font-size: 16px;
            color: #4CAF50;
        }

        .user-info a {
            text-decoration: none;
            color: #4CAF50;
            font-weight: bold;
            margin-left: 10px;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: #fff;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .links {
            text-align: center;
            margin-top: 30px; /* 增大间距，分隔内容 */
        }

        .links a {
            text-decoration: none;
            color: #4CAF50;
            font-weight: bold;
            margin: 0 15px; /* 增大链接之间的间距 */
            transition: color 0.3s;
        }

        .links a:hover {
            color: #333;
        }
        .user-info {
            position: absolute; /* 使用绝对定位 */
            top: 20px; /* 距离页面顶部的距离 */
            right: 20px; /* 距离页面右边的距离 */
            display: flex; /* 使用 flex 布局 */
            align-items: baseline; /* 使用 baseline 对齐，确保文本和按钮文字齐平 */
            gap: 10px; /* 设置按钮和用户名之间的间距 */
        }

        .logout-button {
            padding: 5px 10px; /* 调整内边距，使按钮大小与用户名相似 */
            background-color: #FF4C4C;
            border: none;
            color: white;
            font-size: 14px; /* 与用户名字体大小相同 */
            font-weight: bold;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
        }

        .logout-button:hover {
            background-color: #D93636;
        }

        .logout-button:active {
            background-color: #C82F2F;
        }



    </style>
</head>
<body>
<div class="container">
    <div class="user-info">
        <%
            // 从请求中获取所有 Cookie
            Cookie[] cookies = request.getCookies();
            String username = null;
            boolean isValid = false;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("time".equals(cookie.getName())) {
                        isValid = checkCookieValidity(cookie);
                    }
                }
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        username = cookie.getValue();
                    }
                }
            }
        %>

        <% if (isValid) { %>
        <strong style="font-size: 16px; color: #4CAF50;"><%= username %>
        </strong>
        <!-- 注销按钮 -->
        <!-- 注销按钮 -->
        <form action="logout" method="post" style="margin-top: 20px;">
            <button type="submit" class="logout-button">注销</button>
        </form>
        <% }  %>
    </div>

    <h1>主页 - 已登录用户</h1>
    <table>
        <tr>
            <th>用户名</th>
            <th>登录时间</th>
        </tr>
        <%
            // 从 UserSessionManager 获取已登录用户的信息
            Map
                    <
                            String
                            ,
                            String
                            >
                    loggedInUsers
                    =
                    UserManager
                            .
                            getLoggedInUsers
                                    (
                                    );
            for
            (
                    Map
                            .
                            Entry
                            <
                                    String
                                    ,
                                    String
                                    >
                            entry
                    :
                    loggedInUsers
                            .
                            entrySet
                                    (
                                    )
            ) {
        %>
        <tr>
            <td><%= entry
                    .
                    getKey
                            (
                            ) %>
            </td>
            <td><%= entry
                    .
                    getValue
                            (
                            ) %>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <div class="links">
        <a href="http://localhost:8080/web1">跳转到 web1</a>
        <a href="http://localhost:8080/web2">跳转到 web2</a>
    </div>
</div>
</body>
</html>
