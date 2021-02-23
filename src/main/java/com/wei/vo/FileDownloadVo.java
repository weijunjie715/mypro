package com.wei.vo;

/**
 * @ClassName FileDownloadVo
 * @Description :
 * @Author weijunjie
 * @Date 2020/11/20 15:49
 */
public class FileDownloadVo {
    private String fileName;

    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileDownloadVo() {
    }

    public FileDownloadVo(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
