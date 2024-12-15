package com.huang.utils;
import com.huang.exception.MyException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileEncryptionUtil {
    /**
     * 加密文件
     * @param info 当前路径
     * @param key 密钥
     * @param inputFile 输入文件
     * @param outputFile 输出文件
     * @throws MyException 异常
     */
    public static void encryptFile(Info info, String key, String inputFile, String outputFile) throws MyException {
        Path currentPath = Paths.get(info.getCurrentPath());//当前路径
        Path sourceFile = currentPath.resolve(inputFile);//当前路径下文件的绝对路径
        // 判断 fileName 是否为当前目录下的文件
        if (!Files.exists(sourceFile) || !Files.isRegularFile(sourceFile)) {
            throw new MyException("文件不存在");
        }
        String new_inputFile = sourceFile.toString();
        //判断输出路径是目录还是文件名，如果是目录就在其下创建一个同名文件
        Path outputPath = Paths.get(outputFile);
        if (Files.isDirectory(outputPath)) {
            outputPath = outputPath.resolve(inputFile);
            if (Files.exists(outputPath)) {
                throw new MyException(outputPath + "已存在");
            } else {
                try {
                    Files.createFile(outputPath); // 创建文件
                }catch (IOException e){
                    throw new MyException("创建文件失败");
                }
            }
        }
        outputFile = outputPath.toString();
        Cipher cipher;
        try {
            SecretKey secretKey = generateKey(key); // 生成密钥
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (Exception e) {
            throw new MyException("生成密钥错误");
        }

        try (FileInputStream fis = new FileInputStream(new_inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                fos.write(outputBytes);
                fos.flush();
            }
        } catch (Exception e) {
            throw new MyException("加密失败");
        }
    }

    /**
     * 解密文件
     * @param info 当前路径
     * @param key 密钥
     * @param inputFile 输入文件
     * @throws MyException 异常
     */
    public static void decryptFile(Info info, String key, String inputFile) throws MyException{
        Path currentPath = Paths.get(info.getCurrentPath());
        Path sourceFile = currentPath.resolve(inputFile);

        if (!Files.exists(sourceFile) || !Files.isRegularFile(sourceFile)) {
            throw new MyException("文件不存在");
        }

        Path tempFile = currentPath.resolve(inputFile.substring(0, inputFile.lastIndexOf('.')) + "tmp.txt");
        try {
            Files.createFile(tempFile);
        } catch (IOException e) {
            throw new MyException("创建文件失败");
        }

        inputFile = sourceFile.toString();
        Cipher cipher;
        try {
            SecretKey secretKey = generateKey(key); // 使用稳定密钥
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (Exception e) {
            throw new MyException("生成密钥错误");
        }

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(tempFile.toString())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                fos.write(outputBytes);
                fos.flush();
            }
        } catch (Exception e) {
            try {
                Files.delete(tempFile);
            } catch (IOException ex) {
                throw new MyException("删除临时文件错误");
            }
            throw new MyException("密码错误");
        }

        try {
            Files.delete(sourceFile);
            Files.move(tempFile, sourceFile);
        } catch (Exception e) {
            throw new MyException("无法重命名文件");
        }
    }

    /**
     * 生成密钥
     * @param key 密钥
     * @return 密钥对象
     */
    private static SecretKey generateKey(String key) {
        byte[] keyBytes = key.getBytes(); // 获取字节数组
        byte[] keyBytes16 = new byte[16]; // 确保为16字节的密钥
        System.arraycopy(keyBytes, 0, keyBytes16, 0, Math.min(keyBytes.length, 16));
        return new SecretKeySpec(keyBytes16, "AES"); // 使用 SecretKeySpec
    }

//    public static void main(String[] args) {
//        String key = "mySecretKey"; // 密钥
//        String inputFile = "path/to/your/input/file.txt"; // 要加密的文件路径
//        String encryptedFile = "path/to/your/encrypted/file.enc"; // 加密后文件的路径和名称
//        String decryptedFile = "path/to/your/decrypted/file.txt"; // 解密后文件的路径和名称
//
//        try {
//            // 加密文件
//            encryptFile(key, inputFile, encryptedFile);
//            System.out.println("文件加密成功，已保存为: " + encryptedFile);
//
//            // 解密文件
//            decryptFile(key, encryptedFile, decryptedFile);
//            System.out.println("文件解密成功，已保存为: " + decryptedFile);
//        } catch (Exception e) {
//            System.err.println("加密或解密过程中出错: " + e.getMessage());
//        }
//    }
}
