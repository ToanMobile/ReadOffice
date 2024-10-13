package com.app.office.officereader.filelist;

import java.io.File;

public class FileItem implements Comparable<FileItem> {
    private File file;
    private int fileStar;
    private int iconType;
    private boolean isCheck;
    private boolean showCheckView = true;

    public FileItem(File file2, int i, int i2) {
        this.file = file2;
        this.iconType = i;
        this.fileStar = i2;
    }

    public String getFileName() {
        return getFile().getName();
    }

    public int compareTo(FileItem fileItem) {
        return getFileName().compareToIgnoreCase(fileItem.getFileName());
    }

    public File getFile() {
        return this.file;
    }

    public int getIconType() {
        return this.iconType;
    }

    public void setCheck(boolean z) {
        this.isCheck = z;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public int getFileStar() {
        return this.fileStar;
    }

    public void setFileStar(int i) {
        this.fileStar = i;
    }

    public boolean isShowCheckView() {
        return this.showCheckView;
    }

    public void setShowCheckView(boolean z) {
        this.showCheckView = z;
    }

    public void dispose() {
        this.file = null;
    }
}
