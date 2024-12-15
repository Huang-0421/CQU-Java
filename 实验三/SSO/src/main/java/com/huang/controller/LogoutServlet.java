package com.huang.controller;

import com.huang.service.UserService;
import com.huang.service.UserServiceImpl;
import com.huang.utils.MyException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    public LogoutServlet() throws SQLException, ClassNotFoundException {
    }

    /**
     * 处理注销的post请求
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            //调用服务层的注销方法
            userService.logout(request, response);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }
}
