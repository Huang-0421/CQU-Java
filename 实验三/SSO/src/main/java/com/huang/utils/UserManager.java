package com.huang.utils;

import javax.servlet.http.Cookie;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class UserManager {
    // 使用线程安全的 Map 存储用户信息
    private static Map<String, String> loggedInUsers = new ConcurrentHashMap<>();
    private static Map<String, String> history = new ConcurrentHashMap<>();
    private static Set<String> disabledUsers = new HashSet<String>();
    /**
     * 记录当前用户登录，以及历史登录
     * @param username 用户名
     * @param loginTime 登录时间
     */
    public static void addUser(String username, String loginTime) {
        loggedInUsers.put(username, loginTime);
        history.put(username, loginTime);
    }

    /**
     * @return 当前登录的用户
     */
    public static Map<String, String> getLoggedInUsers() {
        return loggedInUsers;
    }

    /**
     * @return 历史登录的用户
     */
    public static Map<String, String> getHistory() {
        return history;
    }

    /**
     * 移除已登录用户
     * @param username 用户名
     */
    public static void removeUser(String username) {
        loggedInUsers.remove(username);
    }

    /**
     * 禁用用户
     * @param username 用户名
     */
    public static void disableUser(String username) {
        disabledUsers.add(username);
    }

    /**
     * 获取被禁用用户名
     * @return 被禁用用户名集合
     */
    public static Set<String> getDisabledUser() {
        return disabledUsers;
    }

    /**
     * 检查Cookie是否超时
     * @param cookie 用户Cookie
     * @return 验证
     */
    public static boolean checkCookieValidity(Cookie cookie) {
        try {
            // 假设你在 Cookie 中存储的是一个以毫秒为单位的时间戳
            long cookieTimestamp = Long.parseLong(cookie.getValue()); // 从 Cookie 中获取时间戳
            long currentTime = System.currentTimeMillis(); // 获取当前时间的时间戳（以毫秒为单位）
            // 检查时间差是否大于 60 秒（60000 毫秒）
            return (currentTime - cookieTimestamp) <= 120000;
        } catch (NumberFormatException e) {
            // 如果解析时间戳失败，则认为 Cookie 无效
            return false;
        }
    }
}
