package com.huang.utils;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class Info {

    private String currentPath;//当前路径

    /**
     * 获取当前路径
     * @return 路径
     */
    public String getCurrentPath() {
        return currentPath;
    }

    /**
     * 设置路径
     * @param currentPath 目标路径
     */
    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }
}
