package com.huang.utils;

import com.huang.command.CommandInitializer;
import com.huang.command.CommandTree;
import com.huang.executor.CommandExecutor;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class ViewTool {
    /**
     * 初始化执行器，语法树，获取输入
     * @throws IOException io异常
     */
    public void initialize() throws IOException {
        //创建命令执行器
        CommandExecutor executor = new CommandExecutor();
        //初始化语法树
        CommandInitializer initializer = new CommandInitializer();
        CommandTree commandTree = initializer.initial();
        //创建输入
        Scanner scanner = new Scanner(System.in);
        //默认为当前文件夹
        Info info = new Info();
        String currentPath = executor.getCurrentPath();
        info.setCurrentPath(currentPath);
        mainPage(executor, scanner, commandTree, info);
    }

    /**
     * 打印主页面，循环获取输入
     * @param executor 执行器
     * @param scanner 输入
     * @param commandTree 语法树
     * @param info 当前路径
     */
    public void mainPage(CommandExecutor executor, Scanner scanner, CommandTree commandTree, Info info) {
        boolean flag = true;
        while(flag){
            String currentPath = info.getCurrentPath();
            printPath(currentPath);
            String userInput = scanner.nextLine();
            String[] words = userInput.split("\\s+");//将用户输入按照空格分隔
            flag = executor.execute(words,commandTree,info);
        }
    }

    /**
     * 路径显示格式
     * @param currentPath 当前路径
     */
    public void printPath(String currentPath){
        System.out.print(currentPath + "> ");
    }
}