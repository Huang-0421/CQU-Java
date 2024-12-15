package com.nine.tools;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.nine.controller.BaseController;
import com.nine.controller.StudentController;
import com.nine.controller.TeacherController;
import com.nine.pojo.Class;
import com.nine.pojo.Student;
import com.nine.pojo.Teacher;

/**
 * 视图工具
 *
 * @author Huang_ruijie
 * @version 1.0
 */
public class ViewTool {
    // ANSI 转义序列，改变输出颜色
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";


    /**
     * 初始化两个controller对象，分别向学生和教师的功能提供接口
     */
    StudentController studentController = new StudentController();
    TeacherController teacherController = new TeacherController();

    public ViewTool() throws SQLException {
    }

    /**
     * 首页打印
     */
    public void mainShow() throws SQLException, InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print(GREEN + "   _____                      _          __  __                                               \n");
        Thread.sleep(150);
        System.out.print("  / ____|                    | |        |  \\/  |                                              \n");
        Thread.sleep(150);
        System.out.print(" | |  __   _ __    __ _    __| |   ___  | \\  / |   __ _   _ __     __ _    __ _    ___   _ __ \n");
        Thread.sleep(150);
        System.out.print(" | | |_ | | '__|  / _` |  / _` |  / _ \\ | |\\/| |  / _` | | '_ \\   / _` |  / _` |  / _ \\ | '__|\n");
        Thread.sleep(150);
        System.out.print(" | |__| | | |    | (_| | | (_| | |  __/ | |  | | | (_| | | | | | | (_| | | (_| | |  __/ | |   \n");
        Thread.sleep(150);
        System.out.print("  \\_____| |_|     \\__,_|  \\__,_|  \\___| |_|  |_|  \\__,_| |_| |_|  \\__,_|  \\__, |  \\___| |_|   \n");
        System.out.print("                                                                           __/ |              \n");
        System.out.print("                                                                          |___/  \n" + RESET);
        boolean flag = true;
        while (flag) {
            Thread.sleep(150);
            System.out.println("====== 学生成绩管理系统 ======");
            System.out.println(CYAN + "    1. 教师系统");
            System.out.println("    2. 学生系统");
            System.out.println("    3. 退出" + RESET);
            Thread.sleep(300);
            System.out.print("请选择您的操作：");
            int choice = scanner.nextInt();
            flag = switch (choice) {
                case 1 -> pageTeacher(scanner); //进入教师界面
                case 2 -> pageStudent(scanner); //进入学生界面
                case 3 -> false;
                default -> true;
            };
        }
        printLoading("正在退出");
        //关闭数据库连接
        teacherController.shutDown();
    }

    /**
     * 学生身份认证页面
     *
     * @param scanner 输入
     * @return 循环控制
     * @throws SQLException         sql异常
     * @throws InterruptedException 中断异常
     */
    private boolean pageStudent(Scanner scanner) throws SQLException, InterruptedException {
        System.out.println();
        System.out.println("====== 学生身份认证 ======");
        boolean flag = true;
        while (flag) {
            Thread.sleep(300);
            System.out.print("请输入姓名：");
            String stuName = scanner.next();
            Student student = new Student(stuName);
            Thread.sleep(300);
            System.out.print("请输入密码：");
            String stuPwd = scanner.next();

            //调用BaseController检查用户名和密码
            BaseController baseController = new BaseController();
            if (baseController.check(student, stuPwd, 2)) {
                System.out.println();
                //进入下一页面
                pageStudentGrade(scanner, student);
                return false;
            } else {
                System.out.println(CYAN + "    1. 重新输入");
                Thread.sleep(300);
                System.out.println("    2. 返回上一级" + RESET);
                System.out.print("请选择您的操作：");
                if (scanner.nextInt() == 2) {
                    flag = false;
                }
            }
        }
        return true;
    }

    /**
     * 教师身份验证
     *
     * @param scanner 输入
     * @return 循环控制
     * @throws SQLException         sql异常
     * @throws InterruptedException 中断异常
     * @throws IOException          io异常
     */
    private boolean pageTeacher(Scanner scanner) throws SQLException, InterruptedException, IOException {
        System.out.println();
        System.out.println("======= 教师身份认证 =======");
        boolean flag = true;
        while (flag) {
            Thread.sleep(300);
            System.out.print("请输入姓名：");
            String teacherName = scanner.next();
            Teacher teacher = new Teacher(teacherName);
            Thread.sleep(300);
            System.out.print("请输入密码：");
            String teacherPwd = scanner.next();

            //使用BaseController检查用户名和密码
            BaseController baseController = new BaseController();
            if (baseController.check(teacher, teacherPwd, 1)) {
                //进入下一页面
                pageTeacherFuncs(scanner, teacher);
                return false;
            } else {
                System.out.println(CYAN + "    1. 重新输入");
                Thread.sleep(300);
                System.out.println("    2. 返回上一级" + RESET);
                System.out.print("请选择您的操作：");
                if (scanner.nextInt() == 2) {
                    flag = false;
                }
            }
        }
        return true;
    }

    /**
     * 学生功能页面
     *
     * @param scanner 输入
     * @param student 学生
     * @throws SQLException         sql异常
     * @throws InterruptedException 中断异常
     */
    private void pageStudentGrade(Scanner scanner, Student student) throws SQLException, InterruptedException {
        boolean flag = true;
        while (flag) {
            System.out.println("===== 欢迎您 " + student.getName() + " 同学 =====");
            Thread.sleep(300);
            System.out.println(CYAN + "    1. 查询成绩");
            Thread.sleep(300);
            System.out.println("    2. 选课");
            Thread.sleep(300);
            System.out.println("    3. 退出系统" + RESET);
            Thread.sleep(300);
            System.out.print("请选择您的操作：");
            int choice = scanner.nextInt();

            if (choice == 1) {
                printLoading("加载中");
                //学生查询成绩
                flag = studentController.findGrade(student);
                System.out.println();
                System.out.println("按下回车键继续...");
                scanner.nextLine();
                scanner.nextLine();

            } else if (choice == 2) {
                printLoading("加载中");
                //展示课程信息
                Class[] classes = pageStudentChoose();
                System.out.println();
                boolean flag1 = true;
                while (flag1) {
                    Thread.sleep(300);
                    System.out.print("请输入所选教学班编号(使用空格间隔)：");
                    int choose1 = scanner.nextInt();
                    int choose2 = scanner.nextInt();
                    int choose3 = scanner.nextInt();
                    //根据输入进行选课
                    flag1 = studentController.chooseClass(student, classes, choose1, choose2, choose3);
                }
                Thread.sleep(300);
                System.out.println("按下回车键继续...");
                scanner.nextLine();
                scanner.nextLine();
            } else {
                return;
            }
        }
    }

    /**
     * 打印可选课程信息
     *
     * @return 课程数组
     * @throws SQLException sql异常
     */
    private Class[] pageStudentChoose() throws SQLException {
        System.out.println("============================= 课程信息总览 ==============================");
        //课程信息
        Class[] classes = studentController.showClassInfo();
        System.out.printf("    %-7s %-15s %-10s %-10s %-10s%n", "编号", "课程名称", "授课教师", "已选人数", "开课学期");
        System.out.println("-----------------------------------------------------------------------");
        for (Class c : classes) {
            switch (c.getCourceName()) {
                case "computer":
                    System.out.printf("     %-5s %-17s %-12s %-10s %-13s%n", c.getId(), "计算机组成原理", c.getTeacherName(), c.getCount(), c.getStartTerm());
                    break;
                case "database_sys":
                    System.out.printf("     %-5s %-18s %-12s %-10s %-13s%n", c.getId(), "数据库系统", c.getTeacherName(), c.getCount(), c.getStartTerm());
                    break;
                case "java":
                    System.out.printf("     %-5s %-18s %-12s %-10s %-13s%n", c.getId(), "Java企业级应用", c.getTeacherName(), c.getCount(), c.getStartTerm());
                    break;
                case "structure":
                    System.out.printf("     %-5s %-19s %-12s %-10s %-13s%n", c.getId(), "数据结构", c.getTeacherName(), c.getCount(), c.getStartTerm());
                    break;
            }
        }
        return classes;
    }

    /**
     * 打印老师具体功能页面
     *
     * @param scanner 传递的输入器
     * @param teacher 传入该教师
     * @throws SQLException         sql异常
     * @throws InterruptedException 中断异常
     * @throws IOException          io异常
     */
    private void pageTeacherFuncs(Scanner scanner, Teacher teacher) throws SQLException, InterruptedException, IOException {
        boolean flag = true;
        while (flag) {
            System.out.println();
            System.out.println("===== 欢迎您 " + teacher.getName() + " 老师 =====");
            Thread.sleep(300);
            System.out.println(CYAN + "    1. 查询当前教学班");
            Thread.sleep(300);
            System.out.println("    2. 显示教学班所有成绩");
            Thread.sleep(300);
            System.out.println("    3. 统计教学班总体成绩");
            Thread.sleep(300);
            System.out.println("    4. 查找教学班学生成绩");
            Thread.sleep(300);
            System.out.println("    5. 导出教学班成绩");
            Thread.sleep(300);
            System.out.println("    6. 退出系统" + RESET);
            Thread.sleep(300);
            System.out.print("请选择您的操作：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    printLoading("加载中");
                    //显示班级信息
                    flag = teacherController.showClass(teacher, scanner);
                    break;
                case 2:
                    //进入显示所有成绩
                    flag = pageTeacherAllGrade(scanner, teacher);
                    break;
                case 3:
                    printLoading("加载中");
                    //统计教学班总体成绩
                    flag = teacherController.generalGrade(teacher, scanner);
                    break;
                case 4:
                    System.out.print("请输入学生姓名：");
                    String stuName = scanner.next();
                    printLoading("加载中");
                    //查找学生成绩
                    flag = teacherController.searchStu(teacher, scanner, stuName);
                    break;
                case 5:
                    //导出成绩
                    flag = teacherController.exportGrade(teacher, scanner);
                    break;
                default:
                    flag = false;
            }
        }
    }

    /**
     * 教学班成绩统计页面
     * @param scanner 输入
     * @param teacher 教师
     * @return 循环控制
     * @throws SQLException sql异常
     * @throws InterruptedException 中断异常
     */
    public boolean pageTeacherAllGrade(Scanner scanner, Teacher teacher) throws SQLException, InterruptedException {
        boolean flag = true;
        while (flag) {
            System.out.println();
            System.out.println("====== 教学班成绩显示 =====");
            Thread.sleep(300);
            System.out.println(CYAN + "    1. 按姓名排序");
            Thread.sleep(300);
            System.out.println("    2. 按综合成绩排序");
            Thread.sleep(300);
            System.out.println("    3. 返回上一页" + RESET);
            Thread.sleep(300);
            System.out.print("请选择您的操作：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1://按姓名排序
                    printLoading("加载中");
                    flag = teacherController.sortGrade(teacher, "name");
                    break;
                case 2://按成绩排序
                    printLoading("加载中");
                    flag = teacherController.sortGrade(teacher, "grade_avg DESC");
                    break;
                case 3:
                    return true;
                default:
            }
        }
        return true;
    }

    /**
     * 打印加载动效
     *
     * @param text 打印文本
     * @throws InterruptedException 中断异常
     */
    private void printLoading(String text) throws InterruptedException {  // 定义旋转的字符){
        String[] animation = {"|", "/", "-", "\\"};  // 定义旋转的字符
        System.out.print(YELLOW + text + " ");
        for (int i = 0; i < 10; i++) {
            System.out.print("\r" + text + " " + animation[i % animation.length]);  // 使用 '\r' 回车符覆盖之前的内容
            Thread.sleep(200);  // 每隔 200 毫秒切换一次
        }
        System.out.print("\r      " + RESET);
        System.out.println();
    }
}
