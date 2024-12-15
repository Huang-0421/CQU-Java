package com.nine.controller;

import com.nine.service.RollService;
import com.nine.tools.DataBaseTool;

import java.sql.SQLException;

/**
 * 父类控制器
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/14 上午10:24
 * @description StudentController TeacherController 的父类
 */
public class BaseController {
    /**
     * 数据库操作工具
     */
    DataBaseTool dbTool;

    /**
     * 颜色控制
     */
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public BaseController() throws SQLException {
        dbTool = new DataBaseTool();
    }

    /**
     * 密码校验
     * @param roll 角色
     * @param Pwd 密码
     * @param flag 教师or学生
     * @return 密码是否正确
     * @throws SQLException sql异常
     */
    public <T extends RollService> boolean check(T roll, String Pwd, int flag) throws SQLException {
        //根据名字找密码
        String findPwd = dbTool.findPwd(roll.getName(), flag);
        if (findPwd == null){
            System.out.println(RED + "没有相关信息，请检查用户名" + RESET);
            return false;
        }
        if (findPwd.equals(Pwd)) {
            return true;
        }
        System.out.println(RED + "密码错误" + RESET);
        return false;
    }

    /**
     * 关闭数据库连接
     */
    public void shutDown(){
        dbTool.closeConnection();
    }
}
