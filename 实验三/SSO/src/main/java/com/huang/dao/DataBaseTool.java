package com.huang.dao;

import java.sql.*;

/**
 * 数据库操作工具
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午4:20
 */
public class DataBaseTool{

    /**
     * 数据库连接和Statement对象
     */
    private final Connection connection;
    private final Statement statement;

    /**
     * 构造函数：初始化数据库连接
     * @throws SQLException sql异常
     */
    public DataBaseTool() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/sso";// 数据库URL
        String username = "root";  // 数据库用户名
        String password = "20040421a";  // 数据库密码
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);  // 建立连接
        statement = connection.createStatement();  // 创建Statement对象
    }
    /**
     * 关闭数据库连接的方法
     */
    public void closeConnection() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找用户名与密码
     * @param name 姓名
     * @return 密码
     * @throws SQLException SQL异常
     */
    public String findPwd(String name) throws SQLException {
        String result = "";
        String sql = "SELECT password FROM user WHERE name = ?";  // 动态拼接表名
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);  // 设置查询条件（学生密码）
        // 执行查询
        ResultSet resultSet = preparedStatement.executeQuery();
        // 处理查询结果
        if (resultSet.next()) {
            result = resultSet.getString("password");  // 获取匹配的密码
        }
        // 关闭资源
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    /**
     * 用户注册
     * @param name 姓名
     * @param password 密码
     * @throws SQLException SQL异常
     */
    public void signIn(String name, String password) throws SQLException {
        String sql = "INSERT INTO user (name, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}


