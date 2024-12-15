package com.huang.experiment_4.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教师类
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午2:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("teacher")
public class Teacher{

    @TableId("teacher_id")
    private Integer teacherId;  //教师编号
    private String name;
    private String password;
    private String college; //学院
    private String age;
}
