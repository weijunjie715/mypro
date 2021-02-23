package com.wei.util;


import java.io.*;
import java.util.UUID;

/**
 * 文件操作工具类
 */
@SuppressWarnings("all")
public class FileUtils {

    /**
     * 写入文件
     *
     * @param target
     * @param src
     * @throws IOException
     */
    public static void write(String target, String fileName, InputStream src) throws IOException {
        File tempFile = new File(target);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        OutputStream os = new FileOutputStream(target + fileName);
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            os.write(buf, 0, len);
        }
        os.flush();
        os.close();
    }

    /**
     * 分块写入文件
     *
     * @param target
     * @param targetSize
     * @param src
     * @param srcSize
     * @param chunks
     * @param chunk
     * @throws IOException
     */
    public static void writeWithBlok(String target, String fileName, Long targetSize, InputStream src, Long srcSize, Integer chunks, Integer chunk) throws IOException {
        File tempFile = new File(target);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(target + fileName, "rw");
        randomAccessFile.setLength(targetSize);
        if (chunk == chunks - 1) {
            randomAccessFile.seek(targetSize - srcSize);
        } else {
            randomAccessFile.seek(chunk * srcSize);
        }
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            randomAccessFile.write(buf, 0, len);
        }
        randomAccessFile.close();
    }

    /**
     * @Description 分块写入文件，返回地址
     * @Author: weijunjie
     * @Date: 2020/9/3 15:55
     * @return: java.lang.String
     **/
    public static String writeWithBlokRes(String target, String fileName, Long targetSize, InputStream src, Long srcSize, Integer chunks, Integer chunk) throws IOException {
        String fileAddress = "";
        try {
            writeWithBlok(target, fileName, targetSize, src, srcSize, chunks, chunk);
            fileAddress = target + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileAddress;

    }

    /**
     * @Description 写入文件，返回文件保存地址
     * @Author: weijunjie
     * @Date: 2020/9/3 16:11
     **/
    public static String writeRes(String target, String fileName, InputStream src) throws IOException {
        String fileAddress = "";
        try {
            write(target, fileName, src);
            fileAddress = target + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileAddress;
    }

    /**
     * 生成随机文件名
     *
     * @return
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 判断指定的文件或文件夹删除是否成功
     * @param FileName 文件或文件夹的路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean isDeleteAnyone(String FileName) {
        File file = new File(FileName);//根据指定的文件名创建File对象
        if (!file.exists()) {  //要删除的文件不存在
            System.out.println("文件" + FileName + "不存在，删除失败！");
            return false;
        } else { //要删除的文件存在
            if (file.isFile()) { //如果目标文件是文件
                return isDeleteFile(FileName);
            } else {  //如果目标文件是目录
                return deleteDir(FileName);
            }
        }
    }

    /**
     * 判断指定的文件删除是否成功
     *
     * @param FileName 文件路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean isDeleteFile(String fileName) {
        File file = new File(fileName);//根据指定的文件名创建File对象
        if (file.exists() && file.isFile()) { //要删除的文件存在且是文件
            if (file.delete()) {
                System.out.println("文件" + fileName + "删除成功！");
                return true;
            } else {
                System.out.println("文件" + fileName + "删除失败！");
                return false;
            }
        } else {
            System.out.println("文件" + fileName + "不存在，删除失败！");
            return false;
        }


    }


    /**
     * 删除指定的目录以及目录下的所有子文件
     *
     * @param dirName is 目录路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean deleteDir(String dirName) {
        if (dirName.endsWith(File.separator))//dirName不以分隔符结尾则自动添加分隔符
            dirName = dirName + File.separator;
        File file = new File(dirName);//根据指定的文件名创建File对象
        if (!file.exists() || (!file.isDirectory())) { //目录不存在或者
            System.out.println("目录删除失败" + dirName + "目录不存在！");
            return false;
        }
        File[] fileArrays = file.listFiles();//列出源文件下所有文件，包括子目录
        for (int i = 0; i < fileArrays.length; i++) {//将源文件下的所有文件逐个删除
            isDeleteAnyone(fileArrays[i].getAbsolutePath());
        }
        if (file.delete())//删除当前目录
            System.out.println("目录" + dirName + "删除成功！");
        return true;
    }
}
