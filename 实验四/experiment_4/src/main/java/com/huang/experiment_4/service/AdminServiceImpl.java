package com.huang.experiment_4.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huang.experiment_4.mapper.*;
import com.huang.experiment_4.pojo.*;
import com.huang.experiment_4.pojo.Class;
import com.huang.experiment_4.utils.NameMapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Huang_ruijie
 * @version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private NameMapperUtil nameMapperUtil;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private NameMapper nameMapper;

    @Override
    public Result showCourses() {
        // 使用 QueryWrapper 构建查询
        QueryWrapper<Course> qw = new QueryWrapper<>();
        // 查询 course 表中的所有记录
        List<Course> courseList = courseMapper.selectList(qw);
        //将英文转换为中文
        Map<String, String> map = nameMapperUtil.getEnToCn();
        for (Course course : courseList) {
            course.setCourseName(map.get(course.getCourseName()));
        }
        // 返回结果
        return Result.success(courseList);
    }

    @Override
    public Result showTeachers() {
        QueryWrapper<Teacher> qw = new QueryWrapper<>();
        teacherMapper.selectList(qw);
        List<Teacher> teacherList = teacherMapper.selectList(qw);
        return Result.success(teacherList);
    }

    @Override
    public Page<Student> showStudents(int currentPage, int pageSize, String searchQuery) {
        // 创建分页对象
        Page<Student> page = new Page<>(currentPage, pageSize);
        // 创建查询条件构造器
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        // 如果 searchQuery 不为空，使用模糊查询
        if (searchQuery != null && !searchQuery.isEmpty()) {
            queryWrapper.like("name", searchQuery) ;// 根据学生姓名进行模糊查询
        }
        // 使用 MyBatis Plus 提供的分页查询方法
        return studentMapper.selectPage(page, queryWrapper);
    }


    @Override
    public Result showClasses(String courseName) {
        //将中文转换为英文
        Map<String, String> map = nameMapperUtil.getCnToEn();
        courseName = map.get(courseName);
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_name", courseName);
        List<Class> classList = classMapper.selectList(queryWrapper);
        //将英文转换为中文
        Map<String, String> map1 = nameMapperUtil.getEnToCn();
        for (Class course : classList) {
            course.setCourseName(map1.get(course.getCourseName()));
        }
        return Result.success(classList);
    }

    /**
     * 在课程表中新增课程
     * @param courseName 课程名
     * @param detail 开课学期
     * @return 结果
     */
    @Override
    public void addCourse(String courseName, String detail) {
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDetail(detail);
        courseMapper.insert(course);
    }

    @Override
    public void addCourseTable(String courseName) {
        gradeMapper.createCourseTable(courseName);
    }

    @Override
    public void addClass(String courseName, List<Integer> teacherIds, int classNum, String term) {
        //根据teacherId找到教师姓名
        List<Teacher> teacherList = new ArrayList<>();
        for (Integer teacherId : teacherIds) {
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id", teacherId);
            Teacher teacher = teacherMapper.selectOne(queryWrapper);
            teacherList.add(teacher);
        }
        for(int i = 1; i <= classNum; i++) {
            Class aClass = new Class();
            aClass.setCourseName(courseName);
            aClass.setTerm(term);
            aClass.setClassNum(i);
            aClass.setCount(0);
            aClass.setTeacherName(teacherList.get(i-1).getName());
            classMapper.insert(aClass);
        }
    }


    @Override
    public String nameMap(String courseName) {
        Map<String, String> map = nameMapperUtil.getEnToCn();//先拿到现有的课程映射
        int size = map.size();
        //生成目前的英文名
        String courseNameEng = "new" + (size + 1);
        NameMap nameMap = new NameMap();
        nameMap.setCourseName(courseNameEng);
        nameMap.setCn(courseName);
        nameMapper.insert(nameMap);
        //新建映射
        map.put(courseNameEng, courseName);
        Map<String, String> map1 = nameMapperUtil.getCnToEn();
        map1.put(courseName, courseNameEng);
        //返回英文名
        return courseNameEng;
    }

    @Override
    public Result showAllClasses() {
        List<Class> classList = classMapper.selectList(null);
        for (Class clazz : classList) {
            //对每一个教学班的课程名进行映射
            String EngName = clazz.getCourseName();
            clazz.setCourseName(nameMapperUtil.getEnToCn().get(EngName));
        }
        return Result.success(classList);
    }

    @Override
    public Map<String, Object> showStudentScores(String classNum, String searchQuery, int currentPage, int pageSize, int sortType) {
        //首先根据教学班号，在classinfo中取到课程名和班级
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", classNum);
        Class clazz = classMapper.selectOne(queryWrapper);
        String courseName = clazz.getCourseName();
        String classN = String.valueOf(clazz.getClassNum());
        // 在从courseName这张表中，查看名字为模糊搜索，班级为classN的成绩
        List<Grade> grades = gradeMapper.showScore(courseName, searchQuery, classN);
        // 根据 sortType 值进行排序
        if (sortType == 1) {
            // 按成绩升序排序
            grades = grades.stream()
                    .sorted(Comparator.comparingDouble(Grade::getGradeAvg)) // 升序排序
                    .collect(Collectors.toList());
        } else if (sortType == 2) {
            // 按成绩降序排序
            grades = grades.stream()
                    .sorted((g1, g2) -> Float.compare(g2.getGradeAvg(), g1.getGradeAvg())) // 降序排序
                    .collect(Collectors.toList());
        }
        // 计算分页的起始位置和结束位置
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, grades.size()); // 防止越界
        // 截取当前页的数据
        List<Grade> paginatedGrades = grades.subList(startIndex, endIndex);

        Map<String, Object> response = new HashMap<>();
        response.put("data", paginatedGrades);
        response.put("total", grades.size());  // 添加总数
        return response;
    }

    @Override
    public Result showStatistics(String classNum) {
        //首先根据教学班号，在classinfo中取到课程名和班级
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", classNum);
        Class clazz = classMapper.selectOne(queryWrapper);
        String courseName = clazz.getCourseName();
        String classN = String.valueOf(clazz.getClassNum());
        // 在从courseName这张表中，班级为classN的成绩
        List<Grade> grades = gradeMapper.showshowStatistics(courseName,classN);
        Map<String, Integer> scoreRangeMap = new HashMap<>();
        // 初始化分数段
        scoreRangeMap.put("0-59", 0);
        scoreRangeMap.put("60-69", 0);
        scoreRangeMap.put("70-79", 0);
        scoreRangeMap.put("80-89", 0);
        scoreRangeMap.put("90-100", 0);
        // 遍历所有成绩，进行分段统计
        for (Grade grade : grades) {
            float gradeAvg = grade.getGradeAvg();

            if (gradeAvg >= 0 && gradeAvg < 60) {
                scoreRangeMap.put("0-59", scoreRangeMap.get("0-59") + 1);
            } else if (gradeAvg >= 60 && gradeAvg < 70) {
                scoreRangeMap.put("60-69", scoreRangeMap.get("60-69") + 1);
            } else if (gradeAvg >= 70 && gradeAvg < 80) {
                scoreRangeMap.put("70-79", scoreRangeMap.get("70-79") + 1);
            } else if (gradeAvg >= 80 && gradeAvg < 90) {
                scoreRangeMap.put("80-89", scoreRangeMap.get("80-89") + 1);
            } else if (gradeAvg >= 90 && gradeAvg <= 100) {
                scoreRangeMap.put("90-100", scoreRangeMap.get("90-100") + 1);
            }
        }
        // 返回结果
        return Result.success(scoreRangeMap);
    }

}
