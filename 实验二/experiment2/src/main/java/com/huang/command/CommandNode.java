package com.huang.command;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class CommandNode {
    private String name;//关键词
    private List<CommandNode> children;//子节点

    /**
     * 初始化节点
     * @param name 当前节点名
     */
    public CommandNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<CommandNode> getChildren() {
        return children;
    }

    /**
     * 为节点添加子节点
     * @param child 子节点
     */
    public void addChild(CommandNode child) {
        children.add(child);
    }
}

