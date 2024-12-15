package com.huang.experiment_4.controller;

import com.huang.experiment_4.mapper.TeacherMapper;
import com.huang.experiment_4.pojo.Result;
import com.huang.experiment_4.service.AdminService;
import com.huang.experiment_4.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
@Autowired
    private AdminService adminService;
    @GetMapping("/teacher")
    public Result showMyClass(HttpServletRequest request) {
        Integer teacherId = Integer.parseInt(request.getSession().getAttribute("teacherId").toString());
        return Result.success(teacherService.showMyClass(teacherId));
    }

    @PostMapping("/teacher/update-student-score")
    public Result updateStudentScore(HttpServletRequest request) {
        return teacherService.updateStudentScore(request);
    }

    @PostMapping("/teacher/export_to_excel")
    public Result exportToExcel(HttpServletRequest request) {
        return teacherService.exportToExcel(request);
    }
    @PostMapping("/teacher/student-scores")
    public Result showStudentScores(HttpServletRequest req) {
        String classNum = req.getParameter("classNum");
        String searchQuery = req.getParameter("searchQuery");
        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        int sortType = Integer.parseInt(req.getParameter("sort"));
        Map<String, Object> list =  adminService.showStudentScores(classNum, searchQuery, currentPage, pageSize,sortType);
        return Result.success(list);
    }

    @GetMapping("/teacher/getInfo")
    public Result getInfo(HttpServletRequest request) {
        int teacherId = Integer.parseInt(request.getSession().getAttribute("teacherId").toString());
        //根据teacherId找名字
        return teacherService.getInfo(teacherId);
    }
}
