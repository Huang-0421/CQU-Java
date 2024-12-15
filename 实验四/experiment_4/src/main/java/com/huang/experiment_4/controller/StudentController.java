package com.huang.experiment_4.controller;

import com.huang.experiment_4.pojo.Result;
import com.huang.experiment_4.service.AdminService;
import com.huang.experiment_4.service.AdminServiceImpl;
import com.huang.experiment_4.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;

    /**
     * 显示学生成绩
     * @param studentName 学生姓名
     * @return 结果
     */
    @GetMapping("/admin/showgrade")
    public Result showgrade(@RequestParam String studentName) {
        return studentService.showOneGrade(studentName);
    }

    /**
     * 获取当前登录学生的信息
     * @param request
     * @return
     */
    @GetMapping("/student/getInfo")
    public Result getInfo(HttpServletRequest request) {
        String studentName= request.getSession().getAttribute("studentName").toString();
        //根据teacherId找名字
        return studentService.getInfo(studentName);
    }

    /**
     * 获取学生课程
     * @param request 请求
     * @return Result
     */
    @GetMapping("/student/courses")
    public Result getCourses(HttpServletRequest request) {
        String studentName= request.getSession().getAttribute("studentName").toString();
        return studentService.getCourses(studentName);
    }

    /**
     * 获取可选的所有班级信息
     * @return 响应
     */
    @GetMapping("/student/showclasses")
    public Result showClasses() {
        return adminService.showAllClasses();
    }

    /**
     * 学生选课
     * @param request 请求，包含学生信息以及选择的班级id
     * @return
     */
    @PostMapping("/student/selectcourse")
    public Result selectCourse(HttpServletRequest request) {
        String studentName= request.getSession().getAttribute("studentName").toString();
        String[] classIds = request.getParameterValues("classIds[]");
        return studentService.selectClass(studentName, classIds);
    }

    @PostMapping("/student/statistics")
    public Result statistics(HttpServletRequest request) {
        String courseName= request.getParameter("courseName");
        int classNum= Integer.parseInt(request.getParameter("classNum"));
        int id = studentService.statistics(courseName, classNum);
        return adminService.showStatistics(String.valueOf(id));
    }
}
