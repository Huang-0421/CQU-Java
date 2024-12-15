package com.huang.service;

import com.huang.utils.MyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public interface UserService {
    void login(HttpServletRequest request, HttpServletResponse response) throws MyException, IOException, ServletException;
    void logout(HttpServletRequest request, HttpServletResponse response) throws MyException;
    void signIn(HttpServletRequest request, HttpServletResponse response) throws MyException, IOException;
    void disable(HttpServletRequest request, HttpServletResponse response);
}
