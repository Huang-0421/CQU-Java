package com.huang.command;

import java.util.ArrayList;
import java.util.List;

public class CommandInitializer {
    /**
     * 初始化语法树
     * @return 语法树
     */
    public CommandTree initial() {
        // 定义命令路径列表，模拟动态加载的命令
        List<String[]> commandPaths = new ArrayList<>();
        commandPaths.add(new String[]{"cd", "setPath"});
        commandPaths.add(new String[]{"cd", "..", "returnParent"});
        commandPaths.add(new String[]{"cd", "-n", "intoNext"});
        commandPaths.add(new String[]{"ls", "folderList"});
        commandPaths.add(new String[]{"ls", "-s","folderListBySize"});
        commandPaths.add(new String[]{"ls", "-t","folderListBySize"});
        commandPaths.add(new String[]{"touch", "createFile"});
        commandPaths.add(new String[]{"exit", "exit"});
        commandPaths.add(new String[]{"rm", "removeFile"});
        commandPaths.add(new String[]{"cat", "showFile"});
        commandPaths.add(new String[]{"alter", "-c", "alterCover"});
        commandPaths.add(new String[]{"alter", "-e", "alterEnd"});
        commandPaths.add(new String[]{"cp", "copyFile"});
        commandPaths.add(new String[]{"cp", "-r", "copyFolder"});
        commandPaths.add(new String[]{"cp", "-b", "copyFolderBack"});
        commandPaths.add(new String[]{"enc", "encFile"});
        commandPaths.add(new String[]{"dec", "decFile"});
        commandPaths.add(new String[]{"zip", "zipFile"});
        commandPaths.add(new String[]{"unzip", "unzipFile"});
        commandPaths.add(new String[]{"help", "printHelp"});
        // 初始化命令树并添加命令
        CommandTree commandTree = new CommandTree();

        for (String[] commandPath : commandPaths) {
            commandTree.addCommand(commandPath); // 根据路径列表动态构建树
        }
        return commandTree;
    }
}
