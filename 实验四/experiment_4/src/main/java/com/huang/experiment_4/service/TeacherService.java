package com.huang.experiment_4.service;

import com.huang.experiment_4.pojo.Class;
import com.huang.experiment_4.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public interface TeacherService {
    Result checkTeacher(HttpServletRequest request, HttpServletResponse response);
    List<Class> showMyClass(Integer teacherId);
    Result updateStudentScore(HttpServletRequest request);
    Result exportToExcel(HttpServletRequest request);
    Result getInfo(int teacherId);
}
