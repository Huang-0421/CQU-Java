package com.huang.experiment_4.service;


import com.huang.experiment_4.pojo.Result;
import com.huang.experiment_4.pojo.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @author Huang_ruijie
 * @version 1.0
 */
public interface StudentService {
    Result register(String username,String password);
    Result checkStudent(HttpServletRequest request, HttpServletResponse response);
    Result showOneGrade(String studentName);
    Result getInfo(String studentName);
    Result getCourses(String studentName);
    Result selectClass(String studentName, String[] classIds);
    int statistics(String courseName, int classNum);
}
