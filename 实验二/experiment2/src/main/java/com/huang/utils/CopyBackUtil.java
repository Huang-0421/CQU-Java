package com.huang.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Huang_ruijie
 * @version 1.1
 */
public class CopyBackUtil implements Runnable {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private Path sourceDir;//源路径
    private Path destinationDir;//目的路径
    private Path currentPath;//当前路径

    public CopyBackUtil(Path currentPath, Path sourceDir, Path destinationDir) {
        this.currentPath = currentPath;
        this.sourceDir = sourceDir;
        this.destinationDir = destinationDir;
    }

    /**
     * 重写run方法，新建线程
     */
    @Override
    public void run() {
        try {
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetSubDir = destinationDir.resolve(sourceDir.relativize(dir));
                    Files.createDirectories(targetSubDir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path targetFile = destinationDir.resolve(sourceDir.relativize(file));
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
            Thread.sleep(5000);
            System.out.println();
            System.out.println("后台拷贝完成");
            System.out.print(currentPath + "> ");
        } catch (IOException e) {
            System.out.println(RED + "后台拷贝出错" + RESET);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            throw new RuntimeException(e);
        }
    }
}
