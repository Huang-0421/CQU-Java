package com.nine.tools;

import com.nine.pojo.Class;
import com.nine.pojo.Student;
import com.nine.service.DataBaseService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;

/**
 * 数据库操作工具
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午4:20
 */
public class DataBaseTool implements DataBaseService {

    /**
     * 数据库连接和Statement对象
     */
    private final Connection connection;
    private final Statement statement;

    /**
     * 构造函数：初始化数据库连接
     * @throws SQLException sql异常
     */
    public DataBaseTool() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/stu_system";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "20040421a";  // 数据库密码
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
     * @param flag 教师or学生
     * @return 密码
     * @throws SQLException
     */
    public String findPwd(String name, int flag) throws SQLException {
        String result = null;
        String tableName = (flag == 1) ? "teacher" : "student";  // 根据 flag 选择表名
        String sql = "SELECT password FROM " + tableName + " WHERE name = ?";  // 动态拼接表名
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
     * 查找学生成绩
     * @param name 学生姓名
     * @param course 课程
     * @return 学生的四个成绩
     * @throws SQLException sql异常
     */
    public String[] stu_findGrade(String name, String course) throws SQLException {
        // 声明一个长度为4的String数组
        String[] result = new String[5];
        String sql = "SELECT grade1, grade2, grade3, grade4, grade_avg FROM " + course + " WHERE name = ?";  // 动态拼接表名
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);  // 设置查询条件
        // 执行查询
        ResultSet resultSet = preparedStatement.executeQuery();
        // 处理查询结果
        if (resultSet.next()) {
            result[0] = resultSet.getString("grade1");  // 获取成绩
            result[1] = resultSet.getString("grade2");
            result[2] = resultSet.getString("grade3");
            result[3] = resultSet.getString("grade4");
            result[4] = resultSet.getString("grade_avg");
        } else {
            result = null;
        }
        // 关闭资源
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    /**
     * 根据名字找该学生所选的课程
     * @param name 学生姓名
     * @return 所选课程字符串
     * @throws SQLException
     */
    public String[] stu_findCourse(String name) throws SQLException {
        String[] result = new String[3];
        String sql = "SELECT course1,course2,course3 FROM stu_system.student WHERE name = ?";  // 动态拼接表名
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);  // （学生姓名）
        // 执行查询
        ResultSet resultSet = preparedStatement.executeQuery();
        // 处理查询结果
        if (resultSet.next()) {
            result[0] = resultSet.getString("course1");  // 获取课程1
            result[1] = resultSet.getString("course2");  // 获取课程2
            result[2] = resultSet.getString("course3");  // 获取课程3
        }
        // 关闭资源
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    /**
     * 查找可选课程信息
     * @return 课程数组
     * @throws SQLException sql异常
     */
    public Class[] stu_showClassInfo() throws SQLException {
        Class[] result = new Class[8];
        //使用Lambda表达式初始化数组
        Arrays.setAll(result, i -> new Class());
        String sql = "SELECT * FROM stu_system.class_info";  // 动态拼接表名
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 执行查询
        ResultSet resultSet = preparedStatement.executeQuery();
        // 处理查询结果
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            result[id - 1].setId(id);
            result[id - 1].setTeacherName(resultSet.getString("teacher_name"));
            String courseName = resultSet.getString("course_name");
            result[id - 1].setCourceName(courseName);
            result[id - 1].setCount(resultSet.getInt("count"));
            result[id - 1].setEduClassId(resultSet.getInt("class"));
            result[id - 1].setStartTerm(resultSet.getString("term"));
        }
        // 关闭资源
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    /**
     * 基于选择进行选课，操作数据库
     * @param student
     * @param classes
     * @param choose1
     * @param choose2
     * @param choose3
     * @return
     * @throws SQLException
     */
    public boolean stu_chooseClass(Student student, Class[] classes, int choose1, int choose2, int choose3) throws SQLException {

        String updateSQL = "UPDATE student "
                + "SET course1 = ?, course2 = ?, course3 = ? "
                + "WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        String studentName = student.getName();
        String class1 = classes[choose1 - 1].getCourceName();
        String class2 = classes[choose2 - 1].getCourceName();
        String class3 = classes[choose3 - 1].getCourceName();

        //如果将该生插入课程的过程中,sql异常，说明该生已选课，直接返回。
        if(!insertIntoCourseTable(connection, studentName, class1, classes[choose1 - 1].getEduClassId()) ||
                !insertIntoCourseTable(connection, studentName, class2, classes[choose2 - 1].getEduClassId()) ||
                !insertIntoCourseTable(connection, studentName, class3, classes[choose3 - 1].getEduClassId())){
            return false;
        }

        //先将学生插入到每个课程，再更新学生信息。
        preparedStatement.setString(1, class1);
        preparedStatement.setString(2, class2);
        preparedStatement.setString(3, class3);
        preparedStatement.setString(4, studentName);
        preparedStatement.executeUpdate();
        return true;
    }

    /**
     * 查找教师所在教学班信息
     * @param name 教师姓名
     * @return 教学班信息字符串数组
     * @throws SQLException sql异常
     */
    public String[] findTeacherCourse(String name) throws SQLException {
        String[] result = new String[5];
        String sql = "SELECT * FROM stu_system.class_info WHERE class_info.teacher_name = ?";  // 动态拼接表名
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);  // （教师姓名）
        // 执行查询
        ResultSet resultSet = preparedStatement.executeQuery();
        // 处理查询结果
        if (resultSet.next()) {
            result[0] = resultSet.getString("course_name");
            result[1] = resultSet.getString("teacher_name");
            result[2] = resultSet.getString("count");
            result[3] = resultSet.getString("term");
            result[4] = resultSet.getString("class");
        }
        // 关闭资源
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    /**
     * 计算每项成绩的平均数
     * @param courseName 课程名
     * @param classNum 教学班号
     * @return 成绩数组
     * @throws SQLException sql异常
     */
    public double[] culculateScore(String courseName, String classNum) throws SQLException {
        double[] result = new double[5];
        String sql = "SELECT class, " +
                "AVG(grade1) AS avg_grade1, " +
                "AVG(grade2) AS avg_grade2, " +
                "AVG(grade3) AS avg_grade3, " +
                "AVG(grade4) AS avg_grade4 " +  //计算每个成绩的平均
                "FROM " + courseName + " " + // 表名不能作为PreparedStatement的参数，直接拼接
                "WHERE class = ? " +  // 使用占位符传递 classNum
                "GROUP BY class";   //按照class分组

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 设置查询中的参数
        preparedStatement.setString(1, classNum);  // 设置classNum

        ResultSet resultSet = preparedStatement.executeQuery();

        // 处理查询结果
        if (resultSet.next()) {
            result[0] = resultSet.getDouble("avg_grade1");
            result[1] = resultSet.getDouble("avg_grade2");
            result[2] = resultSet.getDouble("avg_grade3");
            result[3] = resultSet.getDouble("avg_grade4");
        }
        // 关闭资源
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    /**
     * 导出为excel文件
     * @param tableName 导出表名
     * @param classNum  教学班号
     * @param excelFilePath 导出路径
     * @throws SQLException sql异常
     * @throws IOException io异常
     */
    public void exportToExcel(String tableName, String classNum, String excelFilePath) throws SQLException, IOException {
        ResultSet resultSet = null;
        try {
            // 2. 执行SQL查询，获取数据
            String sql = "SELECT * FROM " + tableName + " WHERE `class` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classNum);  // 设置classNum
            resultSet = preparedStatement.executeQuery();

            // 3. 创建一个新的 Excel 工作簿
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(tableName);

            // 定义自定义的列头名称
            String[] customHeaders = {"姓名", "平时成绩", "期中成绩", "实验成绩", "期末成绩","综合成绩"};

            // 创建 Excel 表头
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < customHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);  // 创建Excel列
                cell.setCellValue(customHeaders[i]);  // 设置自定义的列头名称
            }

            // 获取 ResultSet 的元数据，获取列数
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 6. 将数据写入 Excel
            int rowIndex = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowIndex++);
                for (int i = 1; i < columnCount; i++) {
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(resultSet.getString(i));
                }
            }

            // 7. 创建 Excel 文件
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }

            // 8. 关闭工作簿
            workbook.close();
        } finally {
            // 9. 关闭资源
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * 插入学生信息
     * @param connection 数据库连接
     * @param studentName 学生姓名
     * @param courseTable 课程名
     * @param classNumber 教学班号
     * @return 插入是否成功
     * @throws SQLException sql异常
     */
    public boolean insertIntoCourseTable(Connection connection, String studentName, String courseTable, int classNumber) throws SQLException {
        String insertCourseSQL = String.format("INSERT INTO %s (name, grade1, grade2, grade3, grade4, grade_avg, class) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)", courseTable);

        PreparedStatement coursePreparedStatement = connection.prepareStatement(insertCourseSQL);
        // 生成随机的成绩和班级
        Random random = new Random();
        int grade1 = random.nextInt(41) + 60;  // 60 到 100
        int grade2 = random.nextInt(41) + 60;
        int grade3 = random.nextInt(41) + 60;
        int grade4 = random.nextInt(41) + 60;
        float grade_avg = (float) ((grade1 + grade2 + grade3 + grade4) / 4.0);

        // 设置插入参数
        coursePreparedStatement.setString(1, studentName);
        coursePreparedStatement.setInt(2, grade1);
        coursePreparedStatement.setInt(3, grade2);
        coursePreparedStatement.setInt(4, grade3);
        coursePreparedStatement.setInt(5, grade4);
        coursePreparedStatement.setFloat(6, grade_avg);
        coursePreparedStatement.setInt(7, classNumber);

        // 执行插入，如果出错说明该课程中已经有该学生的信息，因为是以学生姓名为主键
        try{
            coursePreparedStatement.executeUpdate();
        }catch (SQLException e){
            return false;
        }
        return true;
    }

    /**
     * 数据库查找排序
     * @param courseName 课程名称
     * @param classNum 教学班号
     * @param flag 按姓名or成绩排序
     * @throws SQLException sql异常
     */
    public void sortGrade(String courseName, String classNum, String flag) throws SQLException {
        String sql = "SELECT * " +
                "FROM " + courseName + " " + // 表名不能作为PreparedStatement的参数，直接拼接
                "WHERE class = ? " +  // 使用占位符传递 classNum
                "ORDER BY " + flag;   //排序规则
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 设置查询中的参数
        preparedStatement.setString(1, classNum);  // 设置classNum
        //flag == 1 按名字排序，flag == 2 按成绩排序
        ResultSet resultSet = preparedStatement.executeQuery();

        // 处理查询结果
        while (resultSet.next()) {
            System.out.printf("%-18s %-13s %-13s %-13s %-13s %-13s%n",
                    resultSet.getString("name"), resultSet.getString("grade1"), resultSet.getString("grade2"),
                    resultSet.getString("grade3"), resultSet.getString("grade4"), resultSet.getString("grade_avg"));
        }
        // 关闭资源
        resultSet.close();
        preparedStatement.close();
    }

//    public static void main(String[] args) {
//        // JDBC URL, 用户名和密码（根据你的数据库设置）
//        String url = "jdbc:mysql://localhost:3306/stu_system";  // 数据库URL
//        String user = "root";  // 数据库用户名
//        String password = "20040421a";  // 数据库密码
//
//        try {
//            // 连接数据库
//            Connection connection = DriverManager.getConnection(url, user, password);
//
//            // 查询所有学生的成绩
//            String selectQuery = "SELECT name, grade1, grade2, grade3, grade4 FROM structure WHERE name LIKE 'student%'";
//            PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
//            ResultSet rs = selectStmt.executeQuery();
//
//            // 更新每个学生的成绩平均值
//            String updateQuery = "UPDATE structure SET grade_avg = ? WHERE name = ?";
//            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
//
//            while (rs.next()) {
//                String name = rs.getString("name");
//                int grade1 = rs.getInt("grade1");
//                int grade2 = rs.getInt("grade2");
//                int grade3 = rs.getInt("grade3");
//                int grade4 = rs.getInt("grade4");
//
//                // 计算平均成绩
//                double average = (grade1 + grade2 + grade3 + grade4) / 4.0;
//
//                // 更新数据库中的 grade_avg
//                updateStmt.setDouble(1, average);
//                updateStmt.setString(2, name);
//                updateStmt.executeUpdate();
//
//                System.out.println("Updated average for " + name + ": " + average);
//            }
//
//            // 关闭连接
//            updateStmt.close();
//            selectStmt.close();
//            connection.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}


