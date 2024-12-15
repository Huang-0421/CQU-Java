package com.nine.pojo;

import com.nine.service.RollService;

/**
 * 学生类
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午2:50
 */
public class Student implements RollService {

    private final String stu_name;    //姓名
    private int stu_gender; //性别

    public Student(String stu_name) {
        this.stu_name = stu_name;
    }

    @Override
    public String getName() {
        return stu_name;
    }
}
