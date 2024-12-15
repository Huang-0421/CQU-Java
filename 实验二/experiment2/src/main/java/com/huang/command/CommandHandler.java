package com.huang.command;

import com.huang.core.FileManager;
import com.huang.exception.MyException;
import com.huang.utils.FileEncryptionUtil;
import com.huang.utils.Info;
import com.huang.utils.ZipUtil;

import java.io.IOException;
import java.util.List;


/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class CommandHandler {
    private final CommandTree commandTree;//语法树对象
    private FileManager folderOperator;//文件操作对象


    /**
     * 初始化语法树对象和文件操作对象
     * @param commandTree 语法树对象
     */
    public CommandHandler(CommandTree commandTree) {
        folderOperator = FileManager.getInstance();
        this.commandTree = commandTree;
    }

    /**
     * 语法解析方法
     * @param commands 命令数组
     * @param info 当前路径信息
     * @return 循环控制
     * @throws MyException 错误信息
     */
    public boolean parseCommand(String[] commands, Info info) throws MyException {
        CommandNode currentNode = commandTree.getRoot();
        for (int i = 0; i < commands.length; i++) {
            String command = commands[i];
            CommandNode childNode = commandTree.findChildNode(currentNode, command);//匹配这个命令的节点
            if (childNode == null) {
                //如果没有匹配，说明这个命令不是关键词，可能是 1.参数 2.错误命令
                //1.参数：遍历当前节点的所有子节点，找到为叶子节点的那个操作关键词。
                //再将当前命令作为参数
                List<CommandNode> children = currentNode.getChildren();
                for (CommandNode child : children) {
                    String operation = child.getName();
                    switch (operation) {
                        case "setPath":
                            folderOperator.setPath(command, info);
                            break;
                        case "intoNext":
                            folderOperator.intoNext(info, command);
                            break;
                        case "createFile":
                            try {
                                folderOperator.createFile(info, command);
                            } catch (IOException e) {
                                throw new MyException(e.getMessage());
                            }
                            break;
                        case "removeFile":
                            folderOperator.removeFile(info, command);
                            break;
                        case "showFile":
                            folderOperator.showFile(info, command);
                            break;
                        case "copyFile":
                            //判断是否输入目标目录
                            if (i == commands.length - 1) {
                                throw new MyException("请输入目标路径");
                            }
                            //将当前目录下的文件拷贝到其他目录下
                            folderOperator.copyFile(info, command, commands[i + 1]);
                            break;
                        case "copyFolder":
                            if (i == commands.length - 1) {
                                throw new MyException("请输入目标路径");
                            }
                            folderOperator.copyFolder(info, command, commands[i + 1], false);
                            break;
                        case "copyFolderBack":
                            if (commands.length != 4) {
                                throw new MyException("请输入完整参数");
                            }
                            folderOperator.copyFolder(info, command, commands[i + 1], true);
                            break;
                        case "encFile":
                            //enc filename target code
                            //判断是否输入目标目录
                            if (commands.length != 4) {
                                throw new MyException("请输入完整参数");
                            }
                            //将当前目录下的文件加密
                            FileEncryptionUtil.encryptFile(info, commands[3], command, commands[2]);
                            break;
                        case "decFile":
                            //dec filename code
                            if (commands.length != 3) {
                                throw new MyException("请输入完整参数");
                            }
                            //将当前目录下的文件解密
                            FileEncryptionUtil.decryptFile(info, commands[2], command);
                            break;
                        case "zipFile":
                            //zip filename
                            ZipUtil.choosZip(info, command);
                            break;
                        case "unzipFile":
                            //zip filename
                            //unzip filename target
                            if(commands.length == 3){
                                ZipUtil.decompressFile(info, command, commands[2]);
                            }
                            else if(commands.length == 2){
                                ZipUtil.decompressFile(info, command);
                            }
                            break;
                        case "-c":
                            if (commands[i + 1].equals("-e")) {
                                continue;
                            }
                            //表明用户没有输入文本
                            if (i == commands.length - 2) {
                                folderOperator.alterCover(info, command, "");
                            } else {
                                folderOperator.alterCover(info, command, commands[i + 2]);
                            }
                            break;
                        case "-e":
                            if (commands[i + 1].equals("-c")) {
                                continue;
                            }
                            if (i == commands.length - 2) {
                                folderOperator.alterEnd(info, command, "");
                            } else {
                                folderOperator.alterEnd(info, command, commands[i + 2]);
                            }
                            break;
                        default://如果没有匹配，并且子节点中没有叶子节点，说明是参数错误
                            throw new MyException("未知语法");
                    }
                    return true;
                }
            } else {
                // 继续检查下一个层级
                currentNode = childNode;
            }
        }
        //如果currentNode是叶子节点，直接执行该操作。
        switch (currentNode.getName()) {
            case "..":
                try {
                    folderOperator.returnParent(info);
                } catch (Exception e) {
                    throw new MyException("没有上一级");
                }
                break;
            case "help":
                    folderOperator.printHelp();
                    break;
            case "ls":
                try {
                    folderOperator.folderList(info);
                } catch (MyException e) {
                    throw new MyException("读取错误");
                }
                break;
            case "-s":
                folderOperator.folderListBySize(info);
                break;
            case "-t":
                folderOperator.folderListByTime(info);
                break;
            case "exit":
                return false;
        }
        return true;
    }
}
