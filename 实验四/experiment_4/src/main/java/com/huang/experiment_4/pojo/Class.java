package com.huang.experiment_4.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教学班类
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午2:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("class_info")
public class Class {
    private int id;
    private String teacherName; //教师姓名
    private String courseName;    //课程名称，须存在于课程
    private int count;  //总人数
    @TableField("class")
    private int classNum;    //教学班号
    private String term;  //开课学期
}
