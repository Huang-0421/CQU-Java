package com.huang.experiment_4.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 专门用来做课程名映射
 * @author Huang_ruijie
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("namemap")
public class NameMap {
    private String courseName;
    private String cn;
}
