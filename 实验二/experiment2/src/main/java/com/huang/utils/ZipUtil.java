package com.huang.utils;

import com.huang.exception.MyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
public class ZipUtil {
    /**
     * 选择压缩类型
     * @param info 当前路径
     * @param fileName 文件名
     * @throws MyException 异常
     */
    public static void choosZip(Info info, String fileName) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());//当前路径
        Path sourceFile = currentPath.resolve(fileName);//当前路径下文件的绝对路径

        if (Files.exists(sourceFile) && Files.isDirectory(sourceFile)) {
            compressDirectory(sourceFile);
        }
        else {
            compressFile(sourceFile);
        }
    }

    /**
     * 压缩文件
     * @param sourceFile 源路径
     * @throws MyException 异常
     */
    public static void compressFile(Path sourceFile) throws MyException {

        String source_str = sourceFile.toString();
        // 判断 fileName 是否为当前目录下的文件
        if (!Files.exists(sourceFile) || !Files.isRegularFile(sourceFile)) {
            throw new MyException("文件不存在");
        }

        // 生成 ZIP 文件路径（如果没有扩展名则加 ".zip"）
        String zipFile;
        if (source_str.contains(".")) {
            zipFile = source_str.substring(0, source_str.lastIndexOf('.')) + ".zip";
        } else {
            zipFile = source_str + ".zip";
        }

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos);//创建zip输入流
             FileInputStream fis = new FileInputStream(source_str)) {
            ZipEntry zipEntry = new ZipEntry(new File(source_str).getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
        }catch (IOException e){
            throw new MyException("压缩失败");
        }
    }

    /**
     * 压缩目录
     * @param sourceDir 源路径
     * @throws MyException 异常
     */
    public static void compressDirectory(Path sourceDir) throws MyException {
        String sourceStr = sourceDir.toString();

        // 生成 ZIP 文件路径
        String zipFile = sourceStr + ".zip";

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            // 调用递归方法，添加文件夹中的所有文件
            addFolderToZip(sourceDir, sourceDir, zos);

        } catch (IOException e) {
            throw new MyException("压缩失败: " + e.getMessage());
        }
    }

    /**
     * 递归方法，用于将文件夹中的所有文件和子文件夹添加到 ZIP
     * @param rootDir 根目录
     * @param sourceDir 源路径
     * @param zos 压缩流对象
     * @throws IOException 异常
     */
    private static void addFolderToZip(Path rootDir, Path sourceDir, ZipOutputStream zos) throws IOException {
        File[] files = sourceDir.toFile().listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            Path filePath = file.toPath();
            String zipEntryName = rootDir.relativize(filePath).toString(); // 获取相对路径

            if (file.isDirectory()) {
                // 如果是文件夹，则递归添加
                addFolderToZip(rootDir, filePath, zos);
            } else {
                // 如果是文件，则写入 ZIP
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(zipEntryName);
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }
    }

    /**
     * 解压缩到。。。
     * @param info 当前路径
     * @param fileName 文件名
     * @param outputDir 输出路径
     * @throws MyException 异常
     */
    public static void decompressFile(Info info, String fileName, String outputDir) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath()); // 当前路径
        Path sourceFile = currentPath.resolve(fileName); // 当前路径下 ZIP 文件的绝对路径
        String zipFile = sourceFile.toString();
        // 判断 fileName 是否为当前目录下的文件
        if (!Files.exists(sourceFile) || !Files.isRegularFile(sourceFile)) {
            throw new MyException("文件不存在");
        }
        // 生成解压根目录（将 fileName 去掉扩展名作为子文件夹）
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        File dir = new File(outputDir, fileName);
        if (!dir.exists()) dir.mkdirs();

        try (FileInputStream fis = new FileInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File outFile = new File(dir, zipEntry.getName()); // 将解压文件放在新的子文件夹下

                // 确保解压文件的父目录存在
                if (zipEntry.isDirectory()) {
                    outFile.mkdirs(); // 如果条目是目录，则创建目录
                } else {
                    outFile.getParentFile().mkdirs(); // 创建文件的父目录

                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zis.closeEntry();
            }
        }catch (IOException e){
            throw new MyException("解压失败");
        }
    }

    /**
     * 解压到当前文件夹
     * @param info 当前路径
     * @param fileName 文件名
     * @throws MyException 异常
     */
    public static void decompressFile(Info info, String fileName) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath()); // 当前路径
        Path sourceFile = currentPath.resolve(fileName); // 当前路径下 ZIP 文件的绝对路径

        // 判断 fileName 是否为当前目录下的文件
        if (!Files.exists(sourceFile) || !Files.isRegularFile(sourceFile)) {
            throw new MyException("文件不存在");
        }

        // 去掉 .zip 后缀生成解压文件夹名称
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        File dir = new File(currentPath.toFile(), fileName); // 在当前目录创建同名文件夹
        if (!dir.exists()) dir.mkdirs();

        try (FileInputStream fis = new FileInputStream(sourceFile.toString());
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File outFile = new File(dir, zipEntry.getName()); // 解压到新的子文件夹下

                // 确保解压文件的父目录存在
                if (zipEntry.isDirectory()) {
                    outFile.mkdirs(); // 如果条目是目录，则创建目录
                } else {
                    outFile.getParentFile().mkdirs(); // 创建文件的父目录

                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            throw new MyException("解压失败");
        }
    }
}
