package com.app.office.officereader.filelist;

import com.app.office.constant.MainConstant;
import java.io.File;
import java.util.Comparator;

public class FileSort implements Comparator<FileItem> {
    public static final int FILESORT_ASCENDING = 0;
    public static final int FILESORT_TYPE_DESCENDING = 1;
    public static final int FILESORT_TYPE_DIR = 0;
    public static final int FILESORT_TYPE_MODIFIED_DATE = 1;
    public static final int FILESORT_TYPE_NAME = 3;
    public static final int FILESORT_TYPE_NULL = -1;
    public static final int FILESORT_TYPE_SIZE = 2;
    public static final int FILE_TYPE_DOC = 0;
    public static final int FILE_TYPE_DOCX = 1;
    public static final int FILE_TYPE_PDF = 7;
    public static final int FILE_TYPE_PPT = 4;
    public static final int FILE_TYPE_PPTX = 5;
    public static final int FILE_TYPE_TXT = 6;
    public static final int FILE_TYPE_XLS = 2;
    public static final int FILE_TYPE_XLSX = 3;
    private static FileSort mt = new FileSort();
    private int ascending;
    private int sortType;

    public void dispose() {
    }

    private FileSort() {
    }

    public static FileSort instance() {
        return mt;
    }

    public int compare(FileItem fileItem, FileItem fileItem2) {
        int i = this.sortType;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return 0;
                    }
                    if (this.ascending == 0) {
                        return comparaByNameUp(fileItem, fileItem2);
                    }
                    return comparaByNameDown(fileItem, fileItem2);
                } else if (this.ascending == 0) {
                    return compareBySizeUp(fileItem, fileItem2);
                } else {
                    return compareBySizeDown(fileItem, fileItem2);
                }
            } else if (this.ascending == 0) {
                return compareByModifiedDateUp(fileItem, fileItem2);
            } else {
                return compareByModifiedDateDown(fileItem, fileItem2);
            }
        } else if (this.ascending == 0) {
            return compareByDirUp(fileItem, fileItem2);
        } else {
            return compareByDirDown(fileItem, fileItem2);
        }
    }

    private int comparaByNameDown(FileItem fileItem, FileItem fileItem2) {
        File file = fileItem.getFile();
        File file2 = fileItem2.getFile();
        if (file.isDirectory() && file2.isFile()) {
            return 1;
        }
        if (file.isFile() && file2.isDirectory()) {
            return -1;
        }
        String fileName = fileItem.getFileName();
        String fileName2 = fileItem2.getFileName();
        if (fileName.compareToIgnoreCase(fileName2) < 0) {
            return 1;
        }
        if (fileName.compareToIgnoreCase(fileName2) > 0) {
            return -1;
        }
        return 0;
    }

    private int comparaByNameUp(FileItem fileItem, FileItem fileItem2) {
        File file = fileItem.getFile();
        File file2 = fileItem2.getFile();
        if (file.isDirectory() && file2.isFile()) {
            return -1;
        }
        if (file.isFile() && file2.isDirectory()) {
            return 1;
        }
        String fileName = fileItem.getFileName();
        String fileName2 = fileItem2.getFileName();
        if (fileName.compareToIgnoreCase(fileName2) > 0) {
            return 1;
        }
        if (fileName.compareToIgnoreCase(fileName2) < 0) {
            return -1;
        }
        return 0;
    }

    private int compareByModifiedDateDown(FileItem fileItem, FileItem fileItem2) {
        File file = fileItem.getFile();
        File file2 = fileItem2.getFile();
        if (file.isDirectory() && file2.isFile()) {
            return 1;
        }
        if (file.isFile() && file2.isDirectory()) {
            return -1;
        }
        int i = (fileItem.getFile().lastModified() > fileItem2.getFile().lastModified() ? 1 : (fileItem.getFile().lastModified() == fileItem2.getFile().lastModified() ? 0 : -1));
        if (i == 0) {
            return comparaByNameDown(fileItem, fileItem2);
        }
        if (i < 0) {
            return 1;
        }
        return -1;
    }

    private int compareByModifiedDateUp(FileItem fileItem, FileItem fileItem2) {
        File file = fileItem.getFile();
        File file2 = fileItem2.getFile();
        if (file.isDirectory() && file2.isFile()) {
            return -1;
        }
        if (file.isFile() && file2.isDirectory()) {
            return 1;
        }
        int i = (fileItem.getFile().lastModified() > fileItem2.getFile().lastModified() ? 1 : (fileItem.getFile().lastModified() == fileItem2.getFile().lastModified() ? 0 : -1));
        if (i == 0) {
            return comparaByNameUp(fileItem, fileItem2);
        }
        if (i > 0) {
            return 1;
        }
        return -1;
    }

    private int compareBySizeDown(FileItem fileItem, FileItem fileItem2) {
        File file = fileItem.getFile();
        File file2 = fileItem2.getFile();
        if (file.isDirectory() && file2.isFile()) {
            return 1;
        }
        if (file.isDirectory() && file2.isDirectory()) {
            return comparaByNameDown(fileItem, fileItem2);
        }
        if (file.isFile() && file2.isDirectory()) {
            return -1;
        }
        int i = (file.length() > file2.length() ? 1 : (file.length() == file2.length() ? 0 : -1));
        if (i == 0) {
            return comparaByNameDown(fileItem, fileItem2);
        }
        if (i < 0) {
            return 1;
        }
        return -1;
    }

    private int compareBySizeUp(FileItem fileItem, FileItem fileItem2) {
        File file = fileItem.getFile();
        File file2 = fileItem2.getFile();
        if (file.isDirectory() && file2.isFile()) {
            return -1;
        }
        if (file.isDirectory() && file2.isDirectory()) {
            return comparaByNameUp(fileItem, fileItem2);
        }
        if (file.isFile() && file2.isDirectory()) {
            return 1;
        }
        int i = (file.length() > file2.length() ? 1 : (file.length() == file2.length() ? 0 : -1));
        if (i == 0) {
            return comparaByNameUp(fileItem, fileItem2);
        }
        if (i > 0) {
            return 1;
        }
        return -1;
    }

    private int compareByDirDown(FileItem fileItem, FileItem fileItem2) {
        File file = fileItem.getFile();
        File file2 = fileItem2.getFile();
        if (file.isDirectory() && file2.isFile()) {
            return 1;
        }
        if (file.isDirectory() && file2.isDirectory()) {
            return comparaByNameDown(fileItem, fileItem2);
        }
        if (file.isFile() && file2.isDirectory()) {
            return -1;
        }
        int fileType = getFileType(fileItem.getFileName());
        int fileType2 = getFileType(fileItem2.getFileName());
        if (fileType == fileType2) {
            return comparaByNameDown(fileItem, fileItem2);
        }
        if (fileType < fileType2) {
            return 1;
        }
        return -1;
    }

    private int compareByDirUp(FileItem fileItem, FileItem fileItem2) {
        File file = fileItem.getFile();
        File file2 = fileItem2.getFile();
        if (file.isDirectory() && file2.isFile()) {
            return -1;
        }
        if (file.isDirectory() && file2.isDirectory()) {
            return comparaByNameUp(fileItem, fileItem2);
        }
        if (file.isFile() && file2.isDirectory()) {
            return 1;
        }
        int fileType = getFileType(fileItem.getFileName());
        int fileType2 = getFileType(fileItem2.getFileName());
        if (fileType == fileType2) {
            return comparaByNameUp(fileItem, fileItem2);
        }
        if (fileType > fileType2) {
            return 1;
        }
        return -1;
    }

    public int getFileType(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOT)) {
            return 0;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
            return 1;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLT)) {
            return 2;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
            return 3;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT)) {
            return 4;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
            return 5;
        }
        return lowerCase.endsWith("pdf") ? 7 : 6;
    }

    public void setType(int i, int i2) {
        this.sortType = i;
        this.ascending = i2;
    }
}
