package com.huang.controller;

import com.huang.service.UserService;
import com.huang.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@WebServlet("/disable")
public class DisableServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    public DisableServlet() throws SQLException, ClassNotFoundException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.disable(request, response);
        response.sendRedirect("admin");
    }
}
