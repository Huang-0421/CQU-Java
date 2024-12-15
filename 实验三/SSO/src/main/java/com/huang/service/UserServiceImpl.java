package com.huang.service;

import com.huang.dao.DataBaseTool;
import com.huang.utils.JwtUtil;
import com.huang.utils.MyException;
import com.huang.utils.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class UserServiceImpl implements UserService{
    private DataBaseTool dataBaseTool = new DataBaseTool();

    public UserServiceImpl() throws SQLException, ClassNotFoundException {
    }

    /**
     * 校验密码并生成token
     *
     * @param request  请求
     * @param response 响应
     * @throws MyException 自定义异常
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws MyException, IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        // 获取表单提交的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //如果在黑名单
        if(UserManager.getDisabledUser().contains(username)){
            request.setAttribute("errorMessage", "账户已被禁止使用");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        String target = request.getParameter("target");
        String password_select = "";
        try {
            password_select = dataBaseTool.findPwd(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (password_select.equals(password)) {
                // 生成登录时间
                String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                UserManager.addUser(username, loginTime);
                // 生成 JWT 令牌
                String jwtToken = JwtUtil.createToken(username, password);
                // 将 JWT 令牌存储在 Cookie 中
                Cookie jwtCookie = new Cookie("JWT_TOKEN", jwtToken);
                jwtCookie.setHttpOnly(true); // 防止通过 JavaScript 访问 Cookie
                jwtCookie.setPath("/");      // 设置 Cookie 的路径，适用于整个应用
                jwtCookie.setMaxAge(600); // 设置 Cookie 的过期时间600s
                // 将用户名存储在 Cookie 中
                Cookie userCookie = new Cookie("username", username);
                userCookie.setPath("/");
                userCookie.setMaxAge(600); // 设置 Cookie 的过期时间600s
                // 将时间存储在 Cookie 中（用于判断是否过期）
                long currentTimeMillis = System.currentTimeMillis();// 创建一个以毫秒为单位的当前时间戳
                // 将时间戳转换为字符串，并创建 Cookie
                Cookie timeCookie = new Cookie("time", String.valueOf(currentTimeMillis));
                timeCookie.setPath("/");
                timeCookie.setMaxAge(600); // 设置 Cookie 的过期时间600s
                response.addCookie(jwtCookie);
                response.addCookie(userCookie);
                response.addCookie(timeCookie);
                if (!target.isEmpty()) {
                    if (target.equals("web1")) {
                        response.sendRedirect("web1");
                    }
                    if (target.equals("web2")) {
                        response.sendRedirect("web2");
                    }
                } else {
                    if (username.equals("admin")) {//如果是管理员就进入管理员页面
                        response.sendRedirect("admin");
                        return;
                    }
                    response.sendRedirect("main");
                }
            } else {
                // 登录失败，将错误信息传递到登录页面
                request.setAttribute("errorMessage", "登录失败！用户名或密码错误。");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } catch (ServletException | IOException e) {
            throw new MyException("登录操作错误");
        }
    }

    /**
     * 注销账户
     *
     * @param request  请求
     * @param response 响应
     * @throws MyException 自定义异常
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws MyException {
        try {
            // 清除用户的会话
            request.getSession().invalidate();
            // 清除所有 Cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //删除用户
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        UserManager.removeUser(cookie.getValue());
                    }
                }
                for (Cookie cookie : cookies) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // 设置路径为应用的根，以确保删除所有相关路径下的 Cookie
                    response.addCookie(cookie);
                }
            }
            // 重定向到登录页面或主页
            response.sendRedirect("login");
        } catch (IOException e) {
            throw new MyException("注销时发生错误");
        }
    }

    /**
     * 用户注册
     *
     * @param request  请求
     * @param response 响应
     * @throws MyException 自定义异常
     * @throws IOException IO异常
     */
    public void signIn(HttpServletRequest request, HttpServletResponse response) throws MyException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        // 获取表单提交的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            dataBaseTool.signIn(username, password);
        } catch (SQLException e) {
            throw new MyException("注册插入用户异常");
        }
        //重定向到登录页面
        response.sendRedirect("login");
    }

    public void disable(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        UserManager.disableUser(username);
    }
}
