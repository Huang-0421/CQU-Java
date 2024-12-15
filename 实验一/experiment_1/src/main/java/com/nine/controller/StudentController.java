package com.nine.controller;

import com.nine.pojo.Class;
import com.nine.pojo.Student;
import java.sql.SQLException;

/**
 * 学生控制器
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午4:20
 */
public class StudentController extends BaseController {

    public StudentController() throws SQLException {
    }

    /**
     * 查询成绩
     * @param student 学生
     * @return 循环控制
     * @throws SQLException sql异常
     */
    public boolean findGrade(Student student) throws SQLException {
        String stuName = student.getName();
        // 根据名字找该学生所选的课程
        String[] courseNames = dbTool.stu_findCourse(stuName);
        //课程为空说明没有选课
        if (courseNames[0] == null || courseNames[0].isEmpty()) {
            System.out.println(RED + "暂无选课信息" + RESET);
            return true;
        }
        // 表头
        System.out.printf("%-15s %-10s %-10s %-11s %-12s %-10s%n", "课程名称", "平时成绩", "期中成绩", "实验成绩", "期末成绩","综合成绩");
        System.out.println("----------------------------------------------------------------------------------");

        for (String courseName : courseNames) {
            // 根据名字找成绩
            String[] grades = dbTool.stu_findGrade(stuName, courseName);
            if (grades == null) {
                System.out.println("暂无成绩信息");
                return true;
            }

            // 课程名映射
            switch (courseName) {
                case "java":
                    System.out.printf("%-16s %-13s %-13s %-13s %-13s %-13s%n", "Java企业级应用", grades[0], grades[1], grades[2], grades[3],grades[4]);
                    break;
                case "database_sys":
                    System.out.printf("%-16s %-13s %-13s %-13s %-13s %-13s%n", "数据库系统", grades[0], grades[1], grades[2], grades[3],grades[4]);
                    break;
                case "computer":
                    System.out.printf("%-15s %-13s %-13s %-13s %-13s %-13s%n", "计算机组成原理", grades[0], grades[1], grades[2], grades[3],grades[4]);
                    break;
                case "structure":
                    System.out.printf("%-17s %-13s %-13s %-13s %-13s %-13s%n", "数据结构", grades[0], grades[1], grades[2], grades[3],grades[4]);
                    break;
                default:
                    System.out.println("成绩显示错误");
            }
        }
        return true;
    }

    /**
     * 调用dbTool查找可选课程
     * @return 课程数组
     * @throws SQLException sql异常
     */
    public Class[] showClassInfo() throws SQLException {
        return dbTool.stu_showClassInfo();
    }

    /**
     * 根据课程选课
     * @param student 学生
     * @param classes 课程数组
     * @param choose1 选择1
     * @param choose2 选择1
     * @param choose3 选择1
     * @return 循环控制
     * @throws SQLException sql异常
     * @throws InterruptedException 中断异常
     */
    public boolean chooseClass(Student student, Class[] classes, int choose1, int choose2, int choose3) throws SQLException, InterruptedException {
        //输入合法检查
        if (choose1 == choose2 || choose2 == choose3 || choose3 == choose1) {
            System.out.println(RED + "请选择三个不同的教学班" + RESET);
            return true;
        }
        if(choose1 < 1 || choose1 > 8 || choose2 < 1 || choose2 > 8 || choose3 < 1 || choose3 > 8){
            System.out.println(RED + "请使用正确编号" + RESET);
            return true;
        }
        Class class1 = classes[choose1 - 1];
        Class class2 = classes[choose2 - 1];
        Class class3 = classes[choose3 - 1];

        //输入检查
        if (class1.getCourceName().equals(class2.getCourceName()) || class2.getCourceName().equals(class3.getCourceName())
                || class3.getCourceName().equals(class1.getCourceName())) {
            System.out.println(RED + "同一门课只能选择一个教学班" + RESET);
            return true;
        }

        String[] animation = {"|", "/", "-", "\\"};  // 定义旋转的字符
        System.out.print(YELLOW + "加载中 ");
        for (int i = 0; i < 10; i++) {
            System.out.print("\r" + "加载中 " + animation[i % animation.length]);  // 使用 '\r' 回车符覆盖之前的内容
            Thread.sleep(200);  // 每隔 200 毫秒切换一次
        }
        System.out.print("\r      " + RESET);
        System.out.println();

        //使用dbTool执行选课
        if(!dbTool.stu_chooseClass(student, classes, choose1, choose2, choose3)){
            System.out.println(RED + "选课已确定，无法重新选课" + RESET);
        }
        else{
            System.out.println(GREEN + "选课成功" + RESET);
        }
        return false;
    }

}
