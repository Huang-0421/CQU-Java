package com.huang.experiment_4.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private String courseName;
    private String name;
    @TableField("class_num")
    private int classNum;
    private int grade1;
    private int grade2;
    private int grade3;
    private int grade4;
    private float gradeAvg;
}
