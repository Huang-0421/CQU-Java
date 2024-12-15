package com.huang.experiment_4.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huang.experiment_4.mapper.ClassMapper;
import com.huang.experiment_4.mapper.GradeMapper;
import com.huang.experiment_4.mapper.TeacherMapper;
import com.huang.experiment_4.pojo.*;
import com.huang.experiment_4.pojo.Class;
import com.huang.experiment_4.utils.JwtUtils;
import com.huang.experiment_4.utils.NameMapperUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private NameMapperUtil nameMapperUtil;
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public Result checkTeacher(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = SecureUtil.md5(request.getParameter("password"));

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        queryWrapper.eq("password", password);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        if (teacher == null) {
            return Result.error();
        }
        request.getSession().setAttribute("teacherId", teacher.getTeacherId());
        Map<String, String> map = new HashMap<>();
        map.put("role", "teacher");

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "teacher");
        String jwt = JwtUtils.generateJWT(claims);
        //返回JWT
        Cookie cookie = new Cookie("token", jwt);
        cookie.setMaxAge(60 * 60);//有效期
        response.addCookie(cookie);

        return Result.success(map);
    }

    @Override
    public List<Class> showMyClass(Integer teacherId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        //找到当前教师之后，先找到其名字
        if (teacher == null) {
            return null;
        }
        String teacherName = teacher.getName();
        //再找到这个老师教的课程名
        QueryWrapper<Class> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("teacher_name", teacherName);
        List<Class> classList = classMapper.selectList(queryWrapper1);
        //做课程名的映射
        Map<String, String> map = nameMapperUtil.getEnToCn();
        for (Class c : classList) {
            c.setCourseName(map.get(c.getCourseName()));
        }
        return classList;
    }

    @Override
    public Result updateStudentScore(HttpServletRequest request) {
        String name = request.getParameter("name");
        int grade1 = Integer.parseInt(request.getParameter("grade1"));
        int grade2 = Integer.parseInt(request.getParameter("grade2"));
        int grade3 = Integer.parseInt(request.getParameter("grade3"));
        int grade4 = Integer.parseInt(request.getParameter("grade4"));
        float gradeAvg = Float.parseFloat(request.getParameter("gradeAvg"));
        Integer classNum = Integer.parseInt(request.getParameter("currentClassNum"));
        //首先根据教学班号，在classinfo中取到课程名和班级
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", classNum);
        Class clazz = classMapper.selectOne(queryWrapper);
        String courseName = clazz.getCourseName();
        int classN = clazz.getClassNum();
        //在从courseName表中的classN班级update成绩
        Grade grade = new Grade(courseName, name, classN, grade1, grade2, grade3, grade4, gradeAvg);
        gradeMapper.updateScore(grade);
        return Result.success();
    }

    @Override
    public Result exportToExcel(HttpServletRequest request) {
        int currentClassNum = Integer.parseInt(request.getParameter("currentClassNum"));
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", currentClassNum);
        Class clazz = classMapper.selectOne(queryWrapper);
        //得到表名和班级号
        String courseName = clazz.getCourseName();
        int classNum = clazz.getClassNum();

        List<Grade> grades = gradeMapper.exportScore(courseName, classNum);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(courseName);
        // 定义自定义的列头名称
        String[] customHeaders = {"姓名", "平时成绩", "期中成绩", "实验成绩", "期末成绩", "综合成绩"};
        // 创建 Excel 表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < customHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);  // 创建Excel列
            cell.setCellValue(customHeaders[i]);  // 设置自定义的列头名称
        }

        // 6. 将数据写入 Excel
        int rowIndex = 1;
        for (Grade grade : grades) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(grade.getName());
            row.createCell(1).setCellValue(grade.getGrade1());
            row.createCell(2).setCellValue(grade.getGrade2());
            row.createCell(3).setCellValue(grade.getGrade3());
            row.createCell(4).setCellValue(grade.getGrade4());
            row.createCell(5).setCellValue(grade.getGradeAvg());
        }
        String path = "C:/Users/l/Desktop/" + courseName + ".xlsx";
        // 7. 创建 Excel 文件
        try (FileOutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 8. 关闭工作簿
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }

    @Override
    public Result getInfo(int teacherId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        return Result.success(teacher);
    }
}
