package com.huang.experiment_4.service;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.experiment_4.mapper.ClassMapper;
import ch.qos.logback.core.util.StringUtil;
import com.huang.experiment_4.mapper.GradeMapper;
import com.huang.experiment_4.mapper.StudentMapper;
import com.huang.experiment_4.pojo.*;
import com.huang.experiment_4.pojo.Class;
import com.huang.experiment_4.utils.JwtUtils;
import com.huang.experiment_4.utils.NameMapperUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private NameMapperUtil nameMapperUtil;
    @Autowired
    private ClassMapper classMapper;

    @Override
    public Result register(String username, String password) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("name", username);
        List<Student> list = studentMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            return Result.error();
        }
        //新增一个学生
        Student student = new Student();
        student.setName(username);
        String password_MD5 = SecureUtil.md5(password);
        student.setPassword(password_MD5);
        studentMapper.insert(student);
        return Result.success();
    }

    @Override
    public Result checkStudent(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = SecureUtil.md5(request.getParameter("password"));

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        queryWrapper.eq("password", password);
        Student student = studentMapper.selectOne(queryWrapper);
        if (student == null) {
            return Result.error("用户名或密码错误");
        }
        request.getSession().setAttribute("studentName", student.getName());
        Map<String, String> map = new HashMap<>();
        map.put("role", "student");
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "student");
        String jwt = JwtUtils.generateJWT(claims);
        //返回JWT
        Cookie cookie = new Cookie("token", jwt);
        cookie.setMaxAge(60 * 60);//有效期
        response.addCookie(cookie);
        return Result.success(map);
    }

    @Override
    public Result showOneGrade(String studentName) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", studentName);
        Student student = studentMapper.selectOne(queryWrapper);
        if (student != null) {
            String course1 = student.getCourse1();
            String course2 = student.getCourse2();
            String course3 = student.getCourse3();
            //在分别到三个课程的表中去查找
            Map<String, Grade> map = new HashMap<>();
            Grade grade1 = gradeMapper.showGrade(course1, studentName);
            Grade grade2 = gradeMapper.showGrade(course2, studentName);
            Grade grade3 = gradeMapper.showGrade(course3, studentName);
            grade1.setCourseName(course1);
            grade2.setCourseName(course2);
            grade3.setCourseName(course3);
            map.put(course1, grade1);
            map.put(course2, grade2);
            map.put(course3, grade3);
            return Result.success(map);
        }
        return Result.error();
    }

    @Override
    public Result getInfo(String studentName) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", studentName);
        Student student = studentMapper.selectOne(queryWrapper);
        return Result.success(student);
    }

    @Override
    public Result getCourses(String studentName) {
        // 查询学生信息
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", studentName);
        Student student = studentMapper.selectOne(queryWrapper);
        // 获取学生的课程
        String course1 = student.getCourse1();
        String course2 = student.getCourse2();
        String course3 = student.getCourse3();
        if (course1 == null && course2 == null && course3 == null) {
            return Result.error();
        }
        // 存储最终的课程信息
        Map<String, Object> result = new HashMap<>();
        // 获取每门课程的详细信息
        Map<String, Object> detail1;
        Map<String, Object> detail2;
        Map<String, Object> detail3;
        if(course1 != null) {
            detail1 = getCourseDetails(course1, studentName);
            result.put("course1", detail1);
        }
        if(course2 != null) {
            detail2 = getCourseDetails(course2, studentName);
            result.put("course2", detail2);
        }
        if(course3 != null) {
            detail3 = getCourseDetails(course3, studentName);
            result.put("course3", detail3);
        }
        return Result.success(result);
    }


    // 提取处理单个课程的逻辑
    private Map<String, Object> getCourseDetails(String course, String studentName) {
        Map<String, Object> courseMap = new HashMap<>();
        // 获取成绩信息
        Grade grade = gradeMapper.showGrade(course, studentName);
        if (grade == null) {
            return null;
        }
        grade.setCourseName(nameMapperUtil.getEnToCn().get(course));  // EngToCn
        courseMap.put("grade", grade);

        // 获取授课教师和学期信息
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_name", course);
        queryWrapper.eq("class", grade.getClassNum());
        Class clazz = classMapper.selectOne(queryWrapper);
        if (clazz != null) {
            String teacherName = clazz.getTeacherName();
            String term = clazz.getTerm();

            // 将教师名和学期加入到课程信息中
            courseMap.put("teacher_name", teacherName);
            courseMap.put("term", term);
        }
        return courseMap;
    }

    @Override
    public Result selectClass(String studentName, String[] classIds) {
        //先找到当前登录的学生
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", studentName);
        Student student = studentMapper.selectOne(queryWrapper);

        //找到当前学生以前选的所有课程
        Set<String> selectedCourse = new HashSet<>();
        if(!StringUtil.isNullOrEmpty(student.getCourse1()))
            selectedCourse.add(student.getCourse1());
        if(!StringUtil.isNullOrEmpty(student.getCourse2()))
            selectedCourse.add(student.getCourse2());
        if(!StringUtil.isNullOrEmpty(student.getCourse3()))
            selectedCourse.add(student.getCourse3());

        //选课数量是否超过3门
        if(classIds.length + selectedCourse.size() > 3){
            return Result.error("您已经选择" + selectedCourse.size() + "门课程，还能选" + (3 - selectedCourse.size()) + "门课程");
        }

        //首先要的到这几个id所代表的Eng表名，从class_info中找
        for (String classId : classIds) {
            QueryWrapper<Class> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", Integer.parseInt(classId));
            //首先找到这几个id所代表的课程
            Class clazz = classMapper.selectOne(queryWrapper1);
            String courseName = clazz.getCourseName();
            //判断这个课程是否已经选过
            if(selectedCourse.contains(courseName)){
                return Result.error(nameMapperUtil.getEnToCn().get(courseName) + " 无法重复选择");
            }
            if (StringUtil.isNullOrEmpty(student.getCourse1())) {
                student.setCourse1(courseName);
            } else {
                if (StringUtil.isNullOrEmpty(student.getCourse2())) {
                    student.setCourse2(courseName);
                } else {
                    student.setCourse3(courseName);
                }
            }

            studentMapper.updateById(student);//更新学生表
            //在该课程的表中插入学生
            gradeMapper.addScore(courseName, studentName, clazz.getClassNum());
            //再更新人数
            clazz.setCount(clazz.getCount() + 1);
            classMapper.updateById(clazz);
        }
        return Result.success();
    }

    @Override
    public int statistics(String courseName, int classNum) {
        //EngToCn
        courseName = nameMapperUtil.getCnToEn().get(courseName);
        //找到id
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_name", courseName);
        queryWrapper.eq("class", classNum);
        Class clazz = classMapper.selectOne(queryWrapper);
        return clazz.getId();
    }
}
