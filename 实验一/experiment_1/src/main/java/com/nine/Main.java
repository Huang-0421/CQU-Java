package com.nine;

import com.nine.tools.ViewTool;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Huang_ruijie
 * @version 1.0
 * @date 2024/10/13 下午3:55
 */
public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException, IOException {
        ViewTool viewTool = new ViewTool();
        viewTool.mainShow();
    }
}