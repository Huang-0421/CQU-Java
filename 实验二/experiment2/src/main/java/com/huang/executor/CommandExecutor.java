package com.huang.executor;

import com.huang.command.CommandHandler;
import com.huang.command.CommandTree;
import com.huang.exception.MyException;
import com.huang.utils.Info;

import java.io.File;
import java.io.IOException;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class CommandExecutor {

    /**
     * 获取当前路径
     * @return 当前路径
     * @throws IOException 异常
     */
    public String getCurrentPath() throws IOException {
        File currentDirectory = new File(".");
        return currentDirectory.getCanonicalPath(); // 包含了最后的 "."
    }

    /**
     * 执行解析方法
     * @param commands 命令
     * @param commandTree 语法树
     * @param info 当前路径
     * @return 循环控制
     */
    public boolean execute(String[] commands, CommandTree commandTree, Info info){
        //交给语法树处理
        CommandHandler commandHandler = new CommandHandler(commandTree);
        boolean flag = true;
        try{
            flag = commandHandler.parseCommand(commands, info);
        }catch (MyException e){
            e.printStackTrace();
        }
        return flag;
    }
}
