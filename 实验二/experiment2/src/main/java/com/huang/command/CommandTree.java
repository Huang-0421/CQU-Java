package com.huang.command;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class CommandTree {

    private final CommandNode root;//根节点

    public CommandTree() {
        root = new CommandNode("root");
    }

    public CommandNode getRoot() {
        return root;
    }

    /**
     * 动态添加命令路径到树中
     * @param commandPath 命令路径
     */
    public void addCommand(String[] commandPath) {
        CommandNode currentNode = root;
        for (String command : commandPath) {
            CommandNode childNode = findChildNode(currentNode, command);
            if (childNode == null) { // 若当前层级不存在，创建新节点
                childNode = new CommandNode(command);
                currentNode.addChild(childNode);
            }
            currentNode = childNode; // 进入下一层级
        }
    }

    /**
     * 查找特定名称的子节点
     * @param parent 父节点
     * @param name 匹配名字
     * @return 匹配的子节点
     */
   public CommandNode findChildNode(CommandNode parent, String name) {
        for (CommandNode child : parent.getChildren()) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }
}

