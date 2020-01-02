package com.ivlai.note.utils.bean;

/**
 * 文件信息类
 */
public class FileInfo {

    private String fileName;
    private String fileUrl;
    private String fileAddress;
    private Boolean saveStatus;
    private Boolean imgFile;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public Boolean getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Boolean saveStatus) {
        this.saveStatus = saveStatus;
    }

    public Boolean getImgFile() {
        return imgFile;
    }

    public void setImgFile(Boolean imgFile) {
        this.imgFile = imgFile;
    }
}
