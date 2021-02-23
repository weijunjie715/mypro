package com.wei.util;

import java.io.*;

/**
 * @ClassName FileUtil
 * @Description : 文件操作工具类
 * @Author weijunjie
 * @Date 2020/8/25 9:57
 */
@SuppressWarnings("all")
public class FileUtil {

    public static void getSplitFile(String file,int count){

        //预分配文件所占用的磁盘空间，在磁盘创建一个指定大小的文件，“r”表示只读，“rw”支持随机读写
        try {
            RandomAccessFile raf = new RandomAccessFile(new File(file), "r");
            //计算文件大小
            long length = raf.length();
            System.out.println(length);
            //计算文件切片后每一份文件的大小
            long maxSize = length / count;

            System.out.println(maxSize);

            long offset = 0L;//定义初始文件的偏移量(读取进度)
            //开始切割文件
            for(int i = 0; i < count - 1; i++){ //count-1最后一份文件不处理
                //标记初始化
                long fbegin = offset;
                //分割第几份文件
                long fend = (i+1) * maxSize;
                //写入文件
                offset = getWrite(file, i, fbegin, fend);

            }

            //剩余部分文件写入到最后一份(如果不能平平均分配的时候)
            if (length - offset > 0) {
                //写入文件
                getWrite(file, count-1, offset, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 指定文件每一份的边界，写入不同文件中
     * @param file 源文件
     * @param index 源文件的顺序标识
     * @param begin 开始指针的位置
     * @param end 结束指针的位置
     * @return long
     */
    public static long getWrite(String file,int index,long begin,long end){

        long endPointer = 0L;
        try {
            //申明文件切割后的文件磁盘
            RandomAccessFile in = new RandomAccessFile(new File(file), "r");
            //定义一个可读，可写的文件并且后缀名为.tmp的二进制文件
            RandomAccessFile out = new RandomAccessFile(new File(file + "_" + index + ".tmp"), "rw");

            //申明具体每一文件的字节数组
            byte[] b = new byte[1024];
            int n = 0;
            //从指定位置读取文件字节流
            in.seek(begin);
            //判断文件流读取的边界
            while(in.getFilePointer() <= end && (n = in.read(b)) != -1){
                //从指定每一份文件的范围，写入不同的文件
                out.write(b, 0, n);
            }

            //定义当前读取文件的指针
            endPointer = in.getFilePointer();

            //关闭输入流
            in.close();
            //关闭输出流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return endPointer;
    }

    /**
     * 文件合并
     * @param file 指定合并文件
     * @param tempFile 分割前的文件名
     * @param tempCount 文件个数
     */
    public static void merge(String file,String tempFile,int tempCount) {
        RandomAccessFile raf = null;
        try {
            //申明随机读取文件RandomAccessFile
            raf = new RandomAccessFile(new File(file), "rw");
            //开始合并文件，对应切片的二进制文件
            for (int i = 0; i < tempCount; i++) {
                //读取切片文件
                RandomAccessFile reader = new RandomAccessFile(new File(tempFile + "_" + i + ".tmp"), "r");
                byte[] b = new byte[1024];
                int n = 0;
                while ((n = reader.read(b)) != -1) {
                    raf.write(b, 0, n);//一边读，一边写
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        String path = "D:\\testFile\\";
        String fileName = "产品生命周期-交易类型-确认数据.xlsx";
        RandomAccessFile raf = new RandomAccessFile(new File(path+fileName), "rw");

        for (int i = 0; i < 6; i++) {
            //读取切片文件
            RandomAccessFile reader = new RandomAccessFile(new File(path+i+"_0_"+fileName ), "r");
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = reader.read(b)) != -1) {
                raf.write(b, 0, n);//一边读，一边写
            }
        }
    }

    public static boolean savePic(InputStream inputStream,String path, String fileName) {
        boolean flag = false;
        OutputStream os = null;
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
