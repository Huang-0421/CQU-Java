package com.nine.pojo;

/**
 * 教学班类
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午2:54
 */
public class Class {
    private int id;
    private String teacherName; //教师姓名
    private String courceName;    //课程名称，须存在于课程
    private int count;  //总人数
    private int eduClassId;    //教学班号
    private String startTerm;  //开课学期

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourceName() {
        return courceName;
    }

    public void setCourceName(String courceName) {
        this.courceName = courceName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getEduClassId() {
        return eduClassId;
    }

    public void setEduClassId(int eduClassId) {
        this.eduClassId = eduClassId;
    }

    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
