<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.huang.utils.UserManager" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="static com.huang.utils.UserManager.checkCookieValidity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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
            line-height: 1.6;
        }

        .container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            padding: 30px;
            max-width: 800px;
            width: 100%;
            position: relative;
            overflow: hidden;
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
            margin-top: 30px;
        }

        .links a {
            text-decoration: none;
            color: #4CAF50;
            font-weight: bold;
            margin: 0 15px;
            transition: color 0.3s;
        }

        .links a:hover {
            color: #333;
        }

        /* Modal 样式 */
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            width: 400px;
            text-align: left;
        }

        #historyContent {
            margin-top: 10px; /* 添加顶部间距 */
        }

        .history-record {
            display: flex;
            justify-content: space-between; /* 使用户名和时间分布在两端 */
            font-size: 14px; /* 设置合适的字体大小 */
            padding: 5px 0; /* 为每条记录增加上下内边距 */
            border-bottom: 1px solid #eee; /* 添加下边框分隔每条记录 */
        }

        .history-record:last-child {
            border-bottom: none; /* 去除最后一条记录的下边框 */
        }

        .close-btn {
            float: right;
            cursor: pointer;
            font-size: 18px;
            color: #888;
        }

        .close-btn:hover {
            color: #333;
        }

        .user-info {
            position: absolute;
            top: 20px;
            right: 20px;
            display: flex;
            align-items: baseline;
            gap: 10px;
        }

        .logout-button {
            padding: 5px 10px;
            background-color: #FF4C4C;
            border: none;
            color: white;
            font-size: 14px;
            font-weight: bold;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
        }

        .logout-button:hover {
            background-color: #D93636;
            transform: scale(1.05);
        }

        .logout-button:active {
            background-color: #C82F2F;
        }
        .disable {
            padding: 5px 15px; /* 调整按钮大小 */
            background-color: #ffe082;  /* 添加渐变背景 */
            border: none; /* 移除边框 */
            color: #5d4037; /* 按钮文字颜色 */
            font-size: 14px; /* 字体大小 */
            font-weight: bold; /* 加粗文字 */
            border-radius: 5px; /* 更圆的按钮形状 */
            cursor: pointer; /* 鼠标悬停时变为指针 */
            transition: background-color 0.3s, transform 0.2s, box-shadow 0.2s; /* 添加阴影效果的过渡动画 */
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 默认阴影 */
            display: flex; /* 让按钮内容居中对齐 */
            align-items: center; /* 垂直居中 */
            justify-content: center; /* 水平居中 */
            gap: 8px; /* 图标和文字的间距 */
            margin-left: -15px; /* 向左微调 10px */
        }

        .disable:hover {
            background: linear-gradient(90deg, #ffe082, #ffca28); /* 悬停时的渐变颜色 */
            box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15); /* 增加悬停时的阴影 */
            transform: scale(1.05); /* 悬停时轻微放大 */
        }

        .disable:active {
            background: linear-gradient(90deg, #ffca28, #ffa000);  /* 点击时的渐变颜色 */
            transform: scale(0.95); /* 点击时轻微缩小 */
            box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2); /* 点击时的阴影 */
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
        <strong style="font-size: 16px; color: #4CAF50;"><%= username %></strong>
        <form action="logout" method="post" style="margin-top: 20px;">
            <button type="submit" class="logout-button">注销</button>
        </form>
        <% } %>
    </div>

    <h1>主页 - 已登录用户</h1>
    <table>
        <tr>
            <th>用户名</th>
            <th>登录时间</th>
            <th>操作</th>
        </tr>
        <%
            // 从 UserSessionManager 获取已登录用户的信息
            Map<String, String> loggedInUsers = UserManager.getLoggedInUsers();
            for (Map.Entry<String, String> entry : loggedInUsers.entrySet()) {
                String username1 = entry.getKey(); // 获取用户名
        %>
        <tr>
            <td><%= username1 %></td>
            <td><%= entry.getValue() %></td>
            <td>
                <%
                    // 如果用户名不是 "admin"，则显示禁用按钮
                    if (!"admin".equals(username1)) {
                %>
                <form action="disable" method="post">
                    <input type="hidden" name="username" value="<%= username1 %>" />
                    <input type="submit" class="disable" value="禁用" />
                </form>
                <%
                    }
                %>
            </td>
        </tr>
        <%
            }
        %>

    </table>

    <div class="links">
        <a href="http://localhost:8080/web1">跳转到 web1</a>
        <a href="http://localhost:8080/web2">跳转到 web2</a>
        <a href="#" onclick="showHistoryModal()">查看历史登录</a>
    </div>
</div>

<!-- Modal 弹出框 -->
<div class="modal" id="historyModal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal()">&times;</span>
        <h3>历史登录记录</h3>
        <div id="historyContent">
            <%
                // 从 UserSessionManager 获取历史记录
                Map<String, String> history = UserManager.getHistory();
                if (history != null && !history.isEmpty()) {
                    // 创建一个 List 进行排序
                    List<Map.Entry<String, String>> historyList = new ArrayList<>(history.entrySet());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // 按时间降序排序
                    historyList.sort((entry1, entry2) -> {
                        try {
                            Date date1 = dateFormat.parse(entry1.getValue());
                            Date date2 = dateFormat.parse(entry2.getValue());
                            return date2.compareTo(date1); // 降序
                        } catch (Exception e) {
                            return 0;
                        }
                    });

                    // 输出排序后的记录
                    for (Map.Entry<String, String> entry : historyList) {
            %><div class="history-record"><span><%= entry.getKey() %></span><span><%= entry.getValue() %></span></div><%
            }
        } else {
        %><p>没有历史登录记录</p><%
            }
        %>
        </div>
    </div>
</div>

<script>
    function showHistoryModal() {
        const modal = document.getElementById('historyModal');
        if (modal) {
            modal.style.display = 'flex';
        }
    }

    function closeModal() {
        const modal = document.getElementById('historyModal');
        if (modal) {
            modal.style.display = 'none';
        }
    }
</script>
</body>
</html>
