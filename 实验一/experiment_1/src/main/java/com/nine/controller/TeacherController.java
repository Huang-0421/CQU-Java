package com.nine.controller;

import com.nine.pojo.Teacher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 教师控制器
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午4:20
 */
public class TeacherController extends BaseController {

    public TeacherController() throws SQLException {
    }

    /**
     * 展示教学班信息
     * @param teacher 当前教师
     * @param scanner 键盘输入接收器
     * @return 控制循环标签
     * @throws SQLException sql异常
     */
    public boolean showClass(Teacher teacher, Scanner scanner) throws SQLException {
        String name = teacher.getName();

        //调用dbTool对象的 findTeacherCourse 方法，查找信息教学班信息。
        String[] classInfo = dbTool.findTeacherCourse(name);
        if (classInfo[0] == null) {
            System.out.println("暂无教学班信息");
        }

        //打印教学班信息
        else {
            classInfo[0] = switch (classInfo[0]) {
                case "computer" -> "计算机组成原理";
                case "structure" -> "数据结构";
                case "database_sys" -> "数据库系统";
                case "java" -> "Java企业级应用";
                default -> null;
            };
            System.out.printf("    %-15s %-10s %-10s %-10s%n", "课程名称", "授课教师", "已选人数", "开课学期");
            System.out.println("-----------------------------------------------------------------------");
            System.out.printf("  %-17s %-12s %-10s %-13s%n", classInfo[0], classInfo[1], classInfo[2], classInfo[3]);
        }
        System.out.println();
        System.out.println("按下回车键继续...");
        scanner.nextLine();
        scanner.nextLine();
        return true;
    }

    /**
     * 生成总体成绩
     * @param teacher 该教师
     * @param scanner 输入器
     * @return 循环标签
     * @throws SQLException sql异常
     */
    public boolean generalGrade(Teacher teacher, Scanner scanner) throws SQLException {
        String name = teacher.getName();
        //通过dbTool找到课程名和班级号
        String[] classInfo = dbTool.findTeacherCourse(name);
        String courseName = classInfo[0];   //课程名
        String classNum = classInfo[4];     //班级号
        //计算每项成绩的平均数
        double[] grades = dbTool.culculateScore(courseName, classNum);
        //计算综合成绩
        double avg = (grades[0] + grades[1] + grades[2] + grades[3]) / 4;
        System.out.printf("%-15s %-10s %-10s %-10s %-10s %-10s%n", "课程名称", "平时成绩", "期中成绩", "实验成绩", "期末成绩", "综合成绩");
        System.out.println("---------------------------------------------------------------------------------");
        switch (classInfo[0]) {
            case "java":
                System.out.printf("%-15s %-12.2f %-13.2f %-13.2f %-13.2f %-13.2f%n", "Java企业级应用", grades[0], grades[1], grades[2], grades[3], avg);
                break;
            case "database_sys":
                System.out.printf("%-15s %-12.2f %-13.2f %-13.2f %-13.2f %-13.2f%n", "数据库系统", grades[0], grades[1], grades[2], grades[3], avg);
                break;
            case "computer":
                System.out.printf("%-14s %-12.2f %-13.2f %-13.2f %-13.2f %-13.2f%n", "计算机组成原理", grades[0], grades[1], grades[2], grades[3], avg);
                break;
            case "structure":
                System.out.printf("%-16s %-12.2f %-13.2f %-13.2f %-13.2f %-13.2f%n", "数据结构", grades[0], grades[1], grades[2], grades[3], avg);
                break;
            default:
                System.out.println("成绩显示错误");
        }
        System.out.println();
        System.out.println("按下回车键继续...");
        scanner.nextLine();
        scanner.nextLine();
        return true;
    }

    /**
     * 查找学生成绩
     * @param teacher 该教师
     * @param scanner 输入器
     * @param stuName 查找的学生姓名
     * @return 循环标签
     * @throws SQLException sql异常
     */
    public boolean searchStu(Teacher teacher, Scanner scanner, String stuName) throws SQLException {
        String name = teacher.getName();
        //通过dbTool找到课程名和班级号
        String[] classInfo = dbTool.findTeacherCourse(name);
        String courseName = classInfo[0];
        //在课程中找学生的成绩
        String[] grades = dbTool.stu_findGrade(stuName, courseName);
        if (grades == null) {
            System.out.println("暂无成绩信息");
            return true;
        }
        System.out.printf("%-15s %-10s %-10s %-11s %-12s %-10s%n", "课程名称", "平时成绩", "期中成绩", "实验成绩", "期末成绩","综合成绩");
        System.out.println("----------------------------------------------------------------------------------");
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
        System.out.println();
        System.out.println("按下回车键继续...");
        scanner.nextLine();
        scanner.nextLine();
        return true;
    }

    /**
     * 导出教学班成绩
     * @param teacher 该教师
     * @param scanner 输入
     * @return 循环控制
     * @throws SQLException sql异常
     * @throws IOException io异常
     */
    public boolean exportGrade(Teacher teacher, Scanner scanner) throws SQLException, IOException {
        //找到这张表
        String name = teacher.getName();
        //调用dbTool对象的 findTeacherCourse 方法，查找信息教学班信息。
        String[] classInfo = dbTool.findTeacherCourse(name);
        String courseName = classInfo[0];
        String classNum = classInfo[4];
        String path = "D:/grade/" + courseName + ".xlsx";
        //调用dbTool对象导出为excel
        dbTool.exportToExcel(courseName, classNum, path);
        System.out.println(GREEN + "Excel 文件已成功导出到：" + path + RESET);
        System.out.println("按下回车键继续...");
        scanner.nextLine();
        scanner.nextLine();
        return true;
    }

    /**
     * 成绩按需排序
     * @param teacher 教师
     * @param flag 按姓名or成绩排序
     * @return 循环控制
     * @throws SQLException sql异常
     */
    public boolean sortGrade(Teacher teacher, String flag) throws SQLException {
        String name = teacher.getName();
        //通过dbTool找到课程名和班级号
        String[] classInfo = dbTool.findTeacherCourse(name);
        String courseName = classInfo[0];   //课程名
        String classNum = classInfo[4];   //课程名
        // 表头
        System.out.printf("   %-12s %-10s %-10s %-11s %-12s %-10s%n", "姓名", "平时成绩", "期中成绩", "实验成绩", "期末成绩","综合成绩");
        System.out.println("----------------------------------------------------------------------------------");
        dbTool.sortGrade(courseName, classNum, flag);
        return false;
    }
}
