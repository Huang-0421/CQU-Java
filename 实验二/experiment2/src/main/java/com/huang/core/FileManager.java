package com.huang.core;

import com.huang.exception.MyException;
import com.huang.utils.CopyBackUtil;
import com.huang.utils.Info;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class FileManager {
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static FileManager instance;//文件操作对象实例
    private final ExecutorService executorService;//线程池对象

    /**
     * 构造函数初始化线程池
     */
    public FileManager(){
        this.executorService = Executors.newFixedThreadPool(3);
    }

    /**
     * 获取FileManager实例，确保只有一个实例
     * @return
     */
    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    /**
     * 进入目录
     * @param path 路径参数
     * @param info 当前路径
     * @throws MyException 抛出异常
     */
    public void setPath(String path, Info info) throws MyException {
        try {
            Path path1 = Paths.get(path); // 尝试解析路径
            if (!Files.exists(path1)) {//如文件夹不存在，则创建新文件夹
                Files.createDirectories(path1);
            }
            info.setCurrentPath(path);
        } catch (InvalidPathException e) {
            throw new MyException("路径格式错误");
        } catch (IOException e) {
            throw new MyException("无法创建文件夹");
        }
    }

    /**
     * 进入子目录
     * @param info 当前路径
     * @param fileName 子目录名
     * @throws MyException 抛出异常
     */
    public void intoNext(Info info, String fileName) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        Path targetPath = currentPath.resolve(fileName);
        if (Files.exists(targetPath)) {
            if (Files.isDirectory(targetPath)) {
                // 执行您需要的其他操作
                String path = targetPath.toString();
                setPath(path, info);
            } else {
                throw new MyException("无法进入文件");
            }
        } else {
            throw new MyException("没有此目录");
        }
    }

    /**
     * 返回上一级
     * @param info 当前路径
     */
    public void returnParent(Info info) {
        Path currentPath = Paths.get(info.getCurrentPath());
        Path parentDir = currentPath.getParent();
        info.setCurrentPath(parentDir.toString());
    }

    /**
     * 子目录列表
     * @param info 当前路径
     * @throws MyException 抛出异常
     */
    public void folderList(Info info) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        Stream<Path> paths = null;
        try {
            paths = Files.list(currentPath);
            // 打印表头
            System.out.printf("%-10s %-15s %-19s %-10s%n", "文件名", "大小(字节)", "类型", "最后修改时间");
            // 遍历文件并打印信息
            paths.forEach(this::accept);

        } catch (IOException e) {
            throw new MyException("获取文件列表失败");
        } finally {
            // 确保在使用完后关闭流
            if (paths != null) {
                paths.close();
            }
        }
    }

    /**
     * 子目录列表按照大小排序
     * @param info 当前路径
     * @throws MyException 抛出异常
     */
    public void folderListBySize(Info info) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        // 打印表头
        System.out.printf("%-10s %-15s %-19s %-10s%n", "文件名", "大小(字节)", "类型", "最后修改时间");
        try (Stream<Path> paths = Files.list(currentPath)) {
            paths
                    .sorted((p1, p2) -> {
                        try {
                            return Long.compare(Files.size(p1), Files.size(p2)); // 按文件大小排序
                        } catch (IOException e) {
                            throw new RuntimeException("排序错误", e);
                        }
                    })
                    .forEach(this::accept);
        } catch (IOException e) {
            throw new MyException("列举错误");
        }
    }

    /**
     * 子目录列表按照时间排序
     * @param info 当前路径
     * @throws MyException 抛出异常
     */
    public void folderListByTime(Info info) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
    // 打印表头
        System.out.printf("%-10s %-15s %-19s %-10s%n", "文件名", "大小(字节)", "类型", "最后修改时间");
        try (Stream<Path> paths = Files.list(currentPath)) {
            paths
                    .sorted((p1, p2) -> {
                        try {
                            FileTime time1 = Files.getLastModifiedTime(p1);
                            FileTime time2 = Files.getLastModifiedTime(p2);
                            return time2.compareTo(time1); // 按最后修改时间排序
                        } catch (IOException e) {
                            throw new RuntimeException("排序错误", e);
                        }
                    })
                    .forEach(this::accept);
        } catch (IOException e) {
            throw new MyException("列举错误");
        }
    }

    /**
     * 新建文件
     * @param info 当前路径
     * @param fileName 文件名
     * @throws IOException IO异常
     * @throws MyException 自定义异常
     */
    public void createFile(Info info, String fileName) throws IOException, MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        Path filePath = currentPath.resolve(fileName); // 使用 resolve 拼接路径
        if (Files.exists(filePath)) {
            throw new MyException(fileName + "已存在");
        } else {
            Files.createFile(filePath); // 创建文件
        }
    }

    /**
     * 删除文件
     * @param info 当前路径
     * @param fileName 文件名
     * @throws MyException 抛出异常
     */
    public void removeFile(Info info, String fileName) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        Path filePath = currentPath.resolve(fileName);  // 组合路径

        try {
            // 检查文件是否存在
            if (Files.notExists(filePath)) {
                throw new MyException("文件或目录不存在");
            }

            // 如果是目录，递归删除其内容
            if (Files.isDirectory(filePath)) {
                deleteDirectoryRecursively(filePath);
            } else {
                // 如果是文件，直接删除
                Files.delete(filePath);
            }
        } catch (IOException e) {
            throw new MyException("无法删除文件或目录");
        }
    }

    /**
     * 递归删除目录及其内容
     * @param directory 目录名
     * @throws IOException IO异常
     */
    private void deleteDirectoryRecursively(Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);  // 删除文件
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);  // 删除空目录
                    return FileVisitResult.CONTINUE;
                } else {
                    throw exc;  // 如果出现异常，抛出以便上层捕获
                }
            }
        });
    }

    /**
     * 查看文件
     * @param info 当前路径
     * @param fileName 文件名
     * @throws MyException 抛出异常
     */
    public void showFile(Info info, String fileName) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        Path filePath = currentPath.resolve(fileName);

        // 检查文件是否存在
        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            throw new MyException("文件不存在");
        }

        try {
            // 使用 Files.readAllLines 读取文件的所有行
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new MyException("文件已加密");
        }
    }

    /**
     * 覆盖文件内容
     * @param info 当前路径
     * @param fileName 文件名
     * @param content 输入内容
     * @throws MyException 异常
     */
    public void alterCover(Info info, String fileName, String content) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        Path filePath = currentPath.resolve(fileName);
        // 检查文件是否存在
        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            throw new MyException("文件不存在");
        }
        try {
            Files.write(filePath, content.getBytes(StandardCharsets.UTF_8)); // 使用 UTF-8 编码
        } catch (IOException e) {
            throw new MyException("写入错误");
        }
    }

    /**
     * 从末尾修改
     * @param info 当前路径
     * @param fileName 文件内容
     * @param content 输入内容
     * @throws MyException 抛出异常
     */
    public void alterEnd(Info info, String fileName, String content) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        Path filePath = currentPath.resolve(fileName);
        // 检查文件是否存在
        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            throw new MyException("文件不存在");
        }
        // true 表示在文件末尾追加内容
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile(), true)) {
            // 写入文件
            fileOutputStream.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new MyException("未找到文件");
        } catch (IOException e) {
            throw new MyException("写入错误");
        }
    }

    /**
     * 拷贝文件
     * @param info 当前路径
     * @param fileName 文件名
     * @param targetpath 目标路径
     * @throws MyException 异常
     */
    public void copyFile(Info info, String fileName, String targetpath) throws MyException {
        // targetpath可能是目录也可能文件
        Path currentPath = Paths.get(info.getCurrentPath());
        Path sourceFile = currentPath.resolve(fileName);
        Path targetDir;
        // 判断目标路径是否合法
        try {
            targetDir = Paths.get(targetpath);
        } catch (InvalidPathException e) {
            throw new MyException("目标路径不合法");
        }

        // 判断 fileName 是否为当前目录下的文件
        if (!Files.exists(sourceFile) || !Files.isRegularFile(sourceFile)) {
            throw new MyException("文件不存在或不是一个有效文件");
        }
        Path targetFile;
        // 如果目标路径是目录就创建一个同名文件
        if (Files.exists(targetDir) && Files.isDirectory(targetDir)) {
            // 生成目标文件路径，保持文件名不变
            targetFile = targetDir.resolve(fileName);
        } else {
            targetFile = targetDir;
        }
        // 执行文件拷贝
        try {
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new MyException("拷贝失败");
        }
    }

    /**
     * 拷贝目录
     * @param info 当前路径
     * @param folderName 文件名
     * @param targetPath 目标路径
     * @param flag 是否后台拷贝
     * @throws MyException 异常
     */
    public void copyFolder(Info info, String folderName, String targetPath, boolean flag) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());
        Path sourceDir = currentPath.resolve(folderName); // 源目录的完整路径
        if (!Files.exists(sourceDir)) {
            throw new MyException("不存在此目录");
        }
        Path destinationDir = Paths.get(targetPath).resolve(folderName); // 目标完整路径

        // 获取文件夹的总大小用于计算进度
        long totalBytes = calculateFolderSize(sourceDir);
        AtomicLong copiedBytes = new AtomicLong(0); // 记录已拷贝的文件大小

        if (totalBytes == 0) {
            throw new MyException("文件夹为空");
        }

        if (flag) {
            executorService.submit(new CopyBackUtil(currentPath, sourceDir, destinationDir));
        } else {
            long startTime = System.nanoTime();
            try {
                Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        // 将指定目录下的所有目录路径，转化为目标目录下的路径
                        Path targetSubDir = destinationDir.resolve(sourceDir.relativize(dir));
                        Files.createDirectories(targetSubDir); // 根据目标路径生成目录
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        // 将指定目录下的所有文件路径，转化为目标目录下的路径
                        Path targetFile = destinationDir.resolve(sourceDir.relativize(file));
                        Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING); // 拷贝文件并覆盖

                        // 更新已复制的字节数
                        copiedBytes.addAndGet(attrs.size());

                        // 更新进度条
                        printProgress(copiedBytes.get(), totalBytes);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                throw new MyException("拷贝错误");
            }

            // 计算并显示总耗时
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000; // 转换为毫秒
            System.out.println("总耗时：" + duration + " ms.");
        }
    }

    /**
     * 计算目录大小
     * @param folder 目录
     * @return 目录大小
     * @throws MyException 异常
     */
    private long calculateFolderSize(Path folder) throws MyException {
        final AtomicLong size = new AtomicLong(0);
        try {
            // 遍历文件计算大小
            Files.walkFileTree(folder, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    size.addAndGet(attrs.size()); // 将属性中的大小加到size
                    return FileVisitResult.CONTINUE; // 继续遍历文件
                }
            });
        } catch (IOException e) {
            throw new MyException("计算文件大小出错");
        }
        return size.get();
    }

    /**
     * 打印进度
     * @param current 已执行大小
     * @param total 总大小
     */
    private void printProgress(long current, long total) {
        int progressWidth = 50; // 进度条宽度
        double percentage = Math.min((double) current / total * 100, 100.0); // 确保百分比不超过100%

        // 计算进度条宽度的填充部分
        int progress = (int) (percentage / 100 * progressWidth);

        StringBuilder bar = new StringBuilder();
        bar.append("\r["); // \r用于回到行首
        for (int i = 0; i < progressWidth; i++) {
            if (i < progress) {
                bar.append("=");
            } else {
                bar.append(" ");
            }
        }
        bar.append("] ").append(String.format("%.2f", percentage)).append("%");

        // 输出进度条
        System.out.print(bar.toString());

        if (current >= total) {
            System.out.println(); // 完成后换行
        }
    }

    /**
     * 打印格式
     * @param path 打印路径
     */
    private void accept(Path path) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            String fileName = path.getFileName().toString();
            String size;

            // 根据类型获取大小
            if (Files.isDirectory(path)) {
                size = String.valueOf(calculateFolderSize(path)); // 文件夹大小
            } else {
                size = String.valueOf(attrs.size()); // 文件大小
            }

            String lastModified = dateFormat.format(attrs.lastModifiedTime().toMillis());
            String type = Files.isDirectory(path) ? "目录" : "文件";

            // 打印每行文件信息
            System.out.printf(GREEN + "%-12s %-17s %-15s %-10s%n" + RESET, fileName, size, type, lastModified);
        } catch (IOException e) {
            System.err.println("获取文件属性出错: " + path.getFileName()); // 打印错误信息而不中断
        } catch (MyException e) {
            System.err.println("计算文件夹大小出错: " + path.getFileName());
        }
    }

    /**
     * 帮助文档
     */
    public void printHelp() {
        System.out.println("cd dir                              进入目录");
        System.out.println("cd -n dir                           进入下一级目录");
        System.out.println("cd ..                               返回向上一级目录");
        System.out.println("ls                                  浏览子目录");
        System.out.println("ls -s                               按文件大小排序");
        System.out.println("ls -t                               按修改时间排序");
        System.out.println("touch file                          新增文件");
        System.out.println("rm file                             删除文件（必须在当前目录下）");
        System.out.println("cat file                            查看文件（必须在当前目录下）");
        System.out.println("alter file -c xxxx                  覆盖修改文件（必须在当前目录下）");
        System.out.println("alter file -e xxxx                  从末尾修改文件（必须在当前目录下）");
        System.out.println("cp file target                      文件拷贝");
        System.out.println("cp -r folder target                 文件夹拷贝");
        System.out.println("enc file target code                文件加密");
        System.out.println("dec source1 code                    文件加密");
        System.out.println("zip filename                        文件压缩");
        System.out.println("unzip filename target               解压缩到");
        System.out.println("exit                                退出");
    }

}
