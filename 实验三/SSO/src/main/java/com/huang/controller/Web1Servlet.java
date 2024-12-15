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
@WebServlet("/web1")
public class Web1Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //转发请求到Web1
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/web1.jsp");
        dispatcher.forward(request, response);
    }
}
