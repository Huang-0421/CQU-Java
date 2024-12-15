package com.nine.pojo;

import com.nine.service.RollService;

/**
 * 教师类
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午2:59
 */
public class Teacher implements RollService {
    private final String teacherName; //教师姓名
    private String teacherId;  //教师编号

    public Teacher(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String getName() {
        return teacherName;
    }
}
