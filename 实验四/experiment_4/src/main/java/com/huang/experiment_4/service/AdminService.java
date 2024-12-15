package com.huang.experiment_4.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.experiment_4.pojo.Course;
import com.huang.experiment_4.pojo.Grade;
import com.huang.experiment_4.pojo.Result;
import com.huang.experiment_4.pojo.Student;

import java.util.List;
import java.util.Map;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public interface AdminService {
    Result showCourses();
    Result showTeachers();
    Page<Student> showStudents(int currentPage, int pageSize,String searchQuery);
    Result showClasses(String courseName);
    void addCourse(String courseName, String detail);
    void addCourseTable(String courseName);
    void addClass(String courseName, List<Integer> teacherId, int classNum, String term);
    String nameMap(String courseName);
    Result showAllClasses();
    Map<String, Object> showStudentScores(String classNum, String searchQuery, int currentPage, int pageSize,int sortType);
    Result showStatistics(String classNum);
}
