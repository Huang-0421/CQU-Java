package com.huang.experiment_4.utils;

import com.huang.experiment_4.mapper.NameMapper;
import com.huang.experiment_4.pojo.NameMap;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程名与表明的映射
 * @author Huang_ruijie
 * @version 1.0
 */
@Data
@Component
public class NameMapperUtil {
    private Map<String, String> EnToCn = new HashMap<>();
    private Map<String, String> CnToEn = new HashMap<>();
    @Autowired
    private NameMapper nameMapper;
    @PostConstruct
    public void init() {
        // 从数据库查询名字映射
        List<NameMap> mappings = nameMapper.selectList(null);

        // 将查询结果存入 Map
        for (NameMap mapping : mappings) {
            EnToCn.put(mapping.getCourseName(), mapping.getCn());
            CnToEn.put(mapping.getCn(), mapping.getCourseName());
        }
    }
}
//computer: '计算机组成原理',
//database_sys: '数据库系统',
//java: 'Java企业级应用',
//structure: '数据结构'