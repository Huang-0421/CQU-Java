package com.nine.service;

import com.nine.pojo.Class;
import com.nine.pojo.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public interface DataBaseService {
    //校验密码
    String findPwd(String name, int flag) throws SQLException;
    //学生-查找成绩
    String[] stu_findGrade(String name, String course) throws SQLException;
    //学生-查找课程
    String[] stu_findCourse(String name) throws SQLException;
    //学生-展示选课
    Class[] stu_showClassInfo() throws SQLException;
    //学生-选课操作
    boolean stu_chooseClass(Student student, Class[] classes, int choose1, int choose2, int choose3) throws SQLException;
    //学生-插入班级
    boolean insertIntoCourseTable(Connection connection, String studentName, String courseTable, int classNumber) throws SQLException;
    //教师-显示教学班
    String[] findTeacherCourse(String name) throws SQLException;
    //教师-统计成绩
    double[] culculateScore(String courseName, String classNum) throws SQLException;
    //教师-导出成绩
    void exportToExcel(String tableName, String classNum, String excelFilePath) throws SQLException, IOException;
    //教师-排序成绩
    void sortGrade(String courseName, String classNum, String flag) throws SQLException;
}
