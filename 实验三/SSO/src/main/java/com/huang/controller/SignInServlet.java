package com.huang.controller;
import com.huang.service.UserService;
import com.huang.service.UserServiceImpl;
import com.huang.utils.MyException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
@WebServlet("/sign")
public class SignInServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    public SignInServlet() throws SQLException, ClassNotFoundException {
    }

    /**
     * 注册页面
     * @param req 请求
     * @param resp 相应
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 转发请求到注册页面的 JSP
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/sign.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * 处理注销
     * @param req 请求
     * @param resp 相应
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userService.signIn(req, resp);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }
}
