package com.huang.experiment_4.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.experiment_4.pojo.Result;
import com.huang.experiment_4.pojo.Student;
import com.huang.experiment_4.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 显示所有课程
     *
     * @return 结果
     */
    @GetMapping("/admin/courses")
    public Result getAllCourses() {
        return adminService.showCourses();
    }

    /**
     * 显示所有教师
     *
     * @return 结果
     */
    @GetMapping("/admin/teacherlist")
    public Result getAllTeachers() {
        return adminService.showTeachers();
    }

    /**
     * 显示所有学生并实现模糊查询与分页
     *
     * @param currentPage 当前页数
     * @param pageSize    每页个数
     * @param searchQuery 查询条件
     * @return 结果
     */
    @GetMapping("/admin/studentlist")
    public Result showStudents(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String searchQuery
    ) {
        // 调用 service 层的方法进行分页查询
        Page<Student> studentPage = adminService.showStudents(currentPage, pageSize, searchQuery);
        return Result.success(studentPage);
    }

    @PostMapping("/admin/student-scores")
    public Result showStudentScores(HttpServletRequest req) {
        String classNum = req.getParameter("classNum");
        String searchQuery = req.getParameter("searchQuery");
        int currentPage = Integer.parseInt(req.getParameter("currentPage"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        int sortType = Integer.parseInt(req.getParameter("sort"));
        Map<String, Object> list = adminService.showStudentScores(classNum, searchQuery, currentPage, pageSize, sortType);
        return Result.success(list);
    }

    /**
     * 显示选中的课程所包含的所有教学班
     *
     * @param courseName 课程名
     * @return 结果
     */
    @GetMapping("/admin/showclass")
    public Result showClass(@RequestParam String courseName) {
        return adminService.showClasses(courseName);
    }

    /**
     * 添加课程
     *
     * @param req 请求
     * @return 响应
     */
    @PostMapping("/admin/addcourse")
    public Result addCourse(HttpServletRequest req) {
        String courseName = req.getParameter("courseName");
        int classNum = Integer.parseInt(req.getParameter("classNum"));
        String term = req.getParameter("term");
        // 获取多个 teacherIds 参数
        String[] teacherIdsStr = req.getParameterValues("teacherIds");
        // 将 teacherIds 字符串数组转换为 Integer 列表
        List<Integer> teacherIds = Arrays.stream(teacherIdsStr)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        // 继续处理逻辑
        String EngName = adminService.nameMap(courseName);
        adminService.addCourse(EngName, term);
        adminService.addCourseTable(EngName);
        adminService.addClass(EngName, teacherIds, classNum, term);

        return Result.success();
    }

    /**
     * 显示所有教学班
     * @return 响应
     */
    @GetMapping("/admin/classlist")
    public Result showAllClass() {
        return adminService.showAllClasses();
    }

    @PostMapping("/admin/statistics")
    public Result showStatistics(HttpServletRequest req) {
        String classNum = req.getParameter("classNum");
        return adminService.showStatistics(classNum);
    }
}
