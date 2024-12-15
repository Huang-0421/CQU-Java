package com.huang.experiment_4.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huang.experiment_4.pojo.Course;
import com.huang.experiment_4.pojo.Grade;
import com.huang.experiment_4.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
    Grade showGrade(@Param("course") String course, @Param("name") String name);
    void createCourseTable(@Param("courseName") String courseName);
    List<Grade> showScore(@Param("course") String course, @Param("name") String name, @Param("classnum") String classNum);
    List<Grade> showshowStatistics(@Param("course") String course,  @Param("classnum") String classNum);
    void updateScore(@Param("grade") Grade grade);
    List<Grade> exportScore(@Param("course") String course, @Param("class") int classNum);
    void addScore(@Param("course") String course, @Param("name") String studentName, @Param("class_num") int classNum);
}

