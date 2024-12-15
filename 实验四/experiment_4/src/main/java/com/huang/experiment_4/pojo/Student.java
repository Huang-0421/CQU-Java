package com.huang.experiment_4.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生类
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午2:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("student")
public class Student{
    @TableId(value = "stu_id", type = IdType.AUTO)
    private int studentId;
    @TableField("stu_gender")
    private int gender; //性别
    @TableField("name")
    private String name;
    @TableField("password")
    private String password;
    private String course1;
    private String course2;
    private String course3;
}
