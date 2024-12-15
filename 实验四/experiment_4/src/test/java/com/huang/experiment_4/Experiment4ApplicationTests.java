package com.huang.experiment_4;


import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Experiment4ApplicationTests {

    @Test
    public void testSelect() {
        System.out.println();
        System.out.print(SecureUtil.md5("123"));
        System.out.println();
    }
}