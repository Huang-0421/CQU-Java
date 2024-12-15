package com.huang.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@WebServlet("/web2")
public class Web2Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //转发请求到Web1
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/web2.jsp");
        dispatcher.forward(request, response);
    }
}