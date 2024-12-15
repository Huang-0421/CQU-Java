package com.huang.controller;

import com.huang.service.UserService;
import com.huang.service.UserServiceImpl;
import com.huang.utils.MyException;
import com.huang.utils.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 * 登录，Get请求访问页面，Post请求处理登录
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    public LoginServlet() throws SQLException, ClassNotFoundException {
    }

    /**
     * 处理登录页面的get请求
     * @param request 请求
     * @param response 相应
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取 URL 中的查询参数
        String target = request.getParameter("target");
        if (target != null) {
            //判断是否有Cookie
            boolean isValid = false;
            String username = "";
            if (request.getCookies() != null) {
                //Cookie是否过期
                for (Cookie cookie : request.getCookies()) {
                    if ("time".equals(cookie.getName())) {
                        isValid = UserManager.checkCookieValidity(cookie);
                    }
                    if ("username".equals(cookie.getName())) {
                        username = cookie.getValue();
                    }
                }
                if(!isValid){//Cookie过期了，带参数进login
                    // 将参数存储到请求属性中，以便在 JSP 页面中使用
                    request.setAttribute("target", target);
                    // 转发请求到登录页面的 JSP
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
                    dispatcher.forward(request, response);
                }
                else{//有Cookie
                    //如果被禁用，带参数禁login
                    if(UserManager.getDisabledUser().contains(username)){
                        // 将参数存储到请求属性中，以便在 JSP 页面中使用
                        request.setAttribute("target", target);
                        // 转发请求到登录页面的 JSP
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
                        dispatcher.forward(request, response);
                    }
                    else{
                        response.sendRedirect(target);
                    }
                }
            }
            else {//没有Cookie，带参数进login
                // 将参数存储到请求属性中，以便在 JSP 页面中使用
                request.setAttribute("target", target);
                // 转发请求到登录页面的 JSP
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
                dispatcher.forward(request, response);
            }
        }else{//直接进login
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        }

    }

    /**
     * 处理登录页面Post请求
     * @param request 请求
     * @param response 响应
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            //调用服务层的login方法
            userService.login(request, response);
        } catch (MyException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

