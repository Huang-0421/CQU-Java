package com.huang.experiment_4.controller;

import com.huang.experiment_4.pojo.Result;
import com.huang.experiment_4.service.StudentService;
import com.huang.experiment_4.service.TeacherService;
import com.huang.experiment_4.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@RestController
public class LoginController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/login")
    public Result login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //首先判断是不是Admin
        if(username.equals("admin") && password.equals("admin")){
            Map<String, Object> map = new HashMap<>();
            map.put("role", "admin");
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", "admin");
            String jwt = JwtUtils.generateJWT(claims);
            //返回JWT
            Cookie cookie = new Cookie("token", jwt);
            cookie.setMaxAge(60 * 60);//有效期
            response.addCookie(cookie);
            return Result.success(map);
        }
        //再到教师表里面找
        else {
            Result result = teacherService.checkTeacher(request, response);
            if(result.getCode() == 1){
                return result;
            }
            else{
                //在学生表里面查
                return studentService.checkStudent(request, response);
            }
        }
    }

    @GetMapping("/logout")
    public Result logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0); // 立即删除cookie
        cookie.setPath("/"); // 确保路径正确
        response.addCookie(cookie);
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return studentService.register(username, password);
    }

}
